-- db/migration/V5__fill_data.sql
-- Insert data into the order table
INSERT INTO orders (user_id, gift_certificate_id, order_timestamp, order_cost)
VALUES
    (1, 1, '2024-01-02 12:30:00', 500),
    (2, 2, '2024-01-02 14:45:00', 750),
    (3, 3, '2024-01-02 16:00:00', 1000),
    (3, 2, '2024-01-02 16:00:00', 2000),
    (3, 2, '2024-01-02 16:00:01', 2000),
    (3, 2, '2024-01-02 16:00:02', 2000);
