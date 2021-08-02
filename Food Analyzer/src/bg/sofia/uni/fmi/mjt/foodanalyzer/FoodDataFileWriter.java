package bg.sofia.uni.fmi.mjt.foodanalyzer;

import bg.sofia.uni.fmi.mjt.foodanalyzer.dto.FoodData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FoodDataFileWriter {
    private final FileWriter fileWriter;

    public FoodDataFileWriter() {
        String fileName = "food-file.txt";
        try {
            fileWriter = new FileWriter(new File(fileName));
        } catch (IOException e) {
            throw new IllegalStateException("File creation error!", e);
        }
    }

    public void write(FoodData foodData) {
        try {
            fileWriter.write(foodData.toString());
            fileWriter.write(System.lineSeparator());
            fileWriter.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("File writing error!", e);
        }
    }
}
