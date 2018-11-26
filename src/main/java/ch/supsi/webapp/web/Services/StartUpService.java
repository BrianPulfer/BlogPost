package ch.supsi.webapp.web.Services;

import ch.supsi.webapp.web.Entities.BlogPost;
import ch.supsi.webapp.web.Entities.Category;
import ch.supsi.webapp.web.Entities.Role;
import ch.supsi.webapp.web.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void initDB(){
        Role role = new Role("Admin");
        Category category = new Category("Sport");
        User brian =  new User("BrianP","Brian","Pulfer",role);

        categoryService.postCategory(category);
        categoryService.postCategory(new Category("Cultura"));
        categoryService.postCategory(new Category("Arte"));
        categoryService.postCategory(new Category("Informatica ed elettronica"));

        roleService.postRole(role);
        roleService.postRole(new Role("Elder"));
        roleService.postRole(new Role("Standard"));
        roleService.postRole(new Role("Rookie"));

        userService.postUser(brian);

        blogPostService.addBlogPost(new BlogPost("First BlogPost ever","First BlogPost made at the server start-up", brian, category));
    }
}
