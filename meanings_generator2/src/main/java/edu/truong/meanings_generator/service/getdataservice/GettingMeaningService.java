package edu.truong.meanings_generator.service.getdataservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import edu.truong.meanings_generator.dto.Meaning;
import edu.truong.meanings_generator.dto.Sentence;
import edu.truong.meanings_generator.dto.Word;

public class GettingMeaningService {

	public void setMeanings(String keyword, Word word) {
		getEnViFromCamDic(keyword, word);
		if (word.isFound() == false) {
			getWordFromMochi(keyword, word);
		}
		if (word.isFound() == false) {
			//getEnFromCamDic(word);
		}
	};

	private void getEnViFromCamDic(String keyword, Word word) {
		final String url = "https://dictionary.cambridge.org/dictionary/english-vietnamese/"
				.concat(keyword.replaceAll(" +", "-"));
		try {
			final Document document = Jsoup.connect(url).userAgent("Mozilla").timeout(4000).get();
			Element entry;
			if ((entry = document.selectFirst(".entry-body")) != null) {
				word.setFound(true);
				if (entry.select(".pron-info").first() != null) {
					word.setIpa(entry.select(".pron-info").first().text());
				}
				if (entry.select(".def-block").first() != null) {
					for (Element div : entry.select(".def-block")) {//Loop qua tung Meaning
						Meaning meaning = new Meaning();
						meaning.setEn(div.select(".ddef_h > .db.ddef_d.def").text());// Nghia tieng Anh
						meaning.setVi(div.select(".ddef_b-t.ddef_b.def-body>.dtrans.trans").text());// Nghia tieng

						for (String each : div.select(".examp.dexamp").eachText()) {
							Sentence sentence = new Sentence();
							sentence.setEn(each);
							meaning.getExamples().add(sentence);
						}
						word.getMeanings().add(meaning);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Loi o getEnViFromCamDic trong GettingMeaningService class!");
			word.setFound(false);
		}
	}

	private JSONObject wordGetJSON(String keyword) throws JSONException, IOException {
		StringBuilder urlSB = new StringBuilder();
		urlSB.append("https://mochien3.1-api.mochidemy.com/v3.1/words/dictionary-english?key=")
				.append(keyword.replaceAll(" +", "+")).append("&user_token=notLogin");
		URL url = new URL(urlSB.toString());

		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setRequestProperty("authority", "mochien3.1-api.mochidemy.com");
		http.setRequestProperty("accept", "application/json, text/plain, */*");
		http.setRequestProperty("privatekey", "M0ch1M0ch1_En_$ecret_k3y");
		http.setConnectTimeout(2000);

		String inputLine;
		StringBuilder temp = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
		while ((inputLine = in.readLine()) != null)
			temp.append(inputLine);
		in.close();
		http.disconnect();
		return new JSONObject(temp.toString());
	}

	private Word jsonGetWordDetails(String keyword, Word word) throws JSONException, IOException {
		JSONObject json = wordGetJSON(keyword);
		if (!json.getJSONObject("data").getJSONArray("vi").isEmpty()) {// Neu tu do co trong Mochi
			word.setFound(true);
			JSONArray data_vi = json.getJSONObject("data").getJSONArray("vi");
			JSONObject temp = ((JSONObject) data_vi.get(0));
			// word_trans.word = temp.get("content").toString(); // Lay tu
			word.setIpa(
					"us " + temp.get("phonetic_uk").toString().concat("  uk " + temp.get("phonetic_us").toString()));
			
			for (Object data_vi_details : data_vi) { // data_vi la mot mang. Cac phan tu mang chua details cung la mang
														// chua detail la cai minh can
				JSONArray details = ((JSONObject) data_vi_details).getJSONArray("detail");
				for (Object detail : details) {
					Meaning meaning = new Meaning();
					Sentence sentence = new Sentence();
					meaning.setVi("(" + ((JSONObject) detail).get("position").toString() + ") "
							+ ((JSONObject) detail).get("trans").toString());// Nghia TiengViet
					sentence.setEn(((JSONObject) detail).get("en_sentence").toString());
					sentence.setVi(((JSONObject) detail).get("vi_sentence").toString());
					meaning.getExamples().add(sentence);
					word.getMeanings().add(meaning);
				}
			}
		}
		return word;
	}

	private Word getWordFromMochi(String keyword, Word word) {
		try {
			return jsonGetWordDetails(keyword, word);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			word.setFound(false);
			return word;
		}
	}
}
