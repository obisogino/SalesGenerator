package com.obisogino;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SalesGenerator {

    private static final String PRICE_LIST_FILE = "src/main/resources/pricelist.csv"; // Adjust path if needed
    private static final Map<String, Integer> PRICE_LIST = loadPriceList();
    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);

        // Get starting date
        System.out.print("Enter starting date (dd-MM-yyyy): ");
        String startDateInput = scanner.nextLine();
        LocalDate startDate = LocalDate.parse(startDateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // Get starting OR number
        System.out.print("Enter starting OR number: ");
        int startORNumber = scanner.nextInt();

        // Generate sales
        List<Sale> sales = generateSales(startDate, startORNumber);

        // Create Excel file
        String filename = createExcelFile(sales, startDate);
        System.out.println("Sales data has been generated in " + filename);

        scanner.close();
    }

    private static Map<String, Integer> loadPriceList() {
        Map<String, Integer> priceList = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRICE_LIST_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String itemName = parts[0].trim();
                    Integer price = Integer.parseInt(parts[1].trim());
                    priceList.put(itemName, price); // Add item and price to map
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return priceList;
    }

    private static List<Sale> generateSales(LocalDate startDate, int startORNumber) {
        List<Sale> sales = new ArrayList<>();
        LocalDate endDate = startDate.plusMonths(3); // Limit to 3 months

        LocalDate date = startDate;

        while (date.isBefore(endDate)) {
            // Ensure at least 1 or 2 unique OR numbers
            int dailySalesCount = RANDOM.nextInt(2) + 1; // Randomly 1 or 2 sales for the day
            Set<Integer> orNumbers = new HashSet<>();

            // Generate unique OR numbers
            while (orNumbers.size() < dailySalesCount) {
                orNumbers.add(startORNumber++);
            }

            // Create sales for each unique OR number
            for (Integer orNumber : orNumbers) {
                // Determine number of items (1 to 3)
                int itemsCount = RANDOM.nextInt(5) + 1; // 1 to 3 items
                List<String> items = new ArrayList<>(PRICE_LIST.keySet());
                List<String> selectedItems = new ArrayList<>();

                // Select unique items for the current OR
                Set<String> selectedItemSet = new HashSet<>();
                while (selectedItemSet.size() < itemsCount) {
                    String item = items.get(RANDOM.nextInt(items.size()));
                    selectedItemSet.add(item);
                }
                selectedItems.addAll(selectedItemSet);

                // Create sales entries for each selected item
                for (String item : selectedItems) {
                    int price = PRICE_LIST.get(item); // Get the price from the HashMap
                    sales.add(new Sale(date, orNumber, item, price));
                }
            }

            // Move to the next day
            date = date.plusDays(1);
        }

        return sales;
    }

    private static String createExcelFile(List<Sale> sales, LocalDate startDate) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Date");
        headerRow.createCell(1).setCellValue("OR Number");
        headerRow.createCell(2).setCellValue("Particulars");
        headerRow.createCell(3).setCellValue("Price");

        // Populate data rows
        int rowNum = 1;
        for (Sale sale : sales) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sale.getDate().toString());
            row.createCell(1).setCellValue(sale.getOrNumber());
            row.createCell(2).setCellValue(sale.getParticulars());
            row.createCell(3).setCellValue(sale.getPrice());
        }

        // Create filename based on months covered
        String filename = createFilename(startDate);
        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
            workbook.write(fileOut);
        }

        workbook.close();
        return filename;
    }

    private static String createFilename(LocalDate startDate) {
        StringBuilder filename = new StringBuilder();
        LocalDate endDate = startDate.plusMonths(3);

        // Loop through the months
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(1)) {
            filename.append(date.getMonth().toString().substring(0, 1).toUpperCase())
                    .append(date.getMonth().toString().substring(1).toLowerCase());
        }
        filename.append(".xlsx");
        return filename.toString();
    }

    static class Sale {
        private final LocalDate date;
        private final int orNumber;
        private final String particulars;
        private final int price;

        public Sale(LocalDate date, int orNumber, String particulars, int price) {
            this.date = date;
            this.orNumber = orNumber;
            this.particulars = particulars;
            this.price = price;
        }

        public LocalDate getDate() {
            return date;
        }

        public int getOrNumber() {
            return orNumber;
        }

        public String getParticulars() {
            return particulars;
        }

        public int getPrice() {
            return price;
        }
    }
}
