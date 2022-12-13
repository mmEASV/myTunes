package mytunessys.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Genre;
import mytunessys.bll.GenreManager;
import mytunessys.bll.SongManager;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.exceptions.FactoryException;

import java.util.List;

public class GenreModel {
    private ObservableList<Genre> genres;
    private GenreManager genreManager;

    public GenreModel() throws ApplicationException {
        genreManager = new GenreManager();
    }

    public ObservableList<Genre> getAllGenres() throws Exception {
        List<Genre> temp =  genreManager.getAllObject();
        return genres = FXCollections.observableArrayList(temp);
    }
}
