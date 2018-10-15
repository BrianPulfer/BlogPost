package ch.supsi.webapp;

public class BlogPost {
    private String title;
    private String text;
    private String author;
    private static Long idGiver = new Long(1);
    private final long id;

    public BlogPost(){
        synchronized (idGiver) {
            id = idGiver;
            idGiver++;
        }
    }

    public BlogPost(String title, String text, String author) {
        this.title = title;
        this.text = text;
        this.author = author;

        synchronized (idGiver) {
            id = idGiver;
            idGiver++;
        }
    }

    public BlogPost(String title, String text, String author, long id) {
        this.title = title;
        this.text = text;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId(){
        return id;
    }

    @Override
    public String toString(){
        return new String("Title: "+this.title+", Text: "+this.text+", Author: "+this.author + " id: "+this.id);
    }
}