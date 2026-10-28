package com.example.mentorship;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

// The API. Each method below is one URL you can call from Postman.
@RestController
@RequestMapping("/api")
public class MentorshipController {

    // The data lives in these lists while the app is running.
    // (When you stop the app, the data is gone.)
    private final List<Mentor> mentors = new ArrayList<>();
    private final List<Mentee> mentees = new ArrayList<>();
    private long nextMentorId = 1;
    private long nextMenteeId = 1;

    private final MatchingService matchingService;

    // Spring gives us the MatchingService automatically
    public MentorshipController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    // ---------- Register ----------

    @PostMapping("/mentors")
    public Mentor addMentor(@RequestBody Mentor mentor) {
        checkEmail(mentor.getEmail());
        mentor.setId(nextMentorId++);
        mentor.setMenteeCount(0);
        mentors.add(mentor);
        return mentor;
    }

    @PostMapping("/mentees")
    public Mentee addMentee(@RequestBody Mentee mentee) {
        checkEmail(mentee.getEmail());
        mentee.setId(nextMenteeId++);
        mentee.setMentor(null);          // a new mentee has no mentor yet
        mentees.add(mentee);
        return mentee;
    }

    // ---------- View ----------

    @GetMapping("/mentors")
    public List<Mentor> getMentors() {
        return mentors;
    }

    @GetMapping("/mentees")
    public List<Mentee> getMentees() {
        return mentees;
    }

    // ---------- Matching ----------

    @GetMapping("/mentees/{menteeId}/matches")
    public List<MatchingService.MatchResult> getMatches(@PathVariable("menteeId") long menteeId) {
        Mentee mentee = findMentee(menteeId);
        if (mentee.getMentor() != null) {
            throw error(HttpStatus.CONFLICT, "This mentee already has a mentor");
        }
        return matchingService.findMatches(mentee, mentors);
    }

    // The mentee picks a mentor
    @PostMapping("/mentees/{menteeId}/request/{mentorId}")
    public Mentee sendRequest(@PathVariable("menteeId") long menteeId,
                              @PathVariable("mentorId") long mentorId) {
        Mentee mentee = findMentee(menteeId);
        Mentor mentor = findMentor(mentorId);

        if (mentee.getMentor() != null) {
            throw error(HttpStatus.CONFLICT, "This mentee already has a mentor");
        }
        if (!mentor.hasCapacity()) {
            throw error(HttpStatus.CONFLICT, "This mentor is full");
        }

        mentee.setMentor(mentor);
        mentor.setMenteeCount(mentor.getMenteeCount() + 1);
        return mentee;
    }

    // ---------- Helpers ----------

    private Mentee findMentee(long id) {
        for (Mentee m : mentees) {
            if (m.getId() == id) return m;
        }
        throw error(HttpStatus.NOT_FOUND, "Mentee not found");
    }

    private Mentor findMentor(long id) {
        for (Mentor m : mentors) {
            if (m.getId() == id) return m;
        }
        throw error(HttpStatus.NOT_FOUND, "Mentor not found");
    }

    private void checkEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw error(HttpStatus.BAD_REQUEST, "A valid email is required");
        }
        for (Mentor m : mentors) {
            if (email.equalsIgnoreCase(m.getEmail())) throw error(HttpStatus.CONFLICT, "This email is already registered");
        }
        for (Mentee m : mentees) {
            if (email.equalsIgnoreCase(m.getEmail())) throw error(HttpStatus.CONFLICT, "This email is already registered");
        }
    }

    private ResponseStatusException error(HttpStatus status, String message) {
        return new ResponseStatusException(status, message);
    }
}