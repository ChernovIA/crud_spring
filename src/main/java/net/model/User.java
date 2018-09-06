package net.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return getLogin()   + " - "
                + getRole() + " : "
                + getName() + " - "
                + getPassword();
    }

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public User(){

    }
    public User(String login, String password) {
        this(0,login,password,login, Roles.USER);
    }

    public User(String login, String password, String name) {
        this(0,login,password, name, Roles.USER);
    }

    public User(String login, String password, String name, String role) {
        this(0,login,password, name, role);
    }

    public User(long id, String login, String password, String name, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
