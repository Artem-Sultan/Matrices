package ru.sbt.practice.matrices.fuzzysearch;

public abstract class WordSearcher implements Searcher {
	private final Index index;

	protected WordSearcher(Index index) {
		this.index = index;
	}

	public Index getIndex() {
		return index;
	}


}
