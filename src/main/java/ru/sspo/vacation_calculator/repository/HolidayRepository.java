package ru.sspo.vacation_calculator.repository;

import ru.sspo.vacation_calculator.model.Holiday;

import java.util.List;

public interface HolidayRepository {
    List<Holiday> findHolidays(int year);
}