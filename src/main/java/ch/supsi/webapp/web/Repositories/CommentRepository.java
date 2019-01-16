package ch.supsi.webapp.web.Repositories;

import ch.supsi.webapp.web.Entities.BlogPost;
import ch.supsi.webapp.web.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Override
    Optional<Comment> findById(Integer integer);
    Optional<ArrayList<Comment>> findAllByPost(BlogPost blogpost);
}
