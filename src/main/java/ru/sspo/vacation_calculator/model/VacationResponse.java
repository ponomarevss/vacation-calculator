package ru.sspo.vacation_calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacationResponse {
    private double averageSalary;
    private double vacationPay;
    private Integer vacationDays;
}
