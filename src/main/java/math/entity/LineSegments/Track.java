package math.entity.LineSegments;

import math.entity.Array.ArrayHash;
import math.entity.interval.Interval;

import java.lang.reflect.Array;
import java.util.*;

public class Track implements Cloneable, Iterable<Interval>{
    private int tracksWithIntervalsOffTheSolutionCount=0;
    private static int tracks_length=0;
    private static int tracks_quantity=0;
    private List<Interval> rangeOfIntervals;
    private final int trackNumber;
    int fullLengthOfIntervalsInTrack;
    private Interval trackIntervalTemporarilyinvestigated; //declare temporary intervals' holders
    private Interval solutionIntervalToCompareWith;
    private Interval solutionIntervalPreviouslyComparedWith;
    private Interval indicatorWhenSolIntervalChanges;
    public Track(int trackNumber) {
        this.trackNumber = trackNumber;
        this.rangeOfIntervals = new LinkedList<>();
    }

    public Track(List<Interval> rangeOfIntervals) {
        this.trackNumber = rangeOfIntervals.get(0).getLine();
        this.rangeOfIntervals = new LinkedList<>();
        this.rangeOfIntervals.addAll(rangeOfIntervals);
    }

    public boolean mergeWithoutCrossings(Track investigatedTrack, ArrayHash mainArray) {
        ListIterator<Interval> it_sol=this.rangeOfIntervals.listIterator();
        investigatedTrack.rangeOfIntervals.sort(Comparator.comparing(Interval::getSecondDot));
        //System.out.println(investigatedTrack);
        ListIterator<Interval> it_track=investigatedTrack.rangeOfIntervals.listIterator();
        if(temporaryIntervalsCantBeInitialized(it_track))
            return investigatedTrackIsEmpty(); //return; //switch to the next track
        else
            initializeTemporaryIntervals(it_track, it_sol);
        int IntervalsOffTheSolutionCount=0;
        do {
            if(trackIntervalPrecedesSolInterval())
                if(solIntervalWasChangedOnPreviousStep())
                    if(trackIntervalDisjoinSolPrevInterval())
                        addIntervalToSolutionChangingTmpSol(it_sol,mainArray);
                    else
                        IntervalsOffTheSolutionCount++;
                else
                    addIntervalToSolution(it_sol,mainArray);
            else
            if(solutionHasNextInterval(it_sol)) {
                changeSolInterval(it_sol);
                continue;
            }
            else {
                if (trackIntervalLeadsSolInterval()) {
                    while (investigatedTrackHasNextInterval(it_track)) {
                        appendIntervalToSolution(it_track, it_sol, mainArray);
                    }
                    appendLeftoverIntervalToSolution(it_sol, mainArray);
                }
                else {
                    IntervalsOffTheSolutionCount++;//trackToStartShouldBeChanged(countToBeChanged);
                }
            }

            if(bypassingOfTheTrackShouldBeStopped(investigatedTrack, it_track))
                //|| count_missed_int>=15500) //remove 3rd cond if you want to find the optimized solution
                break;
            else
                trackIntervalTemporarilyinvestigated=it_track.next();
        }while(trackIsntBypassed(it_track));

        if(IntervalsOffTheSolutionCount > 10)
            tracksWithIntervalsOffTheSolutionCount++;
        //crossings,i.e. track should be changed
        return IntervalsOffTheSolutionCount != 0; //no crossings,i.e. track shouldn't be changed
    }

    public boolean mergeWithoutCrossingsDontNeedSort(Track investigatedTrack, ArrayHash mainArray) {
        ListIterator<Interval> it_sol=this.rangeOfIntervals.listIterator();
        ListIterator<Interval> it_track=investigatedTrack.rangeOfIntervals.listIterator();
        if(temporaryIntervalsCantBeInitialized(it_track))
            return investigatedTrackIsEmpty(); //return; //switch to the next track
        else
            initializeTemporaryIntervals(it_track, it_sol);
        int IntervalsOffTheSolutionCount=0;
        do {
            if(trackIntervalPrecedesSolInterval())
                if(solIntervalWasChangedOnPreviousStep())
                    if(trackIntervalDisjoinSolPrevInterval())
                        addIntervalToSolutionChangingTmpSol(it_sol,mainArray);
                    else
                        IntervalsOffTheSolutionCount++;
                else
                    addIntervalToSolution(it_sol,mainArray);
            else
            if(solutionHasNextInterval(it_sol)) {
                changeSolInterval(it_sol);
                continue;
            }
            else {
                if (trackIntervalLeadsSolInterval()) {
                    while (investigatedTrackHasNextInterval(it_track)) {
                        appendIntervalToSolution(it_track, it_sol, mainArray);
                    }
                    appendLeftoverIntervalToSolution(it_sol, mainArray);
                }
                else {
                    IntervalsOffTheSolutionCount++;//trackToStartShouldBeChanged(countToBeChanged);
                }
            }

            if(bypassingOfTheTrackShouldBeStopped(investigatedTrack, it_track))
                //|| count_missed_int>=15500) //remove 3rd cond if you want to find the optimized solution
                break;
            else
                trackIntervalTemporarilyinvestigated=it_track.next();
        }while(trackIsntBypassed(it_track));

        if(IntervalsOffTheSolutionCount > 10)
            tracksWithIntervalsOffTheSolutionCount++;
        //crossings,i.e. track should be changed
        return IntervalsOffTheSolutionCount != 0; //no crossings,i.e. track shouldn't be changed
    }


