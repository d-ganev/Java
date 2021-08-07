package jetbrains.com;

import bg.sofia.uni.fmi.mjt.tagger.Tagger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class Main {

    public static void main(String[] args) throws IOException {
        // creating Tagger instance
        Reader reader = new FileReader("inputWithRealCities.txt");
        Writer writer = new FileWriter("outputFile.txt");
        Tagger tagger = new Tagger(reader);

        // showing functionalities from the tagger
        tagger.tagCities(reader, writer);
        tagger.getAllTaggedCities();
        tagger.getAllTagsCount();
        tagger.getNMostTaggedCities(10);
    }
}
