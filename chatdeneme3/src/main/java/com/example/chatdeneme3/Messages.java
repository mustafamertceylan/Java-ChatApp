package com.example.chatdeneme3;

import java.util.Date;

public class Messages extends Chat{
    private String sender;
    private  String receiver;

    Messages(String Id, Date OlusturulmaTarihi) {
        super(Id, OlusturulmaTarihi);
    }
}
