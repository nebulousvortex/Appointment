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
import ru.sber.appointment.service.interfaces.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
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

    @Override
    public void updateUserRole(User unknownUser, Long role_id) {
        if (roleService.findById(role_id) != null) {
            User user = userRepository.findByUsername(unknownUser.getUsername());
            Role role = roleService.findById(role_id);
            Set<Role> userRoles = user.getRoles();
            if (userRoles != null) {
                userRoles.clear();
            } else {
                userRoles = new HashSet<>();
            }
            userRoles.add(role);
            user.setRoles(userRoles);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(User user){
        userRepository.deleteById(user.getId());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Object> findByFirstName(String firstName) {
        return Collections.singletonList(userRepository.findByFirstName(firstName));
    }
    @Override
    public List<Object> findByLastName(String lastName) {
        return Collections.singletonList(userRepository.findByLastName(lastName));
    }
}
