package ru.sbt.practice.matrices;

/**
 * Created by artem on 05.11.14.
 */
public class ListImpl{
    private int index;
    private double value;
    private ListImpl nextIndex;

    public ListImpl getNextIndex() {
        return nextIndex;
    }

    public double getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public ListImpl(int index, double value)
    {
        this.index = index;
        nextIndex = null;
        this.value = value;
    }

    public ListImpl()
    {
        index = -1;
        value = 0;
        nextIndex = null;
    }

    public void store(int index, double value)
    {
        ListImpl current = this;
        ListImpl previous = null;

        ListImpl node = new ListImpl(index, value);
        while (current != null && current.index < index)
        {
            previous = current;
            current = current.nextIndex;
        }
        if (current == null)
        {
            previous.nextIndex = node;
        }
        else
        {
            if (current.index == index)
            {
                System.out.println("DUPLICATE INDEX");
               // return;
            }
            previous.nextIndex = node;
            node.nextIndex = current;
        }
        return;
    }

    public double fetch(int index)
    {
        ListImpl current = this;
        double value = 0;
        while (current != null && current.index < index)
        {
            current = current.nextIndex;
        }
        if (current != null && current.index == index)
        {
            value = current.value;
        }
        return value;
    }

    public int elementCount()
    {
        int elementCount = 0;
        ListImpl current = this;
        while (current != null)
        {
            current = current.nextIndex;
            elementCount++;
        }
        return elementCount;
    }

    @Override
    public String toString() {
        String res = "";

        ListImpl current = this;
        int tempIndex = -1;

        while (current != null) {

            for (int i =tempIndex+1; i < current.index;i++) {
                res = res + 0 + " ";
            }
            res = res + current.value + " ";
            tempIndex = current.index;
            current = current.nextIndex;

        }
        return res;
    }
}
