package net.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RolesTypes type = RolesTypes.USER;

    public Role(){

    }

    public Role(RolesTypes type){
        this.type = type;
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

    @Override
    public int hashCode() {
        return getType().name().hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null){
            return false;
        }

        if (obj == this){
            return true;
        }

        if (!(obj instanceof Role)){
            return false;
        }

        if (this.type == ((Role)obj).type){
            return true;
        }

        return false;
    }

    @Override
    public String getAuthority() {
        return "ROLE_"+getType().name();
    }
}
