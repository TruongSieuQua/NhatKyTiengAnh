package edu.truong.meanings_generator.service.getdataservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import edu.truong.meanings_generator.dto.Word;

public class GetDataService {
	String name;
	GettingFamilyService gfs;
	GettingMeaningService gms;
	GettingSynonymService gss;

	public GetDataService(String name) {
		super();
		this.name = name;
		gfs = new GettingFamilyService();
		gms = new GettingMeaningService();
		gss = new GettingSynonymService();
	}

	
	
	public String getName() {
		return name;
	}

	public Word process(String word) {
		return getWord(word);
	}
	
	private Word getWord(String word) {
		if(word == null || word.isBlank()) return null;
		Word _word = new Word();
		_word.setWord(word.toLowerCase());
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Runnable getfamsThr = new Runnable() {
			@Override
			public void run() {
				_word.setFamily(gfs.get(word));
			}
		};
		Runnable getsynThr = new Runnable() {
			@Override
			public void run() {
				_word.setSynonyms(gss.get(word));
			}
		};
		Runnable getEnViRun = new Runnable() {
			public void run() {
				gms.setMeanings(word, _word);

			};
		};
		
		executor.execute(getEnViRun);
		executor.execute(getfamsThr);
		executor.execute(getsynThr);

		executor.shutdown();
		while (true) {
			if (executor.isTerminated()) {
				break;
			}
			;
			try {
				executor.awaitTermination(1000L, TimeUnit.MILLISECONDS);
				// System.out.println("42");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(_word.isFound() == false) return null;
		return _word;
	}

}
