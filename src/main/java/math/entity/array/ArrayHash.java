package math.entity.array;

import math.entity.SegmentPack;
import math.entity.areasegments.Area;

import java.util.Collection;
import java.util.HashMap;

public class ArrayHash {
    HashMap<Integer, Area> hashPack;

    public ArrayHash(){
        hashPack = new HashMap<>();
    }

    public ArrayHash(HashMap<Integer, Area> hashPack){
        this.hashPack = hashPack;
    }

    public HashMap<Integer, Area> getHashPack(){
        return hashPack;
    }

    public int size(){
        return hashPack.size();
    }

    public Area remove(Integer key){
        return hashPack.remove(key);
    }

    public void put(Integer key, Area value){
        hashPack.put(key,value);
    }

    public Collection<Area> values(){
      return hashPack.values();
    }

    public Area get(int key){
       return hashPack.get(key);
    }

    public boolean contain(Integer area){
       return hashPack.containsKey(area);
    }

}
