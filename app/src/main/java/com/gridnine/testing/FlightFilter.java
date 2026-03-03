package com.gridnine.testing;

import java.util.List;

/**
 * Интерфейс для реализации различных правил фильтрации.
 */
@FunctionalInterface
public interface FlightFilter {
    List<Flight> filter(List<Flight> flights);
}
