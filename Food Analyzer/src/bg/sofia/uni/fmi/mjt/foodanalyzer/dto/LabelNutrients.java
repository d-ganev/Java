package bg.sofia.uni.fmi.mjt.foodanalyzer.dto;

import java.util.Objects;

public class LabelNutrients {
    private final Calories calories;
    private final Protein protein;
    private final Fat fat;
    private final Carbohydrates carbohydrates;
    private final Fiber fiber;
    private final Sodium sodium;
    private final Cholesterol cholesterol;
    private final Iron iron;
    private final Sugars sugars;
    private final Calcium calcium;

    public LabelNutrients(Calories cal, Protein pr, Fat fat, Carbohydrates carb, Fiber fib,
                          Sodium so, Cholesterol cho, Iron iron, Sugars sug, Calcium calc) {
        calories = cal;
        protein = pr;
        this.fat = fat;
        carbohydrates = carb;
        fiber = fib;
        sodium = so;
        cholesterol = cho;
        this.iron = iron;
        sugars = sug;
        calcium = calc;
    }

    @Override
    public String toString() {
        return "calories: " + calories +
                ", protein: " + protein +
                ", fat: " + fat +
                ", carbohydrates: " + carbohydrates +
                ", fiber: " + fiber +
                ", sodium: " + sodium +
                ", cholesterol: " + cholesterol +
                ", iron: " + iron +
                ", sugars: " + sugars +
                ", calcium: " + calcium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelNutrients that = (LabelNutrients) o;
        return Objects.equals(calories, that.calories) &&
                Objects.equals(protein, that.protein) &&
                Objects.equals(fat, that.fat) &&
                Objects.equals(carbohydrates, that.carbohydrates) &&
                Objects.equals(fiber, that.fiber) &&
                Objects.equals(sodium, that.sodium) &&
                Objects.equals(cholesterol, that.cholesterol) &&
                Objects.equals(iron, that.iron) &&
                Objects.equals(sugars, that.sugars) &&
                Objects.equals(calcium, that.calcium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calories, protein, fat, carbohydrates, fiber, sodium, cholesterol, iron, sugars, calcium);
    }
}
