package ch.supsi.webapp.web.Services;

import ch.supsi.webapp.web.Entities.BlogPost;
import ch.supsi.webapp.web.Entities.Comment;
import ch.supsi.webapp.web.Repositories.CommentRepository;
import ch.supsi.webapp.web.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;


    public Optional<Comment> addComment(Comment comment){
        if (!commentRepository.findById(comment.getID()).isPresent())
            return Optional.of(commentRepository.save(comment));
        return Optional.empty();
    }

    public Optional<ArrayList<Comment>> getComments(BlogPost blogPost){
        if(commentRepository.findAllByPost(blogPost).isPresent())
            return commentRepository.findAllByPost(blogPost);

        return Optional.empty();
    }

    public void deleteComments(BlogPost blogPost){
        if(getComments(blogPost).isPresent()) {
            ArrayList<Comment> comments = getComments(blogPost).get();

            for(Comment c : comments){
                commentRepository.delete(c);
            }
        }
    }

    public Optional<Comment> getComment(int id){
        if(commentRepository.findById(id).isPresent())
            return commentRepository.findById(id);

        return null;
    }

}
