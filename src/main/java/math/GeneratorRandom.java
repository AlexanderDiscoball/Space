package math;

import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import math.entity.AreaSegments.Area;
import math.entity.AreaSegments.AreaList;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArrayList;
import math.entity.Segment.Segment;
import math.entity.LineSegments.Line;
import math.entity.LineSegments.LineList;
import math.entity.SegmentPack;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GeneratorRandom {

    private static final int MAXSTEP = 10;
    private static final int DROPPOINTS = InputData.getDropPoints();
    private static final int STEPDROPPOINTS = InputData.getTimeAmount()/DROPPOINTS;
    private static int seed = 15;
    private static Random gen = new Random(seed);


    static RandomEngine engine = new DRand();
    static Poisson poisson = new Poisson(InputData.getLAMBDA(), engine);
    private static int buf = (2 * poisson.nextInt());
    private static final int COAFSPACE = 6;
    private static final int COAFLENGTh = 1;



    public static TwoDimensionalArrayList generateMatrix(){
        TwoDimensionalArrayList matrixList = new TwoDimensionalArrayList();

        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            Line stackSegments = generateStackSegments(line);
            matrixList.add(stackSegments);
        }
        return matrixList;
    }

    public static TwoDimensionalArrayList generateMatrix(int start,int end){
        TwoDimensionalArrayList matrixList = new TwoDimensionalArrayList();

        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            Line stackSegments = generateStackSegments(line,start,end);
            matrixList.add(stackSegments);
        }
        return matrixList;
    }

    private static Line generateStackSegments(int line){
        LineList stackSegmentsList = new LineList(line);

        for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
          stackSegmentsList.add(generateSegment(line));
        }
        buf = COAFSPACE * poisson.nextInt();
        return stackSegmentsList;
    }

    private static Line generateStackSegments(int line,int start,int end){
        LineList stackSegmentsList = new LineList(line);
        for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
          stackSegmentsList.add(generateSegment(line,start,end));
        }
        buf = start + COAFSPACE * poisson.nextInt();
        return stackSegmentsList;
    }

    private static Segment generateSegment(int line){
        int first = buf;
        int second = buf + 1 +COAFLENGTh* poisson.nextInt();
        buf = second + (COAFSPACE * poisson.nextInt());

        return new Segment(first,second,line);
    }

    private static Segment generateSegment(int line,int start,int end){
        int first = buf;
        int second = buf + 1 +COAFLENGTh* poisson.nextInt();
        while(second >= end){
            first = start +COAFLENGTh* poisson.nextInt();
            second = first + 1 + COAFLENGTh* poisson.nextInt();
        }
        buf = second + (COAFSPACE * poisson.nextInt());

        return new Segment(first,second,line);
    }


    public static int getRandomStep(){
        return gen.nextInt(MAXSTEP);
    }

    private static int getRandomChannel(){
        return ((int) (Math.random() * InputData.getChannelAmount()));
    }
    private static int getRandomLength(){
        return (1 + (int) (Math.random() * STEPDROPPOINTS));
    }

    private static int getRandomAmount(){
        gen.setSeed(seed);
        seed+=4;
        return gen.nextInt(MAXSTEP);
    }

    public static List<LinkedList<SergAlg>> genareteSergEntity(){
        List<LinkedList<SergAlg>> tracks=new ArrayList<>();
        int intervals_num = InputData.getChannelAmount();
        Interval bufInterval;
        SergAlg temp_obj;
        for(int i=0;i<InputData.getChannelAmount();i++) {
            LinkedList<SergAlg> temporary=new LinkedList<>();
            for(int j=0;j<intervals_num;j++) {
                bufInterval = rand_gen();
                temp_obj=new SergAlg(bufInterval.f,bufInterval.s,i);
                temporary.add(temp_obj);
            }
            buf = COAFSPACE * poisson.nextInt();
            tracks.add(temporary);
        }
        return tracks;
    }

    public static Interval rand_gen() {
        int first = buf;
        int second = buf + 1 + COAFLENGTh * poisson.nextInt();
        buf = second +  COAFSPACE * poisson.nextInt();
        return new Interval(first,second);
    }

    static class Interval{
        public int f;
        public int s;
        public Interval(int f,int s){
            this.s=s;
            this.f=f;
        }
    }



    //DELIMITER Генерация интервала с обьектами и точками сброса
    public static TwoDimensionalArray generateBigInterval(){
        List<Integer> dropPoints = createRandomDropPoints();
        int start,end;
        start = dropPoints.get(0);
        TwoDimensionalArray areaMatrix = generateFirstDropInterval(0,start);
        buf=start;
        for (int index = 1; index < dropPoints.size(); index++) {
            end = dropPoints.get(index);
            for (SegmentPack area :areaMatrix) {
                addIntervalInArea(start,end,area,randomAddOrNot());
            }
            start = end;
        }
        return areaMatrix;
    }

    public static TwoDimensionalArray generateFirstDropInterval(int start,int end){
        TwoDimensionalArray twoDimensionalArray = new TwoDimensionalArrayList();
        TwoDimensionalArray generateMat = generateMatrix(start, end);
        for (SegmentPack segmentPack :generateMat) {
            for (Segment segment : segmentPack) {
                twoDimensionalArray.add(new AreaList(){{add(segment);}});
            }
        }
      return twoDimensionalArray;
    }

    private static void addIntervalInArea(int start, int end, SegmentPack area, boolean canAdd) {
        if(canAdd){
           LinkedList<Segment> list =(LinkedList<Segment>) area.getCollection();
           list.add(generateSegmentForArea(start,end,list.getLast().getLength()));
        }
    }

    private static Area generateArea() {
        Area area = new AreaList();
        area.add(generateSegment(getRandomChannel()));
        buf = getRandomStep();
        return area;
    }

    private static Segment generateSegmentForArea(int start,int end,int length){
        int first = buf;
        int second = buf + length;
        while(second >= end){
            first = start + poisson.nextInt();
            second= first + length;
        }
        buf = second + poisson.nextInt();

        return new Segment(first,second,getRandomChannel());
    }

    public static List<Integer> createRandomDropPoints(){
        int point = 0;
        List<Integer> dropPoints = new ArrayList<>();
        for (int i = 0; i < DROPPOINTS; i++) {
            point += STEPDROPPOINTS;
            dropPoints.add(point);
        }
        return dropPoints;
    }

    private static boolean randomAddOrNot() {
        int yes = (int) (Math.random() * 100);
        return yes <= 85;
    }












    //DELIMITER
    public static Segment generateRandomSegment(int min,int max, int lineNumber){
        int firstDot = ThreadLocalRandom.current().nextInt(min, max + 1);
        if(min==0) min = 1;
        int secondDot  = ThreadLocalRandom.current().nextInt(min, max + 1);
        if(firstDot>secondDot){
            firstDot =  firstDot + secondDot;
            secondDot = firstDot - secondDot;
            firstDot = firstDot - secondDot;
        }

        return new Segment(firstDot, secondDot, lineNumber);
    }

    private static int getRandomNumber(int from, int to){
        return (from + (int) (Math.random() * to));
    }

    public static ArrayList<LineList> generateManyStacks() {
        ArrayList<LineList> matrixList = new ArrayList<>();
        int intervalMax;
        int intervalMin;

        double amountOfTime = InputData.getTimeAmount();
        double segmentsAmount = InputData.getSegmentsAmount();
        int segmentCounter = 1;
        for (int channel = 0; channel < InputData.getChannelAmount(); channel++) {
            LineList stackSegmentsList = new LineList(channel);
            HashSet<Integer> howManyZeroElements = getRandomCountZeroElements();

            while (stackSegmentsList.size() != InputData.getSegmentsAmount()){
                if (howManyZeroElements.contains(segmentCounter)) {
                   stackSegmentsList.addZeroSegments();
                }
                else {
                    intervalMax = (int) ((amountOfTime / segmentsAmount) * segmentCounter);
                    intervalMin = getMinInterval(segmentCounter);
                    stackSegmentsList.add(generateRandomSegment(intervalMin, intervalMax, channel));
                }
                segmentCounter++;
            }

            segmentCounter=1;
            matrixList.add(stackSegmentsList);
        }
        return matrixList;
    }

    public static HashSet<Integer> getRandomCountZeroElements(){
        int howManyElements = ThreadLocalRandom.current().nextInt(1, InputData.getSegmentsAmount()+1);

        HashSet<Integer> repoRandomNumber = new HashSet<>();
        for (int i = 1; i < howManyElements; i++) {
            repoRandomNumber.add(ThreadLocalRandom.current().nextInt(1, InputData.getSegmentsAmount()+1));
        }
        return repoRandomNumber;
    }

    private static void addZeroSegments(LineList stackSegmentsList){
        while (stackSegmentsList.size() != InputData.getSegmentsAmount()){
            stackSegmentsList.addZeroSegments();
        }
    }

    public static Segment[][] generateSegmentMatrix(){
        Segment[][] matrix = new Segment[InputData.getChannelAmount()][InputData.getSegmentsAmount()];
        ArrayList<LineList> matrixList = generateManyStacks();
        for (int i = 0; i < InputData.getChannelAmount(); i++) {
            for (int j = 0; j < InputData.getSegmentsAmount(); j++) {
                    matrix[i][j] = matrixList.get(i).get(j);
            }
        }
        return matrix;
    }

    private static int getMinInterval(int segmentCounter){
        double amountOfTime = InputData.getTimeAmount();
        double segmentsAmount = InputData.getSegmentsAmount();
        if (segmentCounter == 1) {
            return  0;
        } else {
            return  (int) ((amountOfTime / segmentsAmount) * (segmentCounter - 1));
        }
    }
}
