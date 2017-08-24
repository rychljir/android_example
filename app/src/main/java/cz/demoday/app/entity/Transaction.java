package cz.demoday.app.entity;

/**
 * Created by Speedy on 22. 8. 2017.
 */


/*
    Model class representing Transaction entity
 */
public class Transaction {
    private String name;
    private String type;
    private Float amount;
    private String date;

    public Transaction(String name, String type, Float amount, String date) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
