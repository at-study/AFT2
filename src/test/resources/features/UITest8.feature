#language: ru

Функция: 8. Администрирование. Создание пользователя.

  Предыстория:
    Пусть В системе существует пользователь "Пользователь11" с параметрами:
      | Администратор  | true |
      | Статус | 1    |
    И Открыт браузер на главной странице

  @generation_sample
  Сценарий:
    Если Авторизоваться пользователем "Пользователь11"
    То На странице "Заголовок" отображается элемент "Домашняя страница"