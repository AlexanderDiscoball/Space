package math.entity.array;

import math.entity.linesegments.Track;

import java.util.*;

public class SeparateArray {
    HashMap<Integer, Track> separatePack;

    public SeparateArray(){
        separatePack = new HashMap<>();
    }

    public SeparateArray(HashMap<Integer, Track> hashPack){
        this.separatePack = hashPack;
    }

    public HashMap<Integer, Track> getSeparatePack(){
        return separatePack;
    }

    public int size(){
        return separatePack.size();
    }

    public Track remove(Integer key){
        return separatePack.remove(key);
    }

    public Track get(Integer key){
        return separatePack.get(key);
    }
    public boolean containsKey(Integer key){
        return separatePack.containsKey(key);
    }

    public void put(Integer key, Track value){
        separatePack.put(key,value);
    }

    public Collection<Track> values(){
        return separatePack.values();
    }
}
