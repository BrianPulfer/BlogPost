package ch.supsi.webapp.web;

public class BlogPost {

    private static int idgiver = 1;
    private int id = -1;

    private String title;
    private String text;
    private String author;

    public static void giveId(BlogPost bp){
        if(bp.id == -1){
            bp.id = idgiver;
            idgiver++;
        }
    }

    public int getId(){
        return this.id;
    }

    private void setId(int id){
        this.id = id;
    }

    public BlogPost(){ };

    public BlogPost(String title, String text, String author) {
        this.title = title;
        this.text = text;
        this.author = author;

        id = idgiver;
        idgiver++;
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
}
