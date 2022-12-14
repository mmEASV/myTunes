package mytunessys.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.bll.GenreManager;
import mytunessys.bll.SongManager;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.exceptions.FactoryException;
import mytunessys.bll.interfaces.ILogicFacade;

import java.util.List;

public class GenreModel {
    private ObservableList<Genre> genres;
    private final ILogicFacade<Genre> genreManager;

    public GenreModel() throws ApplicationException {
        genreManager = new GenreManager();
    }

    public ObservableList<Genre> getAllGenres() throws Exception {
        List<Genre> temp =  genreManager.getAllObject();
        return genres = FXCollections.observableArrayList(temp);
    }
}
