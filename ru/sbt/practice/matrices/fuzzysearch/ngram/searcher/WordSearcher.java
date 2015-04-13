package ru.sbt.practice.matrices.fuzzysearch.ngram.searcher;

import ru.sbt.practice.matrices.fuzzysearch.ngram.index.Index;

public abstract class WordSearcher implements Searcher {
	private final Index index;

	protected WordSearcher(Index index) {
		this.index = index;
	}

	public Index getIndex() {
		return index;
	}


}
