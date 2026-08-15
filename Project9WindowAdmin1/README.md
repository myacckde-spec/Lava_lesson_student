# Project9WindowAdmin1 — Кабинет пользователя

Кратко

Это JavaFX-приложение (Maven) — окно "Кабинет пользователя", которое при старте ищет пользователя с логином `Admin` (создаёт, если нет), показывает его данные в полях и позволяет обновлять их. При сохранении пароль хранится как MD5. Проект рассчитан на локальное использование с MySQL (по умолчанию `root/root`).

Минимальные требования (Windows)
- JDK (совместимая версия; см. pom.xml). Убедитесь, что `java -version` работает.
- MySQL (по умолчанию ожидается на localhost:3306).
- Git (опционально).
- mvnw.cmd (в репо, используется для сборки/запуска).

Быстрый старт (минимум шагов)
1. Клонировать репозиторий и перейти в папку:
```bash
git clone https://github.com/myacckde-spec/Lava_lesson_student.git
cd Lava_lesson_student\Project9WindowAdmin1
```

2. Создать базу и таблицу в MySQL (один раз):
```sql
-- войти в MySQL: mysql -u root -p
CREATE DATABASE IF NOT EXISTS hibernate CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE hibernate;
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(255) UNIQUE NOT NULL,
  Email VARCHAR(255),
  Password VARCHAR(255)
);
```
(В проекте DB.java по умолчанию использует `root/root` и базу `hibernate`. При необходимости измените значения в `src/main/java/org/itproger/project9windowadmin1/controller/DB.java`.)

3. Собрать и запустить (Windows):
```powershell
.\mvnw.cmd clean package
.\mvnw.cmd javafx:run
```

Что проверить (коротко)
- При первом запуске приложение найдёт/создаст пользователя `Admin` и заполнит поля (login/email).
- Измените Email/login/пароль и нажмите "Изменить данные":
  - Если логин пуст — появится ошибка;
  - Если вводимый логин уже есть у другого пользователя — появится ошибка;
  - Если введён пароль — в БД он сохранится как MD5 (hex);
  - После успешного сохранения все поля очистятся.

Полезные команды (проверка в БД)
```sql
SELECT id, login, Email, Password FROM users WHERE login = 'Admin';
```
Пароль должен выглядеть как 32-символьный MD5 hex.

Изменение настроек подключения
- Файл: `src/main/java/org/itproger/project9windowadmin1/controller/DB.java`
- Измените константы HOST, PORT, DB_NAME, LOGIN, PASS под свою среду.

Что делать, если не хотите ставить MySQL
- Можно переключить проект на SQLite (файловая БД). Если нужно — я могу подготовить патч, чтобы запускать без установки MySQL.

Коммит/пуш (если ученик изменил код)
```bash
git add .
git commit -m "Implement/admin cabinet: test run"
git push origin <branch>
```

Частые проблемы
- Ошибка соединения: проверьте DB.java и работоспособность MySQL, порт 3306, учётные данные.
- Проблемы с JavaFX: используйте `mvnw javafx:run` или запустите из IDE с правильно настроенным JavaFX (зависимости в pom.xml уже есть).

Контактная помощь
Если нужно — могу:
- подготовить вариант с SQLite и закоммитить;
- добавить короткий чек-лист в виде файла в репо (сделал README);
- помочь отладить ошибки по логам — пришлите вывод консоли.

---
Файл автоматически добавлен в репозиторий. Проверяйте в `Project9WindowAdmin1/README.md`.
