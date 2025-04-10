package ru.sspo.vacation_calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sspo.vacation_calculator.model.Holiday;
import ru.sspo.vacation_calculator.model.VacationRequest;
import ru.sspo.vacation_calculator.model.VacationResponse;
import ru.sspo.vacation_calculator.validator.VacationRequestValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacationCalculator {
    private final HolidayService holidayService;
    private final VacationRequestValidator validator;

    @Value("${holiday.vacation.average-days-per-month}")
    private double averageDaysPerMonth;

    public VacationResponse calculateVacationPay(
            double averageSalary,
            Integer vacationDays,
            String startDate,
            String endDate
    ) {

        validator.validateInputParameters(averageSalary, vacationDays, startDate, endDate);

        if (vacationDays != null) {
            validator.validateVacationDays(vacationDays);
            double calculated = calculatePayWithDays(averageSalary, vacationDays);
            return new VacationResponse(averageSalary, calculated, vacationDays);
        } else {
            validator.validateDates(startDate, endDate);

            VacationRequest request = VacationRequest.builder()
                    .averageSalary(averageSalary)
                    .startDate(LocalDate.parse(startDate))
                    .endDate(LocalDate.parse(endDate))
                    .build();

            int actualVacationDays = calculateActualDays(request);
            double calculated = calculatePayWithDays(averageSalary, actualVacationDays);
            return new VacationResponse(averageSalary, calculated, actualVacationDays);
        }
    }

    private double calculatePayWithDays(double averageSalary, int days) {
        double dailySalary = averageSalary / averageDaysPerMonth;
        return dailySalary * days;
    }

    private int calculateActualDays(VacationRequest request) {
        List<Holiday> holidays = holidayService.getHolidays(request.getStartDate().getYear());
        Set<LocalDate> holidaySet = holidays.stream()
                .map(Holiday::getDate)
                .collect(Collectors.toSet());

        return (int) request.getStartDate().datesUntil(request.getEndDate().plusDays(1))
                .filter(date -> !holidaySet.contains(date))
                .count();
    }
}