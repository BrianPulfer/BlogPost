package ch.supsi.webapp.web.Services;

import ch.supsi.webapp.web.Entities.Category;
import ch.supsi.webapp.web.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Optional<Category> postCategory(Category c) {
        if (!categoryRepository.findByName(c.getName()).isPresent())
            return Optional.of(categoryRepository.save(c));
        return Optional.empty();
    }

    public Optional<Category> getCategory(int id) {
        return categoryRepository.findById(id);
    }

    public boolean deleteCategory(int id) {
        if (this.getCategory(id).isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Category> putCategory(int index, Category c) {
        if (this.getCategory(index).isPresent()) {
            c.setId(index);
            categoryRepository.save(c);
            return Optional.of(c);
        }
        return Optional.empty();
    }
}