package math.entity.areasegments;

import math.entity.SegmentPack;

public interface Area extends SegmentPack {
    void setId(int id);
    int getAreaId();
    boolean getAdded();
    void setAdded(boolean add);
}
