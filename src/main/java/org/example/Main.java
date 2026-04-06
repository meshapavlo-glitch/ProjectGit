package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final String FILE_NAME = "input.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Store store = new Store();

    public static void main(String[] args) {
        loadFromFile();

        boolean running = true;
        while (running) {
            System.out.println("\n--- МЕНЮ МАГАЗИНУ ---");
            System.out.println("1. Додати товар");
            System.out.println("2. Пошук");
            System.out.println("3. Весь список (як є)");
            System.out.println("4. Сортувати та вивести");
            System.out.println("6. Модифікувати товар за UUID");
            System.out.println("7. Видалити товар за UUID");
            System.out.println("5. Вихід");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showAddMenu();
                case "2" -> showSearchMenu();
                case "3" -> displayList(store.getItems());
                case "4" -> showSortSubMenu();
                case "6" -> showUpdateMenu();
                case "7" -> showDeleteMenu();
                case "5" -> {
                    saveToFile();
                    running = false;
                }
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    /**
     * Підменю сортування з використанням ЛЯМБДА-ВИРАЗІВ.
     */
    private static void showSortSubMenu() {
        List<InventoryItem> sortedList = new ArrayList<>(store.getItems());

        if (sortedList.isEmpty()) {
            System.out.println("Список порожній. Нічого сортувати.");
            return;
        }

        System.out.println("\nОберіть критерій сортування (Lambda Edition):");
        System.out.println("1. За ціною (від найменшої)");
        System.out.println("2. За типом (алфавітний порядок)");
        System.out.println("3. За кількістю на складі (від найбільшої)");
        System.out.println("0. Повернутися в головне меню");
        System.out.print("Ваш вибір: ");

        String sortChoice = scanner.nextLine();
        Comparator<InventoryItem> comparator = null;

        switch (sortChoice) {
            case "1":
                // Лямбда-вираз для ціни
                comparator = (o1, o2) -> Double.compare(o1.getClothes().getPrice(), o2.getClothes().getPrice());
                break;
            case "2":
                // Лямбда-вираз для типу (алфавіт)
                comparator = (o1, o2) -> o1.getClothes().getType().compareTo(o2.getClothes().getType());
                break;
            case "3":
                // Лямбда-вираз для кількості (спадання)
                comparator = (o1, o2) -> Integer.compare(o2.getQuantity(), o1.getQuantity());
                break;
            case "0":
                return;
            default:
                System.out.println("Невірний критерій.");
                return;
        }

        if (comparator != null) {
            sortedList.sort(comparator); // Використовуємо метод sort самого списку
            System.out.println("\n--- РЕЗУЛЬТАТ СОРТУВАННЯ ---");
            displayList(sortedList);
        }
    }

    private static void showAddMenu() {
        System.out.println("\n--- ДОДАВАННЯ ТОВАРУ ---");
        System.out.println("1. Pants | 2. Shirt | 3. Socks | 4. Jacket");
        String typeChoice = scanner.nextLine();

        try {
            System.out.print("Розмір: ");
            String s = scanner.nextLine();
            System.out.print("Ціна: ");
            double p = Double.parseDouble(scanner.nextLine());
            System.out.print("Кількість: ");
            int q = Integer.parseInt(scanner.nextLine());

            Clothes obj = null;
            switch (typeChoice) {
                case "1" -> {
                    System.out.print("Довжина (см): ");
                    int len = Integer.parseInt(scanner.nextLine());
                    obj = new Pants("Штани", s, p, Material.DENIM, len);
                }
                case "2" -> {
                    System.out.print("Має ґудзики (true/false): ");
                    boolean btn = Boolean.parseBoolean(scanner.nextLine());
                    obj = new Shirt("Сорочка", s, p, Material.COTTON, btn);
                }
                case "3" -> {
                    System.out.print("Високі (true/false): ");
                    boolean high = Boolean.parseBoolean(scanner.nextLine());
                    obj = new Socks("Шкарпетки", s, p, Material.WOOL, high);
                }
                case "4" -> {
                    System.out.print("Капюшон (true/false): ");
                    boolean hood = Boolean.parseBoolean(scanner.nextLine());
                    obj = new Jacket("Куртка", s, p, Material.LEATHER, hood);
                }
            }

            if (obj != null) {
                store.addNewClothes(obj, q);
                System.out.println("Успішно додано!");
                System.out.println("Повний UUID для копіювання: " + obj.getUuid());
            }
        } catch (Exception e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        }
    }

    private static void showUpdateMenu() {
        System.out.print("Введіть UUID об'єкта для модифікації: ");
        String uuidStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(uuidStr);
            InventoryItem existing = store.searchByUuid(id);

            if (existing == null) {
                System.out.println("Об'єкт з таким UUID не знайдено.");
                return;
            }

            System.out.println("Знайдено: " + existing);
            System.out.println("Введіть нові дані (тип змінити не можна, тільки атрибути):");

            System.out.print("Новий розмір: ");
            String newSize = scanner.nextLine();
            System.out.print("Нова ціна: ");
            double newPrice = Double.parseDouble(scanner.nextLine());
            System.out.print("Нова кількість: ");
            int newQty = Integer.parseInt(scanner.nextLine());

            Clothes updatedClothes = null;
            Clothes old = existing.getClothes();

            // Створюємо новий об'єкт того ж типу, що й був
            if (old instanceof Pants) {
                updatedClothes = new Pants(old.getType(), newSize, newPrice, old.getMaterial(), 100);
            } else if (old instanceof Shirt) {
                updatedClothes = new Shirt(old.getType(), newSize, newPrice, old.getMaterial(), true);
            } else if (old instanceof Socks) {
                updatedClothes = new Socks(old.getType(), newSize, newPrice, old.getMaterial(), false);
            } else if (old instanceof Jacket) {
                updatedClothes = new Jacket(old.getType(), newSize, newPrice, old.getMaterial(), true);
            }

            if (updatedClothes != null) {
                InventoryItem newItem = new InventoryItem(updatedClothes, newQty);
                if (store.update(id, newItem)) {
                    System.out.println("Дані успішно оновлено!");
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: Некоректний формат UUID.");
        } catch (Exception e) {
            System.out.println("Помилка при введенні даних: " + e.getMessage());
        }
    }

    private static void showDeleteMenu() {
        // Перевірка на порожню колекцію перед початком
        if (store.getItems().isEmpty()) {
            System.out.println("Склад порожній. Нічого видаляти.");
            return;
        }

        System.out.print("Введіть UUID об'єкта, який бажаєте ВИДАЛИТИ: ");
        String uuidStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(uuidStr);

            // Пошук об'єкта для підтвердження (опціонально, але рекомендовано)
            InventoryItem toDelete = store.searchByUuid(id);

            if (toDelete == null) {
                System.out.println("Об'єкт з таким UUID не знайдено.");
                return;
            }

            System.out.println("Ви збираєтеся видалити: " + toDelete);
            System.out.print("Ви впевнені? (так/ні): ");
            String confirm = scanner.nextLine().toLowerCase();

            if (confirm.equals("так")) {
                // Виклик методу агрегуючого класу
                boolean deleted = store.delete(id);

                if (deleted) {
                    System.out.println("Товар успішно видалено зі складу.");
                } else {
                    System.out.println("Помилка під час видалення.");
                }
            } else {
                System.out.println("Видалення скасовано.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА: Некоректний формат UUID.");
        }
    }

    private static void displayList(List<InventoryItem> list) {
        if (list.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            // Тут також можна використати метод посилання або лямбду для виводу
            list.forEach(item -> System.out.println(item));
        }
    }

    private static void showSearchMenu() {
        System.out.println("\n--- ПОШУК ---");
        System.out.println("1. Матеріал | 2. Ціна | 3. Розмір | 4. UUID");
        String choice = scanner.nextLine();

        try {
            if (choice.equals("4")) {
                System.out.print("Введіть повний UUID об'єкта: ");
                String uuidInput = scanner.nextLine();

                try {
                    // Перетворення рядка в об'єкт UUID
                    UUID targetId = UUID.fromString(uuidInput);
                    InventoryItem found = store.searchByUuid(targetId);

                    if (found != null) {
                        System.out.println("Знайдено товар: " + found);
                    } else {
                        System.out.println("Товар з таким UUID не знайдено.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("ПОМИЛКА: Некоректний формат UUID. Приклад: 550e8400-e29b-41d4-a716-446655440000");
                }
                return; // Вихід з методу пошуку
            }

            // Стара логіка пошуку для пунктів 1, 2, 3...
            List<InventoryItem> res = new ArrayList<>();
            if (choice.equals("1")) {
                System.out.print("Матеріал: ");
                res = store.searchByMaterial(Material.valueOf(scanner.nextLine().toUpperCase()));
            } else if (choice.equals("2")) {
                System.out.print("Макс. ціна: ");
                res = store.searchByMaxPrice(Double.parseDouble(scanner.nextLine()));
            } else if (choice.equals("3")) {
                System.out.print("Розмір: ");
                res = store.searchBySize(scanner.nextLine());
            }
            displayList(res);

        } catch (Exception e) {
            System.out.println("Помилка при пошуку: " + e.getMessage());
        }
    }

    private static void saveToFile() {
        try (PrintWriter w = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (InventoryItem i : store.getItems()) {
                Clothes c = i.getClothes();
                String base = c.getSize() + ";" + c.getPrice() + ";" + c.getMaterial();
                String line = "";
                if (c instanceof Pants p) line = "Pants;" + base + ";" + p.getLength();
                else if (c instanceof Shirt s) line = "Shirt;" + base + ";" + s.isHasButtons();
                else if (c instanceof Socks so) line = "Socks;" + base + ";" + so.isHigh();
                else if (c instanceof Jacket j) line = "Jacket;" + base + ";" + j.isHasHood();
                w.println(line + ";" + i.getQuantity());
            }
        } catch (IOException e) { System.out.println("Помилка запису."); }
    }

    private static void loadFromFile() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = r.readLine()) != null) {
                String[] p = l.split(";");
                try {
                    String type = p[0];
                    String size = p[1];
                    double price = Double.parseDouble(p[2]);
                    Material mat = Material.valueOf(p[3]);
                    int qty = Integer.parseInt(p[p.length - 1]);
                    Clothes obj = null;
                    switch (type) {
                        case "Pants" -> obj = new Pants("Штани", size, price, mat, Integer.parseInt(p[4]));
                        case "Shirt" -> obj = new Shirt("Сорочка", size, price, mat, Boolean.parseBoolean(p[4]));
                        case "Socks" -> obj = new Socks("Шкарпетки", size, price, mat, Boolean.parseBoolean(p[4]));
                        case "Jacket" -> obj = new Jacket("Куртка", size, price, mat, Boolean.parseBoolean(p[4]));
                    }
                    if (obj != null) store.addNewClothes(obj, qty);
                } catch (Exception ignored) {}
            }
        } catch (IOException ignored) {}
    }
}
