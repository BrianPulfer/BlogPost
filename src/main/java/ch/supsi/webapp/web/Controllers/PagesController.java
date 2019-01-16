package ch.supsi.webapp.web.Controllers;


import ch.supsi.webapp.web.Entities.*;
import ch.supsi.webapp.web.Repositories.CommentToCommentRepository;
import ch.supsi.webapp.web.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentToCommentService commentToCommentService;

    @Autowired
    private DeletedBlogPostService deletedBlogPostService;


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
            ArrayList<Comment> comments = null;

            if(commentService.getComments(blogPost).isPresent()) {
                comments = commentService.getComments(blogPost).get();
            }

            model.addAttribute("blogpost",blogPost);
            model.addAttribute("comments",comments);
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
        DeletedBlogPost deletedBlogPost = new DeletedBlogPost(blogPostService.getBlogPost(id).get());

        deletedBlogPostService.add(deletedBlogPost);

        commentService.deleteComments(blogPostService.getBlogPost(id).get());
        blogPostService.deleteBlogPost(id);
        return "redirect:/";
    }

    @RequestMapping(value="/blog/{id}/comment", method = RequestMethod.GET)
    public String comment(Model model, @PathVariable int id){
        Comment comment = new Comment();

        model.addAttribute("post",blogPostService.getBlogPost(id).get());
        model.addAttribute("comment",comment);
        return "comment";
    }

    @RequestMapping(value="/blog/{id}/comment", method=RequestMethod.POST)
    public String addComment(@PathVariable int id, @ModelAttribute Comment comment){
        comment.setAuthor(getCurrentUser());
        comment.setPost(blogPostService.getBlogPost(id).get());
        comment.setDate(new Date());

        commentService.addComment(comment);
        return "redirect:/blog/"+id;
    }

    @RequestMapping(value="/comment/{id}", method = RequestMethod.GET)
    public String commentComment(@PathVariable int id, Model model){
        if(commentService.getComment(id) != null) {
            Comment comment = commentService.getComment(id).get();
            CommentToComment ctc = new CommentToComment();

            ctc.setCommented(comment);

            model.addAttribute("comment", comment);
            model.addAttribute("newComment", ctc);

            return "commentOnComment";
        }

        return "redirect:/";
    }

    @RequestMapping(value="/comment/{id}", method = RequestMethod.POST)
    public String commentComment(@ModelAttribute CommentToComment ctc, @PathVariable int id) {
        ctc.setAuthor(getCurrentUser());
        ctc.setDate(new Date());
        ctc.setCommented(commentService.getComment(id).get());
        commentToCommentService.addComment(ctc);


        return "redirect:/blog/"+ctc.getCommented().getPost().getId();
    }

    @RequestMapping(value="/blog/deleted", method = RequestMethod.GET)
    public String deletedComments(Model model) {

        model.addAttribute("blogPosts", deletedBlogPostService.getAll());
        model.addAttribute("comments",null);
        return "deletedBlogPosts";
    }

    @RequestMapping(value="/blog/deleted/{id}", method = RequestMethod.GET)
    public String deletedDetail(Model model, @PathVariable int id) {
        if(deletedBlogPostService.getById(id) != null) {
            model.addAttribute("blogpost", deletedBlogPostService.getById(id));
            return "deletedBlogPost";
        }
        return "redirect:/";
    }

    private User getCurrentUser(){
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserByUsername(user.getUsername());
    }
}