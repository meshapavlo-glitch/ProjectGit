package org.example;

/**
 * Перерахування можливих матеріалів одягу.
 */
public enum Material {
    COTTON("Бавовна"),
    DENIM("Денім"),
    WOOL("Вовна"),
    LEATHER("Шкіра"),
    POLYESTER("Поліестер");

    private final String title;

    Material(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
