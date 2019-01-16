package ch.supsi.webapp.web.Repositories;

import ch.supsi.webapp.web.Entities.DeletedBlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeletedBlogPostRepository extends JpaRepository<DeletedBlogPost, Integer> {

}
