package com.gridnine.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilterRulesTest {
    
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
    }

    @Test
    void testDepartureInPastFilter() {
        // Arrange
        Flight pastFlight = createFlight(now.minusDays(1), now.minusDays(1).plusHours(2));
        Flight futureFlight = createFlight(now.plusDays(1), now.plusDays(1).plusHours(2));
        List<Flight> flights = List.of(pastFlight, futureFlight);

        // Act
        List<Flight> result = FilterRules.DEPARTURE_IN_PAST.filter(flights);

        // Assert
        assertEquals(1, result.size(), "Should keep only 1 flight");
        assertEquals(futureFlight, result.get(0), "Should keep only the future flight");
    }

    @Test
    void testArrivalBeforeDepartureFilter() {
        // Arrange
        Flight invalidFlight = createFlight(now.plusHours(2), now.plusHours(1)); // Arrival before departure
        Flight validFlight = createFlight(now.plusHours(1), now.plusHours(2));
        List<Flight> flights = List.of(invalidFlight, validFlight);
        
        // Act
        List<Flight> result = FilterRules.ARRIVAL_BEFORE_DEPARTURE.filter(flights);

        // Assert
        assertEquals(1, result.size(), "Should filter out the invalid flight");
        assertEquals(validFlight, result.get(0));
    }

    @Test
    void testGroundTimeExceedsTwoHoursFilter() {
        // Arrange
        // Ground time = 3 hours (14:00 to 17:00)
        Flight longGroundTime = createFlight(
                now.plusHours(1), now.plusHours(2), // 12:00 - 13:00
                now.plusHours(5), now.plusHours(6)  // 16:00 - 17:00
        );
        // Ground time = 1 hour
        Flight shortGroundTime = createFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(3), now.plusHours(4)
        );
        // Single segment (no ground time)
        Flight singleSegment = createFlight(now.plusHours(1), now.plusHours(2));

        // Act
        List<Flight> flights = List.of(longGroundTime, shortGroundTime, singleSegment);
        List<Flight> result = FilterRules.GROUND_TIME_EXCEEDS_TWO_HOURS.filter(flights);

        // Assert
        assertEquals(2, result.size(), "Should filter out the flight with > 2h ground time");
        assertFalse(result.contains(longGroundTime));
        assertTrue(result.contains(shortGroundTime));
        assertTrue(result.contains(singleSegment));
    }

    /**
     * Helper to create flights without repeating Builder logic
     */
    private Flight createFlight(LocalDateTime... dates) {
        List<Segment> segments = new java.util.ArrayList<>();
        for (int i = 0; i < dates.length; i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
