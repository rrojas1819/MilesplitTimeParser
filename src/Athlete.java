package src;

import java.util.ArrayList;
import java.util.List;

public class Athlete {
    private String name;
    private String time;
    private String fieldEventVar;
    private String resultLine;
    private String[] events;

    private final List<String> runningTimes = new ArrayList<>();
    private final List<String> fieldEvents = new ArrayList<>();

    public Athlete(String name, String time, boolean reverse) {
        this.name = reverse ? reverseName(name) : name;
        this.time = time;
    }

    public Athlete(String name) {
        this.name = name;
    }

    public Athlete(String name, String time) {
        this.name = name;
        this.time = time;
    }

    private String reverseName(String fullName) {
        String[] parts = fullName.split(",");
        if (parts.length < 2) return fullName.trim();
        return parts[1].trim() + " " + parts[0].trim();
    }

    public void setEvent(String[] newEvent) {
        this.events = newEvent;
    }

    public String getEvent() {
        return (events != null && events.length > 0) ? events[0] : "";
    }

    public List<String> getRunningTimes() {
        return runningTimes;
    }

    public List<String> getFieldEvents() {
        return fieldEvents;
    }

    public void parseAndStoreTime(String rawTime) {
        if (rawTime == null) return;
        rawTime = rawTime.strip();

        if (rawTime.contains("-")) {
            if (!fieldEvents.contains(rawTime)) {
                fieldEventVar = rawTime;
                time = null;
                fieldEvents.add(rawTime);
            }
        } else {
            if (!runningTimes.contains(rawTime)) {
                time = rawTime;
                runningTimes.add(rawTime);
            }
        }
    }

    @Override
    public String toString() {
        String eventStr = getEvent();
        StringBuilder sb = new StringBuilder();

        for (String t : runningTimes) {
            sb.append(name).append("\tTIMES\t").append(t).append("\t").append(eventStr).append("\n");
        }
        for (String f : fieldEvents) {
            sb.append(name).append("\tFIELD_EVENT\t").append(f).append("\t").append(eventStr).append("\n");
        }

        if (runningTimes.isEmpty() && fieldEvents.isEmpty()) {
            sb.append(name).append("\tTIME\t").append(time).append("\t").append(eventStr).append("\n");
        }

        return sb.toString();
    }

    public void setTime(String newTime) {
        this.time = newTime;
    }

    public void setResultLine(String line) {
        this.resultLine = line;
    }
}
