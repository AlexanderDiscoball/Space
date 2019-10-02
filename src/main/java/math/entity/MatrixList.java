package math.entity;

import java.util.*;


public class MatrixList implements Matrix {
    private ArrayList<StackSegments> matrix;

    public MatrixList(){
        matrix = new ArrayList<>();
    }
    public MatrixList(ArrayList<StackSegments> matrix){
        this.matrix = matrix;
    }

    public void add(StackSegments stackSegments){
        matrix.add(stackSegments);
    }
    public Collection<StackSegments> getCollection(){
        return matrix;
    }

    public int size(){
        return matrix.size();
    }

    @Override
    public StackSegments get() {
        return null;
    }

    public StackSegments get(int index){
       return matrix.get(index);
    }

    public void set(Segment segment, int index1, int index2){
        StackSegmentsList segmentsList =(StackSegmentsList) matrix.get(index1);
        segmentsList.set(segment, index2);
    }
    public Matrix clone(){
        ArrayList<StackSegments> m =(ArrayList<StackSegments>) matrix.clone();
        MatrixList matrixList = new MatrixList(m);
        return matrixList;
    }
    public void remove(StackSegmentsList stackSegmentsList){
        matrix.remove(stackSegmentsList);
    }

    public void sort(){
        matrix.sort(StackSegments::compareTo);
    }

    public StackSegments getBestSolution(){
        return Collections.max(matrix, StackSegments::compareTo);
    }

    @Override
    public Iterator<StackSegments> iterator() {
        return matrix.iterator();
    }
}
