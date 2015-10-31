package cddb;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class cddb {
	private int numArtists;
	private ArrayList<Artist> artistList;
	
	//%%%%%%%%%%%%%%%%%% Constructors %%%%%%%%%%%%%%%%%%
	public cddb() {
		numArtists = 0;
		artistList = new ArrayList<Artist>(numArtists);
	}
	
	public cddb(int n) {
		numArtists = n;
		artistList = new ArrayList<Artist>(numArtists);
	}
	
	//%%%%%%%%%%%%%%%%%% Mutators %%%%%%%%%%%%%%%%%%
	public boolean addArtist(Artist a) {
		if(artistList.contains(a)) {
			System.out.println(a.getName() + " already exists");
			return false;
		} else {
			artistList.add(a);
			numArtists++;
			return true;
		}
	}
	
	void sortArtists() {
		Collections.sort(artistList);
	}
		
	//%%%%%%%%%%%%%%%%%% Getters %%%%%%%%%%%%%%%%%%
	public Artist getArtist(int i) {
		if(i < artistList.size()) {
			Artist a = artistList.get(i);
			return a;
		} else {
			System.out.println("Artist not found!");
			Artist a = null;
			return a;
		}
	}
	
	public int getNumArtists() {
		return numArtists;
	}
	
	public int getArtistIndex(Artist a) {
		return artistList.indexOf(a);
	}
		
	//%%%%%%%%%%%%%%%%%% Print Methods %%%%%%%%%%%%%%%%%%
	public void listArtists() {
		for(int i = 0; i < artistList.size(); i++) {
			System.out.println(i + ")" + artistList.get(i).getName());
		}
	}
	
	public void printDatabase() {
		for(int i = 0; i < artistList.size(); i++) {
			artistList.get(i).listArtistDisc();
		}
	}
	
	//%%%%%%%%%%%%%%%%%% Helper Methods %%%%%%%%%%%%%%%%%%
	private static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch(NumberFormatException e) {
			// Won't print error message if quitting the program
			if(!s.equals("q")) {
				System.out.println("Error: Incorrect argument form");
			}
			return false;
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < artistList.size(); i++) {
			s += artistList.get(i).toString();
		}
		return s;
	}
	
	//$$$$$$$$$$$$$$$$$$ MAIN $$$$$$$$$$$$$$$$$$
	public static void main( String[] args ) {
		cddbScanner scanner = new cddbScanner("sample.db");
		scanner.buildDatabase();
		cddb database = scanner.getCddb();
	
		//database.printDatabase();
		
		Scanner userIn = new Scanner(System.in);
		String input = "";
		int artistNum = 0;
		int albumNum = 0;
		
		String[] temp = new String[1];
		temp[0] = "-l";
		args = temp;
		
		if(args.length != 1) {
			System.out.println("Please input valid number of arguments");
		} else if(args[0].equals("-l")) {
			//%%%%%%%%%%%%%%%%%%%%%%%%%% LIST %%%%%%%%%%%%%%%%%%%%%%%%%% 
			// list enumerated artists
			database.listArtists();
			
			int menuLevel = 0;
			while(!input.equals("q")) {
				// if q is input, program quits
				if(menuLevel == 0) {
					//Artist Selection Menu
					input = userIn.nextLine();
					//checking for correct input
					if(isInt(input)) {
						artistNum = Integer.parseInt(input);
						if(artistNum >= -1 && artistNum < database.getNumArtists()) {
							// if input is good, list artist's albums and advance menu
							database.getArtist(artistNum).listAlbums();
							menuLevel++;
						} else if(!input.equals("q")){
							// No error message if quitting the program
							System.out.println("Please choose listed value");
						}
					}
				} else if(menuLevel == 1) {
					//Album Selection Menu
					input = userIn.nextLine();
					// a goes to previous menu
					if(input.equals("a")) {
						menuLevel--;
						database.listArtists();
					} else if(isInt(input)) {
						//listing the selected album
						albumNum = Integer.parseInt(input);
						if(albumNum < database.getArtist(artistNum).getNumAlbums()) {
							database.getArtist(artistNum).getAlbum(albumNum).listAlbum();
							menuLevel++;
						} else if(!input.equals("q")){
							// No error message if quitting the program
							System.out.println("Please choose listed value");
						}
					}
	
				} else if(menuLevel == 2) {
					//Song List
					//any input returns to previous menu
					input = userIn.nextLine();
					if(input != null) {
						database.getArtist(artistNum).listAlbums();
						menuLevel--;
					}
				}
			}
			
		} else if(args[0].equals("-d")) {
			//%%%%%%%%%%%%%%%%%%%%%%%%%%  DELETE %%%%%%%%%%%%%%%%%%%%%%%%%% 
			// list enumerated artists
			database.listArtists();
			
			int menuLevel = 0;
			while(!input.equals("q")) {
				// if q is input, program quits
				if(menuLevel == 0) {
					//Artist Selection Menu
					input = userIn.nextLine();
					//checking for correct input
					if(isInt(input)) {
						artistNum = Integer.parseInt(input);
						if(artistNum >= -1 && artistNum < database.getNumArtists()) {
							// if input is good, list artist's albums and advance menu
							database.getArtist(artistNum).listAlbums();
							menuLevel++;
						} else if(!input.equals("q")){
							// No error message if quitting the program
							System.out.println("Please choose listed value");
						}
					}
				} else if(menuLevel == 1) {
					//Album Selection Menu
					input = userIn.nextLine();
					// a goes to previous menu
					if(input.equals("a")) {
						menuLevel--;
						database.listArtists();
					} else if(isInt(input)) {
						//check for correct album number
						albumNum = Integer.parseInt(input);
						if(albumNum < database.getArtist(artistNum).getNumAlbums()) {
							// if album exists, make sure user wants to delete
							System.out.print("Delete " + database.getArtist(artistNum).getAlbum(albumNum).getTitle() + "? (y/n)");
							input = userIn.nextLine();
							if(input.equals("y")) {
								// if deleted, program ends
								database.getArtist(artistNum).deleteAlbum(albumNum);
								break;
							} else {
								// if not deleted, option to delete a different album
								database.getArtist(artistNum).listAlbums();
							}
						} else  if(!input.equals("q")){
							// No error message if quitting the program
							System.out.println("Please choose listed value");
						}
					}
	
				}
			}
			
		} else if(args[0].equals("-a")) {
			//%%%%%%%%%%%%%%%%%%%%%%%%%% ADD %%%%%%%%%%%%%%%%%%%%%%%%%% 
			System.out.print("Artist: ");
			String artist = userIn.nextLine();
			Artist newArtist = new Artist(artist);
			database.addArtist(newArtist);
			int artistIndex = database.getArtistIndex(newArtist);
			
			System.out.print("Album Name: ");
			String album = userIn.nextLine();
			
			// Statements to check for proper year format
			boolean yearEntered = false;
			int year = -1;
			String tempYear;
			while(!yearEntered) {
				System.out.print("Release Year: ");
				tempYear = userIn.nextLine();
				if(isInt(tempYear)) {
					year = Integer.parseInt(tempYear);
					yearEntered = true;
				} else {
					System.out.println("Please input correct year");
				}
			}
			
			Album newAlbum = new Album(album, year);
			database.getArtist(artistIndex).addAlbum(newAlbum);
			int albumIndex = database.getArtist(artistIndex).getAlbumIndex(newAlbum);
			
			String answer = "y";
			int songNum = 1;
			String songName;
			while(answer.equals("y")) {
				
				System.out.print("Song " + songNum + ": ");
				songName = userIn.nextLine();
				Song newSong = new Song(songName);
				
				if(database.getArtist(artistIndex).getAlbum(albumIndex).addSong(newSong)) {
					songNum++;
				}
				
				System.out.println("Enter another song? (y/n)");
				answer = userIn.nextLine();
			}
			
			System.out.println("Album Added");
			database.getArtist(artistIndex).printArtist();
			database.getArtist(artistIndex).getAlbum(albumIndex).listAlbum();
			
			
			database.getArtist(artistIndex).sortAlbums();
			database.sortArtists();
			
		} else if(args[0].equals("-h")) {
			//%%%%%%%%%%%%%%%%%%%%%%%%%%  USAGE MESSAGE %%%%%%%%%%%%%%%%%%%%%%%%%% 
			System.out.println("-l — List album");
			System.out.println("	1) Display enumerated list of artists alphabetically");
			System.out.println("	2) Choose artist by number, or 'q' to quit");
			System.out.println("	3) Display enumerated list of albums of chosen artist by relase date");
			System.out.println("	4) Choose album by number, or return to the menu above by entering 'a'");
			System.out.println("	5) If an album is chosen, list all songs. Input returns to previous menu");
			
			System.out.println();
			System.out.println("-d -- Delete album");
			System.out.println("	1) Similar to above, displays menus for choosing and deleting an album");
			
			System.out.println();
			System.out.println("-a -- Add artist");
			System.out.println("	1) Prompts for artist, album name, release date, and track list");
			
			System.out.println();
			System.out.println("-h -- Show usage message and quit");
		} else {
			System.out.println("Please input valid argument");
		}
		userIn.close();
		
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("database.db"), "utf-8"));
			String s = database.toString();
			writer.write(s);
		} catch (IOException e) {
			System.out.println("Couldn't write file");
		} finally {
			try {
				writer.close();
			} catch(Exception e) {
				//ignore this
			}
		}
	}
}
