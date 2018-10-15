package ch.supsi.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.sun.deploy.net.HttpRequest;
//import com.sun.deploy.net.HttpResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.UnaryOperator;


@WebServlet(value = "/blogpost/*", loadOnStartup = 1)
public class BlogPostServlet extends HttpServlet {

    private static ObjectMapper mapper;
    private static ArrayList<BlogPost> instances;


    @Override
    public void init(){
        mapper = new ObjectMapper();
        instances = new ArrayList<>();
    }


    //Da aggiustare
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String request_info = req.getPathInfo();

        //resp.setHeader("Content-Type","text/html");

        if(request_info == null || request_info.equals("/")) {
            mapper.writeValue(resp.getWriter(), instances);
            resp.setStatus(200,"Ok");
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
                    return;
                }
            }

            resp.setStatus(404,"Post with id "+id_to_put+" not found. Impossible to replace");
            resp.getWriter().println("Post with id "+id_to_put+" not found. Impossible to replace");
        } else if(req.getContentType().equals("application/x-www-form-urlencoded")){
            if(req.getPathInfo() != null) {
                id_to_put = Long.parseLong(req.getPathInfo().substring(1));
            } else {
                resp.setStatus(400,"Post id not specified. Impossible to put.");
                return;
            }

            String tit = getTitle(req);
            String tex = getText(req);
            String aut = getAuthor(req);

            boolean modified = false;
            BlogPost bp_modified = null;

            for(BlogPost bp : instances){
                if(bp.getId() == id_to_put){
                    modified = true;
                    bp.setAuthor(aut);bp.setText(tex);bp.setTitle(tit);
                    mapper.writeValue(resp.getWriter(),bp);
                    resp.setStatus(200,"Post with id "+id_to_put+" replaced successfully.");
                    return;
                }
            }

            resp.setStatus(404,"Post with id "+id_to_put+" not found. Impossible to replace it.");

        } else {
            resp.getWriter().println("Could not replace the post correctly. Please send a JSON PUT request or a x-www-form-urlencoded PUT request.");
            resp.setStatus(400,"Bad Request");
        }
    }

    private String getAuthor(HttpServletRequest req) {
        return req.getParameter("author");
    }

    private String getText(HttpServletRequest req) {
        return req.getParameter("text");
    }

    private String getTitle(HttpServletRequest req) {
        return req.getParameter("title");
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
                }
            }
        } else if(req.getContentType().equals("application/x-www-form-urlencoded")){

            String tit = req.getParameter("title");
            String tex = req.getParameter("text");
            String aut = req.getParameter("author");

            for(BlogPost bp : instances){
                if(bp.getId() == id_to_patch){
                    if(tit!=null){
                        bp.setTitle(tit);
                    }

                    if(tex != null){
                        bp.setText(tex);
                    }

                    if(aut != null){
                        bp.setAuthor(aut);
                    }
                    patched = true;
                    mapper.writeValue(resp.getWriter(),bp);
                }
            }
        }

        if(patched){
            resp.setStatus(200,"Post with id "+id_to_patch+" patched succesfully.");
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