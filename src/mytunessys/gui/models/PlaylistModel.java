package mytunessys.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.interfaces.ILogicFacade;

import java.util.List;

public class PlaylistModel {

    private ILogicFacade logicManager;
   private  ObservableList<Playlist> playlists;

    public PlaylistModel(){
        this.logicManager = new LogicManager();
    }

    public ObservableList<Playlist> getAllPlaylists() throws ApplicationException { //waiting for backend to develop
        List<Playlist> test =  (List<Playlist>) (Object) logicManager.getAllObject();
        return playlists = FXCollections.observableArrayList(test);
    }


}
