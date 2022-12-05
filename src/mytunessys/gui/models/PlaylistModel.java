package mytunessys.gui.models;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.bll.PlaylistManager;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.exceptions.ApplicationException;

/**
 * @author Bálint, Matej & Tomas
 */

public class PlaylistModel {

    private ILogicFacade logicManager;
    private ObservableList<Playlist> playlists;

    public PlaylistModel(){
        logicManager = new PlaylistManager();
    }

    public ObservableList<Playlist> getAllPlaylists() throws CustomException {
        List<Playlist> temp =  (List<Playlist>) (Object) logicManager.getAllObject();

        return playlists = FXCollections.observableArrayList(temp);

    }
}
