package math.entity.Array;

import math.entity.LineSegments.Track;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Selection implements Iterable<Track> {
    private List<Track> rangeOfTracks;

    public Selection() { //Constructor Конструктор
        rangeOfTracks=new ArrayList<>();
    }

    public Selection(List<Track> rangeOfTracks) {
        this.rangeOfTracks=rangeOfTracks;
    }

    public int getTracksCount() {
        return rangeOfTracks.size();
    }

    public Track getTrackNo(int i) {
        return rangeOfTracks.get(i);
    }

    public int size(){
        return rangeOfTracks.size();
    }

    public void remove(Track track){
        rangeOfTracks.remove(track);
    }

    public void removeAll(List<Track> mask){
        rangeOfTracks.removeAll(mask);
    }

    public void add(Track track){
        rangeOfTracks.add(track);
    }

    @Override
    public Iterator<Track> iterator() {
        return rangeOfTracks.iterator();
    }

    public void sort(){
        rangeOfTracks.sort(Comparator.comparing(Track::getTrackNumber));
    }
}
