package org.example;

import java.util.Objects;

public class Clothes {
    private String type;
    private String size;
    private double price;

    public Clothes(String type, String size, double price) {
        this.type = type;
        this.size = size;
        this.price = price;
    }

    // Геттери та Сеттери (можна згенерувати через Alt+Insert)
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Clothes{type='" + type + "', size='" + size + "', price=" + price + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
                Objects.equals(type, clothes.type) &&
                Objects.equals(size, clothes.size);
    }
}