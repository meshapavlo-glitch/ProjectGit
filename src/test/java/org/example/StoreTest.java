package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    private Store store;

    @BeforeEach
    void setUp() {
        // Ініціалізуємо порожній склад перед кожним тестом
        store = new Store();
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenDeletingNonExistingObject() {
        // Створюємо випадковий UUID, якого точно немає в базі
        UUID nonExistentId = UUID.randomUUID();

        // Перевіряємо, що метод delete викидає ItemNotFoundException
        assertThrows(ItemNotFoundException.class, () -> {
            store.delete(nonExistentId);
        });
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenUpdatingNonExistingObject() throws InvalidFieldValueException {
        UUID nonExistentId = UUID.randomUUID();
        // Створюємо об'єкт, яким хотіли б замінити (дані не мають значення для цього тесту)
        Pants newPants = new Pants("Штани", "L", 500.0, Material.DENIM, 100);
        InventoryItem newItem = new InventoryItem(newPants, 1);

        // Перевіряємо, що метод update викидає ItemNotFoundException
        assertThrows(ItemNotFoundException.class, () -> {
            store.update(nonExistentId, newItem);
        });
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenPriceIsNegative() {
        // Перевіряємо логіку доменної області (клас Clothes/Pants)
        // Конструктор повинен викинути виняток, якщо ціна -100
        assertThrows(InvalidFieldValueException.class, () -> {
            new Pants("Штани", "M", -100.0, Material.DENIM, 100);
        });
    }

    @Test
    void shouldSuccessfullyAddAndRemoveItem() throws InvalidFieldValueException, ItemNotFoundException {
        // Тест на успішний сценарій
        Pants pants = new Pants("Штани", "M", 1000.0, Material.DENIM, 100);
        UUID id = pants.getUuid();

        store.addNewClothes(pants, 5);
        assertEquals(1, store.getItems().size(), "Склад повинен містити 1 товар");

        // Видаляємо і перевіряємо, що помилок немає
        assertDoesNotThrow(() -> store.delete(id));
        assertEquals(0, store.getItems().size(), "Склад повинен стати порожнім");
    }
}
