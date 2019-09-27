package math.entity;

import java.util.ArrayList;

public class Segment implements Comparable<Segment>{
    private Integer firstDot;
    private Integer secondDot;
    private int line;
    private Integer length;
    private Integer priority;
    private int index;

    private ArrayList<Integer> indexes = new ArrayList<>();


    public Segment(int firstDot, int secondDot,int line) throws IllegalArgumentException{
        if(firstDot <= secondDot) {
            this.firstDot = firstDot;
            this.secondDot = secondDot;
            this.line = line;
            this.length = secondDot - firstDot;
            this.priority = setPriority(line);
        }
        else throw new IllegalArgumentException("Первое число должно быть меньше второго");
    }
    public Segment(int firstDot, int secondDot,int length,int line) throws IllegalArgumentException{
        if(firstDot <= secondDot) {
            this.firstDot = firstDot;
            this.secondDot = secondDot;
            this.line = line;
            this.length = length;
            this.priority = setPriority(line);
        }
        else throw new IllegalArgumentException("Первое число должно быть меньше второго");
    }

//    public Segment(Segment segment1, Segment segment2) throws IllegalArgumentException{
//        if(segment1.getFirstDot() > segment2.getSecondDot()){
//            Segment buf;
//            buf = segment1;
//            segment1 = segment2;
//            segment2= buf;
//        }
//        if(segment1.getSecondDot() != segment2.getFirstDot()){
//            new Segment(segment1.getFirstDot(), segment2.getSecondDot(),-2);
//        }
//        else {
//            this.firstDot = segment1.getFirstDot();
//            this.secondDot = segment2.getSecondDot();
//            this.line = -2;
//            this.length = secondDot - firstDot - (segment2.getFirstDot() - segment1.getSecondDot());
//            this.priority = setPriority(line);
//        }
//    }


    private int setPriority(int line) {
        if(line<0){
            return line - (2*line);
        }
        else return line;
    }

    public Integer getLength(){
        return length;
    }

    public int getFirstDot() {
        return firstDot;
    }

    public int getSecondDot() {
        return secondDot;
    }

    public int getLine() {
        return line;
    }

    public Integer getPriority() {
        return priority;
    }


    @Override
    public String toString(){
        return
                +firstDot
                +"-"
                +secondDot
                +"L"
                +length;
    }

    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return index;
    }

    public String getName(){
        return "Segment: " + getLine() + getFirstDot() + getSecondDot();
    }


    @Override
    public int compareTo(Segment segment) {
        return this.secondDot.compareTo(segment.secondDot);
    }

    public void setIndexes(int index){
        indexes.add(index);
    }
    public void setIndexes(ArrayList<Integer> indexes1,Integer index){
            if(indexes1.size() != 0) {
                indexes.addAll(indexes1);
            }else{
                indexes = indexes1;
            }
            indexes.add(index);
    }
    public ArrayList<Integer> getIndexes(){
        return indexes;
    }


}
