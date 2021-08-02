package bg.sofia.uni.fmi.mjt.foodanalyzer.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class FoodDescription {
    private String description;
    @SerializedName("fdcId")
    private String foodId;
    @SerializedName("gtinUpc")
    private String barcode;
    private String ingredients;

    public FoodDescription(String description, String foodId, String barcode, String ingredients) {
        this.description = description;
        this.foodId = foodId;
        this.barcode = barcode;
        this.ingredients = ingredients;
    }

    public String getBarcode() {
        return barcode;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (description != null && !description.equals("")) {
            description = description.replace(System.lineSeparator(), "");
            result.append("description: '").append(description).append("', ").append(System.lineSeparator());
        }
        if (foodId != null && !foodId.equals("")) {
            foodId = foodId.replace(System.lineSeparator(), "");
            result.append("food ID: '").append(foodId).append("', ").append(System.lineSeparator());
        }
        if (barcode != null && !barcode.equals("")) {
            barcode = barcode.replace(System.lineSeparator(), "");
            result.append("barcode: '").append(barcode).append("', ").append(System.lineSeparator());
        }
        if (ingredients != null && !ingredients.equals("")) {
            ingredients = ingredients.replace(System.lineSeparator(), "");
            result.append("ingredients: '").append(ingredients).append("';").append(System.lineSeparator());
        }
        return new String(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodDescription that = (FoodDescription) o;
        return foodId.equals(that.foodId) &&
                barcode.equals(that.barcode) &&
                Objects.equals(description, that.description) &&
                Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, foodId, barcode, ingredients);
    }
}
