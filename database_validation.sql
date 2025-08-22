-- Database validation and migration script for Bodima 2
-- Add payment status functionality
-- Add payment_status column to monthly_payment table
ALTER TABLE monthly_payment
ADD COLUMN payment_status VARCHAR(20) DEFAULT 'PENDING';

-- Update existing records based on payment amounts
UPDATE monthly_payment
SET
    payment_status = CASE
        WHEN paid_amount >= total_amount THEN 'PAID'
        WHEN paid_amount > 0 THEN 'PARTIAL'
        ELSE 'PENDING'
    END;

-- Create index for better performance on status queries
CREATE INDEX idx_payment_status ON monthly_payment (payment_status);

CREATE INDEX idx_payment_user_status ON monthly_payment (user_id, payment_status);

-- Verify the changes
SELECT
    payment_status,
    COUNT(*) as count
FROM
    monthly_payment
GROUP BY
    payment_status;

-- Sample query to get payment summary for a user
SELECT
    user_id,
    COUNT(
        CASE
            WHEN payment_status = 'PENDING' THEN 1
        END
    ) as pending_count,
    COUNT(
        CASE
            WHEN payment_status = 'PAID' THEN 1
        END
    ) as paid_count,
    COUNT(
        CASE
            WHEN payment_status = 'OVERDUE' THEN 1
        END
    ) as overdue_count,
    COUNT(
        CASE
            WHEN payment_status = 'PARTIAL' THEN 1
        END
    ) as partial_count
FROM
    monthly_payment
GROUP BY
    user_id;

-- Bodima Database Validation Script
-- Run this script in MySQL to verify your database implementation
USE bodima;

-- 1. Check if all tables exist
SELECT
    'Checking table existence...' as status;

SHOW TABLES;

-- 2. Verify table structures
SELECT
    'Verifying table structures...' as status;

-- Admin table structure
DESCRIBE admin;

-- Room table structure  
DESCRIBE room;

-- User table structure
DESCRIBE user;

-- Login table structure
DESCRIBE login;

-- Monthly Payment table structure
DESCRIBE monthly_payment;

-- 3. Check foreign key relationships
SELECT
    'Checking foreign key relationships...' as status;

SELECT
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM
    INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE
    REFERENCED_TABLE_SCHEMA = 'bodima'
ORDER BY
    TABLE_NAME,
    COLUMN_NAME;

-- 4. Check indexes
SELECT
    'Checking indexes...' as status;

SELECT
    TABLE_NAME,
    INDEX_NAME,
    COLUMN_NAME
FROM
    INFORMATION_SCHEMA.STATISTICS
WHERE
    TABLE_SCHEMA = 'bodima'
ORDER BY
    TABLE_NAME,
    INDEX_NAME;

-- 5. Insert test data to verify relationships
SELECT
    'Inserting test data...' as status;

-- Insert test admin
INSERT INTO
    admin (name, email)
VALUES
    ('Test Admin', 'test.admin@bodima.com');

-- Insert test room
INSERT INTO
    room (room_number, capacity)
VALUES
    ('TEST101', 2);

-- Insert test user (with relationships)
INSERT INTO
    user (name, email, phone, join_date, admin_id, room_id)
VALUES
    (
        'Test User',
        'test.user@example.com',
        '+1234567890',
        '2024-01-15',
        1,
        1
    );

-- Insert test login
INSERT INTO
    login (user_id, username, password)
VALUES
    (1, 'testuser', 'password123');

-- Insert test payment
INSERT INTO
    monthly_payment (user_id, month, monthly_charge, paid_amount)
VALUES
    (1, '2024-01-01', 500.00, 500.00);

-- 6. Verify data integrity
SELECT
    'Verifying data integrity...' as status;

-- Check all data
SELECT
    'Admin data:' as info;

SELECT
    *
FROM
    admin;

SELECT
    'Room data:' as info;

SELECT
    *
FROM
    room;

SELECT
    'User data:' as info;

SELECT
    *
FROM
    user;

SELECT
    'Login data:' as info;

SELECT
    *
FROM
    login;

SELECT
    'Payment data:' as info;

SELECT
    *
FROM
    monthly_payment;

-- 7. Test relationships
SELECT
    'Testing relationships...' as status;

-- User with room and admin info
SELECT
    u.user_id,
    u.name as user_name,
    u.email as user_email,
    r.room_number,
    r.capacity,
    a.name as admin_name,
    a.email as admin_email
FROM
    user u
    LEFT JOIN room r ON u.room_id = r.room_id
    LEFT JOIN admin a ON u.admin_id = a.admin_id;

-- User with login info
SELECT
    u.user_id,
    u.name,
    l.username,
    l.password
FROM
    user u
    LEFT JOIN login l ON u.user_id = l.user_id;

-- User with payment info
SELECT
    u.user_id,
    u.name,
    mp.month,
    mp.monthly_charge,
    mp.paid_amount,
    (mp.monthly_charge - mp.paid_amount) as outstanding_amount
FROM
    user u
    LEFT JOIN monthly_payment mp ON u.user_id = mp.user_id;

-- 8. Test complex queries
SELECT
    'Testing complex queries...' as status;

-- Users with outstanding payments
SELECT
    u.name,
    u.email,
    SUM(mp.monthly_charge) as total_charged,
    SUM(mp.paid_amount) as total_paid,
    (SUM(mp.monthly_charge) - SUM(mp.paid_amount)) as outstanding
FROM
    user u
    LEFT JOIN monthly_payment mp ON u.user_id = mp.user_id
GROUP BY
    u.user_id,
    u.name,
    u.email
HAVING
    outstanding > 0;

-- Room occupancy
SELECT
    r.room_number,
    r.capacity,
    COUNT(u.user_id) as current_occupants,
    (r.capacity - COUNT(u.user_id)) as available_slots
FROM
    room r
    LEFT JOIN user u ON r.room_id = u.room_id
GROUP BY
    r.room_id,
    r.room_number,
    r.capacity;

-- 9. Performance checks
SELECT
    'Checking performance...' as status;

-- Check table sizes
SELECT
    TABLE_NAME,
    TABLE_ROWS,
    ROUND(((DATA_LENGTH + INDEX_LENGTH) / 1024 / 1024), 2) AS 'Size (MB)'
FROM
    INFORMATION_SCHEMA.TABLES
WHERE
    TABLE_SCHEMA = 'bodima';

-- 10. Clean up test data (optional)
-- Uncomment the following lines if you want to remove test data
/*
SELECT 'Cleaning up test data...' as status;
DELETE FROM monthly_payment WHERE user_id = 1;
DELETE FROM login WHERE user_id = 1;
DELETE FROM user WHERE user_id = 1;
DELETE FROM room WHERE room_id = 1;
DELETE FROM admin WHERE admin_id = 1;
 */
SELECT
    'Database validation completed!' as status;