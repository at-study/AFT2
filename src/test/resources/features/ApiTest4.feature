#language: ru

Функция: 4. Удаление пользователей. Пользователь без прав администратора

  Предыстория:
    Пусть В системе существует пользователь "Пользователь21" с параметрами:
      | Администратор  | false |
      | Статус | 1     |
    И У пользователя есть доступ к API и ключ API
  @generation_sample
  Сценарий:
    Если Отправить запрос на создание пользователя "user22" с параметрами:
    |Администратор|false|
    |Статус|1   |
    То Получен статус код ответа 403
