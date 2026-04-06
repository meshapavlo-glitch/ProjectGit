package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Store {
    private ArrayList<InventoryItem> items = new ArrayList<>();

    public void addNewClothes(Clothes cl, int quantity) {
        for (InventoryItem item : items) {
            if (item.getClothes().equals(cl)) {
                item.addQuantity(quantity);
                return;
            }
        }
        items.add(new InventoryItem(cl, quantity));
    }

    public ArrayList<InventoryItem> getItems() {
        return items;
    }

    // Метод для отримання відсортованого списку
    public List<InventoryItem> getSortedItems() {
        List<InventoryItem> sorted = new ArrayList<>(items);
        // Сортуємо копію, використовуючи метод compareTo з класу Clothes
        sorted.sort((a, b) -> a.getClothes().compareTo(b.getClothes()));
        return sorted;
    }

    // Методи пошуку
    public ArrayList<InventoryItem> searchByMaterial(Material mat) {
        ArrayList<InventoryItem> res = new ArrayList<>();
        for (InventoryItem i : items) if (i.getClothes().getMaterial() == mat) res.add(i);
        return res;
    }

    public ArrayList<InventoryItem> searchByMaxPrice(double max) {
        ArrayList<InventoryItem> res = new ArrayList<>();
        for (InventoryItem i : items) if (i.getClothes().getPrice() <= max) res.add(i);
        return res;
    }

    public ArrayList<InventoryItem> searchBySize(String size) {
        ArrayList<InventoryItem> res = new ArrayList<>();
        for (InventoryItem i : items) if (i.getClothes().getSize().equalsIgnoreCase(size)) res.add(i);
        return res;
    }

    public InventoryItem searchByUuid(UUID targetUuid) {
        for (InventoryItem item : items) {
            if (item.getClothes().getUuid().equals(targetUuid)) {
                return item;
            }
        }
        return null;
    }
}
