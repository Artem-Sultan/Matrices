package ru.sbt.practice.matrices.TextProcessing;

/**
 * Created by artem on 03.03.15.
 */
public class TextConverter {
    public static String[] stemText(String text) {
        text = text.replaceAll("[^а-яА-Я a-zA-Z]", "");
        String[] tokens = text.split("\\s+");
        for (String s : tokens) {
            System.out.println(s + " " + Porter.stem(s));
            s = Porter.stem(s);
        }
        return tokens;
    }
}

