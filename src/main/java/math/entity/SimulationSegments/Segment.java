package math.entity.SimulationSegments;

import java.util.ArrayList;

public class Segment implements Comparable<Segment>{
    private Integer firstDot;
    private Integer secondDot;
    private int line;
    private Integer length;
    private Integer priority;

    private ArrayList<Integer> indexes = new ArrayList<>();

    public Segment(int firstDot,int secondDot, int line) throws IllegalArgumentException{
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


    @Override
    public int compareTo(Segment o) {
        Integer x1 = this.secondDot;
        Integer x2 = o.secondDot;
        int sComp = x1.compareTo(x2);

        if (sComp != 0) {
            return sComp;
        } else {
            x1 = this.firstDot;
            x2 = o.firstDot;
            sComp = x1.compareTo(x2);
            if (sComp != 0) {
                return sComp;
            } else {
                x1 = this.line;
                x2 = o.line;
                sComp = x1.compareTo(x2);
            }
        }
        return sComp;
    }
}
