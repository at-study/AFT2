#language: ru
Функция: 3. Авторизация неподтвержденным пользователем

  Предыстория:
    Пусть В системе существует пользователь "Пользователь31" с параметрами:
      | Администратор  | false |
      | Статус | 2    |
    И Открыт браузер на главной странице

  @generation_sample
  Сценарий:3. Авторизация неподтвержденным пользователем
    Если Авторизоваться пользователем "Пользователь31"
    То На странице "Заголовок" отображается элемент "Домашняя страница"
    И Для "Пользователь31" отображается ошибка "Вошли как" с текстом "Your account was created and is now pending administrator approval."
    И На странице "Заголовок" не отображается элемент "Моя страница"
    И На странице "Заголовок" отображается элемент "Войти"
    И На странице "Заголовок" отображается элемент "Регистрация"