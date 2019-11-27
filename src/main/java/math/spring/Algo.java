package math.spring;

import math.entity.array.Selection;

import java.io.Serializable;
import java.util.ArrayList;

public interface Algo extends Serializable {
    //Track nadirAlgorithmWhenSort(Selection selection, ArrayHash mainArray);
    ArrayList nadirAlgorithmAll(Selection bunchOfTracks, int sizeOfBunchOfTracks, int trackToStartNo, int passageNum, int asd);
}
