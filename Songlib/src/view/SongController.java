package view;
//Krishna Patel kgp58
//William Yubeaton - wpy2
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import object.SongComparator;
import object.Song;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class SongController {
	@FXML BorderPane mainborder;
	@FXML ListView<Song> songview;
	@FXML Button addButton;
	@FXML Button editButton;
	@FXML Button deleteButton;
	@FXML GridPane defaultPage;
	@FXML GridPane editPage;
	@FXML GridPane addPage;
	@FXML GridPane detailsPage;
	@FXML GridPane deletePage;
	@FXML Text detname;
	@FXML Text detartist;
	@FXML Text detalbum;
	@FXML Text detyear;
	@FXML TextField addName;
	@FXML TextField addArtist;
	@FXML TextField addAlbum;
	@FXML TextField addYear;
	@FXML Button confirmAdd;
	@FXML Button cancelAdd;
	@FXML Button editFinish;
	@FXML Button editCancel;
	@FXML Button deleteCancel;
	@FXML Button deleteConfirm;
	@FXML TextField editName;
	@FXML TextField editArtist;
	@FXML TextField editAlbum;
	@FXML TextField editYear;
	private ObservableList<Song> Library;

	private Stage main;



public void initialize(Stage mainStage) throws TransformerException {
	main = mainStage;
	mainborder.setBottom(defaultPage);
	loadLibrary();
}





	// will read the xml file called song_list and create the list view
	 public void loadLibrary() throws TransformerException {
		 Library = FXCollections.observableArrayList();
		 try {
			 Path path = Paths.get("song_list.xml");
			 if (Files.exists(path)) {
	                read_xml();
	            }
	            else {
	               create_xml();
	            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 sortLibrary(Library);
		 songview.setItems(Library);
		 //showSongs();
		 // if song library is empty 
		 if(!Library.isEmpty()) {
			 songview.getSelectionModel().select(0);
			 showSongs();
		 }

		 songview.getSelectionModel().selectedIndexProperty().addListener(
					(obs, oldVal, newVal) -> { showSongs(); });
		}



	// shows the song with the full details
	public void showSongs() {
		mainborder.setBottom(detailsPage);

		Song temp = songview.getSelectionModel().getSelectedItem();
		if(null != temp) {
			detname.setText(temp.getName().length() > 25 ? temp.getName().substring(0, 21) + "..." : temp.getName());
			detartist.setText(temp.getArtist().length() > 25 ? temp.getArtist().substring(0, 21) + "..." : temp.getArtist());
			detalbum.setText(temp.getAlbum().length() > 35 ? temp.getAlbum().substring(0, 31) + "..." : temp.getAlbum());
			detyear.setText(temp.getYear());

		}

	}

	public void sortLibrary(ObservableList<Song> temp) {
		SongComparator comp = new SongComparator();
		Song [] songarr = new Song[Library.size()];
		songarr=temp.toArray(songarr);
		Arrays.sort(songarr,comp);

		// remove duplicates that might have been preloaded in
		temp.remove(0,temp.size());

		for(int i=0; i<songarr.length;i++) {
			temp.add(songarr[i]);
		}

		return;
	}


	public void buttonSelection(ActionEvent ae) {
		Button btn = (Button) ae.getSource();
		if(btn==addButton) {
			mainborder.setBottom(addPage);
		} else if (btn== confirmAdd) {
			addAction();
		} else if (btn== cancelAdd) {
			setDefault();
			showSongs();
		} else if (btn== deleteButton) {
			setToDelete();
	   }else if (btn == deleteConfirm) {
		   deleteAction();

	   } else if (btn == deleteCancel) {
		   mainborder.setBottom(defaultPage);
		   showSongs();
	   } else if (btn == editButton) {
		   setToEdit();
	   } else if (btn == editFinish) {
		   editAction();
	   } else if (btn == editCancel) {
		   mainborder.setBottom(defaultPage);
		   showSongs();
	   }
	}


	public void setToDelete() {
		if(Library.isEmpty()) {
			return;
			// this results to an empty list in which we cannot use the delete functionality
		}
		mainborder.setBottom(deletePage);
		return;
	}
	
	
	
	
	public void deleteAction() {
		Song deleteSong=songview.getSelectionModel().getSelectedItem();
		Library.remove(deleteSong);
		refreshLibrary();
		
		if(Library.isEmpty()) {
			setDefault();
		}
		
	}
	
	public void setToEdit() {
			Song editSong = songview.getSelectionModel().getSelectedItem();
			
			if(editSong!=null) {
				String n1= editSong.getName();
				String a1=editSong.getArtist();
				String a2=editSong.getAlbum();
				String y1=editSong.getYear();
				mainborder.setBottom(editPage);
				editName.setText(n1);
				editArtist.setText(a1);
				editAlbum.setText(a2);
				editYear.setText(y1);
			
			} else {
				popupAlerts("Error","Select a song");
			}
			return;
	}
	
	public void editAction() {
		String editnewName = editName.getText().trim();
		String editnewArtist = editArtist.getText().trim();
		String editnewAlbum = editAlbum.getText().trim();
		String editnewYear = editYear.getText().trim();
		Song editSong;
		// problem is here when year is changes to empty space program not working.
	
			
		if(editnewYear.isEmpty()) {
			
			 editSong = new Song(editnewName,editnewArtist,editnewAlbum);
		} else {
		// int edityear = Integer.parseInt(editnewYear);
		 editSong = new Song(editnewName,editnewArtist,editnewAlbum,editnewYear);
		}
		
	
		Song viewEdit = songview.getSelectionModel().getSelectedItem();
		Library.remove(viewEdit);
		
		
		try {
			
			if(editSong.getName().isEmpty() || editSong.getArtist().isEmpty()) {
				Library.add(viewEdit);
				refreshLibrary(viewEdit);
				popupAlerts("Error","Can't Delete Song Name or Artist");
				setToEdit();
				
			} else if (duplicateChecker(editSong)==0) {
				Library.add(viewEdit);
				refreshLibrary(viewEdit);
				popupAlerts("Error","this song already exists in the list");
				setToEdit();
			} else if(editSong.getYear().trim().isEmpty()) {
				Library.add(editSong);
				refreshLibrary(editSong);
				
			}  else if (Integer.parseInt(editnewYear) >0) {
				Library.add(editSong);
				refreshLibrary(editSong);
				
			} else {
				throw new IllegalArgumentException();
			}
			
			
		} catch (IllegalArgumentException e){
			Library.add(viewEdit);
			refreshLibrary(viewEdit);
			popupAlerts("Error","enter a year after 0, or do not fill it in , its optional");
			setToEdit();
			
		}
		
		return;
		
		
	}
	
		public void addAction() {
			String newName = addName.getText().trim();
			String newArtist = addArtist.getText().trim();
			String newAlbum = addAlbum.getText().trim();
			String newYear = addYear.getText().trim();
			Song newSong ;
			
			// first going to check if name and artist are empty 
			
			if(newName.isEmpty() || newArtist.isEmpty()) {
				popupAlerts("Error","you need a name and artist , try again");
			} else {
			
					
				if(newYear.isEmpty()) {
				  newSong = new Song(newName,newArtist,newAlbum);
				} 
					else {
					//int year = Integer.parseInt(newYear);
					newSong = new Song(newName,newArtist,newAlbum,newYear);
				}
						
				
				
				if(duplicateChecker(newSong)==0) {
					popupAlerts("Error","the song already exists");
					
				}else if (newYear.trim().isEmpty()) {
					Library.add(newSong);
					sortLibrary(Library);
					setDefault();
					refreshLibrary(newSong);
					
				}else {
					try {
						if(Integer.parseInt(newYear)>0) {
						 Library.add(newSong);
						 sortLibrary(Library);
						 songview.setItems(Library);
						 songview.getSelectionModel().select(newSong);
						 setDefault();
						 showSongs();
						} else {
							throw new IllegalArgumentException();
						}
					} catch(Exception e) {
						popupAlerts("Error","Enter a year after 0 if you want , its optional");
					}
				}
				
			}
			
		}


		
		public void refreshLibrary(Song s) {
			sortLibrary(Library);
			songview.setItems(Library);
			songview.getSelectionModel().select(s);
			showSongs();
		}
		
		public void refreshLibrary() {
			sortLibrary(Library);
			songview.setItems(Library);
			songview.getSelectionModel().selectFirst();
			showSongs();
		}
		
		public int duplicateChecker(Song sng) {
			for (Song i : Library) {
				if (i.equals(sng)) {
					return 0;
				}
			}	
			return 1;
		}
		
		
		
		
		


	// will give pop up alerts to only
	private void popupAlerts(String title, String text) {
		Alert alt = new Alert(AlertType.ERROR);
		alt.initOwner(main);
		alt.setTitle("Error");
		alt.setHeaderText(title);
		alt.setContentText(text);
		alt.showAndWait();

	}

	// will clear the pane and set to the default one
	private void setDefault() {
		addName.clear();
		addArtist.clear();
		addAlbum.clear();
		addYear.clear();
		mainborder.setBottom(defaultPage);
	}



public ObservableList<Song> getLibrary(){
		return Library;

	}









public void read_xml() throws IOException, SAXException, ParserConfigurationException {
	String file = "song_list.xml";
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();

	Document document = builder.parse(new File(file));
	document.getDocumentElement().normalize();

	Element root = document.getDocumentElement();
	// System.out.println(root.getNodeName());

	NodeList nList = document.getElementsByTagName("song");

	//ArrayList<Song> list = new ArrayList<Song>();

	for (int temp = 0; temp < nList.getLength(); temp++) {
		Node node = nList.item(temp);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) node;
			String temp_name = eElement.getAttribute("name");
			String temp_artist = eElement.getElementsByTagName("artist").item(0).getTextContent();
			String temp_album = eElement.getElementsByTagName("album").item(0).getTextContent();
			String temp_year = eElement.getElementsByTagName("year").item(0).getTextContent();
			// System.out.println("Name: " + eElement.getAttribute("name"));
			// System.out.println("Artist: " +
			// eElement.getElementsByTagName("artist").item(0).getTextContent());
			// System.out.println("Album: " +
			// eElement.getElementsByTagName("album").item(0).getTextContent());
			// System.out.println("Year: " +
			// eElement.getElementsByTagName("year").item(0).getTextContent());

			Song temp_song = new Song();

			if (temp_year.equals(" ")) {
				temp_song = new Song(temp_name, temp_artist, temp_album);
			}

			else {
				// int temp_year_i = Integer.parseInt(temp_year);
				temp_song = new Song(temp_name, temp_artist, temp_album, temp_year);
			}

			// System.out.println(temp_song);

			Library.add(temp_song);
		}
	}

	//return list;
}

public static void write_xml(ObservableList<Song> song_list, String file)
		throws IOException, SAXException, ParserConfigurationException, TransformerException {
		
	
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();

	Document document = builder.parse(new File(file));
	document.getDocumentElement().normalize();

	Element root = document.getDocumentElement();
	// System.out.println(root.getNodeName());

	NodeList nList = document.getElementsByTagName("song");

	// int counter = nList.getLength();

	while (nList.getLength() > 0) {
		Node node = nList.item(0);
		node.getParentNode().removeChild(node);
	}

	for (int temp = 0; temp < song_list.size(); temp++) {
		Song song_a = song_list.get(temp);
		Element test_e = document.createElement("song");
		test_e.setAttribute("name", song_a.getName());
		Element test_2 = document.createElement("artist");
		test_2.appendChild(document.createTextNode(song_a.getArtist()));
		test_e.appendChild(test_2);
		Element test_3 = document.createElement("album");
		test_3.appendChild(document.createTextNode(song_a.getAlbum()));
		test_e.appendChild(test_3);
		Element test_4 = document.createElement("year");
		test_4.appendChild(document.createTextNode(song_a.getYear()));
		test_e.appendChild(test_4);

		root.appendChild(test_e);
	}

	Transformer tr = TransformerFactory.newInstance().newTransformer();
	tr.setOutputProperty(OutputKeys.INDENT, "yes");
	tr.setOutputProperty(OutputKeys.METHOD, "xml");
	tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	// tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
	tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	// send DOM to file
	tr.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
}


public void shutdown() throws IOException, SAXException, ParserConfigurationException, TransformerException {
    
    ObservableList<Song> lib = getLibrary();
    write_xml(lib, "song_list.xml");
    
}
public static void create_xml() throws ParserConfigurationException, FileNotFoundException, IOException, TransformerException {
    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.newDocument();
    Element root = document.createElement("songs");
    document.appendChild(root);
    
    try (FileOutputStream output = new FileOutputStream("song_list.xml")) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
        
    }
    catch (IOException e) {
        e.printStackTrace();
    }
    
}

}


