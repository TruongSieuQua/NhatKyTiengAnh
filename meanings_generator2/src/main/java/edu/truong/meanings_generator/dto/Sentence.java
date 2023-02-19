package edu.truong.meanings_generator.dto;

public class Sentence  implements Cloneable{
	private String en;
	private String vi;
	
	
	public Sentence() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Sentence(String en, String vi) {
		super();
		this.en = en;
		this.vi = vi;
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
	@Override
	public Sentence clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Sentence) super.clone();
	}
	
	
}
