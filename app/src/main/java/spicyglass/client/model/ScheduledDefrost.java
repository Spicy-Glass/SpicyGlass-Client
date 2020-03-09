package spicyglass.client.model;

import spicyglass.client.integration.system.SGLogger;

public class ScheduledDefrost {
    private long time;
    //Which days to repeat on, S M T W T F S. Should always be of length 7.
    private boolean[] days;
    private boolean repeat, active;

    public ScheduledDefrost(long time, boolean[] days, boolean repeat, boolean active) {
        this.time = time;
        this.days = days;
        if(days.length != 7)
            SGLogger.warn("Warning: Days length in ScheduledDefrost was %s, expected 7.", days.length);
        this.repeat = repeat;
        this.active = active;
    }

    public ScheduledDefrost(long time, boolean[] days, boolean repeat) {
        this(time, days, repeat, true);
    }

    public long getTime() {
        return time;
    }

    public boolean[] getDays() {
        return days;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isActive() {
        return active;
    }
}
