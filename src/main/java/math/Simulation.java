package math;

import math.entity.areasegments.Area;
import math.entity.areasegments.AreaList;
import math.entity.array.*;
import math.entity.linesegments.Algorithms;
import math.entity.linesegments.LineList;
import math.entity.linesegments.Track;
import math.entity.interval.Interval;
import math.entity.SegmentPack;
import math.spring.Algo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

import static math.GeneratorRandom.*;

public class Simulation  {
    public  int step;
    public  Track allResults;
    private  Algo algorithms;
    public  int amountSolutions;
    TwoDimensionalArray firstArray;


    public Simulation() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        algorithms = context.getBean(Algo.class);
    }

    public ArrayHash genSimulationForTest() {
        int count = 0;
        firstArray = GeneratorRandom.generateMatrixForTest();
        Separator separator = new Separator();

        ArrayHash arrayHash = convertToArrayHash(firstArray);
        setStep(firstArray);

        HashMap<Integer, Track> separateArray = separator.separationArrays(arrayHash,0,step);
        amountSolutions = nadirTest(separateArray);

        if(InputData.getDropPoints() > 0){
            amountSolutions = InputData.getDropPoints();
        }
        InputData.setDropPoints(amountSolutions);
        System.out.println("Число Интервалов " + InputData.getChannelAmount()*InputData.getSegmentsAmount()*amountSolutions);

        List<TwoDimensionalArray> manySameMatrix = genSamePackArrays(firstArray, amountSolutions);
        ArrayHash mainArray = new ArrayHash();
        List<LineList> allLineList = new ArrayList<>();
        for (int i = 0; i < InputData.getChannelAmount() * InputData.getSegmentsAmount(); i++) {
            allLineList.add( new LineList(count));
        }

            for (TwoDimensionalArray sameMatrix : manySameMatrix) {
                for (SegmentPack segments :sameMatrix) {
                    for (Interval interval :segments) {
                        allLineList.get(count++).add(interval);
                    }
                }
                count=0;
            }

        for (SegmentPack segments :allLineList) {
            AreaList area = new AreaList(segments.getFirstSegment().getAreaId());
            for (Interval interval :segments) {
                area.add(interval);
            }
            mainArray.put(area.getAreaId(),area);
        }

        return mainArray;
    }

    public static ArrayHash convertToArrayHash(TwoDimensionalArray firstArray) {
        ArrayHash arrayHash = new ArrayHash();
        for (SegmentPack segmentPack :firstArray) {
            arrayHash.put(segmentPack.getFirstSegment().getRoll(),new AreaList(segmentPack.getFirstSegment().getRoll()){{addAll(segmentPack);}});
        }
        return arrayHash;
    }

    public int  nadirTest(HashMap<Integer, Track> separateArray) {
        System.out.println("NadirStart");
        Selection nadir = convertToSelection(separateArray);
        for (Track track :nadir) {
            track.getRangeOfIntervals().sort(Comparator.comparing(Interval::getSecondDot));
        }
        ArrayList list = algorithms.nadirAlgorithmAll(nadir,nadir.getTracksCount(),0,0,InputData.getVoluntaristCriteria());
        int resultBunch  = (Integer) list.get(0);
        allResults = (Track)list.get(1);
        System.out.println(resultBunch + " Количество проходов надирным алгоритмом");
        return resultBunch;
    }

    private Selection convertToSelection(HashMap<Integer, Track> map2) {
        Selection selection = new Selection();
        for (Track track :map2.values()) {
            Track track1 = new Track(track.getTrackNumber());
            track1.addAll(track);
            selection.add(track1);
        }
        return selection;
    }

    public static List<Integer> setResiduePoints(TwoDimensionalArray firstArray,List<Integer> dropPoints,int step) {
        List<Integer> residuePoints;
        residuePoints = findResidueObjects(dropPoints,firstArray,step);
        residuePoints.sort(Integer::compareTo);
        return residuePoints;
    }

    public void setStep(TwoDimensionalArray twoDimensionalArray) {
        LineList allIntervals = Algorithms.getAllIntervals(twoDimensionalArray);
        step = findMaxPoint(allIntervals);
    }

    private static List<Integer> findResidueObjects(List<Integer> dropPoints, TwoDimensionalArray twoDimensionalArray, int step) {
        int index = findIndexDropPoints(dropPoints,step);
        List<Integer> viewObject = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            int b =dropPoints.get(i);
            for (SegmentPack segments : twoDimensionalArray) {
                for (Interval interval : segments) {
                    if(interval.getFirstDot() < b && interval.getSecondDot() > b  ){
                        viewObject.add(interval.getAreaId());
                    }
                }
            }
        }
        return viewObject;
    }

    private static int findIndexDropPoints(List<Integer> dropPoints,int step) {
        for (int i = 1; i < dropPoints.size(); i++) {
            if(dropPoints.get(i) > step){
                return i - 1;
            }
        }
        return dropPoints.size() - 1;
    }

    public List<TwoDimensionalArray> genSamePackArrays(TwoDimensionalArray twoDimensionalArray, int amount){
        List<TwoDimensionalArray> allSame = new ArrayList<>();
        allSame.add(twoDimensionalArray);
        for (int i = 0; i < amount - 1; i++) {
            allSame.add(createSameArray((allSame.get(i)),step));
        }

        return allSame;
    }

    public static TwoDimensionalArray createSameArray(TwoDimensionalArray twoDimensionalArray,int step) {
        TwoDimensionalArray nextArray = new TwoDimensionalArrayList();
        for (SegmentPack segments :twoDimensionalArray) {
            LineList buffer = new LineList(segments.getFirstSegment().getRoll());
            for (Interval interval :segments) {
                buffer.add(new Interval((interval.getFirstDot() + step),(interval.getSecondDot() + step), interval.getRoll(), interval.getAreaId()));
            }
            nextArray.add(buffer);
        }
        return nextArray;
    }


    public int findMaxPoint(SegmentPack allIntervals) {
        Interval maxInterval = Collections.max(allIntervals.getCollection(), Comparator.comparing(Interval::getSecondDot));
       return maxInterval.getSecondDot();
    }

    public void addIntervalInArea(int start, int end, SegmentPack area, boolean canAdd) {
        if(canAdd){
            ArrayList<Interval> list =(ArrayList<Interval>) area.getCollection();
            list.add(generateSegmentForArea(start,end,list.get(list.size() - 1).getLength(),((Area)area).getAreaId()));
        }
    }

    public Interval generateSegmentForArea(int start, int end, int length, int id){
        int first = buf;
        int second = buf + length;
        while(second >= end){
            first = start + poisson.nextInt();
            second= first + length;
        }
        buf = second + poisson.nextInt();

        return new Interval(first,second,getRandomChannel(),id);
    }

    public static List<Integer> createRandomDropPoints(int amount, int step){
        List<Integer> dropPoints = new ArrayList<>();
        int maxPoint = step * amount;
        int newStep = (int) (step * InputData.getCoefDropPoints());
        int point = gen.nextInt((int) (newStep/InputData.getCoefDropPoints()));
        while (point < maxPoint){
            dropPoints.add(point);
            point += gen.nextInt((int) (newStep/InputData.getCoefDropPoints()));
        }

        if(dropPoints.size() < amount / InputData.getCoefDropPoints()) {
            while (dropPoints.size() < amount/ InputData.getCoefDropPoints()) {
                int buf = gen.nextInt(maxPoint);
                if (!dropPoints.contains(buf)) {
                    dropPoints.add(buf);
                }
            }
        }

        if(dropPoints.size() > amount / InputData.getCoefDropPoints()) {
            while (dropPoints.size() > amount/ InputData.getCoefDropPoints()) {
                dropPoints.remove(dropPoints.size() - dropPoints.size() / 2);
            }
        }


        if (!(dropPoints.isEmpty())) {
            dropPoints.sort(Comparator.comparing(Integer::intValue));
            return dropPoints;
        } else {
            return Collections.emptyList();
        }
    }

    private static Integer randomSign(float halfStep) {
        int hStep = gen.nextInt((int)halfStep);
        int rand = GeneratorRandom.getRandomStep();
        if(rand < MAXSTEP/2){
            return  (-hStep);
        }
        else return  (hStep);
    }

    public static List<Integer> createDropPoints(int step) {
        List<Integer> dropPoints = new ArrayList<>();
        int loopStart = 0;
        if(isMoreTight()) {
            for (int i = 0; i < InputData.getDropPoints(); i++) {
                loopStart += step / InputData.getCoefDropPoints();
                dropPoints.add(loopStart);
            }
            int diff =(int) (step - (InputData.getCoefDropPoints() * dropPoints.get(0)));
            dropPoints.set(0, diff + dropPoints.get(0));
            for (int i = 1; i < InputData.getDropPoints(); i++) {
                for (int j = 0; j < InputData.getCoefDropPoints(); j++) {
                    dropPoints.add(dropPoints.get(j) + i * step);
                }
            }
        }
        else if(isLessTight()) {
            for (int i = 1; i < InputData.getDropPoints() * InputData.getCoefDropPoints() + 1; i++) {
                dropPoints.add((int)(i * (step / InputData.getCoefDropPoints())));
            }
        }

        if (!(dropPoints.isEmpty())) {
            return dropPoints;
        } else {
            return Collections.emptyList();
        }
    }

    public static List<Integer> createBigOrSmallDropPoints(int amount,int step) {
        List<Integer> dropPoints = new ArrayList<>();
        int loopStart = step;
            for (int i = 1; i <= amount; i++) {
                dropPoints.add(loopStart);
                if(isBig()) {
                    loopStart += step / InputData.getCoefDropPoints();
                }
                else {
                    loopStart += step * InputData.getCoefDropPoints();
                }
            }

        if (!(dropPoints.isEmpty())) {
            return dropPoints;
        } else {
            return Collections.emptyList();
        }
    }

    public static List<Integer> createDropPoints(int amount,int step) {
        List<Integer> dropPoints = new ArrayList<>();
        int loopStart = 0;
        if(InputData.getCoefDropPoints() >= 2) {
            for (int i = 0; i < InputData.getCoefDropPoints(); i++) {
                loopStart += step / InputData.getCoefDropPoints();
                dropPoints.add(loopStart);
            }
            int diff =(int) (step - (InputData.getCoefDropPoints() * dropPoints.get(0)));
            diff = (int) (diff /InputData.getCoefDropPoints());
            dropPoints.set(0, diff + dropPoints.get(0));
            for (int i = 1; i < InputData.getDropPoints(); i++) {
                for (int j = 0; j < InputData.getCoefDropPoints(); j++) {
                    dropPoints.add(dropPoints.get(j) + i * step);
                }
            }
        }
        else {
            for (int i = 1; i < amount * InputData.getCoefDropPoints() + 1; i++) {
                dropPoints.add((int)(i * (step / InputData.getCoefDropPoints())));
            }
        }
        if (!(dropPoints.isEmpty())) {
            return dropPoints;
        } else {
            return Collections.emptyList();
        }
    }

    public static boolean randomAddOrNot() {
        int yes = (int) (Math.random() * 100);
        return yes <= 85;
    }

    public static int generateAreaId() {
        return gen.nextInt(InputData.getSegmentsAmount() * InputData.getChannelAmount());
    }

    private static boolean isBig() {
        return gen.nextInt(2) == 1;
    }

    private static boolean isMoreTight(){
        return InputData.getCoefDropPoints() >= 2;
    }

    private static boolean isLessTight(){
        return InputData.getCoefDropPoints() < 2;
    }
}
