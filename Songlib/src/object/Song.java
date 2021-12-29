package object;
// Krishna Patel kgp58
//William Yubeaton - wpy2
public class Song {

	private String name;
	private String artist;
	private String album;
	private String year;
	
	public Song() {
		this.name = " ";
		this.artist = " ";
		this.album = " ";
		this.year = " ";
	}
	
	public Song(String name, String artist) {
		this.name = name;
		this.artist = artist;
		this.album = " ";
		this.year = " ";
	}
	
	public Song(String name, String artist, String album) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = " ";
	}
	
	public Song(String name, String artist, int year) {
		this.name = name;
		this.artist = artist;
		this.album = " ";
		this.year = Integer.toString(year);
	}
	
	public Song(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public void setYear(int year) {
		this.year = Integer.toString(year);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public String getAlbum() {
		return this.album;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public boolean equals(Song song2) {
		return (this.name.trim().equalsIgnoreCase(song2.name.trim()) &&
				this.artist.trim().equalsIgnoreCase(song2.artist.trim()));
	}
	
	public String toString() {
		return "Name : "+this.name + " , "+"Artist :"+this.artist;
	}

}
