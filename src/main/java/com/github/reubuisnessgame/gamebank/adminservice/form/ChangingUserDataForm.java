package com.github.reubuisnessgame.gamebank.adminservice.form;

public class ChangingUserDataForm {

    private Object user;

    private String token;

    public ChangingUserDataForm() {
    }

    public ChangingUserDataForm(Object user, String token) {
        this.user = user;
        this.token = token;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
