package math;


import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import math.entity.AreaSegments.AreaList;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArrayList;
import math.entity.LineSegments.Line;
import math.entity.LineSegments.LineList;
import math.entity.SegmentPack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static math.Simulation.*;

public class GeneratorRandom {

    public static final int MAXSTEP = 10;
    public static final int DROPPOINTS = InputData.getDropPoints();
    public static final int STEPDROPPOINTS = InputData.getTimeAmount()/DROPPOINTS;


    public static Random gen = new Random(InputData.getSeed());
    public static int areaCunt = 0;
    public static RandomEngine engine = new DRand();
    public static Poisson poisson = new Poisson(InputData.getLAMBDA(), engine);
    public static int buf = (2 * poisson.nextInt());
    public static int channel = 0;
    public static boolean reverse;

    private static int areaIdBuf = 0;


    public static TwoDimensionalArrayList generateMatrix(){
        TwoDimensionalArrayList matrixList = new TwoDimensionalArrayList();

        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            Line stackSegments = generateStackSegments(line);
            matrixList.add(stackSegments);
        }
        return matrixList;
    }
    public static TwoDimensionalArrayList generateMatrixForTest(){
        TwoDimensionalArrayList matrixList = new TwoDimensionalArrayList();

        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            Line stackSegments = generateStackSegments(getReverseChannel(), line);
            matrixList.add(stackSegments);
        }
        return matrixList;
    }

    public static int getReverseChannel(){
        if(channel > InputData.getChannelAmount()){
            reverse = true;
        }
        if(channel < 0){
            reverse = false;
        }
        if(reverse){
            return channel--;
        }
        else {
            return channel++;
        }
    }

    public static Line generateStackSegments(int line){
        LineList stackSegmentsList = new LineList(line);

        for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
            stackSegmentsList.add(generateSegment(line));
        }
        buf = InputData.getCoefSpace() * getRandomStep();
        return stackSegmentsList;
    }

    public static Line generateStackSegments(int line, int areaId){
        LineList stackSegmentsList = new LineList(line);

        for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
            stackSegmentsList.add(generateSegment(line,areaId));
        }
        buf = InputData.getCoefSpace() * getRandomStep();
        return stackSegmentsList;
    }

    public static math.entity.interval.Interval generateSegment(int line){
        int first = buf;
        int second = buf + 1 + InputData.getCoefLength() * getRandomStep();
        buf = second + (InputData.getCoefSpace() * getRandomStep());

        return new math.entity.interval.Interval(first,second,line,generateAreaId());
    }

    public static math.entity.interval.Interval generateSegment(int line, int areaId){
        int first = buf;
        int second = buf + 1 + InputData.getCoefLength() * getRandomStep();
        buf = second + (InputData.getCoefSpace() * getRandomStep());
        return new math.entity.interval.Interval(first,second,line,areaIdBuf++);
    }

    public static int getModuleLine(int line) {
        if(line + 1 < InputData.getChannelAmount()){
            return line + 1;
        }
        else return 0;
    }

    public static math.entity.interval.Interval generateSegment(int step, int areaId, int firstDot, int secondDot){
        int first = buf;
        int second = buf + 1 + InputData.getCoefLength() * getRandomStep();
        buf = second + (InputData.getCoefSpace() * getRandomStep());

        return new math.entity.interval.Interval(first,second,getRandomChannel(),areaId);
    }


    public static math.entity.interval.Interval generateSegment(int line, int start, int end){
        int first = buf;
        int second = buf + 1 + InputData.getCoefLength() * poisson.nextInt();
        while(second >= end){
            first = start + InputData.getCoefLength() * poisson.nextInt();
            second = first + 1 + InputData.getCoefLength() * poisson.nextInt();
        }
        buf = second + (InputData.getCoefSpace() * poisson.nextInt());

        return new math.entity.interval.Interval(first,second,line,areaCunt);
    }


    public static int getRandomStep(){
        return 1 +  gen.nextInt(MAXSTEP);
    }

    public static int getRandomChannel(){
        return ((int) (Math.random() * InputData.getChannelAmount()));
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
            buf = InputData.getCoefSpace() * poisson.nextInt();
            tracks.add(temporary);
        }
        return tracks;
    }

    public static Interval rand_gen() {
        int first = buf;
        int second = buf + 1 + InputData.getCoefLength() * poisson.nextInt();
        buf = second +  InputData.getCoefSpace() * poisson.nextInt();
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
        TwoDimensionalArray generateMat = generateAreaMatrix(start,end);
        for (SegmentPack segmentPack :generateMat) {
            for (int i = 0; i < segmentPack.size(); i++) {
                final int integer = i;
                twoDimensionalArray.add(new AreaList(segmentPack.get(i).getAreaId()){{add(segmentPack.get(integer));}});
            }
        }
        return twoDimensionalArray;
    }

    public static TwoDimensionalArrayList generateAreaMatrix(int start,int end){
        TwoDimensionalArrayList matrixList = new TwoDimensionalArrayList();
        areaCunt = 0;
        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            LineList stackSegments = generateAreaSegments(line,start,end);
            matrixList.add(stackSegments);
        }
        return matrixList;
    }

    public static LineList generateAreaSegments(int line,int start,int end){
        LineList stackSegmentsList = new LineList(line);
        for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
            stackSegmentsList.add(generateSegment(line,start,end));
            areaCunt++;
        }
        buf = start + InputData.getCoefSpace() * getRandomStep();
        return stackSegmentsList;
    }









    //DELIMITER
    public static math.entity.interval.Interval generateRandomSegment(int min, int max, int lineNumber){
        int firstDot = ThreadLocalRandom.current().nextInt(min, max + 1);
        if(min==0) min = 1;
        int secondDot  = ThreadLocalRandom.current().nextInt(min, max + 1);
        if(firstDot>secondDot){
            firstDot =  firstDot + secondDot;
            secondDot = firstDot - secondDot;
            firstDot = firstDot - secondDot;
        }
        return new math.entity.interval.Interval(firstDot, secondDot, lineNumber, generateAreaId());
    }

    public static int getRandomNumber(int from, int to){
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

    public static void addZeroSegments(LineList stackSegmentsList){
        while (stackSegmentsList.size() != InputData.getSegmentsAmount()){
            stackSegmentsList.addZeroSegments();
        }
    }

    public static math.entity.interval.Interval[][] generateSegmentMatrix(){
        math.entity.interval.Interval[][] matrix = new math.entity.interval.Interval[InputData.getChannelAmount()][InputData.getSegmentsAmount()];
        ArrayList<LineList> matrixList = generateManyStacks();
        for (int i = 0; i < InputData.getChannelAmount(); i++) {
            for (int j = 0; j < InputData.getSegmentsAmount(); j++) {
                matrix[i][j] = matrixList.get(i).get(j);
            }
        }
        return matrix;
    }

    public static int getMinInterval(int segmentCounter){
        double amountOfTime = InputData.getTimeAmount();
        double segmentsAmount = InputData.getSegmentsAmount();
        if (segmentCounter == 1) {
            return  0;
        } else {
            return  (int) ((amountOfTime / segmentsAmount) * (segmentCounter - 1));
        }
    }
}

