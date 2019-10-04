package math;

import math.entity.SimulationSegments.Segment;
import math.entity.SimulationSegments.StackSegmentsList;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GeneratorRandomTest {

    @Test
    public void createRandomDropPoints(){
        List<Integer> dropPoints = GeneratorRandom.createRandomDropPoints();
        assertEquals(dropPoints.size(),InputData.getDropPoints());
        assertEquals(dropPoints.get(dropPoints.size() - 1).intValue(),InputData.getTimeAmount());
    }

    @Test
    public void generateRandomSegment() {
       Segment segment = GeneratorRandom.generateRandomSegment(1,2,10);

       assertTrue(segment.getFirstDot()<=segment.getSecondDot());
       assertTrue(segment.getFirstDot()>=1);
       assertTrue(segment.getSecondDot()<=5);
    }

    @Test
    public void generateRandomIntervals() {
        int intervalMax;
        int intervalMin;
        int previousMax = 0;
        double amountOfTime = InputData.getTimeAmount();
        double segmentsAmount = InputData.getSegmentsAmount();
        for (int segmentCounter = 1; segmentCounter <= segmentsAmount; segmentCounter++) {
            intervalMin = getMinInterval(segmentCounter);
            assertEquals(previousMax,intervalMin);

            intervalMax = (int) ((amountOfTime / segmentsAmount) * segmentCounter);
            previousMax = intervalMax;
        }
    }

    @Test
    public void generateRandomSegmentsWithIntervals() {
       ArrayList<StackSegmentsList> list  = GeneratorRandom.generateManyStacks();
        for (StackSegmentsList stackSegmentsList : list) {
            if(stackSegmentsList.size() < InputData.getSegmentsAmount()){
                Assert.fail("будет содержать null в  матрице");
            }
        }
    }

    private static int getMinInterval(int segmentCounter){
        double amountOfTime = InputData.getTimeAmount();
        double segmentsAmount = InputData.getSegmentsAmount();
        if (segmentCounter == 1) {
            return  0;
        } else {
            return  (int) ((amountOfTime / segmentsAmount) * (segmentCounter - 1));
        }
    }
}