package math.entity;

import math.entity.SimulationSegments.Segment;
import org.junit.Test;

import static org.junit.Assert.*;

public class BetterGeneratorRandomTest {

    @Test
    public void generateRandomSegment() {
        int counterMinEquals = 0;
        int counterMaxEquals = 0;
        for (int i = 0; i < 5000; i++) {
            Segment segment = BetterGeneratorRandom.generateRandomSegment(0, 20,0);
            assertTrue(segment.getFirstDot()<segment.getSecondDot());
            assertNotEquals(segment.getFirstDot(), segment.getSecondDot());
            if(segment.getFirstDot() == 0){
                counterMinEquals++;
            }
            if(segment.getSecondDot() == 20){
                counterMaxEquals++;
            }
        }
        assertTrue(counterMinEquals > 0);
        assertTrue(counterMaxEquals > 0);
    }

}