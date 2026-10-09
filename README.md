# Notification Service (Refactored)

Бұл жоба — хабарлама жіберу қызметін (Email, Telegram, SMS, WhatsApp) **Factory Method** және **Strategy** паттерндері арқылы қайта жасақтау (refactoring) мысалы. Негізгі мақсат — **SOLID** принциптерінің бірі болып табылатын **Open/Closed Principle (OCP)** сақталуын қамтамасыз ету.

---

## 1. Applied Design Patterns (Қолданылған дизайн паттерндері)

| # | Pattern | Where / Implementation | Purpose (Мақсаты) |
|---|---|---|---|
| 1 | **Factory Method** | `ContactChannel` (абстрактілі класс) және оның мұрагерлері (`EmailChannel`, `TelegramChannel`, `SmsChannel`, `WhatsAppChannel`) | `createNotifier()` әдісі арқылы нақты хабарлама жіберуші объектілерді (*Notifier*) клиенттік кодтан оқшаулап құрастыру. |
| 2 | **Strategy** | `Notifier` (интерфейс) және оның орындаушылары (`EmailNotifier`, `TelegramNotifier`, `SmsNotifier`, `WhatsAppNotifier`) | Хабарлама жіберудің әртүрлі алгоритмдерін жеке кластарға бөліп, оларды орындалу барысында (*runtime*) динамикалық түрде ауыстыруға мүмкіндік беру. |

---

## 2. Жобада енгізілген негізгі өзгерістер (What was changed?)

1. **`if-else` монолиті жойылды:**
   - **Бұрын (legacy code):** Хабарлама түріне байланысты (`SMS`, `Email`, `WhatsApp`) барлық логика бір орында шартты операторлар арқылы орындалатын.
   - **Қазір (refactored code):** Әрбір хабарлама түрі жеке классқа бөлінді.
2. **Арналарды кеңейту жеңілдетілді (OCP принципі):**
   - Жаңа арна (мысалы, `WhatsApp`) қосу үшін бұрыннан бар кодты өзгертпей-ақ, тек жаңа `WhatsAppChannel` және `WhatsAppNotifier` кластарын қосу жеткілікті болды.
3. **Кодтың құрылымы мен пакеттері реттелді:**
   - Пакеттер арасындағы конфликттер жойылып, `before` және `after` логикалары оқшауланды.

---

## 3. Кодты жазу және рефакторинг кезінде кездескен қиындықтар (Challenges Encountered)

- **Пакеттер мен кластардың атауларындағы конфликттер (Package Naming & Scope Conflicts):**
  - **Қиындық:** `before` және `after` папкаларындағы кластардың (мысалы, `Main` және арна кластары) аты бірдей болғандықтан, Java компиляторы оларды шатастырып, орындау кезінде қателіктер туындады.
  - **Шешімі:** Пакет құрылымдары нақтыланып, `Main.java` файлдарының шақырылуы тексерілді (`after.Main` және `before.Main`).
- **Factory және Strategy паттерндерінің жауапкершілігін бөлу (Separation of Concerns):**
  - **Қиындық:** Нені Фабрика жасайды және нені Стратегия орындайды деген аражікті дұрыс ажырату.
  - **Шешімі:** `ContactChannel` тек объектіні құрастыруға (Factory), ал `Notifier` тек хабарламаны орындап/жіберуге (Strategy) жауап беретіндей етіп сәтті бөлінді.
- **Монолитті кодтан OCP-ге өту барысындағы логикалық ауысу:**
  - Шартты монолитті кодты бұзбай, жұмыс істеп тұрған функционалдылықты сақтай отырып қайта жазу.

---

## 4. Толық UML Архитектура диаграммасы

```mermaid
classDiagram
    class Notifier {
        <<interface>>
        +notify(String message) void
    }

    class EmailNotifier {
        +notify(String message) void
    }
    class TelegramNotifier {
        +notify(String message) void
    }
    class SmsNotifier {
        +notify(String message) void
    }
    class WhatsAppNotifier {
        +notify(String message) void
    }

    Notifier <|.. EmailNotifier
    Notifier <|.. TelegramNotifier
    Notifier <|.. SmsNotifier
    Notifier <|.. WhatsAppNotifier

    class ContactChannel {
        <<abstract>>
        +createNotifier() Notifier
        +send(String message) void
    }

    class EmailChannel {
        +createNotifier() Notifier
    }
    class TelegramChannel {
        +createNotifier() Notifier
    }
    class SmsChannel {
        +createNotifier() Notifier
    }
    class WhatsAppChannel {
        +createNotifier() Notifier
    }

    ContactChannel <|-- EmailChannel
    ContactChannel <|-- TelegramChannel
    ContactChannel <|-- SmsChannel
    ContactChannel <|-- WhatsAppChannel

    ContactChannel ..> Notifier : Creates
    Main ..> ContactChannel : Uses