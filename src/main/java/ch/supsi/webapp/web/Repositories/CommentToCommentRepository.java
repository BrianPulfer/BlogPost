package ch.supsi.webapp.web.Repositories;

import ch.supsi.webapp.web.Entities.BlogPost;
import ch.supsi.webapp.web.Entities.Comment;
import ch.supsi.webapp.web.Entities.CommentToComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CommentToCommentRepository extends JpaRepository<CommentToComment, Integer> {
    @Override
    Optional<CommentToComment> findById(Integer integer);
    Optional<ArrayList<CommentToComment>> findAllByCommented(Comment comment);
}
