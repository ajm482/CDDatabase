package cddb;

public class Song implements Comparable<Song>{
	private String title;
	
	//%%%%%%%%%%%%%%%%%% Constructors %%%%%%%%%%%%%%%%%%
	public Song(String t) {
		title = t;
	}
	
	//%%%%%%%%%%%%%%%%%% Getters %%%%%%%%%%%%%%%%%%
	public String getTitle() {
		return title;
	}
	
	//%%%%%%%%%%%%%%%%%% AUX %%%%%%%%%%%%%%%%%%
	@Override
	public boolean equals(Object object) {
		if(object != null && object instanceof Song) {
			Song s = (Song)object;
			if(this.getTitle().equals(s.getTitle())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Song s) {
		return this.getTitle().compareTo(s.getTitle());
	}
	
	@Override
	public String toString() {
		String s = "-" + this.getTitle() + "\n";
		return s;
	}
	
	
	//%%%%%%%%%%%%%%%%%% Print Methods %%%%%%%%%%%%%%%%%%
	public void printSong() {
		System.out.println("-" + title);
	}
}
