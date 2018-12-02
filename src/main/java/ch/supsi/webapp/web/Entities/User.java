package ch.supsi.webapp.web.Entities;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(length = 30)
    private String username;
    private String name;
    private String surname;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_role")
    private Role role;

    public User(){}

    public User(String username, String name, String surname, Role role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
