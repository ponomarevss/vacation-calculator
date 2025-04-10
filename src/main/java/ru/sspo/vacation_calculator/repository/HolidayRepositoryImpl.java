package ru.sspo.vacation_calculator.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import ru.sspo.vacation_calculator.model.Holiday;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HolidayRepositoryImpl implements HolidayRepository {

    private final RestClient restClient;

    @Value("${holiday.api.url}")
    private String holidayApiUrl;

    @Override
    public List<Holiday> findHolidays(int year) {
        try {
            return restClient.get()
                    .uri(holidayApiUrl, year)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to retrieve holidays: " + e.getMessage(), e);
        }
    }
}