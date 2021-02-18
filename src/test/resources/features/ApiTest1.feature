#language: ru

Функция: 1. Создание, изменение, получение, удаление пользователя. Администратор системы

  Предыстория:
    Пусть В системе существует пользователь "Пользователь11" с параметрами:
      | Администратор | true |
      | Статус        | 1     |

  @CucumberTests
  Сценарий:1. Создание, изменение, получение, удаление пользователя. Администратор системы

    Если Отправить запрос на создание пользователя "Пользователь12" "пользователем" "Пользователь11" со статусом:2
    То Получен статус код ответа 201
    То Тело содержит данные созданного пользователя "Пользователь12"
    То В базе данных появилась запись с данными пользователя "Пользователь12"

    Если Отправить повторный запрос с тем же телом запроса
    То Получен статус код ответа 422


