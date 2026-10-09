package after;

import java.util.HashMap;
import java.util.Map;

class User {
    private String name;
    private String contact;

    public User(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getContact() { return contact; }
    public String getName() { return name; }
}

interface Notifier {
    void send(User to, String message);
}

interface ContactChannel {
    String code();
    Notifier createNotifier();
}

class EmailChannel implements ContactChannel {
    public String code() { return "email"; }
    public Notifier createNotifier() {
        return (to, msg) -> System.out.println("  [Email " + to.getContact() + "] " + msg);
    }
}

class TelegramChannel implements ContactChannel {
    public String code() { return "telegram"; }
    public Notifier createNotifier() {
        return (to, msg) -> System.out.println("  [Telegram " + to.getContact() + "] " + msg);
    }
}

class SmsChannel implements ContactChannel {
    public String code() { return "sms"; }
    public Notifier createNotifier() {
        return (to, msg) -> System.out.println("  [SMS " + to.getContact() + "] " + msg);
    }
}

class NotifierFactory {
    private static final Map<String, ContactChannel> CHANNELS = new HashMap<>();

    public static void register(ContactChannel channel) {
        CHANNELS.put(channel.code().toLowerCase(), channel);
    }

    public static Notifier create(String channelName) {
        ContactChannel channel = CHANNELS.get(channelName.toLowerCase());
        if (channel == null) {
            throw new IllegalArgumentException("Белгісіз арна: " + channelName);
        }
        return channel.createNotifier();
    }
}

class WhatsAppChannel implements ContactChannel {
    public String code() { return "whatsapp"; }
    public Notifier createNotifier() {
        return (to, msg) -> System.out.println("  [WhatsApp " + to.getContact() + "] " + msg);
    }
}

class Main {
    public static void main(String[] args) {
        NotifierFactory.register(new EmailChannel());
        NotifierFactory.register(new TelegramChannel());
        NotifierFactory.register(new SmsChannel());
        NotifierFactory.register(new WhatsAppChannel());

        User user = new User("Айбек", "+77011234567");

        Notifier sms = NotifierFactory.create("sms");
        sms.send(user, "SMS хабарламасы!");

        Notifier wa = NotifierFactory.create("whatsapp");
        wa.send(user, "WhatsApp хабарламасы!");
    }
}