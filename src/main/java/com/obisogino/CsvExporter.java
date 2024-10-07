package com.obisogino;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CsvExporter {

    private static final String[] NAMES = {"Alice", "Bob", "Charlie", "Diana", "Edward"};
    private static final String FILE_NAME = "src/main/resources/randomData.csv"; // Change this if needed

//    public static void main(String[] args) {
//        try {
//            generateRandomCsv(10); // Generate CSV with 10 random entries
//            System.out.println("CSV file generated successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void generateRandomCsv(int numberOfRows) throws IOException {
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // Write header
            writer.write("Name,Age,Email");
            writer.newLine();

            for (int i = 0; i < numberOfRows; i++) {
                String name = NAMES[random.nextInt(NAMES.length)];
                int age = random.nextInt(60) + 18; // Random age between 18 and 77
                String email = name.toLowerCase() + i + "@example.com";

                writer.write(String.format("%s,%d,%s", name, age, email));
                writer.newLine();
            }
        }
    }
}
