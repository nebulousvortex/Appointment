package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Role;
import ru.sber.appointment.repository.RoleRepository;
import ru.sber.appointment.service.interfaces.RoleService;

/**
 * Сервис работы с ролями
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByName(String roleDoctor) {
        return roleRepository.findByName(roleDoctor);
    }

    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow();
    }
}
