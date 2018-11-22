package ch.supsi.webapp.web.Entities;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public final class Category {
    @Id
    @GeneratedValue
    int id;

    private final String name;

    public Category(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
