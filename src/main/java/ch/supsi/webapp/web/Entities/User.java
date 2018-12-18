package ch.supsi.webapp.web.Entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 30)
    private String username;
    private String name;
    private String surname;
    private String password;

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

    public User(String username, String name, String surname, Role role, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
        setPassword(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username){
        this.username = username;
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

    public void setPassword(String newPassword){
        this.password = encoder.encode(newPassword);
    }

    public String getPassword(){
        return password;
    }
}
