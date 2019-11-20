package math.spring;

import math.entity.Array.ArrayHash;
import math.entity.Array.Selection;
import math.entity.LineSegments.Track;

import java.util.ArrayList;

public interface Algo {
    //Track nadirAlgorithmWhenSort(Selection selection, ArrayHash mainArray);
    ArrayList nadirAlgorithmAll(Selection bunchOfTracks, int sizeOfBunchOfTracks, int trackToStartNo, int passageNum);
}
