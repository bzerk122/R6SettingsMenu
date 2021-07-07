package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
	private Window primaryStage;
	private FileContext fileContext;

	@FXML
	private Button btn_load;

	@FXML
	private Button btn_save;

	@FXML
	private TabPane tab_pane;

	@FXML
	private Label status;

	@FXML
	private Button btn_sync;




	@FXML
	void load(ActionEvent event){
		Node source = (Node) event.getSource();
		primaryStage = source.getScene().getWindow();
		fileContext = new FileContext(primaryStage);

		if(fileContext.openDialog() == 0){
			tab_pane.getTabs().clear();
			fileContext.getProfiles().forEach((k,v) ->{
				Tab tab = new Tab(k);
				TextArea ta = new TextArea(v.getData());
				tab.setContent(ta);
				tab_pane.getTabs().add(tab);
			});
			updateStatus(Color.GREEN,"Game Directory Successfully loaded!");
		}
		else{

			updateStatus(Color.ORANGE,"Game Directory could not be loaded!");
		}

	}

	@FXML
	void save(ActionEvent event) throws IOException {
		Tab tab = tab_pane.getSelectionModel().getSelectedItem();
		if(tab != null){
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to overwrite?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				TextArea ta = (TextArea) tab.getContent();
				fileContext.writeToFile(tab.getText(),ta.getText());
				updateStatus(Color.GREEN,"Profile Saved");
			}
		}
		else{
			updateStatus(Color.ORANGE,"No Profile Selected");
		}
	}

	@FXML
	void sync(ActionEvent event) throws IOException {
		Tab tab = tab_pane.getSelectionModel().getSelectedItem();
		if(tab != null){
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will overwrite all your profiles, do you wish to continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				TextArea ta = (TextArea) tab.getContent();
				fileContext.writeToAll(ta.getText());
				updateStatus(Color.GREEN,"All Profiles Synced");
			}
		}
		else{
			updateStatus(Color.ORANGE,"No Profile Selected");
		}
	}

	public void updateStatus(Color color, String text){
		status.setTextFill(color);
		status.setText(text);
	}



}
