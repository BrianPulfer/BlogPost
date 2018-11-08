package ch.supsi.webapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class BlogPostController {

    @Autowired
    BlogPostService blogPostService;

    private ArrayList<BlogPost> blogPosts = new ArrayList<>();

    @RequestMapping(value ="/blogposts", method = RequestMethod.GET)
    public ArrayList<BlogPost> getBlogPosts(){
        return new ArrayList<>(blogPostService.getBlogPosts());
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public ResponseEntity<BlogPost> getBlogPost(@PathVariable int id){
        if(blogPostService.getBlogPost(id) != null)
            return new ResponseEntity<>(blogPostService.getBlogPost(id), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/blogposts", method = RequestMethod.POST)
    public ResponseEntity<BlogPost> addBlogPost(@RequestBody BlogPost bp){
        blogPostService.addBlogPost(bp);
        return new ResponseEntity<>(bp, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BlogPost> putBlogPost(@PathVariable int id, @RequestBody BlogPost newBp){
        BlogPost temp = blogPostService.putBlogPost(id, newBp);

        if(temp != null)
            return new ResponseEntity<>(temp, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<SuccessJSON> deleteBlogPost(@PathVariable int id){
        SuccessJSON success = new SuccessJSON(true);
        SuccessJSON fail = new SuccessJSON(false);

        if(blogPostService.deleteBlogPost(id) != null)
            return new ResponseEntity<>(success, HttpStatus.OK);

        return new ResponseEntity<>(fail, HttpStatus.NOT_FOUND);
    }
}