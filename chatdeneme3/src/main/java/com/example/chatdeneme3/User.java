package com.example.chatdeneme3;
import java.util.Date;
public class User extends Chat{
    public String nickName;
    private String UserName;
    private String UserLastName;
    private String UserPassword;
    User(String UserName,String UserLastname,String UserPassword,String nickName){
        super("1234"+UserName,new Date());
        this.nickName=nickName;
        this.UserName=UserName;
        this.UserLastName=UserLastname;
        this.UserPassword=UserPassword;
    }

    public String getUserName() {
        return UserName;
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


}