package com.obisogino;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PriceListReader {

    private static final String FILE_NAME = "src/main/resources/pricelist.csv"; // Adjust path as needed

    public static void main(String[] args) {
        try {
            Map<String, Integer> priceList = loadPriceList();
            System.out.println("Price List: " + priceList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> loadPriceList() throws IOException {
        Map<String, Integer> priceList = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            // Skip the header
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String item = parts[0].trim();
                    int price = Integer.parseInt(parts[1].trim());
                    priceList.put(item, price); // This will ensure unique items
                }
            }
        }
        return priceList;
    }
}
