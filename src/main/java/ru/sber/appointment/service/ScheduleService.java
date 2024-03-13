package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.DayType;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Schedule;
import ru.sber.appointment.repository.ScheduleRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    DoctorService doctorService;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    TicketService ticketService;

    public List<Schedule> findAllSchedule(){
        return scheduleRepository.findAll();
    }

    public void saveSchedule(Schedule schedule){
        scheduleRepository.save(schedule);
        ticketService.saveForDay(schedule);
    }

    public void saveScheduleForWeek(Doctor unknownDoctor){
        Doctor doctor = doctorService.findById(unknownDoctor.getId());
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            Schedule schedule = new Schedule();
            schedule.setDoctor(doctor);
            schedule.setDate(date);
            schedule.setDayType(DayType.WORK);
            scheduleRepository.save(schedule);
            ticketService.saveForDay(schedule);
            date = date.plusDays(1);
        }
    }

    public void updateSchedule(Schedule unknownSchedule) {
        Doctor doctor = doctorService.findById(unknownSchedule.getDoctor().getId());
        Schedule schedule = scheduleRepository.findByDateAndDoctor(unknownSchedule.getDate(), doctor);
        schedule.setDayType(unknownSchedule.getDayType());
        scheduleRepository.save(schedule);
    }
}
