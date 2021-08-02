package bg.sofia.uni.fmi.mjt.foodanalyzer.dto;

import java.util.Objects;

public class Fat implements Nutrient{
    private final double value;

    public Fat(double value){
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
        Fat fat = (Fat) o;
        return Double.compare(fat.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
