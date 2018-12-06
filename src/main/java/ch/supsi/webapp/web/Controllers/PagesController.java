package ch.supsi.webapp.web.Controllers;


import ch.supsi.webapp.web.Entities.BlogPost;
import ch.supsi.webapp.web.Entities.Category;
import ch.supsi.webapp.web.Entities.User;
import ch.supsi.webapp.web.Services.BlogPostService;
import ch.supsi.webapp.web.Services.CategoryService;
import ch.supsi.webapp.web.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
public class PagesController {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllBlogPosts(Model model){
        List<BlogPost> blogPosts = blogPostService.getBlogPosts();
        model.addAttribute("blogPosts",blogPosts);
        return "index";
    }

    @RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
    public String getBlogPost(@PathVariable int id, Model model){
        if(blogPostService.getBlogPost(id) != null) {
            BlogPost blogPost = blogPostService.getBlogPost(id).get();
            model.addAttribute("blogpost",blogPost);
            return "blogpost";
        }

        return "index";
    }

    @RequestMapping(value = "/blog/new", method = RequestMethod.GET)
    public String form(Model model){
        List<Category> categories = categoryService.getAllCategory();
        List<User> users = userService.getAllUser();
        BlogPost blogPost = new BlogPost();

        model.addAttribute("blogPost", blogPost);
        model.addAttribute("categories",categories);
        model.addAttribute("users",users);
        return "form";
    }

    @RequestMapping(value = "/blog/new", method = RequestMethod.POST)
    public String createBlogPost(Model model,@ModelAttribute BlogPost bp){
        bp.setDate(new Date());
        blogPostService.addBlogPost(bp);
        return getAllBlogPosts(model);
    }


    @RequestMapping(value = "/blog/{id}/edit", method = RequestMethod.GET)
    public String editBlogPostGet(Model model, @PathVariable int id){
        model.addAttribute("blogPost",blogPostService.getBlogPost(id).get());
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("users",userService.getAllUser());
        return "edit-blogpost";
    }

    @RequestMapping(value = "/blog/{id}/edit", method = RequestMethod.POST)
    public String editBlogPostPost(@PathVariable int id, @ModelAttribute BlogPost bp){
        bp.setDate(new Date());
        blogPostService.putBlogPost(id, bp);
        return "redirect:/";
    }

    @RequestMapping(value="/blog/{id}/delete", method = RequestMethod.GET)
    public String deleteBlogPost(@PathVariable int id){
        blogPostService.deleteBlogPost(id);
        return "redirect:/";
    }
}