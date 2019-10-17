package math.entity.Segment;

import math.InputData;

public class ZeroSegment extends Segment {

    private static ZeroSegment zeroSegment;
    private final Integer priority;


    private ZeroSegment() throws IllegalArgumentException {
        super(0,0,InputData.getChannelAmount()+1);
        this.priority = InputData.getChannelAmount()+1;
    }

    public static synchronized ZeroSegment getInstance() {
        if (zeroSegment == null) {
            zeroSegment = new ZeroSegment();
        }
        return zeroSegment;
    }
    public Integer getPriority() {
        return priority;
    }
}
