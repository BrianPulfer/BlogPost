package ch.supsi.webapp.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class BlogPostController {

    private ArrayList<BlogPost> blogPosts = new ArrayList<>();

    @RequestMapping(value ="/blogposts", method = RequestMethod.GET)
    public ArrayList<BlogPost> getBlogPosts(){
        return blogPosts;
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public ResponseEntity<BlogPost> getBlogPost(@PathVariable int id){
        for(BlogPost bp : blogPosts){
            if(bp.getId() == id){
                return new ResponseEntity<>(bp, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/blogposts", method = RequestMethod.POST)
    public ResponseEntity<BlogPost> addBlogPost(@RequestBody BlogPost bp){
        BlogPost.giveId(bp);
        blogPosts.add(bp);
        return new ResponseEntity<>(bp, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BlogPost> putBlogPost(@PathVariable int id, @RequestBody BlogPost newBp){
        for(BlogPost bp : blogPosts){
            if(bp.getId() == id){
                bp.setTitle(newBp.getTitle());
                bp.setText(newBp.getText());
                bp.setAuthor(newBp.getAuthor());

                return new ResponseEntity<>(bp, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<SuccessJSON> deleteBlogPost(@PathVariable int id){
        SuccessJSON success = new SuccessJSON(true);
        SuccessJSON fail = new SuccessJSON(false);

        for(BlogPost bp : blogPosts){
            if(bp.getId() == id){
                blogPosts.remove(bp);
                return new ResponseEntity<>(success, HttpStatus.NO_CONTENT);
            }
        }

        return new ResponseEntity<>(fail, HttpStatus.NOT_FOUND);
    }
}