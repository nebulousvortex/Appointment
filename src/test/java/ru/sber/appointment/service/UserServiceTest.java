package ru.sber.appointment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.sber.appointment.model.Role;
import ru.sber.appointment.model.User;
import ru.sber.appointment.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user.setUsername("0000 0000 0000 0000");
        Mockito.when(userRepository.findByUsername("0000 0000 0000 0000")).thenReturn(user);

        UserServiceImpl userService = new UserServiceImpl(userRepository, roleService, bCryptPasswordEncoder);
        UserDetails userDetails = userService.loadUserByUsername("0000 0000 0000 0000");

        assertEquals("0000 0000 0000 0000", userDetails.getUsername());
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("0000 0000 0000 0000");

        Mockito.when(userRepository.findByUsername("0000 0000 0000 0000")).thenReturn(null);
        Mockito.when(roleService.findById(1L)).thenReturn(new Role());

        Mockito.when(bCryptPasswordEncoder.encode(null)).thenAnswer(invocation -> "space11");

        UserServiceImpl userService = new UserServiceImpl(userRepository, roleService, bCryptPasswordEncoder);
        boolean result = userService.saveUser(user);

        assertTrue(result);
        assertEquals("space11", user.getPassword());
    }

    @Test
    public void testUpdateUserRole() {
        User existingUser = new User();
        existingUser.setUsername("0000 0000 0000 0000");
        Role role = new Role();
        role.setId(1L);

        Mockito.when(userRepository.findByUsername("0000 0000 0000 0000")).thenReturn(existingUser);
        Mockito.when(roleService.findById(1L)).thenReturn(role);

        UserServiceImpl userService = new UserServiceImpl(userRepository, roleService, bCryptPasswordEncoder);
        userService.updateUserRole(existingUser, 1L);

        assertEquals(1, existingUser.getRoles().size());
        assertEquals(role, existingUser.getRoles().iterator().next());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);

        UserServiceImpl userService = new UserServiceImpl(userRepository, roleService, bCryptPasswordEncoder);
        userService.deleteUser(user);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }
}