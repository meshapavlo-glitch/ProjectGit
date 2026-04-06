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

    /**
     * Оновлює дані об'єкта за його UUID
     * @param targetId ідентифікатор об'єкта, який шукаємо
     * @param newItem новий об'єкт з оновленими даними
     * @return true, якщо об'єкт знайдено і оновлено
     */
    public boolean update(UUID targetId, InventoryItem newItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getClothes().getUuid().equals(targetId)) {
                // Зберігаємо старий UUID для нового об'єкта
                newItem.getClothes().setUuid(targetId);
                // Замінюємо старий елемент новим у списку
                items.set(i, newItem);
                return true;
            }
        }
        return false;
    }

    /**
     * Видаляє об'єкт з колекції за його UUID.
     * @param targetId UUID об'єкта для видалення
     * @return true, якщо об'єкт знайдено і видалено, false — якщо не знайдено
     */
    public boolean delete(UUID targetId) {
        if (items.isEmpty()) {
            return false;
        }

        // Використовуємо ітератор або RemoveIf для безпечного видалення під час обходу
        return items.removeIf(item -> item.getClothes().getUuid().equals(targetId));
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
