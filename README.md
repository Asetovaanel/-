# Notification Service (Refactored)

Бұл жоба — хабарлама жіберу қызметін (Email, Telegram, SMS, WhatsApp) **Factory** және **Strategy** паттерндері арқылы қайта жасақтау (refactoring) мысалы.

## Жоба құрылымы

- `before/` — Тазаланбаған, Open/Closed Principle (OCP) принципін бұзатын бастапқы код.
- `after/` — Рефакторинг жасалған, жаңа арналарды (мысалы, WhatsApp) оңай қосуға мүмкіндік беретін икемді код.

## Архитектура диаграммасы

```mermaid
graph TD
    Main --> ContactChannel
    ContactChannel --> EmailChannel
    ContactChannel --> TelegramChannel
    ContactChannel --> SmsChannel
    ContactChannel --> WhatsAppChannel
    EmailChannel --> EmailNotifier
    TelegramChannel --> TelegramNotifier
    SmsChannel --> SmsNotifier
    WhatsAppChannel --> WhatsAppNotifier