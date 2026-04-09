-- Open API Platform
-- Sample seed data for local development
-- Login password for both sample accounts: 12345678
-- Password hash below uses the backend salt "yupi"

USE `open_api`;

INSERT INTO `user`
(`id`, `userAccount`, `userPassword`, `userName`, `userAvatar`, `userProfile`, `userRole`, `accessKey`, `secretKey`)
VALUES
    (1001, 'admin_demo', 'b0dd3697a192885d7c055db46155b26a', 'Admin Demo', '/default-avatar.jpg',
     'Seeded administrator account for local testing', 'admin',
     'ak_admin_demo_20260409', 'sk_admin_demo_20260409'),
    (1002, 'demo_user', 'b0dd3697a192885d7c055db46155b26a', 'Demo User', '/default-avatar.jpg',
     'Seeded standard account for local testing', 'user',
     'ak_demo_user_20260409', 'sk_demo_user_20260409');

INSERT INTO `interface_info`
(`id`, `name`, `description`, `url`, `method`, `requestParams`, `requestHeader`, `responseHeader`, `status`, `userId`)
VALUES
    (2001, 'Get User Name By POST', 'Gateway-ready sample interface for the public /api/name/user endpoint',
     'http://localhost:8123/api/name/user', 'POST',
     '[{\"name\":\"name\",\"type\":\"string\",\"required\":true,\"description\":\"User name field inside the JSON request body\"}]',
     '{\"Content-Type\":\"application/json\"}',
     '{\"Content-Type\":\"text/plain;charset=UTF-8\"}',
     1, 1001),
    (2002, 'Get Name By GET', 'Extra sample interface row to demonstrate offline interface management',
     'http://localhost:8123/api/name/', 'GET',
     '[{\"name\":\"name\",\"type\":\"string\",\"required\":true,\"description\":\"Query parameter name\"}]',
     '{}',
     '{\"Content-Type\":\"text/plain;charset=UTF-8\"}',
     0, 1001);

INSERT INTO `user_interface_info`
(`id`, `userId`, `interfaceInfoId`, `totalNum`, `leftNum`, `status`)
VALUES
    (3001, 1001, 2001, 12, 50, 0),
    (3002, 1002, 2001, 3, 10, 0),
    (3003, 1001, 2002, 4, 20, 0),
    (3004, 1002, 2002, 1, 5, 0);
