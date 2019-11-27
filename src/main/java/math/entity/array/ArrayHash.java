package math.entity.array;

import math.entity.areasegments.AreaList;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;


public class ArrayHash{

    HashMap<Integer, AreaList> hashPack;

    public ArrayHash(){
        hashPack = new HashMap<>();
    }

    public ArrayHash(HashMap<Integer, AreaList> hashPack){
        this.hashPack = hashPack;
    }

    public HashMap<Integer, AreaList> getHashPack(){
        return hashPack;
    }

    public int size(){
        return hashPack.size();
    }

    public AreaList remove(Integer key){
        return hashPack.remove(key);
    }

    public void put(Integer key, AreaList value){
        hashPack.put(key,value);
    }

    public Collection<AreaList> values(){
      return hashPack.values();
    }

    public AreaList get(int key){
       return hashPack.get(key);
    }

    public boolean contain(Integer area){
       return hashPack.containsKey(area);
    }

}
