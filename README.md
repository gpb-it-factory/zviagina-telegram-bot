# zviagina-telegram-bot
Репозиторий для работы над учебным telegram-ботом в проекте "Мини-банк". Telegram-бот будет выступать в качестве клиентского приложения и инициировать запросы пользователей. 

## Схема проекта

![img.png](img.png)

## План проекта

```plantuml
@startuml
actor User
participant TelegramBot
participant MiddlewareBackend
participant Backend

User -> TelegramBot: Отправка запроса
TelegramBot -> MiddlewareBackend: Передача запроса
MiddlewareBackend -> Backend: Передача запроса
Backend --> MiddlewareBackend: Ответ
MiddlewareBackend --> TelegramBot: Ответ
TelegramBot --> User: Ответ
@enduml
```

![img_2.png](img_2.png)![img_1.png](img_1.png)

## Запуск проекта
* Клонируйте репозиторий на локальную машину: `git clone <git@github.com:gpb-it-factory/zviagina-telegram-bot.git>`.
* Установите необходимые зависимости: `mvn install` (если используется Maven).
* Настройте файл application.properties, указав необходимые параметры подключения к базе данных и настройки бота.
* Запустите приложение: `mvn spring-boot:run`.

## Использование
* Найдите бота в Telegram по имени или используйте ссылку на [бота](здесь_однажды_будет_ссылка).
* Запустите бота и следуйте инструкциям для использования функционала "Мини-банка".