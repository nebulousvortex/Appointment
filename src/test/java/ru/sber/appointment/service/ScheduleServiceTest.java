package ru.sber.appointment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sber.appointment.model.DayType;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Schedule;
import ru.sber.appointment.repository.ScheduleRepository;
import ru.sber.appointment.service.interfaces.DoctorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private TicketServiceImpl ticketService;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    public void testFindAllSchedule() {
        List<Schedule> schedules = new ArrayList<>();
        Mockito.when(scheduleRepository.findAll()).thenReturn(schedules);

        List<Schedule> foundSchedules = scheduleService.findAllSchedule();

        assertEquals(schedules, foundSchedules);
    }

    @Test
    public void testSaveSchedule() {
        Schedule schedule = new Schedule();
        schedule.setId(1L);

        scheduleService.saveSchedule(schedule);

        Mockito.verify(scheduleRepository).save(schedule);
        Mockito.verify(ticketService).saveForDay(schedule);
    }

    @Test
    public void testSaveScheduleForWeek() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        Mockito.when(doctorService.findById(1L)).thenReturn(doctor);

        scheduleService.saveScheduleForWeek(doctor);

        Mockito.verify(scheduleRepository, Mockito.times(7)).save(Mockito.any());
        Mockito.verify(ticketService, Mockito.times(7)).saveForDay(Mockito.any());
    }

    @Test
    public void testUpdateSchedule() {
        Schedule unknownSchedule = new Schedule();
        unknownSchedule.setDate(LocalDate.now());
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        unknownSchedule.setDoctor(doctor);
        unknownSchedule.setDayType(DayType.WORK);

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDate(LocalDate.now());
        schedule.setDoctor(doctor);
        schedule.setDayType(DayType.WORK);

        Mockito.when(doctorService.findById(1L)).thenReturn(doctor);
        Mockito.when(scheduleRepository.findByDateAndDoctor(LocalDate.now(), doctor)).thenReturn(schedule);

        scheduleService.updateSchedule(unknownSchedule);

        Mockito.verify(scheduleRepository).save(schedule);
    }
}