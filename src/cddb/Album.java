package cddb;

import java.util.ArrayList;

public class Album implements Comparable<Album>{
	private String albTitle;
	private int year;
	ArrayList <Song> songList;
	private int numSongs;
	
	//%%%%%%%%%%%%%%%%%% Constructors %%%%%%%%%%%%%%%%%%
	public Album(String t, int y) {
		albTitle = t;
		year = y;
		//initialize Song array with size of 10, resize if necessary
		numSongs = 0;
		songList = new ArrayList<Song>(numSongs);
	}
	
	public Album(String t, int y, int n) {
		albTitle = t;
		year = y;
		numSongs = n;
		songList = new ArrayList<Song>(numSongs);
	}
	
	//%%%%%%%%%%%%%%%%%% Mutators %%%%%%%%%%%%%%%%%%
	public boolean addSong(Song s) {
		if(songList.indexOf(s) == -1) {
			songList.add(s);
			numSongs++;
			return true;
		} else {
			System.out.println(s.getTitle() + " already exists!");
			return false;
		}
	}
	
	//%%%%%%%%%%%%%%%%%% Getters %%%%%%%%%%%%%%%%%%
	public String getTitle() {
		return albTitle;
	}
	
	public int getNumSongs() {
		return numSongs;
	}
	
	public int getYear() {
		return year;
	}
	
	//%%%%%%%%%%%%%%%%%% AUX %%%%%%%%%%%%%%%%%%
	@Override
	public boolean equals(Object object) {
		if(object != null && object instanceof Album) {
			Album a = (Album)object;
			if(this.getTitle().equals(a.getTitle())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Album a) {
		Integer thisYear = Integer.valueOf(this.getYear());
		Integer thatYear = Integer.valueOf(a.getYear());
		return thisYear.compareTo(thatYear);
	}
	
	@Override
	public String toString() {
		String s = "";
		s += this.getYear() + " " + this.getTitle() + "\n";
		for(int i = 0; i < songList.size(); i++) {
			s += songList.get(i).toString();
		}
		return s;
	}
	
	//%%%%%%%%%%%%%%%%%% Print Methods %%%%%%%%%%%%%%%%%%
	public void printAlbum() {
		System.out.println(year + " " + albTitle);
	}
	
	public void listAlbum() {
		this.printAlbum();
		for(int i = 0; i < numSongs; i++) {
			songList.get(i).printSong();
		}
	}
}
