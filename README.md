# Сайт новостной газеты <b>Daily Buggle</b>.

## Используемый стек

### Frontent
- Vue3 (Composition API)
- Vuetify
- Pinia

### Backend
- Java 8
- Spring Boot 2.7.13
- PostgreSQL

### Схема БД
![Схема БД](https://github.com/Potapov-AA/spring-vue-newspaper/blob/master/scheme.jpg)

---

## Реализован следующий функционал:

1. Пагинация страниц, на одной страницы расположены 3 статьи.
2. Для больших статьей предусмотрена возможность отображения и прятать текст.
3. Предумотрена регистрация пользователей и авторизация через JWT.
4. Авторизованные пользователи могут оставлять комментарии и ставить лайки.
5. Авторизованные пользователи могут выбирать любимые и запретные темы, сначала будут идти статьи с любимыми темами отсортированные по дате, после статьи без помеченных тематик, статьи с запретными темами отображаться не будут.
6. Реализованы функции администратора:
    * Добавление новых статьей.
    * Редактирование статьей.
    * Удаление статьей.
    * Удаление комментариев.
---
## Готовые пользователи после инициализации БД:

<b>ADMIN:</b>
1. <b>Login:</b> admin@gmail.com <b>password:</b> 1234567890

<b>USER:</b>
1. <b>Login:</b> user@gmail.com <b>password:</b> 1234567890
2. <b>Login:</b> jester@gmail.com <b>password:</b> 1234567890

<i>Примечание:</i> по умолчанию все пользователи создаются с ролью USER, роль ADMIN задается только через БД.

Запустить проект можно следующими спосабами<!-- Вставить ссылку на инциализацю проекта -->

---

## Запуск через Docker
Чтобы запустить через Docker необходимо сдлеать следюущие действия

1. Сформировать .jar файл для бекенда, сделать это можно несколькими спосабами:
    * Через Eclips испоьзовать <i>Maven build... </i> команда <b>clean package -Dskip Tests</b>
    * Через консоль находясь в папке backend через команду <b>mvn clean package -Dskip Tests</b>
        * <i>Примечание. Для второго способа нужен установленный Maven и прописаны маршруты в Переменных среды.</i> 
2. Выполнить команду <b>Docker-compose build</b> из папки проекта.
3. Выполнить команду <b>Docker-compose up</b> из папки проекта.

<i>Примечание. БД - будет запущено на порту <b>5432</b>. Backend на порту <b>8081</b>. Frontend на порту <b>8080</b>. При необходимости порты можно поменять в файле [docker-compose.yml](https://github.com/Potapov-AA/spring-vue-newspaper/blob/master/docker-compose.yml)</i>

---

## Запуск отдельными приложениями
1. Заполнить БД с помощью файла [init.sql](https://github.com/Potapov-AA/spring-vue-newspaper/blob/master/DB/init.sql). Подключение идет к БД <b>newspaper</b>, если используется другое имя, то следует это поменять в [application.yml](https://github.com/Potapov-AA/spring-vue-newspaper/blob/master/backend/src/main/resources/application.yml).
2. Изменить остальные данные для подключения к БД в [application.yml](https://github.com/Potapov-AA/spring-vue-newspaper/blob/master/backend/src/main/resources/application.yml). При необходимости отображения SQL запросов поменять <b>show-sql</b> на true.
3. Запустить проект Java из папки backend. Приложение запуститься на порту <b>8081</b>, его можно также поменять в [application.yml](https://github.com/Potapov-AA/spring-vue-newspaper/blob/master/backend/src/main/resources/application.yml).
4. Запустить Vue-проект из папки frontend с помощью команды <b>npm run dev</b>. Приложение запуститься на порту <b>8000</b>, поменять его можно в файле [vite.config](https://github.com/Potapov-AA/spring-vue-newspaper/blob/master/frontend/vite.config.js).



<!-- TODO: отдельный блок для БД, приложить схему БД -->

<!-- TODO: отдельный блок для Docker, о том как запустить -->

<!-- TODO: отдельный блок о том как запустить проект без DOCKER -->

<!-- TODO: в блоке про запуск привести пример application.yml -->
