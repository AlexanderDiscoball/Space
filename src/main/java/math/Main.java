package math;

import math.entity.Array.TwoDimensionalArrayList;
import math.entity.Segment.Segment;
import math.entity.LineSegments.LineList;
import math.entity.Segment.ZeroSegment;


import java.time.LocalDate;
import java.util.*;

public class Main {
    private static LineList bestWay = new LineList(-1);
    private static LineList way = new LineList(-1);
    private static ArrayList<LineList> allWays = new ArrayList<>();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();


        TwoDimensionalArrayList matrixList = GeneratorRandom.generateMatrix();
        long random1 = System.currentTimeMillis();


        //getLengthMatrix(matrix);
        System.out.println();
        //getFirstSecondDotMatrix(matrix);



        long random = System.currentTimeMillis();
        System.out.println("Время на создание матрицы" + " " + (random - startTime) + " миллисекунд");

        LineList ways = new LineList(-1);


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
                bestWay = new LineList(-1);
            }

        }
    }



    private static void sortSegmentByLength(ArrayList<Segment> list) {
        Comparator<Segment> comparator = Comparator.comparing(Segment::getLength);
        list.sort(comparator);
        Collections.reverse(list);
    }

}
