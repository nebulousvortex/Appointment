package ru.sber.appointment.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.sber.appointment.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean saveUser(User user);

    void updateUserRole(User unknownUser, Long role_id);

    void deleteUser(User user);

    User findByUsername(String username);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);
}
