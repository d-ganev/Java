package bg.sofia.uni.fmi.mjt.foodanalyzer.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class FoodIdData extends FoodData {
    @SerializedName("description")
    private final String name;
    private final LabelNutrients labelNutrients;

    public FoodIdData(FoodDescription[] foods, String name, LabelNutrients labelNutrients) {
        super(foods);
        this.name = name;
        this.labelNutrients = labelNutrients;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("food name: '").append(name.toLowerCase()).append("'; ")
                .append(System.lineSeparator()).append("nutrients: '").append(labelNutrients).append("';");
        if (getFoods() != null) {
            result.append(super.toString());
        }
        return new String(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodIdData that = (FoodIdData) o;
        return Objects.equals(labelNutrients, that.labelNutrients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labelNutrients);
    }
}