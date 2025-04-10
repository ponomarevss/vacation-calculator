package ru.sspo.vacation_calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sspo.vacation_calculator.validator.VacationRequestValidatorImpl;

public class VacationCalculatorApplicationTests {

    private VacationRequestValidatorImpl validator;

    @BeforeEach
    public void setUp() {
        validator = new VacationRequestValidatorImpl();
    }

    @Test
    public void testValidateInputParameters_WithValidVacationDays() {
        // Arrange
        Double averageSalary = 3000.0;
        Integer vacationDays = 10;
        String startDate = null;
        String endDate = null;

        // Act & Assert
        assertDoesNotThrow(() -> validator.validateInputParameters(averageSalary, vacationDays, startDate, endDate));
    }

    @Test
    public void testValidateInputParameters_WithValidDates() {
        // Arrange
        Double averageSalary = 3000.0;
        Integer vacationDays = null;
        String startDate = "2025-04-01";
        String endDate = "2025-04-10";

        // Act & Assert
        assertDoesNotThrow(() -> validator.validateInputParameters(averageSalary, vacationDays, startDate, endDate));
    }

    @Test
    public void testValidateInputParameters_WithBothVacationDaysAndDates() {
        // Arrange
        Double averageSalary = 3000.0;
        Integer vacationDays = 10;
        String startDate = "2025-04-01";
        String endDate = "2025-04-10";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateInputParameters(averageSalary, vacationDays, startDate, endDate));
        assertEquals("Please provide either vacation days or start and end dates, not both.", exception.getMessage());
    }

    @Test
    public void testValidateInputParameters_WithNullValues() {
        // Arrange
        Double averageSalary = null;
        Integer vacationDays = null;
        String startDate = null;
        String endDate = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateInputParameters(averageSalary, vacationDays, startDate, endDate));
        assertEquals("Either vacation days or start and end dates must be provided.", exception.getMessage());
    }

    @Test
    public void testValidateAverageSalary_WithNegativeValue() {
        // Arrange
        Double averageSalary = -1000.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateInputParameters(averageSalary, null, null, null));
        assertEquals("Average salary must be a non-negative number.", exception.getMessage());
    }

    @Test
    public void testValidateDates_WithInvalidDateFormat() {
        // Arrange
        String startDate = "invalid-date";
        String endDate = "2025-04-10";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateDates(startDate, endDate));
        assertEquals("Invalid date format. Please use 'yyyy-MM-dd'.", exception.getMessage());
    }

    @Test
    public void testValidateDates_WithStartAfterEnd() {
        // Arrange
        String startDate = "2025-04-10";
        String endDate = "2025-04-01";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateDates(startDate, endDate));
        assertEquals("Start date must be before or equal to end date.", exception.getMessage());
    }
}
