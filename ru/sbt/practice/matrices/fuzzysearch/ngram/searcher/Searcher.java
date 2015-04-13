package ru.sbt.practice.matrices.fuzzysearch.ngram.searcher;

import ru.sbt.practice.matrices.fuzzysearch.ngram.index.Index;

import java.util.Set;

/**
 * Поиск с предварительным индексированием.
 */
public interface Searcher {

	/**
	 * Метод выполняет поиск заданного слова string в словаре по предварительно сгенерированному индексу.
	 *
	 * @param String
	 *            искомое слово
	 * @return множество индексов слов в словаре, соответствующих критерию поиска
	 */
	public Set<Integer> search(String word);

	/**
	 * Возвращает поисковый индекс, используемый при поиске.
	 * 
	 * @return поисковый индекс {@link ru.sbt.practice.matrices.fuzzysearch.ngram.index.Index}
	 */
	public Index getIndex();
}
