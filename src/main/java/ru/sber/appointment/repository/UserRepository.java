package ru.sber.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.appointment.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByFirstName (String firstName);
    List<User> findByLastName (String lastName);
}
