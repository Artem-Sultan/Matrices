package ru.sbt.practice.matrices.fuzzysearch.ngram.indexer;

import ru.sbt.practice.matrices.fuzzysearch.ngram.index.Index;

/**
 * Индексатор поискового алгоритма.
 */
public interface Indexer {

	/**
	 * Создает индекс по заданному словарю.
	 * 
	 * @param dictionary
	 *            словарь
	 * @return индекс {@link ru.sbt.practice.matrices.fuzzysearch.ngram.index.Index}
	 */
	public Index createIndex(String[] dictionary);
}
