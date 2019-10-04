package math;

import math.entity.SimulationSegments.*;

import java.util.*;

public class Algorithms {
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
        b.setFullLength();
        return b;
    }

    public static StackSegmentsSet putAllIntervalsInList(Matrix matrix) {
        StackSegmentsSet sss = new StackSegmentsSet(-1);
        for (StackSegments stackSegments : matrix.getCollection()) {
            sss.addAll(stackSegments);
        }
        return sss;
    }

    private static StackSegmentsList bestWay = new StackSegmentsList(-1);
    private static StackSegmentsList way = new StackSegmentsList(-1);
    private static ArrayList<StackSegmentsList> allWays = new ArrayList<>();
    static StackSegmentsList bufStack = new StackSegmentsList(-2);
    static StackSegmentsList allIntervals;
    static MatrixList buffer;

    private static void greedyAlgorithm(MatrixList matrixList) {
        int sum = 0;
        ArrayList<Segment> list = new ArrayList<>();
        boolean containSegments = true;
        while (containSegments) {
            for (int i = 0; i < matrixList.size(); i++) {
                for (int j = 0; j < matrixList.get(i).size(); j++) {

                    list.add(matrixList.get(j).get(i));


                    if ((j + 1) == matrixList.get(i).size()) {
                        sortSegmentByLength(list);
                        int pointer = list.get(0).getSecondDot() ;
                        sortSegmentByPriority(list);

                        for (Segment segment : list) {

                            int lessThanTimeAmount = sum + segment.getLength();
                            if (lessThanTimeAmount <= InputData.getTimeAmount() && bestWay.isSegmentCanBeInList(segment)) {
                                bestWay.add(segment);
                                sum += segment.getLength();
                                if(segment != ZeroSegment.getInstance()) {
                                    matrixList.set(ZeroSegment.getInstance(), segment.getLine(),i);
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


    private static void betterAlgorithm(Segment[][] matrix){
        int lineNumber = 0;
        List<Segment> zeroPriorityList = new ArrayList<>(Arrays.asList(matrix[getBestChannel(matrix)]));
        zeroPriorityList.remove(ZeroSegment.getInstance());

        way.addWithCheck(zeroPriorityList.get(0));

        for (int segmentIndex = 0; segmentIndex < zeroPriorityList.size() - 1; ) {
            if (way.get(way.size() - 1).getSecondDot() !=
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

    public static void showCoordinateMatrix(MatrixList matrixList) {
        for (int i = 0; i < matrixList.size(); i++) {
            for (int j = 0; j < matrixList.get(i).size(); j++) {
                String b = (matrixList.get(i).get(j)).getFirstDot() + "," + (matrixList.get(i).get(j)).getSecondDot() + "  ";
                System.out.print(b);
            }
            System.out.println();
        }
    }

    private static void sortSegmentByPriority(ArrayList<Segment> list) {
        Comparator<Segment> comparator = Comparator.comparing(Segment::getPriority);
        list.sort(comparator);
    }

    private static void sortSegmentByLength(ArrayList<Segment> list) {
        Comparator<Segment> comparator = Comparator.comparing(Segment::getLength);
        list.sort(comparator);
        Collections.reverse(list);
    }
}
