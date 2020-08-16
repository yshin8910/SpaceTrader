package main.java.models;

public enum Event {
    BANDIT("banditEncounter"),
    TRADER("traderEncounter"),
    POLICE("policeEncounter");

    private String sceneName;
    Event(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getSceneName() {
        return sceneName;
    }
}
