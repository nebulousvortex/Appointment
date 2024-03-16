package ru.sber.appointment.service.interfaces;

import ru.sber.appointment.model.Role;

public interface RoleService {
    Role findByName(String roleDoctor);

    Role findById(Long roleId);
}
