CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    address VARCHAR(255),
    createdAt TIMESTAMP,
    updatedAt TIMESTAMP
);

INSERT INTO users (id, name, email, phone, address, createdAt, updatedAt) VALUES
(1, 'User 1', 'user1@example.com', '1111111111', 'address 1', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(2, 'User 2', 'user2@example.com', '2222222222', 'address 2', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(3, 'User 3', 'user3@example.com', '3333333333', 'address 3', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(4, 'User 4', 'user4@example.com', '4444444444', 'address 4', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(5, 'User 5', 'user5@example.com', '5555555555', 'address 5', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(6, 'User 6', 'user6@example.com', '6666666666', 'address 6', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(7, 'User 7', 'user7@example.com', '7777777777', 'address 7', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(8, 'User 8', 'user8@example.com', '8888888888', 'address 8', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(9, 'User 9', 'user9@example.com', '9999999999', 'address 9', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(10, 'User 10', 'user10@example.com', '1010101010', 'address 10', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(11, 'User 11', 'user11@example.com', '1111111111', 'address 11', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(12, 'User 12', 'user12@example.com', '1212121212', 'address 12', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(13, 'User 13', 'user13@example.com', '1313131313', 'address 13', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(14, 'User 14', 'user14@example.com', '1414141414', 'address 14', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(15, 'User 15', 'user15@example.com', '1515151515', 'address 15', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(16, 'User 16', 'user16@example.com', '1616161616', 'address 16', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(17, 'User 17', 'user17@example.com', '1717171717', 'address 17', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(18, 'User 18', 'user18@example.com', '1818181818', 'address 18', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(19, 'User 19', 'user19@example.com', '1919191919', 'address 19', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days')),
(20, 'User 20', 'user20@example.com', '2020202020', 'address 20', NOW() - (random() * interval '365 days'), NOW() - (random() * interval '30 days'));