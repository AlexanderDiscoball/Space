package math;

import math.entity.array.ArrayHash;
import math.entity.dropintervaltrack.TrackDropInterval;
import math.entity.interval.DropInterval;
import math.entity.linesegments.Algorithms;
import math.entity.linesegments.Track;
import math.exceptions.TryGetResultBeforeMethod;

import java.util.HashMap;

public class FlightPathCreator {
    private TrackDropInterval trackDropInterval;
    private ArrayHash mainArray;
    private Track result = new Track(-1);
    public FlightPathCreator(TrackDropInterval trackDropInterval, ArrayHash mainArray){
        this.trackDropInterval = trackDropInterval;
        this.mainArray = mainArray;
    }

    public ArrayHash getMainArray() {
        return mainArray;
    }

    public TrackDropInterval getTrackDropInterval() {
        return trackDropInterval;
    }

    public Track getResult(){
        if(result != null){
            return result;
        }
        else {
            throw new TryGetResultBeforeMethod("Сначало нужно вызвать метод createResult");
        }
    }

    public void createResult(){
        int end;
        int start;
        HashMap<Integer, Track> separateArray;
        Separator separator = new Separator();
        for (DropInterval dropInterval : trackDropInterval) {
            start = dropInterval.getFirstDot();
            end = dropInterval.getSecondDot();
            separateArray = separator.separationArrays(mainArray,start,end);
            if(separateArray.size() == 0){
                continue;
            }
            Track endSol = Algorithms.nadirAlgorithm(separateArray,mainArray);
            result.addAll(endSol);
            if(mainArray.size() == 0){
                break;
            }
        }
    }

}
