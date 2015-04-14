package ru.sbt.practice.matrices.textprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

public class CharacterMap implements Cloneable {

    private final char[] charset;
    private final int shift;
    private final BitSet contains;
    private byte[] values;

    public CharacterMap(String stringCharset) {

        charset = new char[stringCharset.length()];
        stringCharset.getChars(0, stringCharset.length(), charset, 0);
        int min = 10000;
        int max = 0;

        for(char c:charset) {
            min = Math.min(min, (int)c);
            max = Math.max(max, (int)c);
        }

        shift = min;
        contains = new BitSet(max-min+1);
        values = new byte[max-min+1];

        contains.clear();
        Arrays.fill(values, (byte)0);

        for(char c:charset) {
            contains.set((int)c - shift);
        }

    }

    public boolean contains(char c) {
        int index = (int)c - shift;
        return (index >= 0) && (index < contains.length()) && contains.get(index);
    }

    public boolean isSet(char c) {
        return contains(c) && values[(int)c - shift]!=0;
    }

    public boolean invert(char c) {
        if (!contains(c))
            return false;
        else {
            values[(int)c - shift] = (byte)(1 - values[(int)c - shift]);
            return true;
        }
    }

    public boolean set(char c, boolean value) {
        if (!contains(c))
            return false;
        else {
            values[(int)c - shift] = (byte)(value?1:0);
            return true;
        }
    }

    public char[] getCharset() {
        return Arrays.copyOf(charset, charset.length);
    }

    public Collection<String> toStingCloud() {

        List<String> result = new ArrayList();

        byte[] temp = Arrays.copyOf(values, values.length);

        for(int i = 0; i < temp.length; i++) {
            if(contains.get(i)){
                temp[i] = (byte) (1 - temp[i]);
                result.add(toStringBuilder(temp). toString());

                for(int j = i+1; j < charset.length; j++) {
                    if(contains.get(j) && temp[i]==temp[j]){
                        temp[j] = (byte) (1 - temp[j]);
                        result.add(toStringBuilder(temp).toString());
                        temp[j] = (byte) (1 - temp[j]);
                    }
                }
                temp[i] = (byte) (1 - temp[i]);
            }
        }

        return result;

    }

    private StringBuilder toStringBuilder(byte[] values) {
        StringBuilder sb = new StringBuilder();

        for(char c:charset){
            int index = (int)c - shift;
            if(values[index]!=0){
                sb.append(c);
            }
        }

        return sb;
    }

    public StringBuilder toStringBuilder() {
        return toStringBuilder(this.values);
    }

    @Override
    public String toString() {
        return toStringBuilder().toString();
    }

    public void reset() {
        Arrays.fill(values, (byte)0);
    }

    @Override
    public CharacterMap clone() throws CloneNotSupportedException {
        CharacterMap res = (CharacterMap) super.clone();
        res.values = Arrays.copyOf(values, values.length);
        return res;
    }

}
