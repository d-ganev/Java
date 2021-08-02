package bg.sofia.uni.fmi.mjt.foodanalyzer;


import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Calories;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Calcium;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Carbohydrates;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Cholesterol;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Fat;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Fiber;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodData;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodDescription;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodIdData;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodNameData;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Iron;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.LabelNutrients;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Protein;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Sodium;
import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.Sugars;
import bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions.FoodAnalyzerException;
import bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions.FoodNotFoundException;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodAnalyzerHttpClientTest {

    private static FoodData foodDataRequestedByName;
    private static String foodDataRequestedByNameJson;
    private static FoodData foodDataRequestedByFoodId;
    private static String foodDataRequestedByFoodIdJson;

    @Mock
    private HttpClient foodDataHttpClientMock;

    @Mock
    private HttpResponse<String> httpFoodDataResponseMock;

    private FoodAnalyzerHttpClient client;

    @BeforeClass
    public static void setUpClass() {
        FoodDescription[] foods = new FoodDescription[1];
        String description = "RAFFAELLO, ALMOND COCONUT TREAT";
        String foodId = "1515707";
        String barcode = "009800146130";
        String ingredients = "VEGETABLE OILS (PALM AND SHEANUT). " +
                "DRY COCONUT, SUGAR, ALMONDS, SKIM MILK POWDER, WHEY POWDER (MILK)," +
                " WHEAT FLOUR, NATURAL AND ARTIFICIAL FLAVORS, LECITHIN AS EMULSIFIER (SOY)," +
                " SALT, SODIUM BICARBONATE AS LEAVENING AGENT.";
        foods[0] = new FoodDescription(description, foodId, barcode, ingredients);
        String totalHits = "1";
        foodDataRequestedByName = new FoodNameData(foods, totalHits);
        ((FoodNameData) foodDataRequestedByName).setFoodName("raffaello treat");
        foodDataRequestedByNameJson = new Gson().toJson(foodDataRequestedByName);

        String name = "RAFFAELLO, ALMOND COCONUT TREAT";
        Calories cal = new Calories(189.9000000000000000);
        Protein pr = new Protein(2.0010000000000000);
        Fat fat = new Fat(15.0000000000000000);
        Carbohydrates carb = new Carbohydrates(12.0000000000000000);
        Fiber fib = new Fiber(0.9900000000000000);
        Sodium so = new Sodium(35.1000000000000000);
        Cholesterol cho = new Cholesterol(5.1000000000000000);
        Iron iron = new Iron(0.3600000000000000);
        Sugars sug = new Sugars(9.9990000000000000);
        Calcium calc = new Calcium(39.9000000000000000);
        LabelNutrients nutrients = new LabelNutrients(cal, pr, fat, carb, fib, so, cho, iron, sug, calc);
        foodDataRequestedByFoodId = new FoodIdData(null, name, nutrients);
        foodDataRequestedByFoodIdJson = new Gson().toJson(foodDataRequestedByFoodId);
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
        when(foodDataHttpClientMock.send(Mockito.any(HttpRequest.class), ArgumentMatchers.<BodyHandler<String>>any()))
                .thenReturn(httpFoodDataResponseMock);

        client = new FoodAnalyzerHttpClient(foodDataHttpClientMock);
    }

    @Test(expected = FoodNotFoundException.class)
    public void testGetFoodNonExistentFood() {
        when(httpFoodDataResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);
        client.getFoodData("get-food-report fakeID");
    }

    @Test
    public void testGetFoodDataValidNameRequest() {
        when(httpFoodDataResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(httpFoodDataResponseMock.body()).thenReturn(foodDataRequestedByNameJson);

        var result = client.getFoodData("get-food raffaello treat");

        assertEquals(foodDataRequestedByName, result);
    }

    @Test
    public void testGetFoodDataValidFoodIdRequest() {
        when(httpFoodDataResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(httpFoodDataResponseMock.body()).thenReturn(foodDataRequestedByFoodIdJson);

        var result = client.getFoodData("get-food-report raffaello treat");

        assertEquals(foodDataRequestedByFoodId, result);
    }

    @Test
    public void testGetFoodDataServerError() {
        when(httpFoodDataResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_UNAVAILABLE);

        try {
            client.getFoodData("get-food raffaello treat");
        } catch (Exception e) {
            assertEquals(FoodAnalyzerException.class, e.getClass());
            assertNotEquals(FoodNotFoundException.class, e.getClass());
        }
    }

    @Test
    public void testGetFoodHttpClientIOExceptionIsWrapped() throws Exception {
        IOException expectedExc = new IOException();
        when(foodDataHttpClientMock.send(Mockito.any(HttpRequest.class), ArgumentMatchers.<BodyHandler<String>>any()))
                .thenThrow(expectedExc);

        try {
            client.getFoodData("get-food raffaello treat");
        } catch (Exception actualExc) {
            assertEquals(expectedExc, actualExc.getCause());
        }
    }

}
