package com.example.chatdeneme3;

import java.util.Date;

public class Messages extends Chat {
    private String sender;
    private String receiver;

    Messages(String Id, Date OlusturulmaTarihi, String sender, String receiver) {
        super(Id, OlusturulmaTarihi);
        System.out.println("Mesaj paketi oluşturuldu");
        this.sender = sender;
        this.receiver = receiver;
        storeData(Id, OlusturulmaTarihi);
    }

    public void storeChatData(String ID, Date date, String sender, String receiver) {
        System.out.println("Bilgiler dosyaya kaydedildi");
    }

    public void storeFavoritesChatData() {
        System.out.println("Favori mesajlar dosyaya kaydedildi");
    }

    @Override
    public void storeData(String ID, Date date) {
        System.out.println("Bilgiler Dosyaya kaydediliyor");
        storeChatData(ID, date, this.sender, this.receiver);
    }

    public void storeData(String ID, Date date, String sender, String receiver) {
        System.out.println("Bilgiler Dosyaya kaydediliyor");
        storeFavoritesChatData();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
