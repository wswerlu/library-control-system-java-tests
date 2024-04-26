# Автотесты для API [library-control-system](https://github.com/wswerlu/library-control-system)

## Стек технологий
* Java 19
* Spring Boot
* Gradle
* Lombok
* JUnit5
* Rest Assured
* OpenApi Generator
* Docker
* Линтеры: Checkstyle, PMD
* Pre-commit hook

## Запуск тестов

Реализован запуск тестов с помощью GitHub Action: [Run tests and generate allure report](https://github.com/wswerlu/library-control-system-java-tests/blob/master/.github/workflows/run_tests.yml)
Шаги:
* приложение запускается в контейнере
* запускаются тесты
* собирается отчет allure
* результаты прогона выводятся на GitHub Pages (https://wswerlu.github.io/library-control-system-java-tests)

Тесты можно запускать по тегам:
* `library-control-system` - будут запущены все тесты из пакета `org.library.library_control_system`
* `author` - будут запущены тесты класса `AuthorTests`
* `book` - будут запущены тесты класса `BookTests`