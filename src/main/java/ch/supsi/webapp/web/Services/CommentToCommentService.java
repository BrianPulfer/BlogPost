package ch.supsi.webapp.web.Services;

import ch.supsi.webapp.web.Entities.BlogPost;
import ch.supsi.webapp.web.Entities.Comment;
import ch.supsi.webapp.web.Entities.CommentToComment;
import ch.supsi.webapp.web.Repositories.CommentToCommentRepository;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CommentToCommentService {
    @Autowired
    CommentToCommentRepository repository;

    public Optional<CommentToComment> addComment(CommentToComment comment){
        if (!repository.findById(comment.getId()).isPresent())
            return Optional.of(repository.save(comment));
        return Optional.empty();
    }

    public Optional<ArrayList<CommentToComment>> getComments(Comment comment){
        if(repository.findAllByCommented(comment).isPresent())
            return repository.findAllByCommented(comment);

        return Optional.empty();
    }

    public void deleteComments(Comment comment){
        if(getComments(comment).isPresent()) {
            ArrayList<CommentToComment> comments = getComments(comment).get();

            for(CommentToComment c : comments){
                repository.delete(c);
            }
        }
    }



}
