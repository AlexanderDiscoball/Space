package math.entity.Array;

import math.entity.SegmentPack;

import java.util.HashMap;

public class ArrayHash {
    HashMap<Integer, SegmentPack> hashPack;

    public ArrayHash(){
        hashPack = new HashMap<>();
    }

    public HashMap<Integer, SegmentPack> getHashPack(){
        return hashPack;
    }
}