    private boolean temporaryIntervalsCantBeInitialized(ListIterator<Interval> it_track) {
        return !it_track.hasNext();
    }

    private void initializeTemporaryIntervals(ListIterator<Interval> it_track,
                                              ListIterator<Interval> it_sol) {
        trackIntervalTemporarilyinvestigated=it_track.next();
        solutionIntervalToCompareWith=it_sol.next();
        solutionIntervalPreviouslyComparedWith=new Interval(-1,-1,-1,-1);
        indicatorWhenSolIntervalChanges=solutionIntervalToCompareWith;
    }

    private boolean trackIsntBypassed(ListIterator<Interval> it_track) {
        return it_track.hasNext() || it_track.hasPrevious();
    }

    public boolean trackIntervalPrecedesSolInterval() {
        return trackIntervalTemporarilyinvestigated.getSecondDot() <= solutionIntervalToCompareWith.getFirstDot();
    }

    public boolean solIntervalWasChangedOnPreviousStep() {
        return indicatorWhenSolIntervalChanges != solutionIntervalToCompareWith;
    }

    public boolean trackIntervalDisjoinSolPrevInterval() {
        return trackIntervalTemporarilyinvestigated.getFirstDot() >= solutionIntervalPreviouslyComparedWith.getSecondDot();
    }

    public void	addIntervalToSolutionChangingTmpSol(ListIterator<Interval> it_sol,ArrayHash mainArray){
        indicatorWhenSolIntervalChanges=solutionIntervalToCompareWith;
        solutionInsert(it_sol,mainArray);
        incrementTracksInfo();	//? how to implement
    }

    public void solutionInsert(ListIterator<Interval> it_sol,ArrayHash mainArray){
        it_sol.previous();
        it_sol.add(trackIntervalTemporarilyinvestigated);
        it_sol.next();
        mainArray.remove(trackIntervalTemporarilyinvestigated.getAreaId());
    }

    public void incrementTracksInfo() {
        tracks_length+=trackIntervalTemporarilyinvestigated.getLength();
        tracks_quantity++;
    }

    public boolean investigatedTrackIsEmpty() {
        return false;
    }


    public void	addIntervalToSolution(ListIterator<Interval> it_sol,ArrayHash mainArray) {
        solutionInsert(it_sol,mainArray);
        incrementTracksInfo();
    }

    public boolean solutionHasNextInterval(ListIterator<Interval> it_sol){
        return it_sol.hasNext();
    }

    public void changeSolInterval(ListIterator<Interval> it_sol){
        solutionIntervalPreviouslyComparedWith=solutionIntervalToCompareWith;
        solutionIntervalToCompareWith=it_sol.next();
    }

    public boolean trackIntervalLeadsSolInterval(){
        return trackIntervalTemporarilyinvestigated.getFirstDot() >= solutionIntervalToCompareWith.getSecondDot();
    }

    public boolean investigatedTrackHasNextInterval(ListIterator<Interval> it_track){
        return it_track.hasNext();
    }

    public void appendIntervalToSolution(ListIterator<Interval> it_track, ListIterator<Interval> it_sol,ArrayHash mainArray){
        solutionAppend(it_sol,mainArray);
        incrementTracksInfo();
        trackIntervalTemporarilyinvestigated=it_track.next();
    }

    public void solutionAppend(ListIterator<Interval> it_sol,ArrayHash mainArray){
        it_sol.add(trackIntervalTemporarilyinvestigated);
        mainArray.remove(trackIntervalTemporarilyinvestigated.getAreaId());
    }

    public void appendLeftoverIntervalToSolution(ListIterator<Interval> it_sol,ArrayHash mainArray){
        solutionAppend(it_sol,mainArray);
        incrementTracksInfo();
    }

    private boolean bypassingOfTheTrackShouldBeStopped(Track investigatedTrack, ListIterator<Interval> it_track) {
        return investigatedTrack.rangeOfIntervals.size() == 0 || !it_track.hasNext();
    }

    public void takeIntoAccountSolutionInfo() {
        tracks_length+=this.getFullLengthOfIntervalsInTrack();
        tracks_quantity+=this.rangeOfIntervals.size();
    }

