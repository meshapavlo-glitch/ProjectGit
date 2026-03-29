package org.example;

import java.util.ArrayList;

public class Store {
    private ArrayList<InventoryItem> items = new ArrayList<>();

    // Метод додавання з урахуванням кількості
    public void addNewClothes(Clothes cl, int quantity) {
        for (InventoryItem item : items) {
            // Перевіряємо, чи такий товар уже є (порівнюємо за змістом)
            if (item.getClothes().toString().equals(cl.toString())) {
                item.addQuantity(quantity);
                return;
            }
        }
        // Якщо товару немає, додаємо новий запис
        items.add(new InventoryItem(cl, quantity));
    }

    public ArrayList<InventoryItem> getItems() {
        return items;
    }

    // Методи пошуку (перенесені з Main)
    public ArrayList<InventoryItem> searchByMaterial(Material mat) {
        ArrayList<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : items) {
            if (item.getClothes().getMaterial() == mat) result.add(item);
        }
        return result;
    }

    public ArrayList<InventoryItem> searchByMaxPrice(double maxPrice) {
        ArrayList<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : items) {
            if (item.getClothes().getPrice() <= maxPrice) result.add(item);
        }
        return result;
    }

    public ArrayList<InventoryItem> searchBySize(String size) {
        ArrayList<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : items) {
            if (item.getClothes().getSize().equalsIgnoreCase(size)) result.add(item);
        }
        return result;
    }
}
