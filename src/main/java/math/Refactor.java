package math;

import math.entity.SimulationSegments.*;

import java.util.Collections;
import java.util.Comparator;

public class Refactor {
    static long timeToDelete2 = 0;

    public static MatrixList dynamicAlgorithm(Matrix matrixList){
        MatrixList ways = new MatrixList();
        MatrixList functions;
        long timeToDelete = 0;

        StackSegments buff;

        StackSegmentsList allIntervals = putAllIntervalsInList(matrixList);
        allIntervals.sort();
        System.out.println("Все интервалы в матрице лист"+allIntervals);
        System.out.println("Размер матрицы лист"+allIntervals.size());

        while (allIntervals.size() > 0) {
            functions = new MatrixList();
            functions.add(new StackSegmentsList(-1){{add(allIntervals.get(0));setFullLength();}});
            for (int i = 1; i < allIntervals.size(); i++) {
                functions.add(addMaxWayIfCanBeIn(functions, allIntervals.get(i)));
            }
            //ДОБАВЛЕНИЕ
            buff = functions.getBestSolution();
            ways.add(buff);
            //УДАЛЕНИЕ
            allIntervals.removeAll(buff);
        }

        return ways;
    }

    public static StackSegmentsList addMaxWayIfCanBeIn(MatrixList functions, Segment fromAll) {
        functions.sort();
        if(functions.get(functions.size() - 1) != Collections.max(functions.getCollection(),Comparator.comparing(StackSegments::getFullLength))){
            System.out.println("Ошибка");
        }
        StackSegmentsList b = new StackSegmentsList(-1);;
        for (int i = functions.size() - 1; i >= 0; i--) {
            if(functions.get(i).getLastSegment().getSecondDot() <= fromAll.getFirstDot()){
                b.addAll(functions.get(i));
                b.add(fromAll);
                b.setFullLength();
                return b;
            }
        }
        b.add(fromAll);
        b.setFullLength();
        return b;
    }

    public static StackSegmentsList putAllIntervalsInList(Matrix matrixList) {
        StackSegmentsList stackSegmentsList = new StackSegmentsList(-2);

        for (int i = 0; i < matrixList.size(); i++) {
            stackSegmentsList.addAll(matrixList.get(i));
        }

        return stackSegmentsList;
    }

}
