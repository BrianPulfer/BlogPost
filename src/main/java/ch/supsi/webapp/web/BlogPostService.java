package ch.supsi.webapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogPostService  {
    private ArrayList<BlogPost> blogPosts = new ArrayList<>();

    @Autowired
    BlogPostRepository blogPostRepository;

    public List<BlogPost> getBlogPosts(){
        return blogPostRepository.findAll();
    }

    public BlogPost getBlogPost(int id){
        return blogPostRepository.findById(id).get();
    }

    public BlogPost addBlogPost(BlogPost bp){
        return blogPostRepository.save(bp);
    }


    public BlogPost putBlogPost(int id, BlogPost newBp){
        BlogPost t = blogPostRepository.findById(id).get();

        if(t==null)
            return null;

        t.setTitle(newBp.getTitle());
        t.setText(newBp.getText());
        t.setAuthor(newBp.getAuthor());

        return t;
    }

    public BlogPost deleteBlogPost(int id){
        BlogPost t = blogPostRepository.findById(id).get();
        blogPostRepository.delete(t);
        return t;
    }
}