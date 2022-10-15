package com.snippet.docketbackend.services.Implementations;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.snippet.docketbackend.models.Availability;
import com.snippet.docketbackend.models.EventTemplate;
import com.snippet.docketbackend.models.GoogleMeet;
import com.snippet.docketbackend.models.User;
import com.snippet.docketbackend.repository.AvailabilityRepo;
import com.snippet.docketbackend.repository.EventRepo;
import com.snippet.docketbackend.repository.GoogleMeetRepo;
import com.snippet.docketbackend.repository.UserRepo;
import com.snippet.docketbackend.services.EventService;
import com.snippet.docketbackend.services.UserService;
import com.snippet.docketbackend.utils.Response;
import com.snippet.docketbackend.utils.ResponseStatus;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private GoogleMeetRepo googleMeetRepo;
    @Autowired
    private AvailabilityRepo availabilityRepo;

    @Override
    public Response getAllEventTemplates(Long userId) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        List<EventTemplate> eventsList = user.get().getEventsTemplates();

        if (eventsList.isEmpty()) {
            return new Response(
                    "No Templates found",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()));
        }

        return new Response(
                "Events Templates fetched successfully",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()),
                eventsList);
    }

    @Override
    public Response getSingleEventTemplate(String name, Long userId) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findByName(name, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "No Template exist with this id",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        return new Response(
                "Event Template successfully fetched",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()),
                event.get());
    }

    @Override
    public Response getEventsByType(Long userId, String type) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        List<EventTemplate> events = eventRepo.findByType(type, user.get());

        if (events.isEmpty()) {
            return new Response(
                    "NO events exist of this type",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        return new Response(
                "Events fetched",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()),
                events);
    }

    @Override
    public Response getEventByLinkName(Long userId, String linkName) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findByLinkName(linkName, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "NO events exist with this link",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        return new Response(
                "Event fetched",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()),
                event);
    }

    @Override
    public Response getEventAvailabilityDetails(Long userId, Long id) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findEventById(id, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "NO events exist with this id",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        return new Response(
                "Event fetched",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()),
                event.get().getAvailability());
    }

    @Override
    public Response getEventGoogleMeetDetails(Long userId, Long id) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findEventById(id, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "NO events exist with this id",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        return new Response(
                "Event fetched",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()),
                event.get().getGoogleMeet());
    }

    @Transactional
    @Override
    public Response createEventTemplate(EventTemplate eventTemplate, Long userId) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        if (user.get().getEventsTemplates().contains(eventTemplate)) {
            return new Response(
                    "Template already exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        user.get().getEventsTemplates().add(eventTemplate);
        userRepo.save(user.get());

        return new Response(
                "Template created successfully",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()),
                eventTemplate);
    }

    @Override
    @Transactional
    public Response addAvailability(Availability availability, Long userId, Long id) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findEventById(id, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "NO events exist with this id",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        availabilityRepo.save(availability);
        event.get().setAvailability(availability);

        return new Response(
                "Availability added",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()),
                availability);
    }

    @Transactional
    @Override
    public Response addGoogleMeet(GoogleMeet googleMeet, Long userId, Long id) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findEventById(id, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "NO events exist with this id",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        googleMeetRepo.save(googleMeet);
        event.get().setGoogleMeet(googleMeet);

        return new Response(
                "google meet settings added",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()),
                googleMeet);
    }

    @Transactional
    @Override
    public Response updateEventTemplate(Long userId, Long id, String name, String description, String type,
            String linkName, String eventColor, Boolean isEnabled) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findEventById(id, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "NO events exist with this id",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));

        }

        if (name != null) {
            event.get().setName(name);
        }

        if (description != null) {
            event.get().setDescription(description);
        }

        if (type != null) {
            event.get().setType(type);
        }

        if (linkName != null) {
            event.get().setLinkName(linkName);
        }

        if (eventColor != null) {
            event.get().setEventColor(eventColor);
        }

        if (isEnabled != null) {
            event.get().setIsEnabled(isEnabled);
        }

        return new Response(
                "Template Updated Successfully!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()));
    }

    @Transactional
    @Override
    public Response updateAvailability(Long userId, Long eId, String day, String start_time, String end_time) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findEventById(eId, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "NO events exist with this id",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));

        }

        if (day != null) {
            event.get().getAvailability().setDays(day);
        }

        if (start_time != null) {
            event.get().getAvailability().setStart_time(start_time);
        }

        if (end_time != null) {
            event.get().getAvailability().setEnd_time(end_time);
        }

        return new Response(
                "Availability done",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()));
    }

    @Transactional
    @Override
    public Response updateGoogleMeet(Long userId, Long eId, Boolean guestsCanInviteOthers,
            Boolean guestsCanModify, Boolean guestsCanSeeOtherGuests) {

        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findEventById(eId, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "NO events exist with this id",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        if (guestsCanInviteOthers != null) {
            event.get().getGoogleMeet().setGuestsCanInviteOthers(guestsCanInviteOthers);
        }

        if (guestsCanModify != null) {
            event.get().getGoogleMeet().setGuestsCanModify(guestsCanModify);
        }

        if (guestsCanSeeOtherGuests != null) {
            event.get().getGoogleMeet().setGuestsCanSeeOtherGuests(guestsCanSeeOtherGuests);
        }

        return new Response(
                "Google meet settings updated",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()));
    }

    @Transactional
    @Override
    public Response deleteEventTemplate(Long userId, Long id) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "User does not exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        Optional<EventTemplate> event = eventRepo.findEventById(id, user.get());

        if (event.isEmpty()) {
            return new Response(
                    "NO events exist with this id",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        eventRepo.delete(event.get());

        return new Response(
                "Event deleted",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()));
    }
}