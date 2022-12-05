package mytunessys.gui.models;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.gui.controller.SongController;

/**
 * @author Bálint, Matej & Tomas
 */

public class SongModel {

    private ILogicFacade logicManager;
    private ObservableList<Song> songs;

    public SongModel(){
        logicManager = new LogicManager();
    }

    public ObservableList<Song> getAllSongs() throws ApplicationException {
        List<Song> temp =  (List<Song>) (Object) logicManager.getAllObject();

        return songs = FXCollections.observableArrayList(temp);
    }

}
