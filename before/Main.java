
class BeforeUser {
    private String name;
    private String contact;

    public BeforeUser(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getContact() { return contact; }
}

interface BeforeNotifier {
    void send(BeforeUser to, String message);
}

class EmailNotifier implements BeforeNotifier {
    public void send(BeforeUser to, String message) {
        System.out.println("  [Email " + to.getContact() + "] " + message);
    }
}

class TelegramNotifier implements BeforeNotifier {
    public void send(BeforeUser to, String message) {
        System.out.println("  [Telegram " + to.getContact() + "] " + message);
    }
}

class SmsNotifier implements BeforeNotifier {
    public void send(BeforeUser to, String message) {
        System.out.println("  [SMS " + to.getContact() + "] " + message);
    }
}

class NotifierFactory {
    public static BeforeNotifier create(String channel) {
        if ("telegram".equalsIgnoreCase(channel)) {
            return new TelegramNotifier();
        } else if ("sms".equalsIgnoreCase(channel)) {
            return new SmsNotifier();
        } else {
            return new EmailNotifier();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BeforeUser user = new BeforeUser("Айбек", "+77011234567");
        BeforeNotifier notifier = NotifierFactory.create("sms");
        notifier.send(user, "Бастапқы код бойынша хабарлама!");
    }
}