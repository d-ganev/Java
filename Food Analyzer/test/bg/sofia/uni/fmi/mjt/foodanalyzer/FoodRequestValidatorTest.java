package bg.sofia.uni.fmi.mjt.foodanalyzer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FoodRequestValidatorTest {
    private static final String INVALID_FOOD_REQUEST_WITH_NO_ARGUMENTS = "get-food";
    private static final String INVALID_FOOD_REQUEST_BY_BARCODE_WITH_MORE_ARGUMENTS = "get-food-by-barcode 1 2 3";
    private static final String INVALID_FOOD_REQUEST_WITH_WHITE_SPACES_IN = "get-food-by-barcode   009800146100";
    private static final String INVALID_FOOD_REQUEST_WITH_WHITE_SPACES_BEFORE = "  get-food milk";
    private static final String INVALID_FOOD_REQUEST_WITH_INVALID_COMMAND = "my-command milk";
    private static final String VALID_FOOD_REQUEST_BY_NAME = "get-food milk";
    private static final String VALID_FOOD_REQUEST_BY_ID = "get-food-report 415269";
    private static final String VALID_FOOD_REQUEST_BY_BARCODE = "get-food-by-barcode barcode.gif";

    @Test
    public void testIsRequestValidWithInvalidFoodRequestDueToNoArguments() {
        assertEquals(Boolean.FALSE, FoodRequestValidator.isRequestValid(INVALID_FOOD_REQUEST_WITH_NO_ARGUMENTS));
    }

    @Test
    public void testIsRequestValidWithInvalidBarcodeFoodRequestDueToMoreArguments() {
        assertEquals(Boolean.FALSE, FoodRequestValidator.isRequestValid(INVALID_FOOD_REQUEST_BY_BARCODE_WITH_MORE_ARGUMENTS));
    }

    @Test
    public void testIsRequestValidWithInvalidRequestDueToWhiteSpacesInTheRequest() {
        assertEquals(Boolean.FALSE, FoodRequestValidator.isRequestValid(INVALID_FOOD_REQUEST_WITH_WHITE_SPACES_IN));
    }

    @Test
    public void testIsRequestValidWithInvalidRequestDueToWhiteSpacesBeforeTheRequest() {
        assertEquals(Boolean.FALSE, FoodRequestValidator.isRequestValid(INVALID_FOOD_REQUEST_WITH_WHITE_SPACES_BEFORE));
    }

    @Test
    public void testIsRequestValidWithInvalidRequestDueToInvalidCommand() {
        assertEquals(Boolean.FALSE, FoodRequestValidator.isRequestValid(INVALID_FOOD_REQUEST_WITH_INVALID_COMMAND));
    }

    @Test
    public void testIsRequestValidWithValidFoodRequestByName() {
        assertEquals(Boolean.TRUE, FoodRequestValidator.isRequestValid(VALID_FOOD_REQUEST_BY_NAME));
    }

    @Test
    public void testIsRequestValidWithValidFoodRequestById() {
        assertEquals(Boolean.TRUE, FoodRequestValidator.isRequestValid(VALID_FOOD_REQUEST_BY_ID));
    }

    @Test
    public void testIsRequestValidWithValidFoodRequestByBarcode() {
        assertEquals(Boolean.TRUE, FoodRequestValidator.isRequestValid(VALID_FOOD_REQUEST_BY_BARCODE));
    }

}
