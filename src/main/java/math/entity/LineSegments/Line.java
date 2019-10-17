package math.entity.LineSegments;

import math.entity.Segment.Segment;
import math.entity.SegmentPack;

public interface Line extends Iterable<Segment>, SegmentPack {
   int getLine();
}
