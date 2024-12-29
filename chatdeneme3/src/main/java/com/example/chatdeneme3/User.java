package com.example.chatdeneme3;

import java.util.Date;
//Kullanıcı bilgilerinin tutulduğu sınıf
public class User extends Chat {
    public String nickName;
    private String UserName;
    private String UserLastName;
    private String UserPassword;

    //parametreli constructor
    User(String UserName, String UserLastname, String UserPassword, String nickName) {
        super("1234" + UserName, new Date());//Üst sınıfın constructor'ına çağrı
        this.nickName = nickName;
        this.UserName = UserName;
        this.UserLastName = UserLastname;
        this.UserPassword = UserPassword;
        storeData(ID, OlusturlmaTarihi);
    }

    //Nesne özelliklerimiz private olduğu için getter ve setter metotları
    public String getUserName() {
        return UserName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserLastName() {
        return UserLastName;
    }

    public void setUserLastName(String userLastName) {
        UserLastName = userLastName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    @Override//Üst sınıftan miras alınan storeDate metodu
    public void storeData(String ID, Date date) {
        System.out.println("Bilgiler Dosyaya kaydediliyor");
        storeUserData(UserName, UserLastName, UserPassword, nickName);
    }
    //Miras alınan metodun parametrelerine ekleme yaparak ayrıntılı bir dosyalama işlemi gerçekleştirilir(Overloading)
    public void storeUserData(String UserName, String UserLastname, String UserPassword, String nickName) {
        System.out.println("Bilgiler dosyaya kaydedildi");
    }
}
