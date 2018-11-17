package ch.supsi.webapp.web.Controllers;

import ch.supsi.webapp.web.Entities.Role;
import ch.supsi.webapp.web.SuccessJSON;
import ch.supsi.webapp.web.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> get() {
        return roleService.getAllRole();
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResponseEntity<? extends Object> post(@RequestBody Role r) {
        Optional<Role> roleOptional = roleService.postRole(r);
        if (!roleOptional.isPresent())
            return new ResponseEntity<Error>(new Error("Role already exist!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Role>(roleOptional.get(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<? extends Object> getRole(@PathVariable int id) {
        Optional<Role> roleOptional = roleService.getRole(id);
        if (!roleOptional.isPresent())
            return new ResponseEntity<Error>(new Error("Role not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Role>(roleOptional.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
    public ResponseEntity<? extends Object> putRole(@PathVariable int id, @RequestBody Role r) {
        Optional<Role> roleOptional = roleService.putRole(id, r);
        if (!roleOptional.isPresent())
            return new ResponseEntity<Error>(new Error("Role not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Role>(roleOptional.get(), HttpStatus.OK);

    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<? extends Object> deleteRole(@PathVariable int id) {
        if (!roleService.deleteRole(id))
            return new ResponseEntity<Error>(new Error("Role not found!"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<SuccessJSON>(new SuccessJSON(true), HttpStatus.OK);
    }
}
