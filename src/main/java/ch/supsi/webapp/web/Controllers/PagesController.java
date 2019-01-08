package ch.supsi.webapp.web.Controllers;


import ch.supsi.webapp.web.Entities.BlogPost;
import ch.supsi.webapp.web.Entities.Category;
import ch.supsi.webapp.web.Entities.User;
import ch.supsi.webapp.web.Services.BlogPostService;
import ch.supsi.webapp.web.Services.CategoryService;
import ch.supsi.webapp.web.Services.RoleService;
import ch.supsi.webapp.web.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @Autowired
    private RoleService roleService;


    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String getRegister(Model model){
        User newUser = new User();

        model.addAttribute("newUser", newUser);
        return "register";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String register(@ModelAttribute User user){
        user.setRole(roleService.getRoleByName("ROLE_USER"));
        if(user.getUsername() != null && !user.getUsername().isEmpty()) {
            userService.postUser(user);
            return "redirect:/login";
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(){
        return "login";
    }

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
    public String form(Model model, HttpSession session){
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Category> categories = categoryService.getAllCategory();

        BlogPost blogPost = new BlogPost();
        User author = userService.findUserByUsername(user.getUsername());
        blogPost.setAuthor(author);

        model.addAttribute("blogPost", blogPost);
        model.addAttribute("categories",categories);
        model.addAttribute("postAuthor",author);
        return "form";
    }

    @RequestMapping(value = "/blog/new", method = RequestMethod.POST)
    public String createBlogPost(Model model,@ModelAttribute BlogPost bp){
        bp.setDate(new Date());
        bp.setAuthor(getCurrentUser()); //Modificare per lasciare autore originale
        blogPostService.addBlogPost(bp);
        return getAllBlogPosts(model);
    }


    @RequestMapping(value = "/blog/{id}/edit", method = RequestMethod.GET)
    public String editBlogPostGet(Model model, @PathVariable int id){
        model.addAttribute("blogPost",blogPostService.getBlogPost(id).get());
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("postAuthor",blogPostService.getBlogPost(id).get().getAuthor());
        return "edit-blogpost";
    }

    @RequestMapping(value = "/blog/{id}/edit", method = RequestMethod.POST)
    public String editBlogPostPost(@PathVariable int id, @ModelAttribute BlogPost bp){
        bp.setDate(new Date());
        bp.setAuthor(getCurrentUser()); //Modificare per lasciare autore originale
        blogPostService.putBlogPost(id, bp);
        return "redirect:/";
    }

    @RequestMapping(value="/blog/{id}/delete", method = RequestMethod.GET)
    public String deleteBlogPost(@PathVariable int id){
        blogPostService.deleteBlogPost(id);
        return "redirect:/";
    }

    private User getCurrentUser(){
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserByUsername(user.getUsername());
    }
}