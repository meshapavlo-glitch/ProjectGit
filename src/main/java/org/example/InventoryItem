package org.example;

/**
 * Допоміжний клас-обгортка для зберігання товару та його кількості.
 */
public class InventoryItem {
    private Clothes clothes;
    private int quantity;

    public InventoryItem(Clothes clothes, int quantity) {
        this.clothes = clothes;
        this.quantity = quantity;
    }

    // Геттери для доступу до даних
    public Clothes getClothes() {
        return clothes;
    }

    public int getQuantity() {
        return quantity;
    }

    // Метод для збільшення кількості, якщо такий товар уже є в магазині
    public void addQuantity(int count) {
        this.quantity += count;
    }

    @Override
    public String toString() {
        // Виводимо опис одягу та додаємо інформацію про кількість
        return clothes.toString() + " | В НАЯВНОСТІ: " + quantity + " шт.";
    }
}
