package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для перевірки валідації класу Clothes.
 */
class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        // Перевіряємо, що конструктор кидає помилку при від'ємній ціні
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Футболка", "L", -100.0, "Бавовна");
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes clothes = new Clothes("Джинси", "M", 1200.0, "Денім");

        // Перевіряємо, що сетер кидає помилку, якщо передати порожній рядок
        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setType("");
        });
    }
}
