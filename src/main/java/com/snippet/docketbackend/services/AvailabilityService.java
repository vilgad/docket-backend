package com.snippet.docketbackend.services;

import com.snippet.docketbackend.models.Availability;
import com.snippet.docketbackend.utils.Response;

public interface AvailabilityService {
    // CRUD Operation for event
    Response addEventAvailability(Availability availability, Long eId, Long userId);

    Response getEventAvailabilityDetails(Long userId, Long id);

    Response updateEventAvailability(
            Long aId,
            Long eId,
            Long userId,
            String day, 
            String start_time, 
            String end_time);

    Response deleteEventAvailability(Long aId, Long eId, Long userId);

    // CRUD Operation for user
    Response addUserAvailability(Availability availability, Long userId);

    Response updateUserAvailability(
        Long aId,
        Long userId,
        String day, 
        String start_time, 
        String end_time);

    Response deleteUserAvailability(Long aId, Long userId);

    Response getUserAvailabilityDetails(Long userId);
}
