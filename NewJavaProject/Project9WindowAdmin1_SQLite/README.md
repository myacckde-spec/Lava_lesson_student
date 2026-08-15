# Project9WindowAdmin1_SQLite — Кабинет пользователя (SQLite)

Кратко

Это JavaFX-приложение (Maven) — окно "Кабинет пользователя", но использующее SQLite (файловая БД) вместо MySQL. Подходит для быстрого запуска без настройки сервера БД.

Минимальные требования (Windows)
- JDK (совместимая версия; см. pom.xml).
- mvn (или используйте mvnw из модуля Project9WindowAdmin1_SQLite — он уже включён здесь).

Быстрый старт (минимум шагов)
1. Клонировать репозиторий и перейти в папку модуля SQLite:
   git clone https://github.com/myacckde-spec/Lava_lesson_student.git
   cd Lava_lesson_student\NewJavaProject\Project9WindowAdmin1_SQLite

2. Собрать и запустить (Windows):
   .\mvnw.cmd clean package
   .\mvnw.cmd javafx:run

Что проверить (коротко)
- При первом запуске приложение создаст файл базы `NewJavaProject/Project9WindowAdmin1_SQLite/db/users.db` и таблицу `users`, затем найдёт/создаст пользователя `Admin` и заполнит поля (login/email).
- Измените Email/login/пароль и нажмите "Изменить данные":
  - Если логин пуст — появится ошибка;
  - Если вводимый логин уже есть у другого пользователя — появится ошибка;
  - Если введён пароль — в БД он сохранится как MD5 (hex);
  - После успешного сохранения все поля очистятся.

Пояснение по БД
- Файл базы: `NewJavaProject/Project9WindowAdmin1_SQLite/db/users.db` (создаётся автоматически при первом подключении).
- SQL-таблица создаётся автоматом при запуске.

Коммит/пуш (если ученик изменил код)
```bash
git add .
git commit -m "Test: sqlite admin cabinet"
git push origin <branch>
```

Если хотите, чтобы я добавил дополнительные инструкции или видео по запуску — скажите.
