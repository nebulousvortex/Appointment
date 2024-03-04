package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Role;
import ru.sber.appointment.model.User;
import ru.sber.appointment.repository.RoleRepository;
import ru.sber.appointment.repository.UserRepository;

import java.util.Collections;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null || roleRepository.findById(1L).isEmpty()) {
            return false;
        }
        user.setRoles(Collections.singleton(roleRepository.findById(1L).get()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public void updateUserRole(User unknownUser, Long role_id) {
        if (roleRepository.findById(role_id).isPresent()) {
            User user = userRepository.findByUsername(unknownUser.getUsername());
            Role role = roleRepository.findById(role_id).get();
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
}
