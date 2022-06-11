# Exchange Rate Service

## Данный сервис создан для отбора на вакансию Junior Java Developer в Alfa Bank

Сервис обращается к сервису курсов валют, и отображает gif:

Запросы приходят на HttpEndpoint, туда передается код валюты по отношению с которой сравнивается USD

Пример запроса:

```
http://localhost:8080/api/currencies/afn
```

Пример ответа:

```
{
    "last": 88.998242,
    "yesterday": 88.998242,
    "gif": "https://giphy.com/embed/3ohjV4PgSCtflVFBXW"
}
```

- если курс по отношению к USD за сегодня стал выше вчерашнего, то сервис отдает рандомную gif отсюда https://giphy.com/search/rich
- если ниже - отсюда https://giphy.com/search/broke
- REST API курсов валют - https://docs.openexchangerates.org/
- REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide

## Запуск сервиса

## Docker

```
docker pull yamadahideki/exchange-rate-service
docker run -d -p 8080:8080 -t exchange-rate-service:0.0.2
```

## JDK 11
```
gradlew buid
java -jar build/libs/exchange-rate-service-0.0.2-SNAPSHOT.jar
```
