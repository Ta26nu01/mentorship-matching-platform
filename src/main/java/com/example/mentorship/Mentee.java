package com.example.mentorship;

public class Mentee {

    private long id;
    private String name;
    private String email;
    private String skills;       // skills the mentee already has
    private String interests;    // topics the mentee WANTS TO LEARN, e.g. "java, ai"
    private int experience;      // years
    private String goal;
    private Mentor mentor;       // null until the mentee is paired

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
    public Mentor getMentor() { return mentor; }
    public void setMentor(Mentor mentor) { this.mentor = mentor; }
}