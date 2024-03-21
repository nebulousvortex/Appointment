package ru.sber.appointment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.User;
import ru.sber.appointment.repository.DoctorRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Test
    public void testFindAllDoctors() {
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        Mockito.when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.findAllDoctors();

        assertEquals(2, result.size());
    }

    @Test
    public void testSaveDoctor() {
        Doctor doctor = new Doctor();
        doctor.setUser(new User());
        Mockito.when(userService.findByUsername(Mockito.eq(doctor.getUser().getUsername()))).thenReturn(new User());
        doctorService.saveDoctor(doctor);

        Mockito.verify(userService, Mockito.times(1)).updateUserRole(Mockito.any(), Mockito.eq(2L));
        Mockito.verify(doctorRepository, Mockito.times(1)).save(doctor);
    }

    @Test
    public void testFindBySpecialization() {
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        Mockito.when(doctorRepository.findBySpecialization(Mockito.anyString())).thenReturn(doctors);

        List<Doctor> result = doctorService.findBySpecialization("Кардиолог");

        assertEquals(2, result.size());
    }

    @Test
    public void testFindByFirstName() {
        User user = new User();
        user.setFirstName("Федор");
        Mockito.when(userService.findByFirstName(Mockito.anyString())).thenReturn(Collections.singletonList(user));
        Mockito.when(doctorRepository.findByUser(Mockito.any())).thenReturn(new Doctor());
        List<Doctor> result = doctorService.findByFirstName("Федор");

        assertEquals(1, result.size());
    }

    @Test
    public void testFindByLastName() {
        User user = new User();
        user.setLastName("Слобожанин");
        Mockito.when(userService.findByLastName(Mockito.anyString())).thenReturn(Collections.singletonList(user));
        Mockito.when(doctorRepository.findByUser(Mockito.any())).thenReturn(new Doctor());
        List<Doctor> result = doctorService.findByLastName("Слобожанин");

        assertEquals(1, result.size());
    }

    @Test
    public void testUpdateDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(doctor));

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setId(1L);
        updatedDoctor.setSpecialization("Кардиолог");

        doctorService.updateDoctor(updatedDoctor);

        assertEquals("Кардиолог", doctor.getSpecialization());
    }

    @Test
    public void testFindById() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Mockito.when(doctorRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.findById(1L);

        assertEquals(1L, result.getId().longValue());
    }

    @Test
    public void testFindByUser() {
        User user = new User();
        Doctor doctor = new Doctor();
        doctor.setUser(user);
        Mockito.when(doctorRepository.findByUser(Mockito.any())).thenReturn(doctor);

        Doctor result = doctorService.findByUser(user);

        assertEquals(user, result.getUser());
    }

    @Test
    public void testDeleteDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Mockito.when(doctorRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor(doctor);

        Mockito.verify(userService, Mockito.times(1)).updateUserRole(Mockito.any(), Mockito.eq(1L));
        Mockito.verify(doctorRepository, Mockito.times(1)).delete(doctor);
    }
}
