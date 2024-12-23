package com.example.chatdeneme3;

import java.util.Date;

abstract class Chat {
    String ID;
    Date OlusturlmaTarihi;
    Chat(String Id,Date OlusturulmaTarihi){
        this.ID=Id;
        this.OlusturlmaTarihi=OlusturulmaTarihi;
    }

}
