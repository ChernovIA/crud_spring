package net.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Roles> roles = new HashSet<>();

    public User(){

    }
    public User(String login, String password) {
        this(0,login,password,login, null);
    }

    public User(String login, String password, String name) {
        this(0,login,password, name, null);
    }

    public User(String login, String password, String name, Set<Roles> roles) {
        this(0,login,password, name, roles);
    }

    public User(long id, String login, String password, String name, Set<Roles> roles) {
        Set<Roles> newRoles = roles;
        if (newRoles == null) {
            newRoles = new HashSet<>();
            newRoles.add(new Roles());
        }

        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.roles = newRoles;
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

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return getLogin()   + " - "
                + getName() + " - "
                + getPassword();
    }
}
