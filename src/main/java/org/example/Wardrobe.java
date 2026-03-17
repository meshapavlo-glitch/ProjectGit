package org.example;

/**
 * Клас, що представляє гардероб.
 * Демонструє принцип агрегації, містячи масив об'єктів типу Clothes.
 */
public class Wardrobe {
    private String owner;
    private Clothes[] clothesArray;
    private int currentSize;

    /**
     * Конструктор для створення гардеробу.
     * @param owner власник гардеробу
     * @param capacity максимальна кількість місць для одягу
     */
    public Wardrobe(String owner, int capacity) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Власник не може бути порожнім");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Місткість повинна бути більшою за 0");
        }
        this.owner = owner;
        this.clothesArray = new Clothes[capacity];
        this.currentSize = 0;
    }

    /**
     * Додає предмет одягу до гардеробу.
     * @param item об'єкт класу Clothes (агрегація)
     */
    public void addClothes(Clothes item) {
        if (item == null) {
            throw new IllegalArgumentException("Не можна додати порожній об'єкт (null)");
        }
        if (currentSize < clothesArray.length) {
            clothesArray[currentSize] = item;
            currentSize++;
        } else {
            System.out.println("Гардероб заповнений! Неможливо додати: " + item.getType());
        }
    }

    /**
     * Виводить вміст гардеробу на екран.
     */
    public void displayWardrobe() {
        System.out.println("Гардероб користувача: " + owner);
        System.out.println("Кількість речей: " + currentSize + "/" + clothesArray.length);
        if (currentSize == 0) {
            System.out.println("Порожньо.");
        } else {
            for (int i = 0; i < currentSize; i++) {
                System.out.println(" - " + clothesArray[i].toString());
            }
        }
    }

    public String getOwner() {
        return owner;
    }
}
