package mytunessys.be;

public class Song {
    private int id;
    private final String title;
    private final String duration;
    private final String artist;
    private final String absolutePath;
    private Genre genre;

    public Song(int id, String title, String duration, String artist, String absolutePath, Genre genre) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.absolutePath = absolutePath;
        this.genre = genre;
    }

    public Song(String title, String duration, String artist, String absolutePath, Genre genre) {
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.absolutePath = absolutePath;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
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

    public String getArtist() {
        return artist;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    @Override
    public String toString() {
        return this.id + " " + this.title + " " + this.genre.getName() + " " + this.duration;
    }
}
