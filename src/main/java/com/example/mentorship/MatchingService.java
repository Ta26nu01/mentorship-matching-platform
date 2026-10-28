package com.example.mentorship;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// The "brain": gives every mentor a score for a mentee.
@Service
public class MatchingService {

    // One line of the result list
    public record MatchResult(long mentorId, String mentorName, int score,
                              List<String> canTeachYou, List<String> sharedInterests) {}

    public List<MatchResult> findMatches(Mentee mentee, List<Mentor> mentors) {
        List<MatchResult> results = new ArrayList<>();

        for (Mentor mentor : mentors) {
            if (!mentor.hasCapacity()) continue;       // mentor is full

            // topics the mentee wants to learn that this mentor can teach
            List<String> canTeach = common(split(mentor.getSkills()), split(mentee.getInterests()));
            if (canTeach.isEmpty()) continue;          // nothing to teach = skip this mentor

            List<String> sharedInterests = common(split(mentor.getInterests()), split(mentee.getInterests()));

            int score = canTeach.size() * 5 + sharedInterests.size() * 2;
            if (mentor.getExperience() > mentee.getExperience()) {
                score += 5;                            // bonus for a more experienced mentor
            }

            results.add(new MatchResult(mentor.getId(), mentor.getName(), score, canTeach, sharedInterests));
        }

        results.sort((a, b) -> b.score() - a.score());   // highest score first
        return results;
    }

    // " Java, ML " -> [java, ml]
    private List<String> split(String text) {
        List<String> list = new ArrayList<>();
        if (text == null) return list;
        for (String s : text.split(",")) {
            s = s.trim().toLowerCase();
            if (!s.isEmpty() && !list.contains(s)) list.add(s);
        }
        return list;
    }

    // items that are in BOTH lists
    private List<String> common(List<String> a, List<String> b) {
        List<String> result = new ArrayList<>(a);
        result.retainAll(b);
        return result;
    }
}