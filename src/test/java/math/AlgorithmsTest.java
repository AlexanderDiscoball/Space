package math;

import math.entity.MatrixList;
import math.entity.StackSegmentsList;
import org.junit.Test;

import static math.Algorithms.putAllIntervalsInList;
import static org.junit.Assert.assertEquals;

public class AlgorithmsTest {

    @Test
    public void dynamicAlgorithm() {
        long startTime = System.currentTimeMillis();
        MatrixList matrixList = GeneratorRandom.generateMatrix();
        StackSegmentsList allIntervals = putAllIntervalsInList(matrixList);
        allIntervals.sort();

        Algorithms.showCoordinateMatrix(matrixList);
        StackSegmentsList ways = new StackSegmentsList(-1);
        StackSegmentsList pack = Algorithms.dynamicAlgorithm(matrixList);
        pack.sortByLength();
        pack.reverse();

        assertEquals(pack.size(),allIntervals.size());

        long random = System.currentTimeMillis();
        System.out.println("Время на алгоритм " + (random - startTime) + " миллисекунд");
    }




}