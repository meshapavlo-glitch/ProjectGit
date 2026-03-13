package org.example;

import java.util.Objects;

/**
 * Клас, що представляє об'єкт одягу.
 * Містить валідацію параметрів та документацію JavaDoc.
 */
public class Clothes {
    private String type;
    private String size;
    private double price;
    private String material;


    public Clothes(String type, String size, double price, String material) {
        validateString(type, "Тип");
        validateString(size, "Розмір");
        validateString(material, "Матеріал");
        validatePrice(price);

        this.type = type;
        this.size = size;
        this.price = price;
        this.material = material;
    }

    // Геттери та Сеттери з валідацією

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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        validateString(material, "Матеріал");
        this.material = material;
    }

    /**
     * Допоміжний метод для перевірки рядків.
     */
    private void validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " не може бути порожнім");
        }
    }

    /**
     * Допоміжний метод для перевірки ціни.
     */
    private void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною");
        }
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", material='" + material + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
                Objects.equals(type, clothes.type) &&
                Objects.equals(size, clothes.size) &&
                Objects.equals(material, clothes.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, size, price, material);
    }
}
