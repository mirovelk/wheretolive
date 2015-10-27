package xyz.wheretolive.crawl.geocoding;

import java.util.LinkedList;

public class LimitedQueue<E> extends LinkedList<E> {

    private final int limit;

    public LimitedQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E e) {
        if (size() >= limit) {
            removeFirst();
        }
        return super.add(e);
    }
}
