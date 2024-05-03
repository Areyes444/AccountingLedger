package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class LogEntry
{
    private String description;
    private String vendor;
    private double amount;
    private LocalDate date;
    private LocalTime time;
    private String cardNumber;
    private int expirationMonth;
    private int expirationYear;
    private int ccv;

    public LogEntry(LocalDate date, LocalTime time, String description, String vendor, double amount, String cardNumber, int expirationMonth, int expirationYear, int ccv)
    {
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.ccv = ccv;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public int getExpirationMonth()
    {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth)
    {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear()
    {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear)
    {
        this.expirationYear = expirationYear;
    }

    public int getCcv()
    {
        return ccv;
    }

    public void setCcv(int ccv)
    {
        this.ccv = ccv;
    }

    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getVendor()
    {
        return vendor;
    }

    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}
