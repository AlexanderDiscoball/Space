package math.entity.Array;

import math.entity.SegmentPack;

import java.util.Collection;
import java.util.HashMap;

public class ArrayHash {
    HashMap<Integer, SegmentPack> hashPack;

    public ArrayHash(){
        hashPack = new HashMap<>();
    }

    public ArrayHash(HashMap<Integer, SegmentPack> hashPack){
        this.hashPack = hashPack;
    }

    public HashMap<Integer, SegmentPack> getHashPack(){
        return hashPack;
    }

    public int size(){
        return hashPack.size();
    }

    public SegmentPack remove(Integer key){
        return hashPack.remove(key);
    }

    public void put(Integer key, SegmentPack value){
        hashPack.put(key,value);
    }

    public Collection<SegmentPack> values(){
      return hashPack.values();
    }
}
