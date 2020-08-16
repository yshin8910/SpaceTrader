package main.java.models;

public enum Skill {
    ENGINEER("engineer"), PILOT("pilot"), FIGHTER("fighter"), MERCHANT("merchant");

    private String name;

    Skill(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
