package math.entity;

import math.entity.interval.Interval;
import org.junit.Assert;
import org.junit.Test;


public class IntervalTest {

    @Test
    public void IllegalSegmentLower() throws IllegalArgumentException{
        try {
            Interval interval = new Interval(2,1,0,-1);
            Assert.fail("Expected IOException");
        } catch (IllegalArgumentException thrown) {
            Assert.assertEquals("Первое число должно быть меньше второго", thrown.getMessage());
        }
    }

    @Test
    public void IllegalSegmentEquals(){
        try {
            Interval interval = new Interval(2,1,0,-1);
            Assert.fail("Expected IOException");
        } catch (IllegalArgumentException thrown) {
            Assert.assertEquals("Первое число должно быть меньше второго", thrown.getMessage());
        }
    }

    @Test
    public void setPriority(){
        Interval interval = new Interval(1,2,-1000,-1);
        Assert.assertEquals(interval.getPriority(), (Integer) 1000);
    }

    @Test
    public void setLength(){
        Interval interval = new Interval(1,100,-1000,-1);
        Assert.assertEquals(interval.getLength(), (Integer) 99);
    }

}