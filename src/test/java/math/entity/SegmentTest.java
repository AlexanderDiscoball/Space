package math.entity;

import math.entity.Segment.Segment;
import org.junit.Assert;
import org.junit.Test;


public class SegmentTest {

    @Test
    public void IllegalSegmentLower() throws IllegalArgumentException{
        try {
            Segment segment = new Segment(2,1,0);
            Assert.fail("Expected IOException");
        } catch (IllegalArgumentException thrown) {
            Assert.assertEquals("Первое число должно быть меньше второго", thrown.getMessage());
        }
    }

    @Test
    public void IllegalSegmentEquals(){
        try {
            Segment segment = new Segment(2,1,0);
            Assert.fail("Expected IOException");
        } catch (IllegalArgumentException thrown) {
            Assert.assertEquals("Первое число должно быть меньше второго", thrown.getMessage());
        }
    }

    @Test
    public void setPriority(){
        Segment segment = new Segment(1,2,-1000);
        Assert.assertEquals(segment.getPriority(), (Integer) 1000);
    }

    @Test
    public void setLength(){
        Segment segment = new Segment(1,100,-1000);
        Assert.assertEquals(segment.getLength(), (Integer) 99);
    }

}