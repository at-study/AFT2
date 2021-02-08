#language: ru
Функция: 6. Администрирование. Сортировка списка пользователей по пользователю

  Предыстория:
    Пусть В системе существует пользователь "Пользователь11" с параметрами:
      | Администратор  | true |
      | Статус | 1    |
    И Открыт браузер на главной странице

  @generation_sample
  Сценарий:
    Если Авторизоваться пользователем "Пользователь11"
    То На странице "Заголовок" отображается элемент "Домашняя страница"

    И Отображается "Вошли как Пользователь11"

    И На странице "Заголовок" отображается элемент "Домашняя страница"
    И На странице "Заголовок" отображается элемент "Моя страница"
    И На странице "Заголовок" отображается элемент "Проекты"
