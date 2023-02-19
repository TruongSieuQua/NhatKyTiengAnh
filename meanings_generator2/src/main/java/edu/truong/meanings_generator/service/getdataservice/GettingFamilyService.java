package edu.truong.meanings_generator.service.getdataservice;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.truong.meanings_generator.dto.Family;

public class GettingFamilyService {
//	{
//        "noun": ["nature", "naturalist", "naturalism", "naturalization", "naturalness", "natural", "naturist", "naturism"],
//        "adjective": ["natural", "unnatural", "supernatural", "naturalistic"],
//        "verb": ["naturalize"],
//        "adverb": ["naturally", "unnaturally", "naturalistically", "supernaturally"]
//    }
	
	public Family get(String word) {
		final String url = "https://www.ldoceonline.com/dictionary/".concat(word.replaceAll(" +", "-"));
		Family family = new Family(new ArrayList<String>(), new ArrayList<String>(), 
							new ArrayList<String>(), new ArrayList<String>());
		try {
			final Document document = Jsoup.connect(url).userAgent("Mozilla").timeout(5000).get();
			if (document.selectFirst(".wordfams") != null) {
				Elements wfamily = document.select(".dictionary > .wordfams > span,.dictionary > .wordfams > a");
				List<String> current = null;
				for (int i = 1; i < wfamily.size(); i++) {
					String w = wfamily.get(i).text();
					if (w.matches("\\((.+?)\\)")) {
						switch(w) {
							case "(noun)" : current = family.getNoun(); break;
							case "(adjective)": current = family.getAdjective(); break;
							case "(verb)": current = family.getVerb();break;
							case "(adverb)": current = family.getAdverb();break;
							default:
						}
					}else if(w.matches("[a-zA-Z\\s]+")){
						current.add(w);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Tu \"" + word + "\" trong ham getOwnWordFamily");
		}
		return family;
	}
}
