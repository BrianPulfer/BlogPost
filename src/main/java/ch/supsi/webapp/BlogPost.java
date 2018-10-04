package ch.supsi.webapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;


@WebServlet(value="/blogpost")

public class BlogPost extends HttpServlet {
    private String author;
    private String text;
    private String title;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Get BlogPost");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Post BlogPost");
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Non ancora implementato. Non richiesto in questo esercizio.
    }

    //ArrayList necessario a contenere tutte le nostre istanze (niente database per ora)
    private static ArrayList<BlogPost> instances = new ArrayList<>();


    public BlogPost(){
        addToArrayList();
    }

    public BlogPost(String title, String text, String author){
        this.author = author;
        this.title = title;
        this.text = text;

        addToArrayList();
    }

    private void addToArrayList(){
        BlogPost.instances.add(this);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
