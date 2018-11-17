package ch.supsi.webapp.web.Controllers;


import ch.supsi.webapp.web.Entities.BlogPost;
import ch.supsi.webapp.web.Services.BlogPostService;
import ch.supsi.webapp.web.SuccessJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Le richieste che arrivano dal Browser passano dal controllo che fa le chiamate a chi di dovere
 **/
@RestController
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;

    @RequestMapping(value = "/blogposts", method = RequestMethod.GET)
    public List<BlogPost> getBlogPost() {
        return blogPostService.getBlogPosts();
    }

    @RequestMapping(value = "/blogposts", method = RequestMethod.POST)
    public ResponseEntity<BlogPost> postBlogPost(@RequestBody BlogPost b) {
        return new ResponseEntity<BlogPost>(blogPostService.addBlogPost(b), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public ResponseEntity<? extends Object> getBlogPost(@PathVariable int id) {
        Optional<BlogPost> blogPostOptional = blogPostService.getBlogPost(id);
        if (!blogPostOptional.isPresent())
            return new ResponseEntity<Error>(new Error("BlogPost not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<BlogPost>(blogPostOptional.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<? extends Object> putBlogPost(@PathVariable int id, @RequestBody BlogPost b) {
        Optional<BlogPost> blogPostOptional = blogPostService.putBlogPost(id, b);
        if (!blogPostOptional.isPresent())
            return new ResponseEntity<Error>(new Error("BlogPost not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<BlogPost>(blogPostOptional.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<? extends Object> deleteBlogPost(@PathVariable int id) {
        if (!blogPostService.deleteBlogPost(id))
            return new ResponseEntity<Error>(new Error("BlogPost not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<SuccessJSON>(new SuccessJSON(true), HttpStatus.OK);
    }
}
