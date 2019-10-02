package math;

import math.entity.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class TestTreeSet {
    static long timeToDelete2 = 0;

    public static MatrixSet dynamicAlgorithm(Matrix matrix){
        MatrixSet ways = new MatrixSet(StackSegments::compareTo);
        MatrixSet functions;

        StackSegments buff;

        StackSegmentsSet allIntervals = putAllIntervalsInList(matrix);
        System.out.println("Все интервалы в матрице сет "+allIntervals.getCollection());
        System.out.println("Размер матрицы сет "+allIntervals.size());
        System.out.println();
        int count = 0;

        while (allIntervals.size() > 0) {
            functions = new MatrixSet(StackSegments::compareTo);
            for (Segment segment : allIntervals.getCollection()) {
                functions.add(addMaxWayIfCanBeIn(functions, segment));
                count++;
            }
            //ДОБАВЛЕНИЕ
            buff = functions.getTreeSet().pollLast();
            ways.add(buff);
            //УДАЛЕНИЕ
            allIntervals.removeAll(buff);
        }
        System.out.println("Размер после матрицы сет "+allIntervals.size());
        System.out.println("счетчик "+count);
        return ways;
    }

    public static StackSegmentsSet addMaxWayIfCanBeIn(MatrixSet functions, Segment fromAll) {
        StackSegmentsSet b = new StackSegmentsSet(-1);
        b.add(fromAll);
        b.setFullLength();
        Iterator iterator = functions.getTreeSet().descendingIterator();
        while (iterator.hasNext()){
            StackSegments s = (StackSegments) iterator.next();
            if(s.getLastSegment().getSecondDot() <= fromAll.getFirstDot()){
                b.addAll(s);
                b.setFullLength();
                //System.out.println(b.getTreeSet());
                return b;
            }
        }
        return b;
    }

    public static StackSegmentsSet putAllIntervalsInList(Matrix matrix) {
        StackSegmentsSet sss = new StackSegmentsSet(-1);
        for (StackSegments stackSegments : matrix.getCollection()) {
            sss.addAll(stackSegments);
        }
        return sss;
    }

}
