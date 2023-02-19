package edu.truong.meanings_generator.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.truong.meanings_generator.dto.Family;
import edu.truong.meanings_generator.dto.Meaning;
import edu.truong.meanings_generator.dto.Sentence;
import edu.truong.meanings_generator.dto.Word;

public class WordCloneUtils {
	/*
	 * Nếu các thuộc tính của 1 đối tượng là kiểu nguyên thủy và String thì chỉ cần
	 * gọi clone là được.
	 * 
	 * Dù origin và clone của String cùng tham chiếu đến 1 đối tượng trong string
	 * pool Nhưng khi clone đổi nó sẽ ko ảnh hưởng tới original
	 * 
	 */

	public WordCloneUtils() {
		super();
	}

	/*
	 * Clone Family
	 */
	private List<String> cloneStringList(List<String> list) {
		List<String> cloneList = new ArrayList<>(list.size());
		for (String item : list) {
			cloneList.add(new String(item));
		}
		return cloneList;
	}

	private Family cloneFamily(Family fam) {
		Family cloneFam = null;
		try {
			cloneFam = fam.clone();
		} catch (CloneNotSupportedException e) {
			cloneFam = new Family();
		}
		cloneFam.setNoun(cloneStringList(fam.getNoun()));
		cloneFam.setAdjective(cloneStringList(fam.getAdjective()));
		cloneFam.setVerb(cloneStringList(fam.getVerb()));
		cloneFam.setAdverb(cloneStringList(fam.getAdverb()));
		return cloneFam;
	}

	/*
	 * Clone Sentence
	 */
	private Sentence cloneSentence(Sentence sen) {
		Sentence cloneSen = null;
		try {
			cloneSen = sen.clone();
		} catch (CloneNotSupportedException e) {
			cloneSen = new Sentence();
			cloneSen.setEn(sen.getEn());
			cloneSen.setVi(sen.getVi());
		}
		return cloneSen;
	}

	/*
	 * Clone List Sentence
	 */
	private List<Sentence> cloneSentenceList(List<Sentence> sensList) {
		List<Sentence> cloneSenList = new ArrayList<Sentence>(sensList.size());
		for (Sentence sen : sensList) {
			cloneSenList.add(cloneSentence(sen));
		}
		return cloneSenList;
	}

	/*
	 * Clone Meaning
	 */
	private Meaning cloneMeaning(Meaning mean) {
		Meaning cloneMeaning;
		try {
			cloneMeaning = mean.clone();
		} catch (CloneNotSupportedException e) {
			cloneMeaning = new Meaning();
			cloneMeaning.setEn(mean.getEn());
			cloneMeaning.setVi(mean.getVi());
		}
		cloneMeaning.setExamples(cloneSentenceList(mean.getExamples()));
		return cloneMeaning;
	}
	
	/*
	 * Clone List<Meaning>
	 */
	private List<Meaning> cloneMeaningList(List<Meaning> means){
		List<Meaning> cloneMeanings = new ArrayList<>();
		for(Meaning mean : means) {
			cloneMeanings.add(cloneMeaning(mean));
		}
		return cloneMeanings;
	}
	
	public Word cloneWord(Word word) {
		Word cloneWord = null;
		try {
			cloneWord = word.clone();
		} catch (CloneNotSupportedException e) {
			cloneWord = new Word();
		}
		cloneWord.setFamily(cloneFamily(word.getFamily()));
		cloneWord.setMeanings(cloneMeaningList(word.getMeanings()));
		cloneWord.setSynonyms(cloneStringList(word.getSynonyms()));
		return cloneWord;
	}
	

	public static void main(String[] args) {
		Word w = new Word();
		w.setWord("Hello");
		w.setIpa("Hello");
		List<String> noun = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3"}));
		List<String> adjective = new ArrayList<>(Arrays.asList(new String[]{"3", "4", "5"}));
		List<String> verb = new ArrayList<>(Arrays.asList(new String[]{"6", "7", "8"}));
		List<String> adverb = new ArrayList<>(Arrays.asList(new String[]{"7", "8", "3"}));	
		w.setFamily(new Family(noun, adjective, verb, adverb));
		Sentence s1 = new Sentence("sentence en", "sentence vi");
		Sentence s2 = new Sentence("sentence en2", "sentence vi2");
		List<Sentence> examples = new ArrayList<>();
		examples.add(s1);
		examples.add(s2);
		Meaning m = new Meaning("en", "vi", examples);
		List<Meaning> meanings = new ArrayList<>();
		meanings.add(m);
		w.setMeanings(meanings);
		
		WordCloneUtils wc = new WordCloneUtils();
		
		Word w2 = wc.cloneWord(w);
		System.out.println("w == w2 ? " + (w == w2));
		System.out.println("w word == w2 ? " + (w.getWord() == w2.getWord()));
		System.out.println("w ipa == w2 ? " + (w.getIpa() == w2.getIpa()));
		System.out.println("w family == w2 ? " + (w.getFamily().getAdjective().get(0) == w2.getFamily().getAdjective().get(0)));
		System.out.println("w meanings== w2 ? " + 
				(w.getMeanings().get(0).getExamples().get(0) == w2.getMeanings().get(0).getExamples().get(0)));
		
		w2.getMeanings().get(0).getExamples().get(0).setEn("a b  v v v v");
		System.out.println(w.getMeanings().get(0).getExamples().get(0).getEn());
		System.out.println(w2.getMeanings().get(0).getExamples().get(0).getEn());
	}
}
