package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void testStaticCounter() {
        int initialCount = Clothes.getInstanceCount();
        new Clothes("T-shirt", "M", 100, Material.COTTON);
        new Clothes("Jeans", "L", 200, Material.DENIM);

        // Перевіряємо, що лічильник збільшився на 2
        assertEquals(initialCount + 2, Clothes.getInstanceCount());
    }

    @Test
    void testCopyConstructor() {
        Clothes original = new Clothes("Dress", "S", 500, Material.WOOL);
        Clothes copy = new Clothes(original);

        // Перевіряємо, що дані однакові, але об'єкти різні (різні посилання в пам'яті)
        assertEquals(original.getType(), copy.getType());
        assertEquals(original.getPrice(), copy.getPrice());
        assertNotSame(original, copy);
    }

    @Test
    void testAggregationInWardrobe() {
        Wardrobe wardrobe = new Wardrobe("TestOwner", 2);
        Clothes item = new Clothes("Hat", "OneSize", 50, Material.POLYESTER);

        wardrobe.addClothes(item);
        // Тут ми просто перевіряємо, що додавання не викликає помилок
        assertDoesNotThrow(() -> wardrobe.displayWardrobe());
    }
}
