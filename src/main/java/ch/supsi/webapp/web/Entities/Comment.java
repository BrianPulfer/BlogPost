package ch.supsi.webapp.web.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    int id;

    private String text;
    private Date date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_author")
    private User author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_post")
    private BlogPost post;

    public Comment(){};

    public Comment(String text){this.text = text;}

    public Comment(String text, User author, BlogPost blogPost, Date date){
        this.text = text;
        this.author = author;
        this.post = blogPost;
        this.date = date;
    }

    public int getID(){
        return id;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

    public User getAuthor(){
        return this.author;
    }

    public void setAuthor(User author){
        this.author = author;
    }

    public BlogPost getPost(){
        return this.post;
    }

    public void setPost(BlogPost blogPost){
        this.post = blogPost;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return this.date;
    }
}
