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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FoodAnalyzerTest {
    private static FoodData raffaelloTreatDataRequestedByNameOrBarcode;
    private static FoodData raffaelloTreatDataRequestedByFoodId;
    private static FoodAnalyzer ANALYZER;

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
        raffaelloTreatDataRequestedByNameOrBarcode = new FoodNameData(foods, totalHits);
        ((FoodNameData) raffaelloTreatDataRequestedByNameOrBarcode).setFoodName("raffaello treat");

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
        raffaelloTreatDataRequestedByFoodId = new FoodIdData(null, name, nutrients);
    }

    @Before
    public void setUp() {
        ANALYZER = new FoodAnalyzer();
    }

    @Test(expected = FoodNotFoundException.class)
    public void testGetFoodInformationAboutFoodThatIsNotFound() {
        ANALYZER.getFoodInformation("get-food fakeFood");
    }

    @Test(expected = FoodNotFoundException.class)
    public void testGetFoodInformationByBarcodeThatIsNotInDatabase() {
        ANALYZER.getFoodInformation("get-food-by-barcode 009800146130");
    }

    @Test(expected = FoodAnalyzerException.class)
    public void testGetFoodInformationByInvalidInput() {
        ANALYZER.getFoodInformation("get-food-report fakeId");
    }

    @Test
    public void testGetFoodInformationByName() {
        assertEquals(raffaelloTreatDataRequestedByNameOrBarcode,
                ANALYZER.getFoodInformation("get-food raffaello treat"));
    }

    @Test
    public void testGetFoodInformationFromDatabaseByBarcode() {
        ANALYZER.getFoodInformation("get-food raffaello treat"); //loading info. in database
        assertEquals(raffaelloTreatDataRequestedByNameOrBarcode,
                ANALYZER.getFoodInformation("get-food-by-barcode 009800146130"));
    }

    @Test
    public void testGetFoodInformationByFoodId() {
        assertEquals(raffaelloTreatDataRequestedByFoodId,
                ANALYZER.getFoodInformation("get-food-report 415269"));
    }

}
