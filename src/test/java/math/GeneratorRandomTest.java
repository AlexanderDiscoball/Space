package math;

import math.entity.Segment;
import math.entity.StackSegments;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GeneratorRandomTest {

    @After
    public void tearDown() throws Exception {
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
       ArrayList<StackSegments> list  = GeneratorRandom.generateManyStacks();
        for (StackSegments stackSegments : list) {
            if(stackSegments.getSize() < InputData.getSegmentsAmount()){
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