CREATE TABLE IF NOT EXISTS inventory (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    size VARCHAR(10) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    material VARCHAR(50) NOT NULL,
    special_attribute VARCHAR(100),
    quantity INTEGER DEFAULT 1
    );
