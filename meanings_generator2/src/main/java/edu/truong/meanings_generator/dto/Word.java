package edu.truong.meanings_generator.dto;

import java.util.ArrayList;
import java.util.List;

public class Word implements Cloneable{
	private String word, ipa;
	private Family family;
	private List<String> synonyms;
	private List<Meaning> meanings;
	private boolean isFound = false;
	
	
	public Word() {
		
	}

	public String getIpa() {
		return ipa;
	}

	public void setIpa(String ipa) {
		this.ipa = ipa;
	}

	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

	public Family getFamily() {
		return family;
	}
	public void setFamily(Family family) {
		this.family = family;
	}
	public List<String> getSynonyms() {
		if(synonyms==null) {
			synonyms = new ArrayList<String>();
		}
		return synonyms;
	}
	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}
	public List<Meaning> getMeanings() {
		if(meanings == null) {
			meanings = new ArrayList<Meaning>();
		}
		
		return meanings;
	}
	public void setMeanings(List<Meaning> meanings) {
		this.meanings = meanings;
	}
	public boolean isFound() {
		return isFound;
	}
	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}
	
	@Override
	public String toString() {
		return "Word [word=" + word + ", ipa=" + ipa + ", family=" + family + ", synonyms=" + synonyms + ", meanings="
				+ meanings + ", isFound=" + isFound + "]";
	}

	@Override
	public Word clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Word) super.clone();
	}
}
