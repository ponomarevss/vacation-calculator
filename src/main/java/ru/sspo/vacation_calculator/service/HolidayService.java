package ru.sspo.vacation_calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sspo.vacation_calculator.model.Holiday;
import ru.sspo.vacation_calculator.repository.HolidayRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {
    private final HolidayRepository holidayRepository;

    public List<Holiday> getHolidays(int year) {
        return holidayRepository.findHolidays(year);
    }

    public boolean isHoliday(LocalDate date, List<Holiday> holidays) {
        return holidays.stream().anyMatch(holiday -> holiday.getDate().equals(date));
    }
}