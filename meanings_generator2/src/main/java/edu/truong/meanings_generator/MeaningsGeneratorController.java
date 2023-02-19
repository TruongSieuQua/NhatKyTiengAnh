package edu.truong.meanings_generator;

import edu.truong.meanings_generator.service.FileService;
import edu.truong.meanings_generator.service.GenerateReviewFile;
import edu.truong.meanings_generator.service.JsonService2;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MeaningsGeneratorController {
	FileService fileService = new FileService();
	JsonService2 jsonService = new JsonService2();
	GenerateReviewFile grf = new GenerateReviewFile();
	@FXML
	private Button cancel;

	@FXML
	private Button generate;

	@FXML
	private TextField savedFile;

	@FXML
	private TextField separator;

	@FXML
	private TextArea ta_words;
	
	private String getInput() {
		String input = ta_words.getSelectedText();
		if(input != null && !input.isBlank()) {
			return input;
		}
		return ta_words.getText();
	}

	@FXML
	public void autoAddToExcel() {
		String s = !separator.getText().isBlank() ?  separator.getText() : "\n";
		String[] words = getInput().split(s);
		grf.generate(words, savedFile.getText());
		ta_words.appendText("\nNhững từ không tìm thấy: " + grf.getWordsNotFound());
	}
	
	@FXML
	public void openFile() {
		fileService.openFile(savedFile.getText());
	}
}
