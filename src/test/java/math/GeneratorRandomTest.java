package math;

import math.entity.AreaSegments.Area;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArrayList;
import math.entity.Segment.Segment;
import math.entity.LineSegments.LineList;
import math.entity.SegmentPack;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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
       ArrayList<LineList> list  = GeneratorRandom.generateManyStacks();
        for (LineList stackSegmentsList : list) {
            if(stackSegmentsList.size() < InputData.getSegmentsAmount()){
                Assert.fail("будет содержать null в  матрице");
            }
        }
    }

    @Test
    public void SergEntityTest(){
        List<LinkedList<SergAlg>> tracks = GeneratorRandom.genareteSergEntity();
        int sumLength = 0;
        int sumBetween = 0;
        int start;
        int end ;
        int size = 0;
        for (LinkedList<SergAlg> list :tracks) {
            start = 0;
            size += list.size();
            for (SergAlg sergAlg :list) {
               sumLength += sergAlg.getLen();
               end = sergAlg.getBegin();
               sumBetween += end - start;
               start = sergAlg.getEnd();
            }
        }
        System.out.println(sumLength);
        System.out.println(sumBetween);
        assertEquals(InputData.getLAMBDA() * size,sumLength);
        assertEquals(2 * InputData.getLAMBDA()-1,sumBetween/size);

    }


    @Test
    public void separation(){
//        Integer end = 3000;
//        Integer start = 1000;
//        TwoDimensionalArray twoDimensionalArray = GeneratorRandom.generateBigInterval();
//        twoDimensionalArray = Algorithms.separation(twoDimensionalArray,start,end);
//        twoDimensionalArray = Algorithms.greedyAlgorithm(twoDimensionalArray);
//        System.out.println("Лучшее решение "+twoDimensionalArray.get(0));

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