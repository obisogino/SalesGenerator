package com.obisogino;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
//    public static void main(String[] args) {
//        try {
//            Scanner scanner = new Scanner(System.in);
//
//            // Get starting date
//            System.out.print("Enter starting date (dd-MM-yyyy): ");
//            String startDateInput = scanner.nextLine();
//            LocalDate startDate = LocalDate.parse(startDateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//
//            // Get starting OR number
//            System.out.print("Enter starting OR number: ");
//            int startORNumber = scanner.nextInt();
//
//            // Generate sales
//            List<SalesGenerator.Sale> sales = SalesGenerator.generateSales(startDate, startORNumber);
//
//            // Create Excel file
//            String filename = SalesGenerator.createExcelFile(sales, startDate);
//            System.out.println("Sales data has been generated in " + filename);
//
//            scanner.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}