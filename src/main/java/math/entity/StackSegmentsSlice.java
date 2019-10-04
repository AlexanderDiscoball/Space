package math.entity;

import math.entity.SimulationSegments.StackSegmentsList;
import planner.spacecraft.AbstractMPZ;
import java.util.List;

public class StackSegmentsSlice extends StackSegmentsList {

    private List<AbstractMPZ> segmentsList;

    public StackSegmentsSlice(int LineNumber) {
        super(LineNumber);
    }
}
