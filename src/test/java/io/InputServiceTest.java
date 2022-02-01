package io;

import io.InputService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class InputServiceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final Map<String, Double> currencies = new HashMap<>();
    private final String correctCurrency = "PLN";
    private final String wrongCurrency = "PLCF";
    private final String wrongCurrencyWithNumber = "P2C";
    private InputService inputService;

    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));
        currencies.put("PLN", 4.5892);
        inputService = new InputService();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void resultShouldBeCorrectForPln() {
        // given;
        double amount = 500;
        double target = currencies.get(correctCurrency);
        // when
        double result = target * amount;
        // then
        assertEquals(2294.6, result, 0.0);
    }

    @Test
    public void shouldThrowIOExceptionIfWrongCurrencyCode() {
        // when
        Exception exception = assertThrows(IOException.class, () -> inputService.checkCurrency(wrongCurrency));
        String expectedMessage = "Wrong currency code";
        String actualMessage = exception.getMessage();
        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowIOExceptionIfWrongCurrencyCodeWithNumber() {
        // when
        Exception exception = assertThrows(IOException.class, () -> inputService.checkCurrency(wrongCurrencyWithNumber));
        String expectedMessage = "Wrong currency code";
        String actualMessage = exception.getMessage();
        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowIOExceptionIfAmountIsLessOrEqualThan0() {
        // given
        double amount = 0;
        // when
        Exception exception = assertThrows(IOException.class, () -> inputService.checkAmount(amount));
        String expectedMessage = "Wrong amount";
        String actualMessage = exception.getMessage();
        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowIOExceptionIfCurrencyIsNotAvailable() {
        // when
        Exception exception = assertThrows(IOException.class,
                () -> inputService.checkCurrencyIfAvailable(wrongCurrency, currencies));
        String expectedMessage = "Wrong currency code";
        String actualMessage = exception.getMessage();
        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldReturnCorrectString() {
        double amount = 500;
        String expectedString = "500.00 EUR is equal to 2294.60 PLN";
        // when

        String availableString = inputService.calculate(amount, correctCurrency, currencies);
        // then
        Assert.assertEquals(availableString, expectedString);
    }

    //    protected String calculate(double amount, String currency, Map<String, Double> currencies) {
    //        double targetAmount = currencies.get(currency);
    //        double result = targetAmount * amount;
    //        return String.format("%.2f %s is equal to %.2f %s", amount, "EUR", result, currency);
    //    }
    @Test
    public void printLine() {
        // given
        String messageLine = "this is message.";
        // when
        inputService.printLine(messageLine);
        // then
        assertEquals(messageLine, outContent.toString().trim());

    }
}