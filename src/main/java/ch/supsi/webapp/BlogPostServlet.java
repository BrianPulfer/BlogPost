package ch.supsi.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet(value = "/blogpost")
public class BlogPostServlet extends HttpServlet {

    private static ObjectMapper mapper;
    private static ArrayList<BlogPost> instances;


    @Override
    public void init(){
        mapper = new ObjectMapper();
        instances = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        mapper.writeValue(resp.getWriter(),instances);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BlogPost created = new BlogPost();

        if(req.getContentType().equals("application/x-www-form-urlencoded")){
            created.setTitle(req.getParameter("title"));
            created.setText(req.getParameter("text"));
            created.setAuthor(req.getParameter("author"));
        }else if(req.getContentType().equals("application/json")){
            created = mapper.readValue(req.getReader(),BlogPost.class);
        }

        instances.add(created);
        mapper.writeValue(resp.getWriter(), created);

    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Non ancora implementato. Non richiesto in questo esercizio (Esercizio 2 - Applet).
    }
}