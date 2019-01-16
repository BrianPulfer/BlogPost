package ch.supsi.webapp.web.Services;

import ch.supsi.webapp.web.Entities.DeletedBlogPost;
import ch.supsi.webapp.web.Repositories.DeletedBlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeletedBlogPostService {

    @Autowired
    DeletedBlogPostRepository deletedBlogPostRepository;

    public void add(DeletedBlogPost deletedBlogPost){
        if(!deletedBlogPostRepository.findById(deletedBlogPost.getId()).isPresent()){
            deletedBlogPostRepository.save(deletedBlogPost);
        }
    }

    public List<DeletedBlogPost> getAll(){
        if(!deletedBlogPostRepository.findAll().isEmpty()){
            return deletedBlogPostRepository.findAll();
        }
        return null;
    }

    public DeletedBlogPost getById(int id){
        if(deletedBlogPostRepository.findById(id).isPresent()){
            return deletedBlogPostRepository.findById(id).get();
        }

        return null;
    }

}
