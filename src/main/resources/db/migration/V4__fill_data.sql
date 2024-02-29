-- db/migration/V6__fill_data.sql
-- Insert data into the order table
INSERT INTO orders (user_id, gift_certificate_id, order_timestamp, order_cost)
VALUES
    (1, 101, '2024-01-02 12:30:00', 500),
    (2, 102, '2024-01-02 14:45:00', 750),
    (3, 103, '2024-01-02 16:00:00', 1000);
