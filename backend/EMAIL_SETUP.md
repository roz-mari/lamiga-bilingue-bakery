# Настройка отправки email для формы обратной связи

## Текущее состояние

Сейчас все сообщения с формы обратной связи:
1. **Сохраняются в файл** `data/contact.jsonl` на сервере Render (backup)
2. **Отправляются на email** (если настроено)

## Настройка отправки на email

### Вариант 1: Gmail (рекомендуется)

1. **Создайте App Password для Gmail:**
   - Перейдите в [Google Account Settings](https://myaccount.google.com/)
   - Security → 2-Step Verification (должна быть включена)
   - App passwords → Generate app password
   - Выберите "Mail" и "Other (Custom name)" → введите "La Miga Backend"
   - Скопируйте сгенерированный пароль (16 символов)

2. **Настройте Environment Variables на Render:**
   - Откройте ваш сервис на Render → Settings → Environment
   - Добавьте следующие переменные:

   ```
   CONTACT_EMAIL_ENABLED=true
   CONTACT_EMAIL_TO=ваш-email@gmail.com
   CONTACT_EMAIL_FROM=ваш-email@gmail.com
   SPRING_MAIL_HOST=smtp.gmail.com
   SPRING_MAIL_PORT=587
   SPRING_MAIL_USERNAME=ваш-email@gmail.com
   SPRING_MAIL_PASSWORD=ваш-app-password-16-символов
   SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
   SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
   ```

3. **Перезапустите сервис на Render**

### Вариант 2: Другой email провайдер

Настройте переменные окружения аналогично, изменив:
- `SPRING_MAIL_HOST` - SMTP сервер вашего провайдера
- `SPRING_MAIL_PORT` - обычно 587 (TLS) или 465 (SSL)
- `SPRING_MAIL_USERNAME` и `SPRING_MAIL_PASSWORD` - ваши учетные данные

**Примеры SMTP серверов:**
- **Outlook/Hotmail**: `smtp-mail.outlook.com`, порт `587`
- **Yahoo**: `smtp.mail.yahoo.com`, порт `587`
- **SendGrid**: `smtp.sendgrid.net`, порт `587`
- **Mailgun**: `smtp.mailgun.org`, порт `587`

### Без настройки email

Если не настроить email, форма всё равно будет работать:
- Сообщения будут сохраняться в файл `data/contact.jsonl` на сервере
- В логах Render будет сообщение: `[EmailService] Email отправка отключена`

## Проверка работы

1. Отправьте тестовое сообщение через форму на сайте
2. Проверьте ваш email (если настроен)
3. Проверьте логи на Render - должно быть: `[EmailService] Письмо успешно отправлено на ваш-email@gmail.com`

## Доступ к файлу с сообщениями

Файл `data/contact.jsonl` находится на сервере Render. Чтобы получить доступ:
1. Используйте SSH (если доступен)
2. Или добавьте endpoint для просмотра сообщений (можно добавить позже)

