package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Role;
import ru.sber.appointment.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String roleDoctor) {
        return roleRepository.findByName(roleDoctor);
    }

    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow();
    }
}
