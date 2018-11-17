package ch.supsi.webapp.web.Controllers;

import ch.supsi.webapp.web.SuccessJSON;
import ch.supsi.webapp.web.Entities.User;
import ch.supsi.webapp.web.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> get() {
        return userService.getAllUser();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<? extends Object> post(@RequestBody User user) {
        Optional<User> u = userService.postUser(user);
        if (!u.isPresent())
            return new ResponseEntity<Error>(new Error("User already exist!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<User>(u.get(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<? extends Object> getUser(@PathVariable int id) {
        Optional<User> optionalUser = userService.getUser(id);
        if (!optionalUser.isPresent())
            return new ResponseEntity<Error>(new Error("User not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<User>(optionalUser.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<? extends Object> putUser(@PathVariable int id, @RequestBody User user) {
        Optional<User> optionalUser = userService.putUser(id, user);
        if (!optionalUser.isPresent())
            return new ResponseEntity<Error>(new Error("User not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<User>(optionalUser.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<? extends Object> deleteUser(@PathVariable int id) {
        if (!userService.deleteUser(id))
            return new ResponseEntity<Error>(new Error("User not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<SuccessJSON>(new SuccessJSON(true), HttpStatus.OK);
    }
}
