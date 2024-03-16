package ru.sber.appointment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sber.appointment.model.Role;
import ru.sber.appointment.repository.RoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void testFindByName() {
        Role role = new Role();
        role.setName("ROLE_DOCTOR");

        Mockito.when(roleRepository.findByName("ROLE_DOCTOR")).thenReturn(role);

        Role foundRole = roleService.findByName("ROLE_DOCTOR");

        assertEquals("ROLE_DOCTOR", foundRole.getName());
    }

    @Test
    public void testFindById() {
        Role role = new Role();
        role.setId(1L);

        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Role foundRole = roleService.findById(1L);

        assertEquals(1L, foundRole.getId());
    }
}