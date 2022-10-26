package com.snippet.docketbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snippet.docketbackend.models.Availability;
import com.snippet.docketbackend.models.EventTemplate;
import com.snippet.docketbackend.models.GoogleMeet;
import com.snippet.docketbackend.services.AvailabilityService;
import com.snippet.docketbackend.services.EventService;
import com.snippet.docketbackend.utils.Response;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping(path = "/createEventTemplate")
    public Response createEventTemplate(
            @RequestBody EventTemplate eventTemplate,
            @RequestParam Long userId) {
        return eventService.createEventTemplate(eventTemplate, userId);
    }

    @GetMapping(path = "/getAllEventTemplates")
    public Response getAllEventTemplates(@RequestParam Long userId) {
        return eventService.getAllEventTemplates(userId);
    }

    @GetMapping(path = "/getSingleEventTemplate")
    public Response getSingleEventTemplate(
            @RequestParam Long userId,
            @RequestParam String name) {
        return eventService.getSingleEventTemplate(name, userId);
    }

    @GetMapping(path = "/getEventsByType")
    public Response getEventsByType(
            @RequestParam Long userId,
            @RequestParam String type) {
        return eventService.getEventsByType(userId, type);
    }

    @GetMapping(path = "/getEventByLinkName")
    public Response getEventByLinkName(
            @RequestParam Long userId,
            @RequestParam String linkName) {
        return eventService.getEventByLinkName(userId, linkName);
    }

    @GetMapping(path = "/getEventAvailabilityDetails")
    public Response getEventAvailabilityDetails(
            @RequestParam Long userId,
            @RequestParam Long id) {
        return availabilityService.getEventAvailabilityDetails(userId, id);
    }

    @GetMapping(path = "/getEventGoogleMeetDetails")
    public Response getEventGoogleMeetDetails(@RequestParam Long userId, @RequestParam Long id) {
        return eventService.getEventGoogleMeetDetails(userId, id);
    }

    @PutMapping(path = "/addAvailability")
    public Response addAvailability(@RequestBody Availability availability, @RequestParam Long userId,
            @RequestParam Long id) {
        return availabilityService.addEventAvailability(availability, id, userId);
    }

    @PutMapping(path = "/addGoogleMeet")
    public Response addGoogleMeet(@RequestBody GoogleMeet googleMeet, @RequestParam Long userId,
            @RequestParam Long id) {
        return eventService.addGoogleMeet(googleMeet, userId, id);
    }

    @PutMapping(path = "/updateEventTemplate")
    public Response updateEventTemplate(
            @RequestParam Long userId,
            @RequestParam Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String linkName,
            @RequestParam(required = false) String eventColor,
            @RequestParam(required = false) Boolean isEnabled) {
        return eventService.updateEventTemplate(userId, id, name, description, type, linkName, eventColor, isEnabled);
    }

    @PutMapping(path = "/updateAvailability")
    public Response updateAvailability(
        @RequestParam Long aId,
            @RequestParam Long userId,
            @RequestParam Long eId,
            @RequestParam(required = false) String day,
            @RequestParam(required = false) String start_time,
            @RequestParam(required = false) String end_time) {
        return availabilityService.updateEventAvailability(aId, eId, userId, day, start_time, end_time);
    }

    @PutMapping(path = "/updateGoogleMeet")
    public Response updateGoogleMeet(
            @RequestParam Long userId,
            @RequestParam Long eId,
            @RequestParam(required = false) Boolean guestsCanInviteOthers,
            @RequestParam(required = false) Boolean guestsCanModify,
            @RequestParam(required = false) Boolean guestsCanSeeOtherGuests) {
        return eventService.updateGoogleMeet(userId, eId, guestsCanInviteOthers, guestsCanModify,
                guestsCanSeeOtherGuests);
    }

    @DeleteMapping(path = "/deleteEventTemplate")
    public Response deleteEventTemplate(@RequestParam Long userId, @RequestParam Long id) {
        return eventService.deleteEventTemplate(userId, id);
    }

    @DeleteMapping(path = "/deleteAvailability")
    public Response deleteAvailability(
        @RequestParam Long aId,
            @RequestParam Long userId,
            @RequestParam Long eId
     ) {
        return availabilityService.deleteEventAvailability(aId, eId, userId);
    }
}
