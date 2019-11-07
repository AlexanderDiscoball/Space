package math.entity.interval;

import math.InputData;

public class ZeroInterval extends Interval {

    private static ZeroInterval zeroSegment;
    private final Integer priority;


    private ZeroInterval() throws IllegalArgumentException {
        super(0,0,InputData.getChannelAmount()+1, -1);
        this.priority = InputData.getChannelAmount()+1;
    }

    public static synchronized ZeroInterval getInstance() {
        if (zeroSegment == null) {
            zeroSegment = new ZeroInterval();
        }
        return zeroSegment;
    }
    public Integer getPriority() {
        return priority;
    }
}
