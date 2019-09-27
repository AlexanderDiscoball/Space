package math;

import math.entity.*;

import java.util.*;

public class Main {
    private static StackSegments bestWay = new StackSegments(-1);
    private static StackSegments way = new StackSegments(-1);
    private static ArrayList<StackSegments> allWays = new ArrayList<>();

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();


        Matrix matrix = GeneratorRandom.generateMatrix();
        long random1 = System.currentTimeMillis();


        //getLengthMatrix(matrix);
        System.out.println();
        //getFirstSecondDotMatrix(matrix);



        long random = System.currentTimeMillis();
        System.out.println("Время на создание матрицы" + " " + (random - startTime) + " миллисекунд");

        StackSegments ways = new StackSegments(-1);
        ways = Algorithms.dynamicAlgorithm(matrix);

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
                bestWay = new StackSegments(-1);
            }

        }
    }

    private static void betterAlgorithm(Segment[][] matrix){
        int lineNumber = 0;
        List<Segment> zeroPriorityList = new ArrayList<>(Arrays.asList(matrix[getBestChannel(matrix)]));
        zeroPriorityList.remove(ZeroSegment.getInstance());

        way.addWithCheck(zeroPriorityList.get(0));

        for (int segmentIndex = 0; segmentIndex < zeroPriorityList.size() - 1; ) {
            if (way.get(way.getSize() - 1).getSecondDot() !=
                    zeroPriorityList.get(segmentIndex + 1).getFirstDot()){
               SEARCHING_SEGMENTS: for (lineNumber = 1; lineNumber < matrix.length; lineNumber++) {
                    for (int segmentNumber = 0; segmentNumber < matrix[lineNumber].length; segmentNumber++) {

                        if(zeroPriorityList.get(segmentIndex).getSecondDot() < matrix[lineNumber][segmentNumber].getFirstDot() &&
                        zeroPriorityList.get(segmentIndex + 1).getFirstDot() > matrix[lineNumber][segmentNumber].getSecondDot()){
                            way.addWithCheck(matrix[lineNumber][segmentNumber]);
                            break SEARCHING_SEGMENTS;
                        }
                    }
                }
                way.addWithCheck(zeroPriorityList.get(segmentIndex + 1));
                segmentIndex++;
            }
            else {
                way.addWithCheck(zeroPriorityList.get(segmentIndex + 1));
                segmentIndex++;
            }
        }

    }

    private static int getBestChannel(Segment[][] matrix){
        int bestPriority = InputData.getChannelAmount();
        for (int i = 0; i < matrix.length; i++) {
           if( bestPriority > matrix[i][0].getPriority()){
               bestPriority = matrix[i][0].getPriority();
           }
        }
        return bestPriority;
    }

    private static int getIndex(){
        if(way.getSize() == 1){
            return 0;
        }
        else return way.getSize() - 1;
    }

    private static void sortSegmentByLength(ArrayList<Segment> list) {
        Comparator<Segment> comparator = Comparator.comparing(Segment::getLength);
        list.sort(comparator);
        Collections.reverse(list);
    }
    private static void sortSegmentByPriority(ArrayList<Segment> list) {
        Comparator<Segment> comparator = Comparator.comparing(Segment::getPriority);
        list.sort(comparator);
    }

    private static void getLengthMatrix(Segment[][] matrix) {
        for (int i = 0; i < InputData.getChannelAmount(); i++) {
            for (int j = 0; j < InputData.getSegmentsAmount(); j++) {
                System.out.printf("%3d", matrix[i][j].getLength());
            }
            System.out.println();
        }
    }

    private static void getFirstSecondDotMatrix(Segment[][] matrix) {
        for (int i = 0; i < InputData.getChannelAmount(); i++) {
            for (int j = 0; j < InputData.getSegmentsAmount(); j++) {
                String b = (matrix[i][j]).getFirstDot() + "," + (matrix[i][j]).getSecondDot() + "  ";
                System.out.print(b);
            }
            System.out.println();
        }
    }

}
