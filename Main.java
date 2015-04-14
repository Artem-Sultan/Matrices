package ru.sbt.practice.matrices.textprocessing;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DictionaryUpdate update = new DictionaryUpdate();
        /* В train.txt хранится пиьсмо, на основе которого мы будем пополнять словарь */
        update.processText(new File("D:\\IdeaProjects\\project\\src\\ru\\sbt\\practice\\matrices\\textprocessing\\train.txt"));
        update.processText(new File("D:\\IdeaProjects\\project\\src\\ru\\sbt\\practice\\matrices\\textprocessing\\train2.txt"));
        update.closeDictionary();
    }
}
