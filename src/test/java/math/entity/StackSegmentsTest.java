package math.entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackSegmentsTest {

    private StackSegments stack;

    @Test
    public void addBefore() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentBefore = new Segment(0,1,0);
        int size = 2;
        addSements(mainSegment,segmentBefore);
        int realSize = stack.getSize();
        assertEquals(size,realSize);
    }
    @Test
    public void addOnBefore() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentOnBefore = new Segment(0,2,0);
        int size = 2;
        addSements(mainSegment,segmentOnBefore);
        int realSize = stack.getSize();
        assertEquals(size,realSize);
    }
    @Test
    public void addAfter() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentAfter = new Segment(5,6,0);
        int size = 2;
        addSements(mainSegment,segmentAfter);
        int realSize = stack.getSize();
        assertEquals(size,realSize);

    }
    @Test
    public void addOnAfter() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentOnAfter = new Segment(4,6,0);
        int size = 2;
        addSements(mainSegment,segmentOnAfter);
        int realSize = stack.getSize();
        assertEquals(size,realSize);

    }
    @Test
    public void addEquals() {
        Segment mainSegment = new Segment(2,4,0);
        Segment equalsSegment = new Segment(2,4,0);
        int size = 1;
        addSements(mainSegment,equalsSegment);
        int realSize = stack.getSize();
        assertEquals(size,realSize);
    }

    @Test
    public void insideBefore() {
        int size = 1;
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentInsideBefore = new Segment(1,3,0);
        addSements(mainSegment,segmentInsideBefore);
        System.out.println(stack.get(0));
        int realSize = stack.getSize();
        assertEquals(size,realSize);
    }
    @Test
    public void insideAfter() {
        int size = 1;
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentInsideAfter = new Segment(3,5,0);
        addSements(mainSegment,segmentInsideAfter);
        int realSize = stack.getSize();
        assertEquals(size,realSize);
    }

    private void addSements(Segment segment1, Segment segment2){
        stack = new StackSegments(0);
        stack.add(segment1);
        stack.add(segment2);
    }

    @Test
    public void addWithoutLineEquals() {
        Segment mainSegment = new Segment(2,4,0);
        Segment segmentInsideAfter = new Segment(4,6,1);
        stack = new StackSegments(0);
        stack.add(mainSegment);
        stack.addWithoutLineEquals(segmentInsideAfter);
        int size = 2;
        int realSize = stack.getSize();
        assertEquals(size,realSize);
    }

    @Test
    public void addZeroSegments() {
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
}