package math;

import math.entity.Array.ArrayHash;
import math.entity.Array.TwoDimensionalArray;
import math.entity.LineSegments.LineList;
import math.entity.interval.Interval;
import math.entity.SegmentPack;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static math.Simulation.genSamePackArrays;
import static org.junit.Assert.*;

public class SimulationTest {

    @Test
    public void genSimulationForTest() {
        ArrayHash mainArray = Simulation.genSimulationForTest();
        System.out.println(Simulation.step);
        for (SegmentPack segments :mainArray.values()) {
            System.out.println(segments);
        }

    }

    @Test
    public void genSameArrays() {
        TwoDimensionalArray twoDimensionalArray = GeneratorRandom.generateMatrixForTest();
        List<TwoDimensionalArray> manySameMatrix = genSamePackArrays(twoDimensionalArray,InputData.getDropPoints());
        List<LineList> forTest = new ArrayList<>();
        System.out.println(Simulation.step);

        for (TwoDimensionalArray segmentPacks :manySameMatrix) {
            for (SegmentPack segments :segmentPacks) {
                System.out.println(segments);
            }
            System.out.println();
        }

        for (TwoDimensionalArray segmentPacks :manySameMatrix) {
            LineList list = new LineList(-1);
            for (SegmentPack segments :segmentPacks) {
                list.addAll(segments);
            }
            forTest.add(list);
        }

        LineList forCompare = forTest.get(0);
        forTest.remove(0);
        for (SegmentPack segments :forTest) {
            for (int i = 0; i < forCompare.size(); i++) {
                Interval intervalComp = forCompare.get(i);
                Interval interval = segments.get(i);
                assertTrue(interval.isSegmentIntersections(intervalComp));
                assertEquals(interval.getAreaId(), intervalComp.getAreaId());
            }
        }

    }


    @Test
    public void generateMatrixForTest() {
        TwoDimensionalArray twoDimensionalArray = GeneratorRandom.generateMatrixForTest();
        for (SegmentPack segments :twoDimensionalArray) {
            int id = segments.getFirstSegment().getAreaId();
            for (Interval interval :segments) {
                assertEquals(id, interval.getAreaId());
            }

        }

    }
}