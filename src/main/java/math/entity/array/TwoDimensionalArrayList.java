package math.entity.array;

import math.entity.areasegments.Area;
import math.entity.linesegments.LineList;
import math.entity.interval.Interval;
import math.entity.SegmentPack;

import java.util.*;


public class TwoDimensionalArrayList implements TwoDimensionalArray {
    private ArrayList<SegmentPack> matrix;

    public TwoDimensionalArrayList(){
        matrix = new ArrayList<>();
    }
    public TwoDimensionalArrayList(ArrayList<SegmentPack> matrix){
        this.matrix = matrix;
    }

    public void add(SegmentPack segmentPack){
        matrix.add(segmentPack);
    }
    public void add(int index,SegmentPack segmentPack){
        matrix.add(index,segmentPack);
    }
    public Collection<SegmentPack> getCollection(){
        return matrix;
    }

    public int size(){
        return matrix.size();
    }

    @Override
    public SegmentPack get() {
        return null;
    }

    public SegmentPack get(int index){
       return matrix.get(index);
    }

    public void set(Interval interval, int index1, int index2){
        LineList segmentsList =(LineList) matrix.get(index1);
        segmentsList.set(interval, index2);
    }
    public TwoDimensionalArray clone(){
        ArrayList<SegmentPack> m =(ArrayList<SegmentPack>) matrix.clone();
        return new TwoDimensionalArrayList(m);
    }

    @Override
    public List<Integer> setAreasId() {
        int id = 0;
        List<Integer> indexes = new ArrayList<>();
        for (SegmentPack segmentPack :matrix) {
            indexes.add(id);
            if(segmentPack instanceof Area){
               Area area = (Area) segmentPack;
               area.setId(id);
            }
            for (Interval interval :segmentPack) {
                interval.setRoll(id);
            }
            id++;
        }
        return indexes;
    }

    @Override
    public void remove(int index) {
        matrix.remove(index);
    }

    @Override
    public void remove(SegmentPack segmentPack) {
        matrix.remove(segmentPack);
    }

    public void remove(LineList stackSegmentsList){
        matrix.remove(stackSegmentsList);
    }

    public void sort(){
        matrix.sort(SegmentPack::compareTo);
    }


    public SegmentPack getBestSolution(){
        return Collections.max(matrix, SegmentPack::compareTo);
    }

    @Override
    public Iterator iterator() {
        return matrix.iterator();
    }

}
