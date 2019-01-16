package ch.supsi.webapp.web.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity //Tabella sul DB.
public class BlogPost {
    @Id
    @GeneratedValue
    private int id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    private Date date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_author")
    private User author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_category")
    private Category category;

    public BlogPost(){ }

    public BlogPost(String title, String text, User author, Category category) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.category = category;
    }

    public BlogPost(String title, String text, User author, Category category, Date date) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getDate(){ return date; }

    public void setDate(Date date){ this.date = date;}
}
