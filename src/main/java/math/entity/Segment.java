package math.entity;

public class Segment implements Comparable<Segment>{
    private Integer firstDot;
    private Integer secondDot;
    private int line;
    private Integer length;
    private Integer priority;


    public Segment(int firstDot, int secondDot,int line) throws IllegalArgumentException{
        if(firstDot < secondDot || (firstDot == 0 && secondDot == 0)) {
            this.firstDot = firstDot;
            this.secondDot = secondDot;
            this.line = line;
            this.length = secondDot - firstDot;
            this.priority = setPriority(line);
        }
        else if(firstDot == secondDot){
            this.firstDot = firstDot;
            this.secondDot = secondDot+1;
            this.line = line;
            this.length = secondDot - firstDot;
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
        return "Segment ("
                +firstDot
                +","
                +secondDot
                +","
                +"Канал: "
                +line
                +")";
    }

    public String getName(){
        return "Segment: " + getLine() + getFirstDot() + getSecondDot();
    }


    @Override
    public int compareTo(Segment segment) {
        return this.secondDot.compareTo(segment.secondDot);
    }

}
