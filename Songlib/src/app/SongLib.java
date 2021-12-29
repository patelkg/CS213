package app;
//Krishna Patel kgp58
//William Yubeaton - wpy2
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import object.Song;
import view.SongController;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.*;

public class SongLib extends Application {

	
	static SongController rootController;
	
	
	
	
	@Override
	public void start(Stage primarystage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader rootloader = new FXMLLoader();
		rootloader.setLocation(getClass().getResource("/view/test.fxml"));
		BorderPane root = (BorderPane)rootloader.load();
		
		rootController = rootloader.getController();
		rootController.initialize(primarystage);
		
		primarystage.setTitle("Song Library");
		primarystage.setScene(new Scene(root));
		primarystage.setResizable(false);
		primarystage.setOnHidden(e -> {
            try {
                rootController.shutdown();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SAXException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (ParserConfigurationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (TransformerException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		
		
		
		
		
		primarystage.show();
		
		

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}
	
	
	
	
	
	
	
	
	

}
