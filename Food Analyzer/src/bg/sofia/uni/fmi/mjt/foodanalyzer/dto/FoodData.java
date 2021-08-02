package bg.sofia.uni.fmi.mjt.foodanalyzer.dto;

import java.util.Arrays;

public abstract class FoodData {
    private final FoodDescription[] foods;

    public FoodData(FoodDescription[] foods) {
        this.foods = foods;
    }

    public FoodDescription[] getFoods() {
        return foods;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (FoodDescription currDescription : foods) {
            result.append(currDescription.toString());
        }
        return new String(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodData foodData = (FoodData) o;
        return Arrays.equals(foods, foodData.foods);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(foods);
    }
}
