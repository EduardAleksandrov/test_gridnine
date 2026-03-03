package com.gridnine;

import java.util.List;

import com.gridnine.testing.FilterRules;
import com.gridnine.testing.Flight;
// import com.gridnine.testing.Segment;
import com.gridnine.testing.FlightBuilder;
import com.gridnine.testing.FlightFilterImpl;

/*
 * Program entry point
 */
public class App {
    public String getGreeting() {
        return "Hello Gridnine!";
    }

    public static void main(String[] args) {
        
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilterImpl filterService = new FlightFilterImpl(flights);

        System.out.println("--- Полный список перелетов ---");
        flights.forEach(System.out::println);

        // 1. Исключить вылет до текущего момента времени
        System.out.println("\n--- 1. Исключен вылет до текущего момента ---");
        List<Flight> filtered1 = filterService.filterBy(FilterRules.DEPARTURE_IN_PAST);
        filtered1.forEach(System.out::println);

        // 2. Исключить сегменты с датой прилёта раньше даты вылета
        System.out.println("\n--- 2. Исключены сегменты с прилетом раньше вылета ---");
        List<Flight> filtered2 = filterService.filterBy(FilterRules.ARRIVAL_BEFORE_DEPARTURE);
        filtered2.forEach(System.out::println);

        // 3. Исключить перелеты, где общее время на земле > 2 часов
        System.out.println("\n--- 3. Исключено время на земле более 2 часов ---");
        List<Flight> filtered3 = filterService.filterBy(FilterRules.GROUND_TIME_EXCEEDS_TWO_HOURS);
        filtered3.forEach(System.out::println);

    }
}
