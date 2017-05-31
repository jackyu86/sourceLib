package com.caej.underwriting.builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.caej.api.underwritting.UnderwritingRequest;

import io.sited.form.Form;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class UnderwritingFlightBuilder {
    public static UnderwritingRequest.UnderwritingRequestFlight build(Form form) {
        UnderwritingRequest.UnderwritingRequestFlight flight = new UnderwritingRequest.UnderwritingRequestFlight();
        Map<String, Object> map = form.value;
        if (map.get("flight") == null) return flight;
        Map<String, Object> group = JSON.fromJSON(JSON.toJSON(map.get("flight")), Map.class);
        flight.flightNo = group.get("flightNo").toString();
        LocalDateTime flightTime = JSON.fromJSON(JSON.toJSON(map.get("flightTime")), LocalDateTime.class);
        flight.flightTime = flightTime.format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
        return flight;
    }
}
