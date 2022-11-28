package mytunessys.be;

public class Song {
    private int id;
    private String title;
    private Genre genre;
    private String duration;
    private String artist;
    private String absolutePath;


    public Song(int id, String title, Genre genre, String duration, String artist, String absolutePath) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.artist = artist;
        this.absolutePath = absolutePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String toString(){
        return this.id + " " + this.title + " " + this.genre.getName() + " " + this.duration;
    }
}
