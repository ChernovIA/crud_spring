package net.model;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
public class Roles {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RolesTypes type = RolesTypes.USER;

    public Roles(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RolesTypes getType() {
        return type;
    }

    public void setType(RolesTypes type) {
        this.type = type;
    }
}
