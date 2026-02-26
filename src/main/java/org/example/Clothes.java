package org.example;

public class Clothes {
    private String type;
    private String size;
    private double price;

    public Clothes(String type, String size, double price) {
        this.type = type;
        this.size = size;
        this.price = price;
    }

    // Гетери та сетери
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}