package edu.truong.meanings_generator.service;

import java.util.List;

import com.google.gson.Gson;

import edu.truong.meanings_generator.dto.Word;

public class JsonService2 {
	private String toJSon(Word w, Gson gson) {
		return gson.toJson(w);
	}
	
//	private Word toWord(String json, Gson gson) {
//		return gson.fromJson(json, Word.class);
//	}
	
	public String toJsonItems(List<edu.truong.meanings_generator.dto.Word> words) {
		StringBuilder sb = new StringBuilder();
		Gson gson = new Gson();
		words.stream().forEach(word ->{
			sb.append(toJSon(word, gson)).append(",\r\n");
		});
		return sb.toString();
	}
	
	
}
