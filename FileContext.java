package sample;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Path;
import java.util.*;

public class FileContext {
	private HashMap<String, TabObj> profiles = new HashMap<String, TabObj>();
	private final Window primaryStage;

	public FileContext(Window stage){
		primaryStage = stage;
	}

	public int openDialog() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Open GameSettings File: ");
		String userDirectoryString = System.getProperty("user.home");

		File userDirectory = new File(userDirectoryString+"/Documents/My Games/Rainbow Six - Siege");
		if(userDirectory.exists()) {
			dc.setInitialDirectory(userDirectory);
			loadProfiles(userDirectory);
			return 0;
		}else{
			File f = dc.showDialog(primaryStage);

			if(f != null && f.isDirectory() && f.getName().contains("Rainbow")){
				loadProfiles(f);
				return 0;
			}
			else{
				return -1;
			}
		}
	}

	public HashMap<String, TabObj> getProfiles() {
		return profiles;
	}

	public void writeToFile(String key, String data) throws IOException {
		File output = profiles.get(key).getFile().listFiles()[0];
		FileWriter writer = new FileWriter(output);

		writer.write(data);
		writer.flush();
		writer.close();
	}
	public void writeToAll(String data) throws IOException {
		profiles.forEach((k, v) -> {
			File output = profiles.get(k).getFile().listFiles()[0];
			try {
				FileWriter writer = new FileWriter(output);
				writer.write(data);
				writer.flush();
				writer.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		});
	}

	private void loadProfiles(File file){
		clearList();
		for(int i = 0; i < file.listFiles().length;i++){
			File dir = file.listFiles()[i];
			if(dir.isDirectory()){
				TabObj tabObj = new TabObj();
				try {
					tabObj.setData(readFile(dir.listFiles()[0].getPath()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				tabObj.setFile(file.listFiles()[i]);
				profiles.put(dir.getName(),tabObj);

			}
		}

	}

	private String readFile(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String data = "";
		String line;
		while((line = reader.readLine())!= null){
			data += line +"\n";
		}
		return data;
	}

	public void clearList(){
		profiles.clear();
	}

}
