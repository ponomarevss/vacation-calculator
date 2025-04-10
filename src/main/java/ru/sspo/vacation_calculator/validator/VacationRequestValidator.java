package ru.sspo.vacation_calculator.validator;

public interface VacationRequestValidator {
    void validateInputParameters(
            Double averageSalary,
            Integer vacationDays,
            String startDate, String endDate
    );
    void validateVacationDays(Integer vacationDays);
    void validateDates(String startDate, String endDate);
}
