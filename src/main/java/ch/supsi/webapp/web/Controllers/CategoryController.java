package ch.supsi.webapp.web.Controllers;

import ch.supsi.webapp.web.Entities.Category;
import ch.supsi.webapp.web.SuccessJSON;
import ch.supsi.webapp.web.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<Category> get() {
        return categoryService.getAllCategory();
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseEntity<? extends Object> post(@RequestBody Category c) {
        Optional<Category> optionalCategory = categoryService.postCategory(c);
        if (!optionalCategory.isPresent())
            return new ResponseEntity<Error>(new Error("Category already exist!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Category>(optionalCategory.get(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    public ResponseEntity<? extends Object> getCategory(@PathVariable int id) {
        Optional<Category> optionalCategory = categoryService.getCategory(id);
        if (!optionalCategory.isPresent())
            return new ResponseEntity<Error>(new Error("Category not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Category>(optionalCategory.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
    public ResponseEntity<? extends Object> putCategory(@PathVariable int id, @RequestBody Category c) {
        Optional<Category> optionalCategory = categoryService.putCategory(id, c);
        if (!optionalCategory.isPresent())
            return new ResponseEntity<Error>(new Error("Category not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Category>(optionalCategory.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<? extends Object> deleteCategory(@PathVariable int id) {
        if (!categoryService.deleteCategory(id))
            return new ResponseEntity<Error>(new Error("Category not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<SuccessJSON>(new SuccessJSON(true), HttpStatus.OK);
    }
}
