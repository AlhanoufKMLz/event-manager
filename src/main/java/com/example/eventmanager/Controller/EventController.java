package com.example.eventmanager.Controller;

import com.example.eventmanager.ApiResponse.ApiResponse;
import com.example.eventmanager.Model.Event;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    ArrayList<Event> events = new ArrayList<>();

    //BASIC CRUD ENDPOINTS
    @PostMapping("/add")
    public ApiResponse addEvent(@RequestBody Event newEvent){
        //check id
        for(Event e: events){
            if(e.getId().equalsIgnoreCase(newEvent.getId()))
                return new ApiResponse("The ID: " + newEvent.getId() + " is already used please enter another ID.");
        }
        //check capacity
        if(newEvent.getCapacity() < 0)
            return new ApiResponse("Capacity must be positive number.");
        //check dates
        if(newEvent.getStartDate().isAfter(newEvent.getEndDate())){
            return new ApiResponse("Start date must be before end date");
        }

        events.add(newEvent);
        return new ApiResponse("Event added successfully.");
    }

    @GetMapping("/get/all")
    public ArrayList<Event> getAllEvents(){
        return events;
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateEvent(@PathVariable String id, @RequestBody Event newEvent){
        //check capacity
        if(newEvent.getCapacity() < 0)
            return new ApiResponse("Capacity must be positive number.");
        //check dates
        if(newEvent.getStartDate().isAfter(newEvent.getEndDate())){
            return new ApiResponse("Start date must be before end date");
        }

        for(int i=0; i < events.size(); i++){
            if(events.get(i).getId().equalsIgnoreCase(id)){
                events.set(i, newEvent);
                return new ApiResponse("Event updated successfully.");
            }
        }
        return new ApiResponse("Event with ID: " + id + " not found.");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteEvent(@PathVariable String id){
        for(int i=0; i < events.size(); i++) {
            if (events.get(i).getId().equalsIgnoreCase(id)) {
                events.remove(i);
                return new ApiResponse("Event deleted successfully");
            }
        }
        return new ApiResponse("Event with ID: " + id + " not found.");
    }


    //EXTRA ENDPOINTS
    @PutMapping("/update/capacity/{id}/{capacity}")
    public ApiResponse updateCapacity(@PathVariable String id, @PathVariable int capacity){
        //check capacity
        if(capacity < 0)
            return new ApiResponse("Capacity must be positive number.");

        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                event.setCapacity(capacity);
                return new ApiResponse("Event capacity updated successfully.");
            }
        }
        return new ApiResponse("Event with ID: " + id + " not found.");
    }

    @GetMapping("/get/id/{id}")
    public Event getById(@PathVariable String id){
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                return event;
            }
        }
        return null;
    }
}
