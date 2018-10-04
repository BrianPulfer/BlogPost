package ch.supsi.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import java.io.OutputStream;
import java.util.ArrayList;


@WebServlet(value = "/blogpost")

public class BlogPost extends HttpServlet {

    private String author;
    private String text;
    private String title;
    private ObjectMapper mapper = new ObjectMapper();
    //ArrayList necessario a contenere tutte le nostre istanze (niente database per ora)
    private static ArrayList<BlogPost> instances = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OutputStream responseStream = resp.getOutputStream();

        for(int i = 0; i<instances.size(); i++){
            mapper.writeValue(responseStream,instances.get(i));
        }

        System.out.println(instances); //A scopo di debug...
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BlogPost creato = null;

        System.out.println(req.getContentType());

        if(req.getContentType().equals("application/x-www-form-urlencoded")){
            creato = mapper.readValue(req.getQueryString(),BlogPost.class);
        }else {
            StringBuilder body = new StringBuilder();
            BufferedReader o = req.getReader();
            String s = new String();
            while(!( s =  o.readLine()).isEmpty()){
                body.append(s);
            }

            if(!body.toString().isEmpty())
                creato = mapper.readValue(body.toString(),BlogPost.class);//Bug
        }

        if(creato != null) {
            instances.add(creato);

            OutputStream respOutputStream = resp.getOutputStream();
            mapper.writeValue(respOutputStream, creato);
        }

        System.out.println(instances);      //A scopo di debug...
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Non ancora implementato. Non richiesto in questo esercizio.
    }

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
