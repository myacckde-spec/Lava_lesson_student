# Project9WindowAdmin1_SQLite — Кабинет пользователя (SQLite)

Кратко

Это JavaFX-приложение (Maven) — окно "Кабинет пользователя", но использующее SQLite (файловая БД) вместо MySQL. Подходит для быстрого запуска без настройки сервера БД.

Минимальные требования (Windows)
- JDK (совместимая версия; см. pom.xml).
- mvn (или используйте mvnw из основного модуля) — в этом модуле mvnw не скопирован, поэтому рекомендуется иметь Maven. Если хотите — я добавлю mvnw в этот модуль.

Быстрый старт (минимум шагов)
1. Клонировать репозиторий и перейти в папку модуля SQLite:
   git clone https://github.com/myacckde-spec/Lava_lesson_student.git
   cd Lava_lesson_student\Project9WindowAdmin1_SQLite

2. Собрать и запустить (Windows):
   mvn clean package
   mvn javafx:run

   Если у вас нет mvn, вернитесь в основной модуль и используйте mvnw из Project9WindowAdmin1:
   cd ..\Project9WindowAdmin1
   .\mvnw.cmd -f ..\Project9WindowAdmin1_SQLite\pom.xml clean package
   .\mvnw.cmd -f ..\Project9WindowAdmin1_SQLite\pom.xml javafx:run

Что проверить (коротко)
- При первом запуске приложение создаст файл базы `Project9WindowAdmin1_SQLite/db/users.db` и таблицу `users`, затем найдёт/создаст пользователя `Admin` и заполнит поля (login/email).
- Измените Email/login/пароль и нажмите "Изменить данные":
  - Если логин пуст — появится ошибка;
  - Если вводимый логин уже есть у другого пользователя — появится ошибка;
  - Если введён пароль — в БД он сохранится как MD5 (hex);
  - После успешного сохранения все поля очистятся.

Пояснение по БД
- Файл базы: `Project9WindowAdmin1_SQLite/db/users.db` (создаётся автоматически при первом подключении).
- SQL-таблица создаётся автоматом при запуске.

Коммит/пуш (если ученик изменил код)
```bash
git add .
git commit -m "Test: sqlite admin cabinet"
git push origin <branch>
```

Если хотите, чтобы я добавил mvnw в этот модуль (чтобы запускать без установки Maven) — напишите «Добавь mvnw в SQLite-модуль», и я добавлю wrapper и .mvn.
