package org.example;

import java.util.Objects;

public class Clothes {
    private String type;
    private String size;
    private double price;

    // Конструктор
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

    // Метод toString для виводу інформації
    @Override
    public String toString() {
        return "Одяг: " + type + ", Розмір: " + size + ", Ціна: " + price + " грн";
    }

    // Метод equals для порівняння об'єктів
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 && Objects.equals(type, clothes.type) && Objects.equals(size, clothes.size);
    }
}