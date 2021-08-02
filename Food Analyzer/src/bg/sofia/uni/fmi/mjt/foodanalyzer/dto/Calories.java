package bg.sofia.uni.fmi.mjt.foodanalyzer.dto;

import java.util.Objects;

public class Calories implements Nutrient {
    private final double value;

    public Calories(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calories calories = (Calories) o;
        return Double.compare(calories.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
