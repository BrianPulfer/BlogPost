package ch.supsi.webapp.web.Services;

import ch.supsi.webapp.web.Entities.Role;
import ch.supsi.webapp.web.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public Optional<Role> postRole(Role r) {
        if (!roleRepository.findByName(r.getName()).isPresent())
            return Optional.of(roleRepository.save(r));
        return Optional.empty();
    }

    public Optional<Role> getRole(int id) {
        return roleRepository.findById(id);
    }

    public boolean deleteRole(int id) {
        if (this.getRole(id).isPresent()) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Role> putRole(int index, Role r) {
        if (this.getRole(index).isPresent()) {
            r.setId(index);
            roleRepository.save(r);
            return Optional.of(r);
        }
        return Optional.empty();
    }
}
