# itShop - Spring Boot веб-приложение с авторизацией

Это полнофункциональное веб-приложение на Spring Boot с авторизацией, кабинетом пользователя и панелью администратора.

## 🚀 Функциональность

✅ **Авторизация и регистрация** - Регистрация новых пользователей, вход в систему  
✅ **Кабинет пользователя** - Редактирование профиля (логин, email, пароль)  
✅ **Система ролей** - USER, REDACTOR, ADMIN с разными правами доступа  
✅ **Админ-панель** - Управление пользователями и их ролями  
✅ **Каталог товаров** - Отображение всех товаров на главной странице  
✅ **Управление товарами** - Администраторы могут добавлять новые товары  

## 📋 Требования

- **Java 17+**
- **Maven 3.6+** (или используйте встроенный mvnw)
- **MySQL 5.7+** (или переключитесь на SQLite)
- **Git** (опционально)

## 🛠️ Быстрый старт (Windows)

### 1. Клонировать репозиторий

```bash
git clone https://github.com/myacckde-spec/Lava_lesson_student.git
cd Lava_lesson_student/itshop12Task
```

### 2. Создать базу данных MySQL

Откройте MySQL и выполните:

```sql
CREATE DATABASE IF NOT EXISTS itshop CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE itshop;
```

**Примечание:** База данных создастся автоматически при первом запуске благодаря `spring.jpa.hibernate.ddl-auto=update`

### 3. Собрать и запустить проект

```bash
mvn clean package
mvn spring-boot:run
```

Или через встроенный Maven Wrapper:

```bash
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

### 4. Открыть в браузере

Перейдите на: **http://localhost:8002/**

## 📝 Конфигурация БД

Файл: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/itshop
spring.datasource.username=root
spring.datasource.password=root
```

**Измените** `username` и `password` под вашу конфигурацию MySQL!

## 👥 Тестовые пользователи

После первого запуска создайте пользователей через форму регистрации:

1. **Обычный пользователь (USER)**
   - Логин: `user1`
   - Email: `user1@mail.com`
   - Пароль: `123456`

2. **Редактор (REDACTOR)**
   - Логин: `redactor1`
   - Email: `redactor1@mail.com`
   - Пароль: `123456`

3. **Администратор (ADMIN)**
   - Логин: `admin`
   - Email: `admin@mail.com`
   - Пароль: `123456`

*Роль ADMIN назначается только через админ-панель или прямо в БД!*

## 🗺️ Маршруты приложения

| URL | Описание | Доступ |
|-----|---------|--------|
| `/` | Главная страница с товарами | Все |
| `/about` | Страница "О нас" | Все |
| `/login` | Вход в систему | Гости |
| `/register` | Регистрация | Гости |
| `/user` | Кабинет пользователя | Авторизованные |
| `/admin` | Панель администратора | ADMIN |
| `/admin/add-product` | Добавление товара | ADMIN |
| `/logout` | Выход из системы | Авторизованные |

## 🎯 Основные возможности

### 📱 Кабинет пользователя (`/user`)

На этой странице авторизованный пользователь может:
- **Изменить логин** (проверка на уникальность)
- **Изменить email**
- **Изменить пароль** (пароль кодируется в BCrypt)
- **Просмотреть свою текущую роль** (редактирование только админом)

**Важно:** Параметр `Principal` автоматически передается Spring Security!

```java
@PostMapping("/user/update")
public String updateUser(Principal principal, ...) {
    User user = userService.findByUsername(principal.getName())...
}
```

### 🔐 Админ-панель (`/admin`)

Администраторы могут:
- **Просмотреть всех пользователей**
- **Изменить роль пользователя** (USER → REDACTOR → ADMIN)
- **Просмотреть товары каждого автора**
- **Добавить новый товар** (`/admin/add-product`)

### 📦 Управление товарами

- Каждый товар привязан к **автору** (User)
- Товары отображаются на главной странице
- Администраторы могут добавлять новые товары

