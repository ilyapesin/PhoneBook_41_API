package models;

public class AuthRequestModel {

    private String username;
    private String password;

    public static AuthRequestModel username(String username) {

        return new AuthRequestModel(username, null);
    }
    public AuthRequestModel password(String password) {
        this.password=password;
        return this;
    }

    public AuthRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthRequestModel(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
