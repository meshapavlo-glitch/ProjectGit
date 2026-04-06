package org.example;

import java.util.Objects;
import java.util.UUID;

public abstract class Clothes implements Comparable<Clothes>, Identifiable {
    protected UUID uuid;
    protected String type;
    protected String size;
    protected double price;
    protected Material material;

    public Clothes(String type, String size, double price, Material material) {
        // Автоматична генерація UUID при створенні об'єкта
        this.uuid = UUID.randomUUID();

        this.type = type;
        this.size = size;
        this.price = price;
        this.material = material;
    }
    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не може бути порожнім");
        }
    }

    private void validatePrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна повинна бути більшою за 0");
        }
    }

    // Реалізація методу з інтерфейсу Identifiable
    @Override
    public UUID getUuid() {
        return uuid;
    }

    // Реалізація Comparable: порівняння за ціною
    @Override
    public int compareTo(Clothes other) {
        return Double.compare(this.price, other.price);
    }

    public String getType() { return type; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public Material getMaterial() { return material; }

    @Override
    public String toString() {
        String shortId = uuid.toString().substring(0, 8);
        return String.format("[%s...] %s [Розмір: %s, Матеріал: %s, Ціна: %.2f]",
                shortId, type, size, material.getTitle(), price);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
                Objects.equals(type, clothes.type) &&
                Objects.equals(size, clothes.size) &&
                material == clothes.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, size, price, material);
    }
}
