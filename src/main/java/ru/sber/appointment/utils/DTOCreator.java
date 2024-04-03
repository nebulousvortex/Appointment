package ru.sber.appointment.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.sber.appointment.dto.DoctorDTO;
import ru.sber.appointment.dto.UserDTO;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class DTOCreator {

    public List<DoctorDTO> makeDoctorDTOList(List<Doctor> doctorList){
        List<DoctorDTO> doctorDTOList = new ArrayList<>();
        for(Doctor doctor: doctorList){
            User user = doctor.getUser();
            UserDTO userDTO = new UserDTO();
            DoctorDTO doctorDTO = new DoctorDTO();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setSurName(user.getSurName());
            doctorDTO.setId(doctor.getId());
            doctorDTO.setUser(userDTO);
            doctorDTO.setSpecialization(doctor.getSpecialization());
            doctorDTOList.add(doctorDTO);
        }
        return doctorDTOList;
    }

}
