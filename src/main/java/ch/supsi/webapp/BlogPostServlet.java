package ch.supsi.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


@WebServlet(value = "/blogpost/*", loadOnStartup = 1)
public class BlogPostServlet extends HttpServlet {

    private static ObjectMapper mapper;
    private static CopyOnWriteArrayList<BlogPost> instances;


    @Override
    public void init(){
        mapper = new ObjectMapper();
        instances = new CopyOnWriteArrayList<>();
    }


    //Da aggiustare
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String request_info = req.getPathInfo();

        //resp.setHeader("Content-Type","text/html");

        if(request_info == null || request_info.equals("/")) {
            mapper.writeValue(resp.getWriter(), instances);
            resp.setStatus(200,"Ok");
            resp.setContentType("application/json");
        } else {
            request_info = request_info.substring(1);
            Long id = Long.parseLong(request_info);

            BlogPost toWrite = null;

            for(BlogPost bp : instances){
                if(bp.getId() == id){
                    toWrite = bp;
                    break;
                }
            }

            if(toWrite==null){
                resp.getWriter().println("We couldn't find the Post with id "+id);
                resp.setStatus(404, "Blogpost not found.");
            } else {
                mapper.writeValue(resp.getWriter(),toWrite);
                resp.setStatus(200,"Blogpost found.");
                resp.setContentType("application/json");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BlogPost created = null;

        if(req.getContentType().equals("application/x-www-form-urlencoded")){
            created = new BlogPost(req.getParameter("title"),req.getParameter("text"),req.getParameter("author"));
        }else if(req.getContentType().equals("application/json")){
            created = mapper.readValue(req.getReader(),BlogPost.class);
        }

        if(created != null) {
            instances.add(created);
            mapper.writeValue(resp.getWriter(), created);
            resp.setStatus(201, "Blog Post added succesfully.");
            resp.setContentType("application/json");
        } else {
            resp.setStatus(400,"Could not add the post.");
        }
    }

    //doPut -> (Update)/Replace
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id_to_put;

        if(req.getContentType().equals("application/json")){
            BlogPost new_one = (mapper.readValue(req.getReader(),BlogPost.class));
            if(req.getPathInfo() != null) {
                id_to_put = Long.parseLong(req.getPathInfo().substring(1));
            } else {
                resp.setStatus(400,"Post id not specified. Impossible to put.");
                return;
            }

            if(new_one==null){
                resp.setStatus(400,"Post passed has neither author, title or text.");
                return;
            }


            for(BlogPost bp : instances){
                if(bp.getId() == id_to_put){
                    bp.setTitle(new_one.getTitle());bp.setText(new_one.getText());bp.setAuthor(new_one.getAuthor());
                    mapper.writeValue(resp.getWriter(),bp);
                    resp.setStatus(200,"Post with id "+id_to_put+" replaced successfully.");
                    resp.setContentType("application/json");
                    return;
                }
            }

            resp.setStatus(404,"Post with id "+id_to_put+" not found. Impossible to replace");
            resp.getWriter().println("Post with id "+id_to_put+" not found. Impossible to replace");
        } else {
            resp.getWriter().println("Could not replace the post correctly. Please send a JSON PUT request or a x-www-form-urlencoded PUT request.");
            resp.setStatus(400,"Bad Request");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getMethod().equalsIgnoreCase("PATCH")){
            doPatch(req,resp);
        } else {
            super.service(req,resp);
        }
    }

    //doPatch è un metodo creato da me e non è standard della libreria Java (classe HttpServlet).
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getPathInfo() == null){
            resp.setStatus(400,"Cannot patch post without the post id");
            resp.getWriter().println("Cannot patch post without the post id");
            return;
        }

        long id_to_patch = Long.parseLong(req.getPathInfo().substring(1));

        boolean patched = false;

        if(req.getContentType().equals("application/json")){
            BlogPost recieved = mapper.readValue(req.getReader(),BlogPost.class);
            String tit = null; String tex = null; String aut = null;

            if(recieved != null){
                tit = recieved.getTitle();
                tex = recieved.getText();
                aut = recieved.getAuthor();
            }

            for(BlogPost bp : instances){
                if(bp.getId() == id_to_patch){
                    long id = bp.getId();

                    if(aut!=null){
                        bp.setAuthor(aut);
                    }

                    if(tit!=null){
                        bp.setTitle(tit);
                    }

                    if(tex!=null){
                        bp.setText(tex);
                    }

                    mapper.writeValue(resp.getWriter(),bp);
                    patched = true;
                    resp.setContentType("application/json");
                }
            }
        } else {
            resp.setStatus(404,"Post with id "+id_to_patch+" not found. Impossible to fetch.");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getPathInfo()!=null) {
            long id_to_delete = Long.parseLong(req.getPathInfo().substring(1));

            boolean deleted = false;

            for(BlogPost bp : instances){
                if(bp.getId() == id_to_delete){
                    instances.remove(bp);
                    deleted = true;
                }
            }

            if(deleted){
                resp.setStatus(200,"BlogPost with id "+id_to_delete+" deleted successfully.");
                resp.getWriter().println("BlogPost with id "+id_to_delete+" deleted successfully.");
            } else {
                resp.setStatus(404,"Post with id "+id_to_delete+" not found. Impossible to delete");
                resp.getWriter().println("Post with id "+id_to_delete+" not found. Impossible to delete");
            }
        } else {
            resp.setStatus(404,"BlogPost not found. The id was not specified.");
        }
    }


    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Non ancora implementato. Non richiesto in questo esercizio (Esercizio 2 - Applet).
        //Non ancora implementato. Non richiesto in questo esercizio (Esercizio 3 - Applet).
    }
}