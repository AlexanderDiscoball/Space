package math.entity;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class MatrixSet implements Matrix {
    private TreeSet<StackSegments> matrixSet;

    public MatrixSet(){
        matrixSet = new TreeSet<>(StackSegments::compareTo);
    }
    public MatrixSet(Comparator<StackSegments> comparator){
        matrixSet = new TreeSet<>(comparator);
    }
    public MatrixSet(Collection<StackSegments> collection){
        matrixSet =(TreeSet<StackSegments>) collection;
    }
    public TreeSet<? extends StackSegments> getTreeSet(){
        return matrixSet;
    }

    public int size(){
        return matrixSet.size();
    }
    public StackSegments get(){
        return matrixSet.pollFirst();
    }

    @Override
    public StackSegments get(int index) {
        return null;
    }

    public Collection<? extends StackSegments> getCollection(){
        return matrixSet;
    }

    @Override
    public Matrix clone() {
        TreeSet<StackSegments> matrix =(TreeSet<StackSegments>) matrixSet.clone();
        MatrixSet matrixSet = new MatrixSet(matrix);
        return matrixSet;
    }

    public void add(StackSegments stackSegments){
        matrixSet.add(stackSegments);
    }

    @Override
    public Iterator<StackSegments> iterator() {
        return matrixSet.iterator();
    }
}
