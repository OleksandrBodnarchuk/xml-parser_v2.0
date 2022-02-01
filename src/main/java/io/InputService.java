package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class InputService {

    // Gets data from user
    public void getDataFromUser(Map<String, Double> currencies) {
        boolean flag = true;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            while (flag) { // loop in case errors
                printLine("Target currency (example: USD|PLN|IDR etc.): ");
                String currency = reader.readLine().toUpperCase(Locale.ROOT).trim();
                checkCurrency(currency); // checks user input
                checkCurrencyIfAvailable(currency, currencies);
                printLine("Enter amount of EUR: ");
                double amount = Double.parseDouble(reader.readLine().trim());
                checkAmount(amount); // get and check amount
                // RESULT
                printLine(calculate(amount, currency, currencies));
                flag = false;
            }
        } catch (NumberFormatException | IOException e) {
            System.err.println("Wrong input data!");
            getDataFromUser(currencies);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void checkCurrencyIfAvailable(String currency, Map<String, Double> currencies) throws IOException {
        if (!currencies.containsKey(currency)) {
            throw new IOException("Wrong currency code");
        }
    }

    // final calculations
    protected String calculate(double amount, String currency, Map<String, Double> currencies) {
        double targetAmount = currencies.get(currency);
        double result = targetAmount * amount;
        return String.format("%.2f %s is equal to %.2f %s", amount, "EUR", result, currency);
    }

    protected void checkAmount(double amount) throws IOException, NumberFormatException {
        if (amount <= 0) { // target amount always > 0
            throw new IOException("Wrong amount");
        }
    }

    protected void checkCurrency(String currency) throws IOException {
        if (!(currency.length() == 3 && Pattern.matches("[a-zA-Z]+", currency))) { // checks user input + with XML
            throw new IOException("Wrong currency code");
        }
    }

    protected void printLine(String line) {
        System.out.println("\n" + line);
    }
}
