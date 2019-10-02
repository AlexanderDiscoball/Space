package math;

import math.entity.MatrixList;
import math.entity.Segment;
import math.entity.StackSegmentsList;
import math.entity.ZeroSegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Algorithms {

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

    public static StackSegmentsList dynamicAlgorithm(MatrixList matrixList){
        StackSegmentsList way = new StackSegmentsList(-1);
        StackSegmentsList functions = new StackSegmentsList(-2);
        ArrayList<StackSegmentsList> ways = new ArrayList<>();
        Segment buff;

        StackSegmentsList allIntervals = putAllIntervalsInList(matrixList);
        allIntervals.sort();

        System.out.println("Все интервалы " + allIntervals);
        System.out.println("ДО удаления " + allIntervals.size());

        while (allIntervals.size() > 0) {
            functions = new StackSegmentsList(-2);
            functions.add(allIntervals.get(0));

            for (int i = 1; i < allIntervals.size(); i++) {
                buff = maxWayIfCanBeIn(functions, allIntervals.get(i), i);
                if(buff != ZeroSegment.getInstance()) {
                    functions.add(buff);
                }
            }
            System.out.println("Все интервалы " + allIntervals);
            System.out.println("f(x) "+functions);

            //ArrayList<Integer> deleteList = Collections.max(functions.getSegmentsList(),Comparator.comparing(Segment::getLength)).getIndexes();
            //System.out.println("Делит лист"+deleteList);
            //deleteList.sort(Integer::compareTo);
            //Collections.reverse(deleteList);
            //System.out.println("Сколько удалить " + deleteList.size());
            bufStack.sort();
            bufStack.reverse();
            for (Segment segment : bufStack) {
                way.add(allIntervals.get(segment.getIndex()));
                allIntervals.remove(segment.getIndex());

            }

            ways.add(way);
            way.removeAll();
            System.out.println("bufStack " + way);
            bufStack.removeAll();
            //way.sort();
            //ways.add(way);
           // System.out.println("way " + way);
            //way.removeAll();
            if(allIntervals.size() == 1){
                ways.add(allIntervals);
                break;
            }

        }
        System.out.println("после удаления " + allIntervals.size());
        ways.forEach(System.out::println);
        return functions;
    }

    public static Segment maxWayIfCanBeIn(StackSegmentsList pack, Segment fromAll, int index) {
        Segment funcSegment;
        for (int i = 0; i < pack.size(); i++) {
            funcSegment = Collections.max(pack.getCollection(),Comparator.comparing(Segment::getLength));
            if(funcSegment.getSecondDot() <= fromAll .getFirstDot()){
                Segment segment = new Segment(funcSegment.getFirstDot(),fromAll.getSecondDot(),
                        (fromAll.getLength() + funcSegment.getLength()),-2);
                segment.setIndex(index);
                bufStack.add(segment);
                return segment;
            }
        }

        return ZeroSegment.getInstance();
    }

    public static Segment maxWayIfCanBeIn2(StackSegmentsList pack) {
        //segment.setIndexes(funcSegment.getIndexes(),index);
       return pack.getMaxSegment();
    }

    public static StackSegmentsList putAllIntervalsInList(MatrixList matrixList) {
        StackSegmentsList stackSegmentsList = new StackSegmentsList(-2);

        for (int i = 0; i < matrixList.size(); i++) {
                stackSegmentsList.addAll(matrixList.get(i));
        }

        return stackSegmentsList;
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


    private static boolean isCanAdd(Segment segment, StackSegmentsList bestSolution) {
        return bestSolution.getLastSegment().getSecondDot() <= segment.getFirstDot();
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
