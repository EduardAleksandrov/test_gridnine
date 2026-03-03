package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterImpl {
    private final List<Flight> flights;

    public FlightFilterImpl(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Flight> filterBy(FlightFilter rule) {
        return rule.filter(flights);
    }
}
