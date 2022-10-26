package com.snippet.docketbackend.services;

import com.snippet.docketbackend.models.Availability;
import com.snippet.docketbackend.models.EventTemplate;
import com.snippet.docketbackend.models.GoogleMeet;
import com.snippet.docketbackend.utils.Response;

public interface EventService {
    // GET Methods
    Response getAllEventTemplates(Long userId);

    Response getSingleEventTemplate(String name, Long userId);

    Response getEventsByType(Long userId, String type);

    Response getEventByLinkName(Long userId, String linkName);

    Response getEventGoogleMeetDetails(Long userId, Long id);

    // POST Methods
    Response createEventTemplate(EventTemplate eventTemplate, Long userId);

    // PUT Methods
//     Response addAvailability(Availability availability, Long userId, Long id);

    Response addGoogleMeet(GoogleMeet googleMeet, Long userId, Long id);

    Response updateEventTemplate(Long userId,
            Long id,
            String name,
            String description,
            String type,
            String linkName,
            String eventColor,
            Boolean isEnabled);

//     Response updateAvailability(
//             Long userId,
//             Long eId,
//             String day,
//             String start_time,
//             String end_time);

    Response updateGoogleMeet(
            Long userId,
            Long eId,
            Boolean guestsCanInviteOthers,
            Boolean guestsCanModify,
            Boolean guestsCanSeeOtherGuests);

    // DELETE Event
    Response deleteEventTemplate(Long userId, Long id);
}
