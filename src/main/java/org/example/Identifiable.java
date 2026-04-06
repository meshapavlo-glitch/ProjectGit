package org.example;

import java.util.UUID;

/**
 * Інтерфейс для об'єктів, які мають унікальний ідентифікатор.
 */
public interface Identifiable {
    UUID getUuid();
}
