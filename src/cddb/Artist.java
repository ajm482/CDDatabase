package cddb;

import java.util.ArrayList;
import java.util.Collections;

public class Artist implements Comparable<Artist> {
	private String name;
	private ArrayList <Album> albumList;
	private int numAlbums;
	
	//%%%%%%%%%%%%%%%%%% Constructors %%%%%%%%%%%%%%%%%%
	public Artist(String n) {
		name = n;
		int numAlbums = 0;
		albumList = new ArrayList<Album>(numAlbums);
	}
	
	public Artist(String n, int num) {
		name = n;
		int numAlbums = num;
		albumList = new ArrayList<Album>(numAlbums);
	}
	
	//%%%%%%%%%%%%%%%%%% Mutators %%%%%%%%%%%%%%%%%%
	public void addAlbum(Album a) {
		if(albumList.contains(a)) {
			System.out.println(a.getTitle() + " already exists");
		} else {
			albumList.add(a);
			numAlbums++;
		}
	}
	
	public void deleteAlbum(int i) {
		if(i < albumList.size()) {
			Album a = albumList.get(i);
			if(a != null && albumList.contains(a)) {
				String tempTitle = a.getTitle();
				albumList.remove(a);
				numAlbums--;
				System.out.println(tempTitle + " removed");
			} else {
				System.out.println("Album not found!");
			}
		} else {
			System.out.println("Album not found!");
		}
	}
	
	void sortAlbums() {
		Collections.sort(albumList);
	}
	
	//%%%%%%%%%%%%%%%%%% Getters %%%%%%%%%%%%%%%%%%
	public String getName() {
		return name;
	}
	
	public int getNumAlbums() {
		return numAlbums;
	}
	
	public Album getAlbum(int i) {
		return albumList.get(i);
	}
	
	public int getAlbumIndex(Album a) {
		return albumList.indexOf(a);
	}
	
	//%%%%%%%%%%%%%%%%%% AUX %%%%%%%%%%%%%%%%%%
	@Override
	public boolean equals(Object object) {
		if(object != null && object instanceof Artist) {
			Artist a = (Artist)object;
			if(this.getName().equals(a.getName())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int compareTo(Artist a) {
		return this.getName().compareTo(a.getName());
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < albumList.size(); i++) {
			s += this.getName() + "\n";
			s += albumList.get(i).toString() + "\n";
		}
		return s;
	}
	
	//%%%%%%%%%%%%%%%%%% Print Methods %%%%%%%%%%%%%%%%%%
	public void printArtist() {
		System.out.println(name);
	}
	
	public void listAlbums() {
		for(int i = 0; i < numAlbums; i++) {
			System.out.print(i + ") ");
			albumList.get(i).printAlbum();
		}
	}
	
	public void listArtistDisc() {
		for(int i = 0; i < numAlbums; i++) {
			this.printArtist();
			albumList.get(i).listAlbum();
			System.out.println();
		}
	}
}
