package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

    public class Logger
    {
        private final String LOG_DIRECTORY_PATH = "files";
        private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;
        private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("kk:mm:ss");

        private String fileName;
        private String filePath;
        private ArrayList<LogEntry> logEntries;

        public Logger(String fileName)
        {
            File directory = new File(LOG_DIRECTORY_PATH);
            if(!directory.exists())
            {
                directory.mkdir();
            }

            this.fileName = fileName;
            this.filePath = LOG_DIRECTORY_PATH + "/" + fileName;
            if(!this.filePath.toLowerCase().endsWith(".csv"))
            {
                this.filePath += ".csv";
            }
            this.logEntries = readEntries();
        }

        public void logMessage(String description, String vendor, double amount, String cardNumber, int expirationMonth, int expirationYear, int ccv  )
        {

            File logFile = new File(filePath);
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            try(FileWriter fileWriter = new FileWriter(logFile, true);
                PrintWriter writer = new PrintWriter(fileWriter)
            )
            {   if(logFile.length()==0){
                writer.write("Date|Time|Description|Vendor|Amount|Card Number|Expiration Month|Expiration Year| CCV\n");
            }
                writer.write(String.format("%s | %s | %s | %s | %.2f | %s | %d | %d | %d%n",
                        date.format(DATE_FORMAT), time.format(TIME_FORMAT), description, vendor, amount, cardNumber, expirationMonth, expirationYear, ccv));
            }
            catch (IOException ex)
            {
                System.out.println("Sorry, data was not saved.");
            }


        }

        public ArrayList<LogEntry> readEntries() {
            ArrayList<LogEntry> logEntries = new ArrayList<>();

            File logFile = new File("files/transactions.csv");

            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] parts = line.split("\\s\\|\\s");
                    LocalDate date = LocalDate.parse(parts[0], DATE_FORMAT);
                    LocalTime time = LocalTime.parse(parts[1], TIME_FORMAT);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    String cardNumber = parts[5];
                    int expirationMonth = Integer.parseInt(parts[6]);
                    int expirationYear = Integer.parseInt(parts[7]);
                    int ccv = Integer.parseInt(parts[8]);

                    LogEntry logEntry = new LogEntry(date, time, description, vendor, amount, cardNumber,
                            expirationMonth, expirationYear, ccv);

                    logEntries.add(logEntry);
                }
            } catch (IOException ex) {
            }
            return logEntries;
        }

    }


