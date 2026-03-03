package com.gridnine.testing;

import java.util.List;

/**
 * Интерфейс для реализации различных правил фильтрации.
 */
public interface FlightFilter {
    List<Flight> filter(List<Flight> flights);
}
