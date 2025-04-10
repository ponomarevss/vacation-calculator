package ru.sspo.vacation_calculator.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VacationRequest {
    private double averageSalary;
    private Integer vacationDays;
    private LocalDate startDate;
    private LocalDate endDate;
}