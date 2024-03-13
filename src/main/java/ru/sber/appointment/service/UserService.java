package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Role;
import ru.sber.appointment.model.User;
import ru.sber.appointment.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null || roleService.findById(1L) == null) {
            return false;
        }
        user.setRoles(Collections.singleton(roleService.findById(1L)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public void updateUserRole(User unknownUser, Long role_id) {
        if (roleService.findById(role_id) != null) {
            User user = userRepository.findByUsername(unknownUser.getUsername());
            Role role = roleService.findById(role_id);
            Set<Role> userRoles = user.getRoles();
            userRoles.clear();
            userRoles.add(role);
            user.setRoles(userRoles);
            userRepository.save(user);
        }
    }

    public void deleteUser(User user){
        userRepository.deleteById(user.getId());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Object> findByFirstName(String firstName) {
        return Collections.singletonList(userRepository.findByFirstName(firstName));
    }
    public List<Object> findByLastName(String lastName) {
        return Collections.singletonList(userRepository.findByLastName(lastName));
    }
}
