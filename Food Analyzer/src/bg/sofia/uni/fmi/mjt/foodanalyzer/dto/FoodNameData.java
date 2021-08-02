package bg.sofia.uni.fmi.mjt.foodanalyzer.dto;

import java.util.Objects;

public class FoodNameData extends FoodData {
    private String foodName;
    private final String totalHits;

    public FoodNameData(FoodDescription[] foods, String totalHits) {
        super(foods);
        this.totalHits = totalHits;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getTotalHits() {
        return totalHits;
    }

    @Override
    public String toString() {
        return "food name: '" + foodName + "';" + System.lineSeparator() + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FoodNameData that = (FoodNameData) o;
        return Objects.equals(foodName, that.foodName) &&
                Objects.equals(totalHits, that.totalHits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), foodName, totalHits);
    }
}