    @Override
    public Track clone() {
        Track track = new Track(this.trackNumber);
        track.addAll(rangeOfIntervals);
        return track;
    }

    private void addAll(List<Interval> intervals) {
        this.rangeOfIntervals.addAll(intervals);
    }

    public List<Interval> getRangeOfIntervals(){
        return rangeOfIntervals;
    }

    public final int getTrackNumber() {
        return trackNumber;
    }

    public int getFullLengthOfIntervalsInTrack() { //Change with FOR-EACH
        int length=0;
        for (Interval rangeOfInterval : rangeOfIntervals)
            length += rangeOfInterval.getLength();
        return length;
    }

    public void add(Interval interval){
        rangeOfIntervals.add(interval);
    }

    @Override
    public String toString() {
        return
                trackNumber
                        + rangeOfIntervals.toString() ;
    }

    public void addAll(Track track){
        rangeOfIntervals.addAll(track.getRangeOfIntervals());
    }

    public int size(){
        return rangeOfIntervals.size();
    }

    public boolean mergeWithoutCrossingsAll(Track investigatedTrack) {
        ListIterator<Interval> it_sol=this.rangeOfIntervals.listIterator();
        ListIterator<Interval> it_track=investigatedTrack.rangeOfIntervals.listIterator();
        if(temporaryIntervalsCantBeInitialized(it_track))
            return investigatedTrackIsEmpty(); //return; //switch to the next track
        else
            initializeTemporaryIntervals(it_track, it_sol);
        int IntervalsOffTheSolutionCount=0;
        do {
            if(trackIntervalPrecedesSolInterval())
                if(solIntervalWasChangedOnPreviousStep())
                    if(trackIntervalDisjoinSolPrevInterval())
                        addIntervalToSolutionChangingTmpSol(it_track, it_sol);
                    else
                        IntervalsOffTheSolutionCount++;
                else
                    addIntervalToSolution(it_track, it_sol);
            else
            if(solutionHasNextInterval(it_sol)) {
                changeSolInterval(it_sol);
                continue;
            }
            else
            if(trackIntervalLeadsSolInterval()){
                while(investigatedTrackHasNextInterval(it_track))
                    appendIntervalToSolution(it_track, it_sol);
                appendLeftoverIntervalToSolution(it_track, it_sol);
            }
            else
                IntervalsOffTheSolutionCount++;

            if(bypassingOfTheTrackShouldBeStopped(investigatedTrack, it_track))
                break;
            else
                trackIntervalTemporarilyinvestigated=it_track.next();
        }while(trackIsntBypassed(it_track));

        if(IntervalsOffTheSolutionCount > 10)
            tracksWithIntervalsOffTheSolutionCount++;

        if(IntervalsOffTheSolutionCount==0) {
            IntervalsOffTheSolutionCount=0;
            return false; //crossings aren't presented between solution's and track's intervals
        }
        else {
            IntervalsOffTheSolutionCount=0;
            return true; //crossings are presented between solution's and track's intervals
        }
    }


    public void solutionInsert(ListIterator<Interval> it_track,
                               ListIterator<Interval> it_sol){
        it_sol.previous();
        it_sol.add(trackIntervalTemporarilyinvestigated);
        it_sol.next();
        it_track.remove();
    }


    public void appendIntervalToSolution(ListIterator<Interval> it_track,
                                         ListIterator<Interval> it_sol){
        solutionAppend(it_track, it_sol);
        incrementTracksInfo();
        trackIntervalTemporarilyinvestigated=it_track.next();
    }


    public void appendLeftoverIntervalToSolution(ListIterator<Interval> it_track,
                                                 ListIterator<Interval> it_sol){
        solutionAppend(it_track, it_sol);
        incrementTracksInfo();
    }

    public void	addIntervalToSolutionChangingTmpSol(ListIterator<Interval> it_track,
                                                       ListIterator<Interval> it_sol){
        indicatorWhenSolIntervalChanges=solutionIntervalToCompareWith;
        solutionInsert(it_track, it_sol);
        incrementTracksInfo();	//? how to implement
    }



    public void	addIntervalToSolution(ListIterator<Interval> it_track,
                                         ListIterator<Interval> it_sol) {
        solutionInsert(it_track, it_sol);
        incrementTracksInfo();
    }

    public void solutionAppend(ListIterator<Interval> it_track,
                               ListIterator<Interval> it_sol){
        it_sol.add(trackIntervalTemporarilyinvestigated);
        it_track.remove();
    }

    @Override    public Iterator<Interval> iterator() {
        return rangeOfIntervals.iterator();
    }
    public  int getNumberOfTracksWithIntervalsOffTheSolution() {
        return tracksWithIntervalsOffTheSolutionCount;
    }

    public  void resetNumberOfTracksWithIntervalsOffTheSolution() {
        tracksWithIntervalsOffTheSolutionCount=0;
    }

}
