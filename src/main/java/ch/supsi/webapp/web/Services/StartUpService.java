package ch.supsi.webapp.web.Services;

import ch.supsi.webapp.web.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class StartUpService {

    @Autowired
    BlogPostService blogPostService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RoleService roleService;

    @Autowired
    CommentService commentService;

    @PostConstruct
    public void initDB(){
        Role role = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_ROCKSTAR");
        Category category = new Category("Sport");
        User admin = new User("admin",null,null,role,"admin");
        User brian =  new User("BrianP","Brian","Pulfer",role,"1234");
        User tommy = new User("RockyBalboa","Tommaso","Agnola", role2);

        categoryService.postCategory(category);
        categoryService.postCategory(new Category("Cultura"));
        categoryService.postCategory(new Category("Arte"));
        categoryService.postCategory(new Category("Informatica ed elettronica"));

        roleService.postRole(role);
        roleService.postRole(role2);
        roleService.postRole(new Role("ROLE_ELDER"));
        roleService.postRole(new Role("ROLE_STANDARD"));
        roleService.postRole(new Role("ROLE_ROOKIE"));
        roleService.postRole(new Role("ROLE_USER"));

        userService.postUser(admin);
        userService.postUser(brian);
        userService.postUser(tommy);

        BlogPost blogpost = new BlogPost("First BlogPost ever","First BlogPost made at the server start-up", brian, category,new Date());
        blogPostService.addBlogPost(blogpost);

        Comment comment = new Comment();
        comment.setAuthor(brian);
        comment.setText("This is the very first comment.");
        comment.setPost(blogpost);

        commentService.addComment(comment);
    }
}
