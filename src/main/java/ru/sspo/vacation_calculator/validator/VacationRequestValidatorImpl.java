package ru.sspo.vacation_calculator.validator;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class VacationRequestValidatorImpl implements VacationRequestValidator{
    @Override
    public void validateInputParameters(Double averageSalary, Integer vacationDays, String startDate, String endDate) {
        validateAverageSalary(averageSalary);

        if (vacationDays == null && (startDate == null || endDate == null)) {
            throw new IllegalArgumentException("Either vacation days or start and end dates must be provided.");
        }

        if (vacationDays != null && (startDate != null || endDate != null)) {
            throw new IllegalArgumentException("Please provide either vacation days or start and end dates, not both.");
        }
    }

    private void validateAverageSalary(Double averageSalary) {
        if (averageSalary == null || averageSalary < 0) {
            throw new IllegalArgumentException("Average salary must be a non-negative number.");
        }
    }

    @Override
    public void validateVacationDays(Integer vacationDays) {
        if (vacationDays == null || vacationDays < 0) {
            throw new IllegalArgumentException("Vacation days must be a non-negative integer.");
        }
    }

    @Override
    public void validateDates(String startDate, String endDate) {
        checkNotNull(startDate, "Start date must not be null.");
        checkNotNull(endDate, "End date must not be null.");

        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);

        checkStartBeforeEnd(start, end);
    }

    private void checkNotNull(String date, String message) {
        if (date == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.", e);
        }
    }

    private void checkStartBeforeEnd(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }
    }
}
