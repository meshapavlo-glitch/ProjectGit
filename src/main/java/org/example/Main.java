package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "input.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Clothes> inventory = new ArrayList<>();

    public static void main(String[] args) {
        loadFromFile();

        boolean running = true;
        while (running) {
            System.out.println("\n========== ГОЛОВНЕ МЕНЮ ==========");
            System.out.println("1. Пошук об’єкта (Фільтрація)");
            System.out.println("2. Створити новий об’єкт");
            System.out.println("3. Вивести інформацію про всі об’єкти");
            System.out.println("4. Завершити роботу програми");
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showSearchMenu();
                case "2" -> showCreationMenu();
                case "3" -> showInventory();
                case "4" -> {
                    saveToFile();
                    running = false;
                }
                default -> System.out.println("Помилка: Невірний вибір!");
            }
        }
    }

    /**
     * ПІДМЕНЮ ПОШУКУ (Опція 1)
     */
    private static void showSearchMenu() {
        if (inventory.isEmpty()) {
            System.out.println("Помилка: Колекція порожня. Пошук неможливий.");
            return;
        }

        System.out.println("\n--- КРИТЕРІЇ ПОШУКУ ---");
        System.out.println("1. Пошук за матеріалом");
        System.out.println("2. Пошук за максимальною ціною");
        System.out.println("3. Пошук за розміром");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Виберіть критерій: ");

        String searchChoice = scanner.nextLine();
        switch (searchChoice) {
            case "1" -> searchByMaterial();
            case "2" -> searchByMaxPrice();
            case "3" -> searchBySize();
            case "0" -> {}
            default -> System.out.println("Невірний вибір критерію.");
        }
    }

    // --- ОКРЕМІ МЕТОДИ ПОШУКУ (Функціональна декомпозиція) ---

    private static void searchByMaterial() {
        System.out.print("Введіть назву матеріалу (напр. COTTON, DENIM, LEATHER): ");
        String input = scanner.nextLine().toUpperCase();
        try {
            Material targetMaterial = Material.valueOf(input);
            boolean found = false;
            System.out.println("\nРезультати пошуку за матеріалом " + targetMaterial.getTitle() + ":");

            for (Clothes item : inventory) {
                if (item.getMaterial() == targetMaterial) {
                    System.out.println(item);
                    found = true;
                }
            }
            if (!found) System.out.println("Об'єктів не знайдено.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: Такого матеріалу не існує.");
        }
    }

    private static void searchByMaxPrice() {
        System.out.print("Введіть максимальну ціну: ");
        try {
            double maxPrice = Double.parseDouble(scanner.nextLine());
            boolean found = false;
            System.out.println("\nРезультати пошуку (ціна до " + maxPrice + "):");

            for (Clothes item : inventory) {
                if (item.getPrice() <= maxPrice) {
                    System.out.println(item);
                    found = true;
                }
            }
            if (!found) System.out.println("Об'єктів не знайдено.");
        } catch (NumberFormatException e) {
            System.out.println("Помилка: Введіть коректне число.");
        }
    }

    private static void searchBySize() {
        System.out.print("Введіть розмір для пошуку (напр. L, M, S, XL): ");
        String targetSize = scanner.nextLine().trim();
        boolean found = false;
        System.out.println("\nРезультати пошуку для розміру " + targetSize + ":");

        for (Clothes item : inventory) {
            if (item.getSize().equalsIgnoreCase(targetSize)) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) System.out.println("Об'єктів не знайдено.");
    }

    // --- МЕТОДИ З ПОПЕРЕДНІХ РОБІТ (Збережені без змін) ---

    private static void showCreationMenu() {
        System.out.println("\n--- ТИП ОБ'ЄКТА ---");
        System.out.println("1. Clothes | 2. Pants | 3. Shirt | 4. Socks | 5. Jacket | 0. Назад");
        String typeChoice = scanner.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Введіть розмір: ");
            String size = scanner.nextLine();
            System.out.print("Введіть ціну: ");
            double price = Double.parseDouble(scanner.nextLine());

            switch (typeChoice) {
                case "1" -> inventory.add(new Clothes("Одяг", size, price, Material.COTTON));
                case "2" -> {
                    System.out.print("Довжина (см): ");
                    int len = Integer.parseInt(scanner.nextLine());
                    inventory.add(new Pants("Штани", size, price, Material.DENIM, len));
                }
                case "3" -> {
                    System.out.print("Ґудзики (true/false): ");
                    boolean btn = Boolean.parseBoolean(scanner.nextLine());
                    inventory.add(new Shirt("Сорочка", size, price, Material.COTTON, btn));
                }
                case "4" -> {
                    System.out.print("Високі? (true/false): ");
                    boolean high = Boolean.parseBoolean(scanner.nextLine());
                    inventory.add(new Socks("Шкарпетки", size, price, Material.WOOL, high));
                }
                case "5" -> {
                    System.out.print("Капюшон? (true/false): ");
                    boolean hood = Boolean.parseBoolean(scanner.nextLine());
                    inventory.add(new Jacket("Куртка", size, price, Material.LEATHER, hood));
                }
                default -> System.out.println("Невірний тип.");
            }
        } catch (Exception e) {
            System.out.println("Помилка при створенні: " + e.getMessage());
        }
    }

    private static void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("\nІнвентар порожній.");
            return;
        }
        System.out.println("\n--- ВЕСЬ ІНВЕНТАР ---");
        for (Clothes item : inventory) System.out.println(item);
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Clothes item : inventory) {
                String row = "";
                if (item instanceof Pants p) row = "Pants;"+p.getSize()+";"+p.getPrice()+";"+p.getMaterial()+";"+p.getLength();
                else if (item instanceof Shirt s) row = "Shirt;"+s.getSize()+";"+s.getPrice()+";"+s.getMaterial()+";"+s.isHasButtons();
                else if (item instanceof Socks so) row = "Socks;"+so.getSize()+";"+so.getPrice()+";"+so.getMaterial()+";"+so.isHigh();
                else if (item instanceof Jacket j) row = "Jacket;"+j.getSize()+";"+j.getPrice()+";"+j.getMaterial()+";"+j.isHasHood();
                else row = "Clothes;"+item.getSize()+";"+item.getPrice()+";"+item.getMaterial();
                writer.println(row);
            }
        } catch (IOException e) { System.out.println("Помилка збереження."); }
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
                    switch (type) {
                        case "Clothes" -> inventory.add(new Clothes("Одяг", size, price, mat));
                        case "Pants" -> inventory.add(new Pants("Штани", size, price, mat, Integer.parseInt(p[4])));
                        case "Shirt" -> inventory.add(new Shirt("Сорочка", size, price, mat, Boolean.parseBoolean(p[4])));
                        case "Socks" -> inventory.add(new Socks("Шкарпетки", size, price, mat, Boolean.parseBoolean(p[4])));
                        case "Jacket" -> inventory.add(new Jacket("Куртка", size, price, mat, Boolean.parseBoolean(p[4])));
                    }
                } catch (Exception ignored) {}
            }
        } catch (IOException ignored) {}
    }
}
