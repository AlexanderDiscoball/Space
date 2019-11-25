package math.entity;

import math.entity.interval.Interval;
import math.entity.linesegments.LineList;
import org.junit.Test;

import static org.junit.Assert.*;

public class LineListOldTest {

    private LineList stack;

    @Test
    public void addBefore() {
        Interval mainInterval = new Interval(2,4,0,-1);
        Interval intervalBefore = new Interval(0,1,0,-1);
        int size = 2;
        addSements(mainInterval, intervalBefore);
        int realSize = stack.size();
        assertEquals(size,realSize);
    }
    @Test
    public void addOnBefore() {
        Interval mainInterval = new Interval(2,4,0,-1);
        Interval intervalOnBefore = new Interval(0,2,0,-1);
        int size = 2;
        addSements(mainInterval, intervalOnBefore);
        int realSize = stack.size();
        assertEquals(size,realSize);
    }
    @Test
    public void addAfter() {
        Interval mainInterval = new Interval(2,4,0,-1);
        Interval intervalAfter = new Interval(5,6,0,-1);
        int size = 2;
        addSements(mainInterval, intervalAfter);
        int realSize = stack.size();
        assertEquals(size,realSize);

    }
    @Test
    public void addOnAfter() {
        Interval mainInterval = new Interval(2,4,0,-1);
        Interval intervalOnAfter = new Interval(4,6,0,-1);
        int size = 2;
        addSements(mainInterval, intervalOnAfter);
        int realSize = stack.size();
        assertEquals(size,realSize);

    }
    @Test
    public void addEquals() {
        Interval mainInterval = new Interval(2,4,0,-1);
        Interval equalsInterval = new Interval(2,4,0,-1);
        int size = 1;
        addSements(mainInterval, equalsInterval);
        int realSize = stack.size();
        assertEquals(size,realSize);
    }

    @Test
    public void insideBefore() {
        int size = 1;
        Interval mainInterval = new Interval(2,4,0,-1);
        Interval intervalInsideBefore = new Interval(1,3,0,-1);
        addSements(mainInterval, intervalInsideBefore);
        System.out.println(stack.get(0));
        int realSize = stack.size();
        assertEquals(size,realSize);
    }
    @Test
    public void insideAfter() {
        int size = 1;
        Interval mainInterval = new Interval(2,4,0,-1);
        Interval intervalInsideAfter = new Interval(3,5,0,-1);
        addSements(mainInterval, intervalInsideAfter);
        int realSize = stack.size();
        assertEquals(size,realSize);
    }

    private void addSements(Interval interval1, Interval interval2){
        stack = new LineList(0);
        stack.add(interval1);
        if(stack.isSegmentCanBeInList(interval2)) {
            stack.add(interval2);
        }
    }

    @Test
    public void addWithoutLineEquals() {
        Interval mainInterval = new Interval(2,4,0,-1);
        Interval intervalInsideAfter = new Interval(4,6,1,-1);
        stack = new LineList(0);
        stack.add(mainInterval);
        stack.addWithCheck(intervalInsideAfter);
        int size = 2;
        int realSize = stack.size();
        assertEquals(size,realSize);
    }

    @Test
    public void addZeroSegments() {
    }
    @Test
    public void addAll() {
        stack = new LineList(0);
        LineList stackSegmentsList = new LineList(-1);
        LineList stackSegmentsList2 = new LineList(-1);
        stackSegmentsList.add(new Interval(0,1,-1,-1));
        stackSegmentsList.add( new Interval(4,5,-1,-1));
        stackSegmentsList2.add(new Interval(0,1,-1,-1));
        stackSegmentsList2.add(new Interval(0,1,-1,-1));

        stack.addAll(stackSegmentsList);
        stack.addAll(stackSegmentsList2);
        System.out.println(stack.toString());
        assertEquals(stackSegmentsList.size() + stackSegmentsList2.size(),stack.size());
    }

    @Test
    public void get() {
    }

    @Test
    public void getNumberOfLine() {
    }

    @Test
    public void isSegmentCanBeInList() {
    }

    @Test
    public void sortLineSegments() {
    }

    @Test
    public void getMax() {
        stack = new LineList(-1);
        Interval interval = new Interval(4,8,-1,-1);
        stack.add(interval);
        stack.add( new Interval(0,1,-1,-1));
        stack.add( new Interval(20,21,-1,-1));
        Interval s = stack.getMaxSegment();

        assertEquals(interval.getLength(), s.getLength() );
    }
}