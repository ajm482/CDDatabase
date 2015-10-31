package cddb;

import java.io.*;

public class cddbScanner {
	String filename;
	
	cddb database;
	
	String artist;
	Artist objArtist;
	
	int year;
	String album;
	Album objAlbum;
	
	String song;
	Song objSong;
	
	public cddbScanner(String f) {
		filename = f;
		
		database = new cddb();
	}
	
	public void buildDatabase() {
		try {
			FileReader inputFile = new FileReader(filename);
			
			BufferedReader bufferReader = new BufferedReader(inputFile);
			
			String line;
			int lineNum = 0;
			Artist pastArtist = null;
			
			while((line = bufferReader.readLine()) != null)  {
				if(line.isEmpty()) {
					lineNum = -1;
				}
				
				if(lineNum != -1) {
					if(lineNum == 0) {
						artist = line;
						
						objArtist = new Artist(artist);
						if(!database.addArtist(objArtist)) {
							objArtist = pastArtist;
						}
						//System.out.println(artist);
					} else if(lineNum == 1) {
						year = Integer.valueOf(line.substring(0, 4));
						album = line.substring(5, line.length());
						
						objAlbum = new Album(album, year);
						objArtist.addAlbum(objAlbum);
						//System.out.println(year);
						//System.out.println(album);
					} else {
						song = line.substring(1, line.length());
						
						objSong = new Song(song);
						objAlbum.addSong(objSong);
						//System.out.println(song);
					}
				}
				//System.out.println(lineNum + line);
				lineNum++;
				pastArtist = objArtist;
			}
			
			bufferReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error while reading file");
		}
		
		//Sorting Artists alphabetically and Albums by year
		database.sortArtists();
		for(int i = 0; i < database.getNumArtists(); i++) {
			database.getArtist(i).sortAlbums();
		}
	}
	
	public cddb getCddb() {
		return database;
	}
}