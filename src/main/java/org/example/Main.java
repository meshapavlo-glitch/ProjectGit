package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver-клас для керування магазином одягу.
 * Використовує об'єкт Store як єдине сховище даних.
 */
public class Main {
    private static final String FILE_NAME = "input.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Store store = new Store();

    public static void main(String[] args) {
        // 1. Завантаження даних з файлу в Store при запуску
        loadFromFile();

        boolean running = true;
        while (running) {
            System.out.println("\n========== СИСТЕМА УПРАВЛІННЯ МАГАЗИНОМ ==========");
            System.out.println("1. Додати новий товар (або змінити кількість)");
            System.out.println("2. Пошук товарів за критеріями");
            System.out.println("3. Вивести весь асортимент магазину");
            System.out.println("4. Завершити роботу та зберегти дані");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showAddMenu();
                case "2" -> showSearchMenu();
                case "3" -> displayInventory();
                case "4" -> {
                    saveToFile();
                    System.out.println("Дані збережено. Вихід...");
                    running = false;
                }
                default -> System.out.println("Помилка: Невірний вибір.");
            }
        }
    }

    /**
     * Меню додавання товару. Використовує метод store.addNewClothes
     */
    private static void showAddMenu() {
        System.out.println("\n--- ТИП ОБ'ЄКТА ДЛЯ ДОДАВАННЯ ---");
        System.out.println("1. Clothes | 2. Pants | 3. Shirt | 4. Socks | 5. Jacket | 0. Назад");
        String typeChoice = scanner.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Введіть розмір: ");
            String size = scanner.nextLine();
            System.out.print("Введіть ціну: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Введіть кількість: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            Clothes newClothes = null;
            switch (typeChoice) {
                case "1" -> newClothes = new Clothes("Одяг", size, price, Material.COTTON);
                case "2" -> {
                    System.out.print("Довжина (см): ");
                    int len = Integer.parseInt(scanner.nextLine());
                    newClothes = new Pants("Штани", size, price, Material.DENIM, len);
                }
                case "3" -> {
                    System.out.print("Ґудзики (true/false): ");
                    boolean btn = Boolean.parseBoolean(scanner.nextLine());
                    newClothes = new Shirt("Сорочка", size, price, Material.COTTON, btn);
                }
                case "4" -> {
                    System.out.print("Високі? (true/false): ");
                    boolean high = Boolean.parseBoolean(scanner.nextLine());
                    newClothes = new Socks("Шкарпетки", size, price, Material.WOOL, high);
                }
                case "5" -> {
                    System.out.print("Капюшон? (true/false): ");
                    boolean hood = Boolean.parseBoolean(scanner.nextLine());
                    newClothes = new Jacket("Куртка", size, price, Material.LEATHER, hood);
                }
            }

            if (newClothes != null) {
                store.addNewClothes(newClothes, quantity);
                System.out.println("Операція успішна.");
            }
        } catch (Exception e) {
            System.out.println("Помилка при введенні: " + e.getMessage());
        }
    }

    /**
     * Меню пошуку. Викликає методи пошуку з класу Store
     */
    private static void showSearchMenu() {
        System.out.println("\n--- КРИТЕРІЇ ПОШУКУ ---");
        System.out.println("1. За матеріалом | 2. За макс. ціною | 3. За розміром | 0. Назад");
        String choice = scanner.nextLine();
        ArrayList<InventoryItem> results = new ArrayList<>();

        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("Введіть матеріал: ");
                    Material m = Material.valueOf(scanner.nextLine().toUpperCase());
                    results = store.searchByMaterial(m);
                }
                case "2" -> {
                    System.out.print("Макс. ціна: ");
                    double p = Double.parseDouble(scanner.nextLine());
                    results = store.searchByMaxPrice(p);
                }
                case "3" -> {
                    System.out.print("Розмір: ");
                    results = store.searchBySize(scanner.nextLine());
                }
                case "0" -> { return; }
            }

            if (results.isEmpty()) {
                System.out.println("Нічого не знайдено.");
            } else {
                System.out.println("\nЗнайдені товари:");
                for (InventoryItem i : results) System.out.println(i);
            }
        } catch (Exception e) {
            System.out.println("Помилка пошуку: " + e.getMessage());
        }
    }

    private static void displayInventory() {
        ArrayList<InventoryItem> all = store.getItems();
        if (all.isEmpty()) {
            System.out.println("Магазин порожній.");
        } else {
            System.out.println("\n--- АСОРТИМЕНТ МАГАЗИНУ ---");
            for (InventoryItem i : all) System.out.println(i);
        }
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (InventoryItem item : store.getItems()) {
                Clothes c = item.getClothes();
                StringBuilder sb = new StringBuilder();

                // Визначаємо тип для коректного збереження
                if (c instanceof Pants p) sb.append("Pants;").append(p.getSize()).append(";").append(p.getPrice()).append(";").append(p.getMaterial()).append(";").append(p.getLength());
                else if (c instanceof Shirt s) sb.append("Shirt;").append(s.getSize()).append(";").append(s.getPrice()).append(";").append(s.getMaterial()).append(";").append(s.isHasButtons());
                else if (c instanceof Socks so) sb.append("Socks;").append(so.getSize()).append(";").append(so.getPrice()).append(";").append(so.getMaterial()).append(";").append(so.isHigh());
                else if (c instanceof Jacket j) sb.append("Jacket;").append(j.getSize()).append(";").append(j.getPrice()).append(";").append(j.getMaterial()).append(";").append(j.isHasHood());
                else sb.append("Clothes;").append(c.getSize()).append(";").append(c.getPrice()).append(";").append(c.getMaterial());

                // Додаємо кількість як останній стовпець
                sb.append(";").append(item.getQuantity());
                writer.println(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("Помилка збереження файлу.");
        }
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
                    // Кількість завжди в останньому полі
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
