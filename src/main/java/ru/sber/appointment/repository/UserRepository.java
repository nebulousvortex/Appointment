package ru.sber.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.appointment.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
