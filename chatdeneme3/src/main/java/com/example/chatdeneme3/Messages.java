package com.example.chatdeneme3;

import java.util.Date;

public class Messages extends Chat {
    private String sender;// Göndericinin ismi
    private String receiver;//Kullanıcı ismi

    Messages(String Id, Date OlusturulmaTarihi, String sender, String receiver) {
        super(Id, OlusturulmaTarihi);
        System.out.println("Mesaj paketi oluşturuldu");//Her mesaj paketi oluşturulduğunda konsola bilgi verilir
        this.sender = sender;
        this.receiver = receiver;
        storeData(Id, OlusturulmaTarihi);
    }

    //Gerekli bilgilerler mesaj paketini dosyaya kaydeder
    public void storeChatData(String ID, Date date, String sender, String receiver) {
        System.out.println("Bilgiler dosyaya kaydedildi");
    }

    //Yıldızlı mesajları kaydetmek için kullanılan metot
    public void storeFavoritesChatData() {
        System.out.println("Favori mesajlar dosyaya kaydedildi");
    }

    @Override//Miras alından storeDate metodu bilgileri kaydetmek üzere yeniden düzenleniyor...
    public void storeData(String ID, Date date) {
        System.out.println("Bilgiler Dosyaya kaydediliyor");
        storeChatData(ID, date, this.sender, this.receiver);
    }

    //Yıldızlı mesajları dosyalama için kullanılan metotdur.(Overloading)
    public void storeData(String ID, Date date, String sender, String receiver) {
        System.out.println("Bilgiler Dosyaya kaydediliyor");
        storeFavoritesChatData();
    }


    public String getSender() {
        return sender;
    }//Mesaj atan kişiyi geri döndürür

    public String getReceiver() {
        return receiver;
    }//kullanıcı ismini geri döndürür
}
