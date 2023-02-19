package edu.truong.meanings_generator.service.getdataservice;

import java.util.concurrent.Callable;

import edu.truong.meanings_generator.dto.Word;

public class GetDataServiceClient implements Callable<Word> {
	String word;
	GetDataServicePool gdsp;
	
	public GetDataServiceClient(String word, GetDataServicePool gdsp) {
		super();
		this.word = word;
		this.gdsp = gdsp;
	}

	@Override
	public Word call() throws Exception {
		GetDataService gds = gdsp.get();
		Word w = gds.process(word);
		gdsp.release(gds);
		return w;
	}
	
}
