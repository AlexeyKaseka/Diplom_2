

package ru.practicum;


public class User {

    private String email;
    private String password;
    private String name;



    public String getPassword() {
        return password;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public User withName(String name) {
        this.name = name;
        return this;
    }


}