package math;

import math.entity.interval.Interval;
import math.entity.LineSegments.LineList;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GeneratorRandomTest {

    @Test
    public void createRandomDropPoints(){
        List<Integer> dropPoints = Simulation.createRandomDropPoints();
        if(!dropPoints.isEmpty()) {
            assertEquals(dropPoints.size(), InputData.getDropPoints());
            assertEquals(dropPoints.get(dropPoints.size() - 1).intValue(), InputData.getTimeAmount());
        }
    }

    @Test
    public void generateRandomSegment() {
       Interval interval = GeneratorRandom.generateRandomSegment(1,2,10);

       assertTrue(interval.getFirstDot()<= interval.getSecondDot());
       assertTrue(interval.getFirstDot()>=1);
       assertTrue(interval.getSecondDot()<=5);
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
       ArrayList<LineList> list  = GeneratorRandom.generateManyStacks();
        for (LineList stackSegmentsList : list) {
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