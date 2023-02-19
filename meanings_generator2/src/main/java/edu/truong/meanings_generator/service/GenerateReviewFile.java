package edu.truong.meanings_generator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import edu.truong.meanings_generator.dto.Word;
import edu.truong.meanings_generator.service.getdataservice.GetDataServiceClient;
import edu.truong.meanings_generator.service.getdataservice.GetDataServicePool;


public class GenerateReviewFile {
	JsonService2 jsonService = new JsonService2();
	FileService fileService = new FileService();
	private String wordsNotFound = "";
	
	public void generate(String[] words, String fileName) {
		ExecutorService executorService = Executors.newFixedThreadPool(5, new ThreadFactory() {
			public Thread newThread(Runnable r) {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});
		GetDataServicePool gdsp = new GetDataServicePool();
		List<Word> result = new ArrayList<Word>();
		StringBuilder wordsNotFound = new StringBuilder();
		List<Future<Word>> listFuture = new ArrayList<Future<Word>>();
		for (int i = 0; i < words.length; i++) {
			if (words[i].matches("[a-zA-Z\\s]+")) {
				listFuture.add(executorService.submit(new GetDataServiceClient(words[i].trim(), gdsp)));
			}
			if (i % 50 == 51 || i == words.length - 1) {
				for (Future<Word> future : listFuture) {
					Word w = null;
					try {
						w = future.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (w != null && w.isFound()) {
						result.add(w);
					} else {
						wordsNotFound.append(words[i] + ", ");
					}
				}
				//fileService.insertJsonToFile(fileName, jsonService.toJsonItems(result));
				fileService.appendToFile(fileName, jsonService.toJsonItems(result));
				listFuture.removeAll(listFuture);
				result.removeAll(result);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.wordsNotFound = wordsNotFound.toString();
	}
	
	public String getWordsNotFound() {
		return wordsNotFound;
	}
}
