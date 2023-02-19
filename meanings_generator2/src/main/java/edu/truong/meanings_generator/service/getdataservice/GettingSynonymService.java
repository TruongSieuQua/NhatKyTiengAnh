package edu.truong.meanings_generator.service.getdataservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GettingSynonymService {
	
	public List<String> get(String word) {
		List<String> result = new ArrayList<>();
			//URL url = new URL("https://tuna.thesaurus.com/pageData/".concat());
			Document doc = null;
			try {
				doc = Jsoup.connect("https://www.thesaurus.com/browse/" + word.replace(" +", "%20")).userAgent("Mozilla").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Tu \"" + word + "\" trong ham get lop GettingSynonymService loi: "+ e.getMessage());
			}
			if(doc!=null) {
				Element synonymList = doc.selectFirst("#meanings");
				if(synonymList == null) {
					return result;
				}
				Elements synonyms = synonymList.select("ul > li");
				for(Element synonym : synonyms) {
					result.add(synonym.text());
				}
			}
			return result;
	}
}
