package ru.sbt.practice.matrices.textprocessing;

import java.io.*;
import java.util.HashSet;

public class DictionaryUpdate {
    /* в файле DICT_PATH будет храниться наш словарь */
    private static final String DICT_PATH = "D:\\IdeaProjects\\project\\src\\ru\\sbt\\practice\\matrices\\textprocessing\\dict.txt";
    private FileReader fileReaderDictionary = null;
    private FileWriter fileWriterDictionary = null;
    private static HashSet<String> dict = null;

    public DictionaryUpdate() throws IOException {
        fileReaderDictionary = new FileReader(DICT_PATH);
        fileWriterDictionary = new FileWriter(DICT_PATH, true);
        if (dict == null) {
            loadDictionary();
        }
    }
    /* лучше хранить в сериализованном виде и загружать сразу готовый хэшсэт, но пока в txt */
    private void loadDictionary() throws IOException {
        dict = new HashSet<String>();
        BufferedReader bufferedReader = new BufferedReader(fileReaderDictionary);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            dict.add(line);
        }
        bufferedReader.close();
    }

    private void addToDictionary(String newWord) throws IOException {
        if (!dict.contains(newWord)) {
            dict.add(newWord);
            fileWriterDictionary.write(newWord);
            fileWriterDictionary.write("\n");
        }
    }

    public void processText(String text) throws IOException {
        Porter porter = new Porter();
        String[] words = text.split("[^а-яА-яёa-zA-Z0-9]+");
        for (String word : words) {
            if (word.length() >= 3) {
                word = porter.stem(word);
                addToDictionary(word);
            }
        }
    }

    public void processText(File text) throws IOException {
        FileInputStream inFile = new FileInputStream(text);
        byte[] str = new byte[inFile.available()];
        inFile.read(str);
        processText(new String(str));
    }

    public void closeDictionary() throws IOException {
        fileReaderDictionary.close();
        fileWriterDictionary.close();
    }
}
