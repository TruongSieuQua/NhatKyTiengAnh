package edu.truong.meanings_generator.dto;

import java.util.ArrayList;
import java.util.List;

public class Meaning implements Cloneable{
	private String en;
	private String vi;
	private List<Sentence> examples;
	
	public Meaning() {
	}

	public Meaning(String en, String vi, List<Sentence> examples) {
		super();
		this.en = en;
		this.vi = vi;
		this.examples = examples;
	}
	
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getVi() {
		return vi;
	}
	public void setVi(String vi) {
		this.vi = vi;
	}
	public List <Sentence> getExamples() {
		if(examples==null) {
			examples = new ArrayList<Sentence>();
		}
		return examples;
	}
	
	public void setExamples(List<Sentence> examples) {
		this.examples = examples;
	}

	@Override
	public Meaning clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Meaning) super.clone();
	}


}
