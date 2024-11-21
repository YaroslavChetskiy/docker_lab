# Лабораторная работа "Docker: докеризация приложения"

<hr>

## Выполнение работы

**1)** Для начала было взято простенькое Spring-Boot приложение,
которое использует Hibernate и Spring Data Jpa для работы с БД (4 лабораторная по дисциплине "Базы данных"). 
Немного доделал до web-приложения - сделал контроллер с единственным эндпоинтом для получения всех журналов

**2)** В application.yml файле настроил datasource с использованием ENV-переменных + отключил liquibase от Spring'а:

```yaml
spring:
  liquibase:
    enabled: false
  datasource:
    url: ${DATASOURCE_URL:jdbc:postgresql://localhost:5432/db_practice}
    username: ${DATASOURCE_USERNAME:postgres}
    password: ${DATASOURCE_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver
```

**3)** Для миграций создал папку migrations/liquibase, в котором расположен master.xml (changelog-файл),
в который включил файл sql/init_db.sql - с созданием таблиц для БД

**4)** Создал Dockerfile:
```dockerfile
FROM gradle:jdk22-alpine AS build

WORKDIR /home/gradle/project

COPY build.gradle settings.gradle /home/gradle/project/
COPY src /home/gradle/project/src

RUN gradle clean build --no-daemon

FROM eclipse-temurin:22-jre-alpine

RUN apk add --no-cache bash curl && \
    rm -rf /var/cache/apk/*

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/db-practice.jar /app/app.jar

RUN chown -R appuser:appgroup /app

USER appuser

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
```

В нём происходит следующее: сначала проект собирается с помощью gradle,
создаются необходимые рабочие директории, копируются в них файлы,
затем создаётся непривилегированный пользователь и запускается приложение с необходимыми переменными окружения.

**5)** Наконец, создан docker-compose.yml файл:
```yaml
version: '3.8'

services:
  app:
    build:
      context: .
      dockerfile: Dockerfile
    image: db-practice:latest
    container_name: db-practice-container
    ports:
      - "8080:8080"
    environment:
      DATASOURCE_URL: jdbc:postgresql://db:5432/db_practice
      DATASOURCE_USERNAME: postgres
      DATASOURCE_PASSWORD: postgres
    depends_on:
      - db
      - liquibase-migrations
    networks:
      - backend

  liquibase-migrations:
    image: liquibase/liquibase:4.25
    container_name: liquibase-container
    depends_on:
      - db
    command:
      - --changeLogFile=master.xml
      - --driver=org.postgresql.Driver
      - --url=jdbc:postgresql://db:5432/db_practice
      - --username=postgres
      - --password=postgres
      - update
    volumes:
      - ./migrations/liquibase:/liquibase/changelog
    networks:
      - backend

  db:
    image: postgres:16-alpine
    container_name: postgres-container
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: db_practice
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    volumes:
      - postgresql:/var/lib/postgresql/data
    networks:
      - backend

volumes:
  postgresql: {}

networks:
  backend:
    driver: bridge
```

Что здесь происходит:
1. Определено несколько сервисов: app, liquibase-migrations, db
2. В сервисе app:
 - используем Dockerfile для сборки образа,
 - указываем названия для образа и контейнера
 - пробрасываем порта 8080 контейнера на порт 8080 хоста
 - указываем переменные окружения
 - указываем зависимости: сервисы db и liquibase-migrations.
 - подключаем к сети "backend"
3. В сервисе liquibase-migrations:
 - используем официальный образ liquibase
 - задаём имя контейнеру
 - задаём зависимость от сервиса db
 - задаём команду: указываем путь к changelog файлу,
указываем данные для подключения к БД, вызываем команду update для применения миграций
 - монтируем локальную директорию в контейнер
 - подключаем к сети "backend"
4. В сервисе db:
 - используем официальный образ PostgreSQL
 - указываем имя контейнера
 - пробрасываем порт 5432 контейнера на порт 5432 хоста
 - указываем переменные окружения
 - используем именованный том postgresql для хранения данных
 - подключаем к сети "backend"
5. В volumes указываем именованный том для хранения данных PostgreSQL
6. В networks указываем сеть backend, к которой подключены сервисы
(используется bridge, чтобы контейнеры могли общаться друг с другом напрямую)

**6)** Добавил .dockerignore файл, чтобы исключить ненужные файлы из контекста сборки

**7)** Собираем образы с помощью `docker-compose build`

**8)** Запускаем контейнеры с помощью `docker-compose up -d`

**9)** После запуска отправляем запрос на http://localhost:8080/magazines с помощью curl и получаем нужный json ответ:

```json
[
  {
    "id": 1,
    "title": "Майор Гром. Том 1. Чумной Доктор. Часть 1",
    "isbn": "9785001165699",
    "publicationYear": 2015,
    "price": 279
  },
  {
    "id": 2,
    "title": "Мироходцы. Книга 1: Кровь богов.",
    "isbn": "9784001165698",
    "publicationYear": 2019,
    "price": 750
  },
  {
    "id": 3,
    "title": "Ведьма и зверь. Том 1",
    "isbn": "9785907775336",
    "publicationYear": 2024,
    "price": 500
  },
  {
    "id": 4,
    "title": "Death Stranding. Часть 1",
    "isbn": "9783908875372",
    "publicationYear": 2021,
    "price": 280
  },
  {
    "id": 5,
    "title": "Бесобой. Том 1: Имя ему Бесобой",
    "isbn": "9782912843443",
    "publicationYear": 2014,
    "price": 650
  },
  {
    "id": 6,
    "title": "Бэтмен: Ночной бродяга",
    "isbn": "9781933321384",
    "publicationYear": 2020,
    "price": 210
  },
  {
    "id": 7,
    "title": "Микросупергерои. Самый живучий",
    "isbn": "9783738438685",
    "publicationYear": 2024,
    "price": 980
  }
]
```