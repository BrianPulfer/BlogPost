package ch.supsi.webapp.web.Services;

import ch.supsi.webapp.web.Entities.Role;
import ch.supsi.webapp.web.Entities.User;
import ch.supsi.webapp.web.Repositories.RoleRepository;
import ch.supsi.webapp.web.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> postUser(User u) {
        int roleId = u.getRole().getId();
        Role r = roleRepository.findById(roleId).get();
        u.setRole(r);
        if (!userRepository.findByUsername(u.getUsername()).isPresent())
            return Optional.of(userRepository.save(u));
        return Optional.empty();
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username).get();
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

    public boolean deleteUser(int id) {
        if (this.getUser(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<User> putUser(int id, User u) {
        if (this.getUser(id).isPresent()) {
            u.setId(id);
            int roleID = u.getRole().getId();
            Role r = roleRepository.findById(roleID).get();
            u.setRole(r);
            userRepository.save(u);
            return Optional.of(u);
        }
        return Optional.empty();
    }
}

