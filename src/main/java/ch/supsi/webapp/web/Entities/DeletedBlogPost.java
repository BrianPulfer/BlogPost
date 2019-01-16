package ch.supsi.webapp.web.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DeletedBlogPost {
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


    public DeletedBlogPost(){};

    public DeletedBlogPost(BlogPost blogPost){
        this.author = blogPost.getAuthor();
        this.category = blogPost.getCategory();
        this.date = blogPost.getDate();
        this.text = blogPost.getText();
        this.title = blogPost.getTitle();
    }

    public int getId(){
        return this.id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}

