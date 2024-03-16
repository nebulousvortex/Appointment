package ru.sber.appointment.service.interfaces;

import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findAllSchedule();

    void saveSchedule(Schedule schedule);

    void saveScheduleForWeek(Doctor unknownDoctor);

    void updateSchedule(Schedule unknownSchedule);
}
