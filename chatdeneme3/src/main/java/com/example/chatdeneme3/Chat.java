package com.example.chatdeneme3;

import java.util.Date;

// `Chat` sınıfı, `Storable` arayüzünü uygulayan bir soyut sınıftır.
abstract class Chat implements Storable {
    String ID;
    Date OlusturlmaTarihi;
    Chat(String Id, Date OlusturulmaTarihi) {
        this.ID = Id;
        this.OlusturlmaTarihi = OlusturulmaTarihi;
    }

    // Interface de tanımlanan metodun override işlemi
    @Override
    public abstract void storeData(String ID, Date date);

}
