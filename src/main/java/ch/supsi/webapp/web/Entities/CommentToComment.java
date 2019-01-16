package ch.supsi.webapp.web.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CommentToComment {
    @Id
    @GeneratedValue
    private int id;

    private Date date;
    private String text;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_author")
    private User author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_comment")
    private Comment commented;

    public CommentToComment(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment getCommented() {
        return commented;
    }

    public void setCommented(Comment commented) {
        this.commented = commented;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
