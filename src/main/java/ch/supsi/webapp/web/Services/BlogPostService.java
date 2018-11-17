package ch.supsi.webapp.web.Services;
import ch.supsi.webapp.web.Entities.BlogPost;
import ch.supsi.webapp.web.Entities.Category;
import ch.supsi.webapp.web.Entities.Role;
import ch.supsi.webapp.web.Entities.User;
import ch.supsi.webapp.web.Repositories.BlogPostRepository;
import ch.supsi.webapp.web.Repositories.CategoryRepository;
import ch.supsi.webapp.web.Repositories.RoleRepository;
import ch.supsi.webapp.web.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService  {
    @Autowired
    BlogPostRepository blogPostRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public List<BlogPost> getBlogPosts(){
        return blogPostRepository.findAll();
    }

    public Optional<BlogPost> getBlogPost(int id){
        return blogPostRepository.findById(id);
    }

    public BlogPost addBlogPost(BlogPost bp){
        Category c = categoryRepository.findById(bp.getCategory().getId()).get();
        User u = userRepository.findById(bp.getAuthor().getId()).get();
        Role r = roleRepository.findById(u.getRole().getId()).get();
        bp.getAuthor().setRole(r);
        bp.setAuthor(u);
        bp.setCategory(c);
        return blogPostRepository.save(bp);
    }


    public Optional<BlogPost> putBlogPost(int id, BlogPost newBp){
        if (getBlogPost(id).isPresent()) {
            newBp.setId(id);
            Category c = categoryRepository.findById(newBp.getCategory().getId()).get();
            User u = userRepository.findById(newBp.getAuthor().getId()).get();
            Role r = roleRepository.findById(u.getRole().getId()).get();
            newBp.getAuthor().setRole(r);
            newBp.setAuthor(u);
            newBp.setCategory(c);
            return Optional.of(blogPostRepository.save(newBp));
        }
        return Optional.empty();
    }

    public boolean deleteBlogPost(int id){
        if (getBlogPost(id).isPresent()) {
            blogPostRepository.deleteById(id);
            return true;
        }

        return false;
    }
}