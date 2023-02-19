package edu.truong.meanings_generator.dto;

import java.util.ArrayList;
import java.util.List;

public class Family implements Cloneable {
	private List<String> noun;
	private List<String> adjective;
	private List<String> verb;
	private List<String> adverb;

	public Family() {

	}

	public Family(List<String> noun, List<String> adjective, List<String> verb, List<String> adverb) {
		super();
		this.noun = noun;
		this.adjective = adjective;
		this.verb = verb;
		this.adverb = adverb;
	}

	public List<String> getNoun() {
		return noun;
	}

	public void setNoun(List<String> noun) {
		this.noun = noun;
	}

	public List<String> getAdjective() {
		return adjective;
	}

	public void setAdjective(List<String> adjective) {
		this.adjective = adjective;
	}

	public List<String> getVerb() {
		return verb;
	}

	public void setVerb(List<String> verb) {
		this.verb = verb;
	}

	public List<String> getAdverb() {
		return adverb;
	}

	public void setAdverb(List<String> adverb) {
		this.adverb = adverb;
	}

	@Override
	public Family clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Family) super.clone();
	}

}
