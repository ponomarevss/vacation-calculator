package ru.sspo.vacation_calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sspo.vacation_calculator.model.Holiday;
import ru.sspo.vacation_calculator.model.VacationRequest;
import ru.sspo.vacation_calculator.validator.VacationRequestValidator;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationCalculator {
    private final HolidayService holidayService;
    private final VacationRequestValidator validator;

    @Value("${holiday.vacation.average-days-per-month}")
    private double averageDaysPerMonth;

    public double calculateVacationPay(
            double averageSalary,
            Integer vacationDays,
            String startDate,
            String endDate
    ) {

        validator.validateInputParameters(averageSalary, vacationDays, startDate, endDate);

        if (vacationDays != null) {
            validator.validateVacationDays(vacationDays);
            return calculatePayWithDays(averageSalary, vacationDays);
        } else {
            validator.validateDates(startDate, endDate);

            VacationRequest request = VacationRequest.builder()
                    .averageSalary(averageSalary)
                    .startDate(LocalDate.parse(startDate))
                    .endDate(LocalDate.parse(endDate))
                    .build();

            int actualVacationDays = calculateActualDays(request);
            return calculatePayWithDays(averageSalary, actualVacationDays);
        }
    }

    private double calculatePayWithDays(double averageSalary, int days) {
        double dailySalary = averageSalary / averageDaysPerMonth;
        return dailySalary * days;
    }

    private int calculateActualDays(VacationRequest request) {
        List<Holiday> holidays = holidayService.getHolidays(request.getStartDate().getYear());
        int workingDays = 0;

        for (LocalDate date = request.getStartDate(); !date.isAfter(request.getEndDate()); date = date.plusDays(1)) {
            if (!holidayService.isHoliday(date, holidays)) {
                workingDays++;
            }
        }
        return workingDays;
    }
}