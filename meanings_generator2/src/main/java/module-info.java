module edu.truong.meanings_generator {
    requires javafx.controls;
    requires javafx.fxml;
	requires org.jsoup;
	requires org.json;
	requires com.google.gson;
	requires java.desktop;
	requires transitive javafx.graphics;
	
    opens edu.truong.meanings_generator.dto to com.google.gson;
    opens edu.truong.meanings_generator.service to com.google.gson;
    opens edu.truong.meanings_generator to javafx.fxml;
    exports edu.truong.meanings_generator;
}