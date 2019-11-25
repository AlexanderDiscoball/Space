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

public class Simulation {
    public static int step;
    public static List<Integer> dropPoints;
    public static List<Integer> residuePoints;
    public static Track allResults;
    private static Algo algorithms;
    public static int amountSolutions;

    public Simulation() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        algorithms = context.getBean(Algo.class);
    }

    public static ArrayHash genSimulationForTest() {
        new Simulation();
        int count = 0;
        TwoDimensionalArray firstArray = GeneratorRandom.generateMatrixForTest();
        Separator separator = new Separator();

        ArrayHash arrayHash = convertToArrayHash(firstArray);
        setStep(firstArray);
        SeparateArray separateArray = separator.separationArrays(arrayHash,0,step);

        amountSolutions = nadirTest(separateArray);

        if(InputData.getDropPoints() > 0){
            amountSolutions = InputData.getDropPoints();
        }
        InputData.setDropPoints(amountSolutions);
        setDropPoints(amountSolutions);
        setResiduePoints(firstArray);
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
           Area area = new AreaList(segments.getFirstSegment().getAreaId());
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
            arrayHash.put(segmentPack.getFirstSegment().getLine(),new AreaList(segmentPack.getFirstSegment().getLine()){{addAll(segmentPack);}});
        }
        return arrayHash;
    }

    public static int  nadirTest(SeparateArray separateArray) {
        System.out.println("NadirStart");
        Selection nadir = convertToSelection(separateArray.getSeparatePack());
        for (Track track :nadir) {
            track.getRangeOfIntervals().sort(Comparator.comparing(Interval::getSecondDot));
        }
        ArrayList list = algorithms.nadirAlgorithmAll(nadir,nadir.getTracksCount(),0,0,InputData.getVoluntaristCriteria());
        int resultBunch  = (Integer) list.get(0);
        allResults = (Track)list.get(1);
        System.out.println(resultBunch + " Количество проходов надирным алгоритмом");
        return resultBunch;
    }

    private static Selection convertToSelection(HashMap<Integer, Track> map2) {
        Selection selection = new Selection();
        for (Track track :map2.values()) {
            Track track1 = new Track(track.getTrackNumber());
            track1.addAll(track);
            selection.add(track1);
        }
        return selection;
    }

    private static void setResiduePoints(TwoDimensionalArray firstArray) {
        residuePoints = findResidueObjects(dropPoints,firstArray);
        residuePoints.sort(Integer::compareTo);
    }

    private static void setDropPoints() {
        System.out.println("Равномерные точки "+createDropPoints());
        dropPoints = createDropPoints();
    }

    private static void setDropPoints(int amount) {
        System.out.println("Равномерные точки "+createDropPoints());
        dropPoints = createDropPoints(amount);
    }


    public static void setStep(TwoDimensionalArray twoDimensionalArray) {
        LineList allIntervals = Algorithms.getAllIntervals(twoDimensionalArray);
        step = findMaxPoint(allIntervals);
    }

    private static List<Integer> findResidueObjects(List<Integer> dropPoints, TwoDimensionalArray twoDimensionalArray) {
        int index = findIndexDropPoints(dropPoints);
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

    private static int findIndexDropPoints(List<Integer> dropPoints) {
        for (int i = 1; i < dropPoints.size(); i++) {
            if(dropPoints.get(i) > step){
                return i - 1;
            }
        }
        return dropPoints.size() - 1;
    }

    public static List<TwoDimensionalArray> genSamePackArrays(TwoDimensionalArray twoDimensionalArray, int amount){
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
            LineList buffer = new LineList(segments.getFirstSegment().getLine());
            for (Interval interval :segments) {
                buffer.add(new Interval((interval.getFirstDot() + step),(interval.getSecondDot() + step), interval.getLine(), interval.getAreaId()));
            }
            nextArray.add(buffer);
        }
        return nextArray;
    }


    public static int findMaxPoint(SegmentPack allIntervals) {
        Interval maxInterval = Collections.max(allIntervals.getCollection(), Comparator.comparing(Interval::getSecondDot));
       return maxInterval.getSecondDot();
    }

    public static ArrayHash generateBigHashInterval(){
        List<Integer> dropPoints = createRandomDropPoints(step);
        int start,end;
        start = dropPoints.get(0);
        ArrayHash arrayHash = generateFirstHashDropInterval(0,start);
        GeneratorRandom.buf=start;
        for (int index = 1; index < dropPoints.size(); index++) {
            end = dropPoints.get(index);
            for (SegmentPack area :arrayHash.getHashPack().values()) {
                addIntervalInArea(start,end,area,randomAddOrNot());
            }
            start = end;
        }

        return arrayHash;
    }

    public static ArrayHash generateFirstHashDropInterval(int start,int end){
        ArrayHash arrayHash = new ArrayHash();
        ArrayHash generateMat = generateHashAreaMatrix(start,end);
        for (SegmentPack segmentPack :generateMat.getHashPack().values()) {
            for (int i = 0; i < segmentPack.size(); i++) {
                final int integer = i;
                AreaList areaList = new AreaList(segmentPack.get(i).getAreaId()){{add(segmentPack.get(integer));}};
                arrayHash.getHashPack().put(areaList.getAreaId(),areaList);
            }
        }
        return arrayHash;
    }

    public static ArrayHash generateHashAreaMatrix(int start,int end){
        ArrayHash arrayHash = new ArrayHash();
        areaCunt = 0;
        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            AreaList areaList = generateHashAreaSegments(line,start,end);
            arrayHash.getHashPack().put(areaList.getAreaId(),areaList);
        }
        return arrayHash;
    }

    public static AreaList generateHashAreaSegments(int line,int start,int end){
        AreaList areaList = new AreaList(areaCunt);
        for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
            areaList.add(generateSegment(line,start,end));
            areaCunt++;
        }
        buf = start + InputData.getCoefSpace() * getRandomStep();
        return areaList;
    }

    public static void addIntervalInArea(int start, int end, SegmentPack area, boolean canAdd) {
        if(canAdd){
            ArrayList<Interval> list =(ArrayList<Interval>) area.getCollection();
            list.add(generateSegmentForArea(start,end,list.get(list.size() - 1).getLength(),((Area)area).getAreaId()));
        }
    }

    public static Interval generateSegmentForArea(int start, int end, int length, int id){
        int first = buf;
        int second = buf + length;
        while(second >= end){
            first = start + poisson.nextInt();
            second= first + length;
        }
        buf = second + poisson.nextInt();

        return new Interval(first,second,getRandomChannel(),id);
    }

    public static List<Integer> createRandomDropPoints(int step){
        dropPoints = new ArrayList<>();
        int loopStart = 0;

        if(isMoreTight()) {
            for (int i = 0; i < InputData.getCoefDropPoints(); i++) {
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

        else if(isLessTight()){
            for (int i = 1; i < InputData.getDropPoints() * InputData.getCoefDropPoints() + 1; i++) {
                dropPoints.add((int)(i * (step / InputData.getCoefDropPoints())));
            }
        }

        for (int i = 0; i < dropPoints.size(); i++) {
            dropPoints.set(i,dropPoints.get(i) + randomSign((step / InputData.getCoefDropPoints()) / 2));
        }

        if(dropPoints.size() == 1){
            dropPoints.set(0,step * InputData.getDropPoints());
            return dropPoints;
        }

        if (!(dropPoints.isEmpty())) {
            return dropPoints;
        } else {
            return Collections.emptyList();
        }

    }

    private static boolean isMoreTight(){
        return InputData.getCoefDropPoints() >= 2;
    }

    private static boolean isLessTight(){
        return InputData.getCoefDropPoints() < 2;
    }

    private static Integer randomSign(float halfStep) {
        int hStep = gen.nextInt((int)halfStep);
        int rand = GeneratorRandom.getRandomStep();
        if(rand < MAXSTEP/2){
            return  (-hStep);
        }
        else return  (hStep);
    }

    private static List<Integer> createDropPoints() {
        dropPoints = new ArrayList<>();
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

    public static List<Integer> createDropPoints(int amount) {
        dropPoints = new ArrayList<>();
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


}
