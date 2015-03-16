/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sbt.practice.matrices.TextProcessing;

import java.util.Collection;

/**
 *
 * @author dron
 */
public class BucketCode {

    private static final CharacterMap bucketInclude = new CharacterMap("бвгджзклмнпрстфхцчшщ");

    private final CharacterMap code;
    private final String stringValue;

    public BucketCode (String word) {
        try {
            code = bucketInclude.clone();
            code.reset();///

            String lWord = word.toLowerCase();

            for (int i = 0; i < lWord.length(); i++) {
                char currentChar = lWord.charAt(i);
                code.set(currentChar, true);
            }

            stringValue = code.toString();
            
        } catch (CloneNotSupportedException ex) {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public Collection<String> getCodeCloud() {
        return code.toStingCloud();
    }

}
