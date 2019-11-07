package math;

import math.entity.Array.TwoDimensionalArray;
import math.entity.LineSegments.LineList;
import math.entity.interval.Interval;
import math.entity.SegmentPack;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.*;

public class SeparatorTest {
    private static final Separator separator = new Separator();
    private static int end;
    private static int start;
    private static LineList line;
    private static int firstDot;
    private static int secondDot;

    @Test
    public void createSubLine() {
        preparing();
        Interval interval = new Interval(firstDot,secondDot,-1,-1);
        int[] indexes = separator.getIndexes(line, interval);
        if(indexes[0] != -1) {
            LineList subLine = separator.createSubLine(line, indexes[0], indexes[1]);
            System.out.println(subLine);
            if (subLine.size() != 0) {
                assertTrue(subLine.getLastSegment().getSecondDot() <= secondDot);
                assertTrue(subLine.getFirstSegment().getFirstDot() >= firstDot);
            }
        }

    }

    @Test
    public void testSeparation() {
        preparing();
        System.out.println(line);
        LineList subLine = separator.separation(line, firstDot, secondDot);
        if (subLine.size() != 0) {
            assertTrue(subLine.getLastSegment().getSecondDot() <= secondDot);
            assertTrue(subLine.getFirstSegment().getFirstDot() >= firstDot);
        }
    }

    @Test
    public void cutOff() {
        preparing();
        Interval interval = new Interval(firstDot,secondDot,-1,-1);
        int[] index;
        index = separator.getIndexes(line, interval);
        LineList subLine = separator.createSubLine(line,index[0],index[1]);
        separator.cutOff(line,index[0],index[1]);
        for (Interval interval1 :subLine) {
            assertFalse(line.contain(interval1));
        }
    }

    @Test
    public void removeChosenSegments() {
        preparing();
        Interval interval = new Interval(firstDot,secondDot,-1,-1);
        int[] index = separator.getIndexes(line, interval);
        LineList subLine = separator.createSubLine(line,index[0],index[1]);
        separator.removeChosenSegments(line,subLine);
    }


    @Test
    public void getIndexes() {
        preparing();
        Interval interval = new Interval(firstDot,secondDot,-1,-1);
            int[] index;
        index = separator.getIndexes(line, interval);
        assertTrue(index[0] <= index[1]);
        if(index[0] != -1) {
            if (index[0] - 1 >= 0) {
                assertTrue(line.get(index[0] - 1).getFirstDot() < firstDot);
            }
            assertTrue(line.get(index[0]).getFirstDot() >= firstDot);
            assertTrue(line.get(index[1]).getSecondDot() <= secondDot);
            if (index[1] + 1 < line.size()) {
                assertTrue(line.get(index[1] + 1).getSecondDot() > secondDot);
            }
        }
    }

    @Test
    public void searchEndBorderIndex() {
        preparing();
        Interval interval = new Interval(firstDot,secondDot,-1,-1);
        int index = separator.searchEndBorderIndex(line, interval);
        assertTrue(line.get(index).getSecondDot() <= secondDot);
        if(index + 1 < line.size()) {
            assertTrue(line.get(index + 1).getSecondDot() > secondDot);
        }
        if(index - 1 >= 0) {
            assertTrue(line.get(index - 1).getSecondDot() < secondDot);
        }
    }

    @Test
    public void searchStartBorderIndex() {
        preparing();
        Interval interval = new Interval(firstDot,secondDot,-1,-1);
        int index = separator.searchStartBorderIndex(line, interval);
        assertTrue(line.get(index).getFirstDot() >= firstDot);
        if(index + 1 < line.size()) {
            assertTrue(line.get(index + 1).getFirstDot() > firstDot);
        }
        if(index - 1 >= 0) {
            assertTrue(line.get(index - 1).getFirstDot() < firstDot);
        }
    }


    @Test
    public void createLineArray() {
        preparing();
        TwoDimensionalArray twoDimensionalArray = GeneratorRandom.generateMatrix();
        line = Algorithms.getAllIntervals(twoDimensionalArray);
        TwoDimensionalArray array = separator.createLineArray(line);
        for (SegmentPack segments :array) {
            for (Interval interval :segments) {
                assertEquals(interval.getLine(), ((LineList) segments).getLine());
            }
        }

    }

    public static void preparing(){
        line =(LineList) GeneratorRandom.generateStackSegments(-1);
        firstDot = generateRandomStartPointForTest(line);
        secondDot = generateRandomEndPointForTest();
    }

    @Test
    public void TestForTest(){
        assertTrue(firstDot >= start);
        assertTrue(secondDot <= end);
    }

    private static int generateRandomStartPointForTest(LineList line){
        Random random = new Random();
        line.getCollection().sort(Comparator.comparing(Interval::getSecondDot));
        end = line.get(line.size()-1).getSecondDot();
        line.getCollection().sort(Comparator.comparing(Interval::getFirstDot));
        start = line.get(0).getFirstDot();
        return random.nextInt(end - start);
    }

    private static int generateRandomEndPointForTest(){
        Random random = new Random();
        return firstDot + random.nextInt(end - firstDot - 1);
    }
}