## 🗄️ Структура базы данных

### Таблица `users`
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255),
  role ENUM('USER', 'REDACTOR', 'ADMIN') DEFAULT 'USER'
);
```

### Таблица `products`
```sql
CREATE TABLE products (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DOUBLE NOT NULL,
  author_id BIGINT NOT NULL,
  FOREIGN KEY (author_id) REFERENCES users(id)
);
```

## 📂 Структура проекта

```
itshop12Task/
├── pom.xml                              # Maven конфигурация
├── src/
│   ├── main/
│   │   ├── java/com/itproger/itshop/
│   │   │   ├── ItShopApplication.java   # Главный класс
│   │   │   ├── controller/              # Контроллеры
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── UserController.java
│   │   │   │   └── AdminController.java
│   │   │   ├── entity/                  # Модели данных
│   │   │   │   ├── User.java
│   │   │   │   └── Product.java
│   │   │   ├── repository/              # Репозитории (работа с БД)
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── ProductRepository.java
│   │   │   ├── service/                 # Бизнес-логика
│   │   │   │   ├── UserService.java
│   │   │   │   └── ProductService.java
│   │   │   └── config/                  # Конфигурация
│   │   │       ├── SecurityConfig.java
│   │   │       └── WebSecurityConfig.java
│   │   └── resources/
│   │       ├── application.properties   # Настройки приложения
│   │       ├── templates/               # HTML шаблоны (Thymeleaf)
│   │       │   ├── index.html
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── about.html
│   │       │   ├── user/
│   │       │   │   └── cabinet.html
│   │       │   └── admin/
│   │       │       ├── panel.html
│   │       │       └── add-product.html
│   │       └── static/
│   │           └── css/
│   │               └── style.css
```

## 🔧 Особенности реализации

### Spring Security

- **BCryptPasswordEncoder** для кодирования паролей
- **UserDetailsService** для загрузки пользователей из БД
- **Role-based access control** (RBAC)
- **CSRF protection** (отключена для упрощения)

### JPA / Hibernate

- Автоматическое создание таблиц (`ddl-auto=update`)
- Связи между сущностями (One-to-Many)
- Встроенные Repository методы

### Thymeleaf

- Динамические HTML шаблоны
- Интеграция с Spring Security (`sec:authorize`)
- Отображение ошибок и сообщений об успехе

## ⚠️ Важные моменты

1. **Пароли** кодируются в BCrypt, а не в MD5!
2. **Роли** хранятся в БД как ENUM (USER, REDACTOR, ADMIN)
3. **Principal** автоматически передается Spring Security в контроллеры
4. **CSRF защита** отключена для упрощения (включить в продакшене!)
5. **Логин чувствителен к регистру** (настройка в БД)

## 🐛 Решение проблем

### Ошибка подключения к БД

```
java.sql.SQLNonTransientConnectionException: Cannot connect to MySQL server
```

**Решение:**
1. Проверьте, что MySQL запущена: `net start MySQL80` (Windows)
2. Проверьте учетные данные в `application.properties`
3. Убедитесь, что БД `itshop` существует

### Ошибка портов

Если порт 8002 занят, измените в `application.properties`:

```properties
server.port=8003
```

### Проблемы с кодировкой

Если текст на русском отображается неправильно, добавьте в `application.properties`:

```properties
server.servlet.encoding.charset=UTF-8
server.servlet.encoding.enabled=true
```

## 📖 Дополнительные ресурсы

- [Spring Boot документация](https://spring.io/projects/spring-boot)
- [Spring Security Guide](https://spring.io/guides/gs/securing-web/)
- [Thymeleaf Tutorial](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

## 📝 Лицензия

Проект создан в образовательных целях.

---

**Автор:** Oleg (myacckde@gmail.com)  
**Дата создания:** 2026-09-04  
**Версия:** 1.0.0
