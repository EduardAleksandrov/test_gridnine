package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Интерфейс для реализации различных правил фильтрации.
 */
public interface FlightFilter {
    List<Flight> filter(List<Flight> flights);
}
