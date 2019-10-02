package math.entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackSegmentsListTest {

    private StackSegmentsList stack;

    @Test
    public void addBefore() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentBefore = new Segment(0,1,0);
        int size = 2;
        addSements(mainSegment,segmentBefore);
        int realSize = stack.size();
        assertEquals(size,realSize);
    }
    @Test
    public void addOnBefore() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentOnBefore = new Segment(0,2,0);
        int size = 2;
        addSements(mainSegment,segmentOnBefore);
        int realSize = stack.size();
        assertEquals(size,realSize);
    }
    @Test
    public void addAfter() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentAfter = new Segment(5,6,0);
        int size = 2;
        addSements(mainSegment,segmentAfter);
        int realSize = stack.size();
        assertEquals(size,realSize);

    }
    @Test
    public void addOnAfter() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentOnAfter = new Segment(4,6,0);
        int size = 2;
        addSements(mainSegment,segmentOnAfter);
        int realSize = stack.size();
        assertEquals(size,realSize);

    }
    @Test
    public void addEquals() {
        Segment mainSegment = new Segment(2,4,0);
        Segment equalsSegment = new Segment(2,4,0);
        int size = 1;
        addSements(mainSegment,equalsSegment);
        int realSize = stack.size();
        assertEquals(size,realSize);
    }

    @Test
    public void insideBefore() {
        int size = 1;
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentInsideBefore = new Segment(1,3,0);
        addSements(mainSegment,segmentInsideBefore);
        System.out.println(stack.get(0));
        int realSize = stack.size();
        assertEquals(size,realSize);
    }
    @Test
    public void insideAfter() {
        int size = 1;
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentInsideAfter = new Segment(3,5,0);
        addSements(mainSegment,segmentInsideAfter);
        int realSize = stack.size();
        assertEquals(size,realSize);
    }

    private void addSements(Segment segment1, Segment segment2){
        stack = new StackSegmentsList(0);
        stack.add(segment1);
        stack.add(segment2);
    }

    @Test
    public void addWithoutLineEquals() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentInsideAfter = new Segment(4,6,1);
        stack = new StackSegmentsList(0);
        stack.add(mainSegment);
        stack.addWithCheck(segmentInsideAfter);
        int size = 2;
        int realSize = stack.size();
        assertEquals(size,realSize);
    }

    @Test
    public void addZeroSegments() {
    }
    @Test
    public void addAll() {
        stack = new StackSegmentsList(0);
        StackSegmentsList stackSegmentsList = new StackSegmentsList(-1);
        StackSegmentsList stackSegmentsList2 = new StackSegmentsList(-1);
        stackSegmentsList.add(new Segment(0,1,-1));
        stackSegmentsList.add( new Segment(4,5,-1));
        stackSegmentsList2.add(new Segment(0,1,-1));
        stackSegmentsList2.add(new Segment(0,1,-1));

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
        stack = new StackSegmentsList(-1);
        Segment segment = new Segment(4,8,-1);
        stack.add(segment);
        stack.add( new Segment(0,1,-1));
        stack.add( new Segment(20,21,-1));
        Segment s = stack.getMaxSegment();

        assertEquals(segment.getLength(), s.getLength() );
    }
}