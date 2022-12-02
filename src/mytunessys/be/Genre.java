package mytunessys.be;

public class Genre {
    private int id;
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //TODO delete and add this code into backend branch
    @Override
    public String toString(){
        return name;
    }
}
