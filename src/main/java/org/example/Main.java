package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver-клас для керування ієрархією одягу.
 * Реалізує меню, роботу з ArrayList та демонстрацію поліморфізму.
 */
public class Main {
    private static final String FILE_NAME = "input.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Clothes> inventory = new ArrayList<>();

    public static void main(String[] args) {
        // 1. Автоматичне зчитування даних при запуску
        loadFromFile();

        boolean running = true;
        while (running) {
            System.out.println("\n========== ГОЛОВНЕ МЕНЮ (Файли + Поліморфізм) ==========");
            System.out.println("1. Створити новий об’єкт");
            System.out.println("2. Вивести інформацію про всі об’єкти");
            System.out.println("3. Завершити роботу програми (Зберегти у файл)");
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showCreationMenu();
                case "2" -> showInventory();
                case "3" -> {
                    // 2. Автоматичне збереження перед виходом
                    saveToFile();
                    System.out.println("Дані збережено. Програма завершена.");
                    running = false;
                }
                default -> System.out.println("Помилка: Невірний вибір!");
            }
        }
    }

    private static void showCreationMenu() {
        System.out.println("\n--- ОБЕРІТЬ ТИП НОВОГО ОБ'ЄКТА ---");
        System.out.println("1. Базовий одяг (Clothes)");
        System.out.println("2. Штани (Pants)");
        System.out.println("3. Сорочка (Shirt)");
        System.out.println("4. Шкарпетки (Socks)");
        System.out.println("5. Куртка (Jacket)");
        System.out.println("0. Повернутися до меню");
        System.out.print("Вибір: ");

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
                    System.out.print("Довжина штанин (см): ");
                    int len = Integer.parseInt(scanner.nextLine());
                    inventory.add(new Pants("Штани", size, price, Material.DENIM, len));
                }
                case "3" -> {
                    System.out.print("Чи є ґудзики? (true/false): ");
                    boolean btn = Boolean.parseBoolean(scanner.nextLine());
                    inventory.add(new Shirt("Сорочка", size, price, Material.COTTON, btn));
                }
                case "4" -> {
                    System.out.print("Високі шкарпетки? (true/false): ");
                    boolean high = Boolean.parseBoolean(scanner.nextLine());
                    inventory.add(new Socks("Шкарпетки", size, price, Material.WOOL, high));
                }
                case "5" -> {
                    System.out.print("Є капюшон? (true/false): ");
                    boolean hood = Boolean.parseBoolean(scanner.nextLine());
                    inventory.add(new Jacket("Куртка", size, price, Material.LEATHER, hood));
                }
                default -> System.out.println("Помилка: Невірний тип.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ПОМИЛКА: Вводьте лише числа!");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА ВАЛІДАЦІЇ: " + e.getMessage());
        }
    }

    private static void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("\nІнвентар порожній.");
            return;
        }
        System.out.println("\n--- ВМІСТ КОЛЕКЦІЇ ---");
        for (Clothes item : inventory) {
            System.out.println(item.toString());
        }
    }

    /**
     * Записує вміст ArrayList у файл input.txt
     */
    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Clothes item : inventory) {
                StringBuilder sb = new StringBuilder();
                // Використовуємо геттери, які ми щойно додали
                if (item instanceof Pants p) {
                    sb.append("Pants;").append(p.getSize()).append(";").append(p.getPrice())
                            .append(";").append(p.getMaterial()).append(";").append(p.getLength());
                } else if (item instanceof Shirt s) {
                    sb.append("Shirt;").append(s.getSize()).append(";").append(s.getPrice())
                            .append(";").append(s.getMaterial()).append(";").append(s.isHasButtons());
                } else if (item instanceof Socks so) {
                    sb.append("Socks;").append(so.getSize()).append(";").append(so.getPrice())
                            .append(";").append(so.getMaterial()).append(";").append(so.isHigh());
                } else if (item instanceof Jacket j) {
                    sb.append("Jacket;").append(j.getSize()).append(";").append(j.getPrice())
                            .append(";").append(j.getMaterial()).append(";").append(j.isHasHood());
                } else {
                    sb.append("Clothes;").append(item.getSize()).append(";").append(item.getPrice())
                            .append(";").append(item.getMaterial());
                }
                writer.println(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        }
    }

    /**
     * Зчитує дані з input.txt та створює об'єкти
     */
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
                } catch (Exception e) {
                    System.out.println("Пропущено некоректний рядок у файлі.");
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка завантаження: " + e.getMessage());
        }
    }
}
