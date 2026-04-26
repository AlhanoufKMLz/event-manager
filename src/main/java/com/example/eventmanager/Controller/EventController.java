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
        for(Event e: events){
            if(e.getId().equalsIgnoreCase(newEvent.getId()))
                return new ApiResponse("The ID: " + newEvent.getId() + " is already used please enter another ID.");
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
}
