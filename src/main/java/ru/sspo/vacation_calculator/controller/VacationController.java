package ru.sspo.vacation_calculator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sspo.vacation_calculator.service.VacationCalculator;

@RestController
@RequiredArgsConstructor
public class VacationController {
    private final VacationCalculator vacationCalculator;

    @GetMapping("/calculate")
    public double calculateVacationPay(
            @RequestParam double averageSalary,
            @RequestParam(required = false) Integer vacationDays,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        return vacationCalculator.calculateVacationPay(averageSalary, vacationDays, startDate, endDate);
    }
}