package math.entity.array;

import math.entity.linesegments.Line;
import math.entity.linesegments.LineList;
import math.entity.interval.Interval;
import math.entity.SegmentPack;

import java.util.*;

public class TwoDimensionalArraySet implements TwoDimensionalArray {
    private TreeSet<SegmentPack> matrixSet;

    public TwoDimensionalArraySet(){
        matrixSet = new TreeSet<>(SegmentPack::compareTo);
    }
    public TwoDimensionalArraySet(Comparator<SegmentPack> comparator){
        matrixSet = new TreeSet<>(comparator);
    }
    public TwoDimensionalArraySet(Collection<SegmentPack> collection){
        matrixSet =(TreeSet<SegmentPack>) collection;
    }
    public TreeSet<SegmentPack> getTreeSet(){
        return matrixSet;
    }

    public int size(){
        return matrixSet.size();
    }
    public SegmentPack get(){
        return matrixSet.pollFirst();
    }

    @Override
    public Line get(int index) {
        return null;
    }

    public TreeSet<SegmentPack> getCollection(){
        return matrixSet;
    }

    @Override
    public TwoDimensionalArray clone() {
        TreeSet<SegmentPack> matrix =(TreeSet<SegmentPack>) matrixSet.clone();
        return new TwoDimensionalArraySet(matrix);
    }

    public TwoDimensionalArrayList castToList(){
        TwoDimensionalArrayList list = new TwoDimensionalArrayList();
        LineList listLine;
        for (SegmentPack segments :matrixSet) {
            listLine = new LineList(segments.getFirstSegment().getLine());
            for (Interval interval :segments) {
                listLine.add(interval);
            }
            list.add(listLine);
        }
        return list;
    }

    public void add(SegmentPack segmentPack){
        matrixSet.add(segmentPack);
    }

    @Override
    public List<Integer> setAreasId() {
        int id = 0;
        List<Integer> indexes = new ArrayList<>();
        for (SegmentPack segmentPack :matrixSet) {
            for (Interval interval :segmentPack) {
                interval.setLine(id);
                indexes.add(id);
            }
            id++;
        }
        return indexes;
    }

    @Override
    public void remove(int index) {

    }

    @Override
    public void remove(SegmentPack segmentPack) {
        matrixSet.remove(segmentPack);
    }

    @Override
    public Iterator iterator() {
        return matrixSet.iterator();
    }
}