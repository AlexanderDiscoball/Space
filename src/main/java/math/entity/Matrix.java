package math.entity;

import java.util.Collection;

public interface Matrix extends Iterable<StackSegments> {
    void add(StackSegments stackSegments);
    int size();
    StackSegments get();
    StackSegments get(int index);
    Collection<? extends StackSegments> getCollection();
    Matrix clone();
}
