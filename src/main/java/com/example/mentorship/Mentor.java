package com.example.mentorship;

// A plain Java class. Spring converts it to and from JSON automatically.
public class Mentor {

    private long id;
    private String name;
    private String email;
    private String skills;       // skills the mentor can teach, e.g. "java, ml"
    private String interests;    // e.g. "backend, ai"
    private int experience;      // years
    private int maxMentees = 2;  // how many mentees this mentor can take
    private int menteeCount;     // how many mentees this mentor has right now

    public long getId()
    {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
    public int getMaxMentees() { return maxMentees; }
    public void setMaxMentees(int maxMentees) { this.maxMentees = maxMentees; }
    public int getMenteeCount() { return menteeCount; }
    public void setMenteeCount(int menteeCount) { this.menteeCount = menteeCount; }

    public boolean hasCapacity() {
        return menteeCount < maxMentees;
    }
}