# Open API Backend

Open API Platform backend built with Spring Boot 2.7. It provides the core management APIs for users, interfaces, invoke quotas, analysis data, file upload, and the gRPC services used by the gateway for auth and quota checks.

## Current Features

- User login, registration, logout, admin management, and profile data
- Interface management and online/offline control
- User-interface invoke quota tracking and analysis
- Gateway-facing gRPC services for:
  - lookup by `accessKey`
  - quota reserve / commit / rollback
- COS-backed file upload for user avatar and other business files
- Knife4j API docs

## Tech Stack

- Spring Boot 2.7
- Spring MVC
- MyBatis + MyBatis-Plus
- Redis / Spring Session support
- gRPC
- MySQL
- Tencent COS

## Quick Start

1. Update the database config in `src/main/resources/application.yml`.
2. Run `sql/01_create_current_tables.sql` to create the current backend tables.
3. Run `sql/02_insert_sample_data.sql` to load local sample users, interfaces, and quota data.
4. Start the backend and open `http://localhost:8101/api/doc.html` for the API docs.

## Sample Data

- Admin account: `admin_demo`
- Demo account: `demo_user`
- Password for both accounts: `12345678`
- The sample data also creates:
  - one online `POST /api/name/user` interface row for gateway/public-interface testing
  - one extra interface row to demonstrate status management
  - related `user_interface_info` quota rows for analysis and invoke-count testing

## Optional Redis Session

If you want distributed session storage:

1. Fill in the Redis config in `application.yml`.
2. Uncomment `spring.session.store-type: redis`.
3. Remove the `exclude = {RedisAutoConfiguration.class}` part from `@SpringBootApplication` in `MainApplication`.
