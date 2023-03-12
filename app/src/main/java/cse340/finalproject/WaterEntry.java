package cse340.finalproject;

import java.util.Date;

/**

 The WaterEntry class represents a single water consumption entry with a date, amount, and method.
 */
public class WaterEntry {
    private Date date;
    private int amount;
    private String method;
    /**
     Constructs a new WaterEntry object.
     @param date the date of the entry
     @param amount the amount of water consumed in ounces
     @param method the method of water consumption (e.g. manual or auto)
     */
    public WaterEntry(Date date, int amount, String method) {
        this.date = date;
        this.amount = amount;
        this.method = method;
    }
    /**
     Returns the date of the water consumption entry.
     @return the date of the entry
     */
    public Date getDate() {
        return date;
    }
    /**
     Sets the date of the water consumption entry.
     @param date the date of the entry
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     Returns the amount of water consumed in ounces.
     @return the amount of water consumed
     */
    public int getAmount() {
        return amount;
    }
    /**
     Sets the amount of water consumed in ounces.
     @param amount the amount of water consumed
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
    /**
     Returns the method of water consumption (e.g. manual or auto).
     @return the method of water consumption
     */
    public String getMethod() {
        return method;
    }
    /**
     Sets the method of water consumption (e.g. manual or auto).
     @param method the method of water consumption
     */
    public void setMethod(String method) {
        this.method = method;
    }
}





