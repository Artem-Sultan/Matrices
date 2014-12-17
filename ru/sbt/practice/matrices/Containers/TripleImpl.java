package ru.sbt.practice.matrices.Containers;

/**
 * Created by artem on 17.12.14.
 */
public  class TripleImpl {
    private final int x;
    private final int y;
    private final double element;

    public TripleImpl(int x, int y, double element) {
        this.x = x;
        this.y = y;
        this.element = element;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public double getElement() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripleImpl)) return false;
        TripleImpl key = (TripleImpl) o;
        return x == key.x && y == key.y&& element== key.element;
    }

    @Override
    public int hashCode() {
        int res = 17;
        res = res * 31 + Math.min(x, y);
        res = res * 31 + Math.max(y, x);
        return res;
    }
    @Override
    public String toString() {
        return  x + " " + y + " "+ element;
    }

}

