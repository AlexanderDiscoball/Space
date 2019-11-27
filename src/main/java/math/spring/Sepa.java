package math.spring;

import math.entity.array.ArrayHash;
import math.entity.linesegments.Track;

import java.util.HashMap;

public interface Sepa {
    HashMap<Integer, Track> separationArrays(ArrayHash mainArray, int start, int end);
}
