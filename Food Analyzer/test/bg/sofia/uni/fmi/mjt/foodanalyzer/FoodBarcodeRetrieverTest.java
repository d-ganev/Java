package bg.sofia.uni.fmi.mjt.foodanalyzer;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FoodBarcodeRetrieverTest {
    private static final String FOOD_REQUEST_ONLY_WITH_BARCODE_IMAGE = "get-food-by-barcode barcode.gif";
    private static final String FOOD_REQUEST_ONLY_WITH_BARCODE = "get-food-by-barcode 009800146130";
    private static final String FOOD_REQUEST_WITH_BOTH_IMAGE_AND_BARCODE = "get-food-by-barcode barcode.gif 009800146130";
    private static final String FOOD_REQUEST_WITH_BOTH_BARCODE_AND_IMAGE = "get-food-by-barcode 009800146130 barcode.gif";

    @Test
    public void testGetBarcodeFromFoodRequestWithImage() {
        assertEquals("009800146130", FoodBarcodeRetriever.getBarcodeFromFoodRequest(FOOD_REQUEST_ONLY_WITH_BARCODE_IMAGE));
    }

    @Test
    public void testGetBarcodeFromFoodRequestWithBarcode() {
        assertEquals("009800146130", FoodBarcodeRetriever.getBarcodeFromFoodRequest(FOOD_REQUEST_ONLY_WITH_BARCODE));
    }

    @Test
    public void testGetBarcodeFromFoodRequestWithImageAndBarcode() {
        assertEquals("009800146130", FoodBarcodeRetriever.getBarcodeFromFoodRequest(FOOD_REQUEST_WITH_BOTH_IMAGE_AND_BARCODE));
    }

    @Test
    public void testGetBarcodeFromFoodRequestWithBarcodeAndImage() {
        assertEquals("009800146130", FoodBarcodeRetriever.getBarcodeFromFoodRequest(FOOD_REQUEST_WITH_BOTH_BARCODE_AND_IMAGE));
    }

}
