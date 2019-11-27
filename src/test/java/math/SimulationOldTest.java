package math;

import math.entity.areasegments.AreaList;
import math.entity.array.ArrayHash;
import math.entity.array.TwoDimensionalArray;
import math.entity.linesegments.LineList;
import math.entity.interval.Interval;
import math.entity.SegmentPack;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

public class SimulationOldTest {

    Simulation simulation = new Simulation();

    @Test
    public void randomDropPointsTest(){
        Simulation simulation = new Simulation();
        simulation.genSimulationForTest();
        System.out.println(simulation.step+"   "+ simulation.amountSolutions);
        List<Integer> dropPoints = Simulation.createRandomDropPoints(simulation.amountSolutions,simulation.step);
        System.out.println(dropPoints);
        System.out.println("dropPoints.size() "+dropPoints.size());
    }

    @Test
    public void genSimulationForTestTest() {
        Simulation simulation = new Simulation();
        ArrayHash mainArray = simulation.genSimulationForTest();
        System.out.println(simulation.step);
        for (AreaList segments :mainArray.values()) {
            System.out.println(segments);
        }

    }

    @Test
    public void genSameArrays() {
        TwoDimensionalArray twoDimensionalArray = GeneratorRandom.generateMatrixForTest();
        List<TwoDimensionalArray> manySameMatrix = simulation.genSamePackArrays(twoDimensionalArray,InputData.getDropPoints());
        List<LineList> forTest = new ArrayList<>();
        System.out.println(simulation.step);

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