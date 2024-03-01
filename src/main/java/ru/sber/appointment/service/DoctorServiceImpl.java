package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.User;
import ru.sber.appointment.repository.DoctorRepository;
import ru.sber.appointment.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private final DoctorRepository repository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, UserRepository userRepository) {
        this.repository = doctorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return repository.findAll();
    }

    @Override
    public void saveDoctor(Doctor doctor) {
//        if(doctor.getUser() != null) {
//            Optional<User> users = userRepository.findById(doctor.getUser().getId());
//            if( users.isPresent()){
//                User user = users.get();
//                doctor.setUser(user);
//                doctor.setFirstName(user.getFirstName());
//                doctor.setLastName(user.getLastName());
//                repository.save(doctor);
//            }
//        };
        repository.save(doctor);
    }

    @Override
    public void deleteDoctor(Doctor doctor) {
        repository.delete(doctor);
    }

    @Override
    public List<Doctor> findBySpecialization(String specialization) {
        return repository.findBySpecialization(specialization);
    }

    @Override
    public List<Doctor> findByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    @Override
    public List<Doctor> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        repository.save(doctor);
    }
}
