package ru.sspo.vacation_calculator.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Holiday {
    private LocalDate date;
    private String localName;
    private String name;
}