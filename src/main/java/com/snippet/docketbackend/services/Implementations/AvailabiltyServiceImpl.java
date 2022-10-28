package com.snippet.docketbackend.services.Implementations;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.snippet.docketbackend.models.Availability;
import com.snippet.docketbackend.models.EventTemplate;
import com.snippet.docketbackend.models.User;
import com.snippet.docketbackend.repository.AvailabilityRepo;
import com.snippet.docketbackend.repository.EventRepo;
import com.snippet.docketbackend.repository.UserRepo;
import com.snippet.docketbackend.services.AvailabilityService;
import com.snippet.docketbackend.utils.Response;
import com.snippet.docketbackend.utils.ResponseStatus;

@Service
public class AvailabiltyServiceImpl implements AvailabilityService {

        @Autowired
        private UserRepo userRepo;
        @Autowired
        private EventRepo eventRepo;
        @Autowired
        private AvailabilityRepo availabilityRepo;

        @Transactional
        @Override
        public Response addEventAvailability(Availability availability, Long eId, Long userId) {
                Optional<User> user = userRepo.findById(userId);

                if (user.isEmpty()) {
                        return new Response(
                                        "User does not exist",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                Optional<EventTemplate> event = eventRepo.findEventById(eId, user.get().getEmail());

                if (event.isEmpty()) {
                        return new Response(
                                        "NO events exist with this id",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                ArrayList<String> days = new ArrayList();

                for (Availability avail : event.get().getAvailability()) {
                        days.add(avail.getDays());
                }

                if (days.contains(availability.getDays())) {
                        return new Response(
                                        "This day is already booked",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                availabilityRepo.save(availability);
                event.get().getAvailability().add(availability);

                return new Response(
                                "Availability added",
                                new ResponseStatus(
                                                HttpStatus.OK.value(),
                                                HttpStatus.OK.getReasonPhrase()),
                                availability);
        }

        @Transactional
        @Override
        public Response updateEventAvailability(Long aId, Long eId, Long userId, String day, String start_time,
                        String end_time) {
                Optional<User> user = userRepo.findById(userId);

                if (user.isEmpty()) {
                        return new Response(
                                        "User does not exist",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                Optional<EventTemplate> event = eventRepo.findEventById(eId, user.get().getEmail());

                if (event.isEmpty()) {
                        return new Response(
                                        "NO events exist with this id",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));

                }

                Optional<Availability> availability = availabilityRepo.findById(aId);

                if (availability.isEmpty()) {
                        return new Response(
                                        "NO availability exist with this id",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));

                }

                if (event.get().getAvailability().contains(availability.get())) {

                        if (day != null) {
                                ArrayList<String> days = new ArrayList();

                                for (Availability avail : event.get().getAvailability()) {
                                        days.add(avail.getDays());
                                }

                                if (days.contains(day)) {
                                        return new Response(
                                                        "This day is already booked",
                                                        new ResponseStatus(
                                                                        HttpStatus.BAD_REQUEST.value(),
                                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                                } else {
                                        availability.get().setDays(day);
                                }
                        }

                        if (start_time != null) {
                                availability.get().setStart_time(start_time);
                        }

                        if (end_time != null) {
                                availability.get().setEnd_time(end_time);
                        }

                        return new Response(
                                        "Availability updation done",
                                        new ResponseStatus(
                                                        HttpStatus.OK.value(),
                                                        HttpStatus.OK.getReasonPhrase()));
                }

                return new Response(
                                "event does not contain this availability",
                                new ResponseStatus(
                                                HttpStatus.BAD_REQUEST.value(),
                                                HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        @Transactional
        @Override
        public Response deleteEventAvailability(Long aId, Long eId, Long userId) {
                Optional<User> user = userRepo.findById(userId);

                if (user.isEmpty()) {
                        return new Response(
                                        "User does not exist",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                Optional<EventTemplate> event = eventRepo.findEventById(eId, user.get().getEmail());

                if (event.isEmpty()) {
                        return new Response(
                                        "NO events exist with this id",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));

                }

                Optional<Availability> availability = availabilityRepo.findById(aId);

                if (availability.isEmpty()) {
                        return new Response(
                                        "NO availability exist with this id",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));

                }

                if (event.get().getAvailability().contains(availability.get())) {
                        event.get().getAvailability().remove(availability.get());
                        availabilityRepo.deleteById(aId);

                        return new Response(
                                        "Successfully Deleted!",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                return new Response(
                                "event does not contain this availability",
                                new ResponseStatus(
                                                HttpStatus.BAD_REQUEST.value(),
                                                HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        @Transactional
        @Override
        public Response addUserAvailability(Availability availability, Long userId) {
                Optional<User> user = userRepo.findById(userId);

                if (user.isEmpty()) {
                        return new Response(
                                        "User does not exist",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                ArrayList<String> days = new ArrayList();

                for (Availability avail : user.get().getAvailability()) {
                        days.add(avail.getDays());
                }

                if (days.contains(availability.getDays())) {
                        return new Response(
                                        "This day is already booked",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                availabilityRepo.save(availability);
                user.get().getAvailability().add(availability);

                return new Response(
                                "Availability added",
                                new ResponseStatus(
                                                HttpStatus.OK.value(),
                                                HttpStatus.OK.getReasonPhrase()),
                                availability);
        }

        @Transactional
        @Override
        public Response updateUserAvailability(Long aId,
                        Long userId,
                        String day,
                        String start_time,
                        String end_time) {
                Optional<User> user = userRepo.findById(userId);

                if (user.isEmpty()) {
                        return new Response(
                                        "User does not exist",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                Optional<Availability> availability = availabilityRepo.findById(aId);

                if (availability.isEmpty()) {
                        return new Response(
                                        "NO availability exist with this id",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));

                }

                if (user.get().getAvailability().contains(availability.get())) {

                        if (day != null) {
                                ArrayList<String> days = new ArrayList();

                                for (Availability avail : user.get().getAvailability()) {
                                        days.add(avail.getDays());
                                }

                                if (days.contains(day)) {
                                        return new Response(
                                                        "This day is already booked",
                                                        new ResponseStatus(
                                                                        HttpStatus.BAD_REQUEST.value(),
                                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                                } else {
                                        availability.get().setDays(day);
                                }
                        }

                        if (start_time != null) {
                                availability.get().setStart_time(start_time);
                        }

                        if (end_time != null) {
                                availability.get().setEnd_time(end_time);
                        }

                        return new Response(
                                        "Availability updation done",
                                        new ResponseStatus(
                                                        HttpStatus.OK.value(),
                                                        HttpStatus.OK.getReasonPhrase()));
                }

                return new Response(
                                "user does not contain this availability",
                                new ResponseStatus(
                                                HttpStatus.BAD_REQUEST.value(),
                                                HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }

        @Transactional
        @Override
        public Response deleteUserAvailability(Long aId, Long userId) {
                Optional<User> user = userRepo.findById(userId);

                if (user.isEmpty()) {
                        return new Response(
                                        "User does not exist",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                Optional<Availability> availability = availabilityRepo.findById(aId);

                if (availability.isEmpty()) {
                        return new Response(
                                        "NO availability exist with this id",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));

                }

                if (user.get().getAvailability().contains(availability.get())) {
                        user.get().getAvailability().remove(availability.get());
                        availabilityRepo.deleteById(aId);

                        return new Response(
                                        "Successfully Deleted!",
                                        new ResponseStatus(
                                                        HttpStatus.OK.value(),
                                                        HttpStatus.OK.getReasonPhrase()));
                }

                return new Response(
                                "user does not contain this availability",
                                new ResponseStatus(
                                                HttpStatus.BAD_REQUEST.value(),
                                                HttpStatus.BAD_REQUEST.getReasonPhrase()));
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

                Optional<EventTemplate> event = eventRepo.findEventById(id, user.get().getEmail());

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
        public Response getUserAvailabilityDetails(Long userId) {
                Optional<User> user = userRepo.findById(userId);

                if (user.isEmpty()) {
                        return new Response(
                                        "User does not exist",
                                        new ResponseStatus(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));
                }

                return new Response(
                                "Availability fetched",
                                new ResponseStatus(
                                                HttpStatus.OK.value(),
                                                HttpStatus.OK.getReasonPhrase()),
                                user.get().getAvailability());
        }
}
