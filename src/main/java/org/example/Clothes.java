package org.example;

import java.util.Objects;

/**
 * Клас, що описує предмет одягу.
 * Містить інформацію про тип, розмір, ціну та матеріал.
 */
public class Clothes {
    private String type;
    private String size;
    private double price;
    private Material material;

       /**
     * Конструктор з параметрами для створення нового об'єкта.
     *
     * @param type     тип одягу (напр. "Футболка")
     * @param size     розмір (напр. "L")
     * @param price    ціна (має бути більше 0)
     * @param material матеріал з перерахування Material
     * @throws IllegalArgumentException якщо вхідні дані некоректні
     */
    public Clothes(String type, String size, double price, Material material) {
        validateString(type, "Тип");
        validateString(size, "Розмір");
        validatePrice(price);
        validateObject(material, "Матеріал");

        this.type = type;
        this.size = size;
        this.price = price;
        this.material = material;

    }

    /**
     * Конструктор копіювання.
     * Створює новий об'єкт на основі існуючого.
     *
     * @param other об'єкт, який потрібно скопіювати
     * @throws IllegalArgumentException якщо об'єкт для копіювання є null
     */
    public Clothes(Clothes other) {
        if (other == null) {
            throw new IllegalArgumentException("Об'єкт для копіювання не може бути null");
        }
        this.type = other.type;
        this.size = other.size;
        this.price = other.price;
        this.material = other.material;

    }

   // --- Геттери та Сеттери ---

    public String getType() {
        return type;
    }

    public void setType(String type) {
        validateString(type, "Тип");
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        validateString(size, "Розмір");
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        validatePrice(price);
        this.price = price;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        validateObject(material, "Матеріал");
        this.material = material;
    }

    // --- Методи валідації ---

    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не може бути порожнім");
        }
    }

    private void validatePrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна повинна бути більшою за нуль");
        }
    }

    private void validateObject(Object obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException(fieldName + " не може бути null");
        }
    }

    // --- Перевизначені методи ---

    @Override
    public String toString() {
        return String.format("Одяг [Тип: %s, Розмір: %s, Матеріал: %s, Ціна: %.2f]",
                type, size, material.getTitle(), price);
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
