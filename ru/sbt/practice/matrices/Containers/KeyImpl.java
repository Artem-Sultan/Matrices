package ru.sbt.practice.matrices.Containers;

public  class KeyImpl {
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private final int x;
    private final int y;

    public KeyImpl(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyImpl)) return false;
        KeyImpl key = (KeyImpl) o;
        return x == key.x && y == key.y;
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
        return  x + " " + y;
    }

}
