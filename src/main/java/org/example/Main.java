package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Головний драйвер програми.
 * Тепер підтримує роботу з базою даних через DatabaseManager.
 */
public class Main {
    private static final String FILE_NAME = "input.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Store store = new Store();
    private static DatabaseManager dbManager;

    public static void main(String[] args) {
        // ПЕРЕВІРКА АРГУМЕНТІВ
        if (args.length < 1) {
            System.err.println("ПОМИЛКА: Вкажіть шлях до файлу конфігурації БД в аргументах!");
            return;
        }

        // Ініціалізація менеджера БД
        try {
            dbManager = new DatabaseManager(args[0]);
            System.out.println("З'єднання з БД налаштовано (конфігурація: " + args[0] + ")");
        } catch (IOException e) {
            System.err.println("КРИТИЧНА ПОМИЛКА: Не вдалося прочитати файл " + args[0]);
            return;
        }

        // Завантаження існуючих даних з файлу
        loadFromFile();

        boolean running = true;
        while (running) {
            System.out.println("\n========== МАГАЗИН (DB + FILES) ==========");
            System.out.println("1. Додати новий товар (Зберегти в БД та Файл)");
            System.out.println("2. Пошук товарів");
            System.out.println("3. Вивести весь асортимент");
            System.out.println("4. Завершити роботу");
            System.out.print("Виберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showAddMenu();
                case "2" -> showSearchMenu();
                case "3" -> displayInventory();
                case "4" -> {
                    saveToFile();
                    System.out.println("Програму завершено.");
                    running = false;
                }
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private static void showAddMenu() {
        System.out.println("\n--- ОБЕРІТЬ ТИП ОБ'ЄКТА ---");
        System.out.println("1. Clothes | 2. Pants | 3. Shirt | 4. Socks | 5. Jacket | 0. Назад");
        String typeChoice = scanner.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Розмір: ");
            String size = scanner.nextLine();
            System.out.print("Ціна: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Кількість: ");
            int qty = Integer.parseInt(scanner.nextLine());

            Clothes newObj = null;
            switch (typeChoice) {
                case "1" -> newObj = new Clothes("Одяг", size, price, Material.COTTON);
                case "2" -> {
                    System.out.print("Довжина (см): ");
                    int len = Integer.parseInt(scanner.nextLine());
                    newObj = new Pants("Штани", size, price, Material.DENIM, len);
                }
                case "3" -> {
                    System.out.print("Ґудзики (true/false): ");
                    boolean btn = Boolean.parseBoolean(scanner.nextLine());
                    newObj = new Shirt("Сорочка", size, price, Material.COTTON, btn);
                }
                case "4" -> {
                    System.out.print("Високі? (true/false): ");
                    boolean high = Boolean.parseBoolean(scanner.nextLine());
                    newObj = new Socks("Шкарпетки", size, price, Material.WOOL, high);
                }
                case "5" -> {
                    System.out.print("Капюшон? (true/false): ");
                    boolean hood = Boolean.parseBoolean(scanner.nextLine());
                    newObj = new Jacket("Куртка", size, price, Material.LEATHER, hood);
                }
            }

            if (newObj != null) {
                // 1. Додаємо в колекцію Store (з урахуванням кількості)
                store.addNewClothes(newObj, qty);

                // 2. Виконуємо INSERT у базу даних (Вимога завдання)
                dbManager.insertClothes(newObj, qty);

                System.out.println("Товар успішно додано до системи.");
            }
        } catch (Exception e) {
            System.out.println("Помилка при створенні: " + e.getMessage());
        }
    }

    private static void showSearchMenu() {
        System.out.println("\n--- ПОШУК ---");
        System.out.println("1. Матеріал | 2. Ціна | 3. Розмір | 0. Назад");
        String choice = scanner.nextLine();
        ArrayList<InventoryItem> res = new ArrayList<>();
        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("Матеріал (COTTON/DENIM/WOOL/LEATHER): ");
                    res = store.searchByMaterial(Material.valueOf(scanner.nextLine().toUpperCase()));
                }
                case "2" -> {
                    System.out.print("Макс. ціна: ");
                    res = store.searchByMaxPrice(Double.parseDouble(scanner.nextLine()));
                }
                case "3" -> {
                    System.out.print("Розмір: ");
                    res = store.searchBySize(scanner.nextLine());
                }
            }
            if (res.isEmpty()) System.out.println("Нічого не знайдено.");
            else res.forEach(System.out::println);
        } catch (Exception e) { System.out.println("Помилка пошуку."); }
    }

    private static void displayInventory() {
        if (store.getItems().isEmpty()) System.out.println("Склад порожній.");
        else store.getItems().forEach(System.out::println);
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (InventoryItem item : store.getItems()) {
                Clothes c = item.getClothes();
                String data = "";
                if (c instanceof Pants p) data = "Pants;"+p.getSize()+";"+p.getPrice()+";"+p.getMaterial()+";"+p.getLength();
                else if (c instanceof Shirt s) data = "Shirt;"+s.getSize()+";"+s.getPrice()+";"+s.getMaterial()+";"+s.isHasButtons();
                else if (c instanceof Socks so) data = "Socks;"+so.getSize()+";"+so.getPrice()+";"+so.getMaterial()+";"+so.isHigh();
                else if (c instanceof Jacket j) data = "Jacket;"+j.getSize()+";"+j.getPrice()+";"+j.getMaterial()+";"+j.isHasHood();
                else data = "Clothes;"+c.getSize()+";"+c.getPrice()+";"+c.getMaterial();

                writer.println(data + ";" + item.getQuantity());
            }
        } catch (IOException e) { System.out.println("Помилка збереження файлу."); }
    }

    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                try {
                    String type = p[0];
                    String size = p[1];
                    double price = Double.parseDouble(p[2]);
                    Material mat = Material.valueOf(p[3]);
                    int qty = Integer.parseInt(p[p.length - 1]);

                    Clothes obj = null;
                    switch (type) {
                        case "Clothes" -> obj = new Clothes("Одяг", size, price, mat);
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
