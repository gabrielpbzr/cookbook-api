/**
 * V0__initial_structure.sql
 * Create basic structure for database. PostgreSQL dialect
 * author: Gabriel P. Bezerra
 */
CREATE TABLE IF NOT EXISTS recipes (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    uuid CHAR(36) UNIQUE NOT NULL
);
