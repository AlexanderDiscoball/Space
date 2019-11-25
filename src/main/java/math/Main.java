package math;


import math.entity.SegmentPack;
import math.entity.array.ArrayHash;
import math.entity.dropintervaltrack.TrackDropInterval;
import math.entity.interval.Interval;
import math.entity.linesegments.Algorithms;
import math.entity.linesegments.Track;

import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        ArrayHash mainArray = Simulation.genSimulationForTest();
        SegmentPack segmentPack = Algorithms.getAllIntervals(mainArray);

        int max  = Collections.max(segmentPack.getCollection(), Comparator.comparing(Interval::getSecondDot)).getSecondDot();
        int min = Collections.min(segmentPack.getCollection(), Comparator.comparing(Interval::getFirstDot)).getFirstDot();
        int fullLength = max - min;

        TrackDropInterval dropIntervals = GeneratorRandom.createDropIntervals(InputData.getDropPoints(),fullLength);
        FlightPathCreator flightPathCreator = new FlightPathCreator(dropIntervals,mainArray);
        flightPathCreator.createResult();
        Track result = flightPathCreator.getResult();
        System.out.println("Остатки "+flightPathCreator.getMainArray().values());

    }
}
