package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;
import java.time.Duration;
import java.util.stream.Collectors;

/**
 * Filter rules
 */
public class FilterRules {

    // 1. Исключен вылет до текущего момента
    public static final FlightFilter DEPARTURE_IN_PAST = f -> f.stream()
                .filter(flight -> flight.getSegments().get(0).getDepartureDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());

    // 2. Исключить сегменты с датой прилёта раньше даты вылета
    public static final FlightFilter ARRIVAL_BEFORE_DEPARTURE = fList -> fList.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(seg -> seg.getArrivalDate().isAfter(seg.getDepartureDate())))
                .collect(Collectors.toList());

    // 3. Исключить перелеты, где общее время на земле > 2 часов
    public static final FlightFilter GROUND_TIME_EXCEEDS_TWO_HOURS = fList -> fList.stream()
                .filter(flight -> {
                    List<Segment> segs = flight.getSegments();
                    if (segs.size() <= 1) return true;
                    long groundTime = 0;
                    for (int i = 0; i < segs.size() - 1; i++) {
                        groundTime += Duration.between(segs.get(i).getArrivalDate(), 
                                                      segs.get(i + 1).getDepartureDate()).toHours();
                    }
                    return groundTime <= 2;
                })
                .collect(Collectors.toList());
}
