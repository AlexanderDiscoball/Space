package math;

import math.entity.SimulationSegments.MatrixList;
import math.entity.SimulationSegments.Segment;
import math.entity.SimulationSegments.StackSegmentsList;
import math.entity.SimulationSegments.ZeroSegment;


import java.util.*;

public class Main {
    private static StackSegmentsList bestWay = new StackSegmentsList(-1);
    private static StackSegmentsList way = new StackSegmentsList(-1);
    private static ArrayList<StackSegmentsList> allWays = new ArrayList<>();

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();


        MatrixList matrixList = GeneratorRandom.generateMatrix();
        long random1 = System.currentTimeMillis();


        //getLengthMatrix(matrix);
        System.out.println();
        //getFirstSecondDotMatrix(matrix);



        long random = System.currentTimeMillis();
        System.out.println("Время на создание матрицы" + " " + (random - startTime) + " миллисекунд");

        StackSegmentsList ways = new StackSegmentsList(-1);


        System.out.println(way);
        System.out.println("Колличество путей: " + allWays.size());
        System.out.println("Лучший путь: " + allWays.get(0));

        long stopTime = System.currentTimeMillis();
        System.out.println("Время выполнения программы: " + ((stopTime - random)) + " миллисекунд");
    }

    private static void greedyAlgorithm(Segment[][] matrix) {
        int sum = 0;
        ArrayList<Segment> list = new ArrayList<>();
        boolean containSegments = true;
        while (containSegments) {
            for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
                for (int j = 0; j < InputData.getChannelAmount(); j++) {

                    list.add(matrix[j][i]);

                    if ((j + 1) == InputData.getChannelAmount()) {

                        sortSegmentByLength(list);

                        for (Segment segment : list) {

                            int lessThanTimeAmount = sum + segment.getLength();
                            if (lessThanTimeAmount <= InputData.getTimeAmount() && bestWay.isSegmentCanBeInList(segment)) {
                                bestWay.add(segment);
                                sum += segment.getLength();
                                if(segment != ZeroSegment.getInstance()) {
                                    matrix[segment.getLine()][i] = ZeroSegment.getInstance();
                                }
                                break;
                            }
                        }
                    }
                }
                list = new ArrayList<>();
            }

            if (sum == 0) {
                containSegments = false;
            }
            else {
                sum=0;
                allWays.add(bestWay);
                bestWay = new StackSegmentsList(-1);
            }

        }
    }



    private static void sortSegmentByLength(ArrayList<Segment> list) {
        Comparator<Segment> comparator = Comparator.comparing(Segment::getLength);
        list.sort(comparator);
        Collections.reverse(list);
    }

}
