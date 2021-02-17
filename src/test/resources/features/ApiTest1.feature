#language: ru

Функция: 01. Создание пользователя. Пользователь без прав администратора

  Предыстория:
    Пусть В системе существует пользователь "Пользователь11" с параметрами:
      | Администратор | false |
      | Статус        | 1     |

  @CucumberTests
  Сценарий:01. Создание пользователя. Пользователь без прав администратора
    Если Отправить запрос на создание пользователя "Пользователь12" пользователем "Пользователь11" с параметрами::
      | Администратор | false |
      | Статус        | 1     |
    То Получен статус код ответа 403

