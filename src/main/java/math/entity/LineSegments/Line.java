package math.entity.LineSegments;

import math.entity.interval.Interval;
import math.entity.SegmentPack;

public interface Line extends Iterable<Interval>, SegmentPack {
   int getLine();
}
