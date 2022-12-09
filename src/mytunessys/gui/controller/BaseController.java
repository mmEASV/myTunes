package mytunessys.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.utilities.AlertNotification;
import mytunessys.gui.models.PlaylistModel;
import mytunessys.gui.models.SongModel;

/**
 * @author Bálint, Matej & Tomas
 */

public class BaseController implements Initializable {

    //region FXML
    //TODO QUESTION: SHOULD BE ANNOTATED AS FXML ?

    public AnchorPane top;
    public AnchorPane contentWindow;
    @FXML
    public Button btnUp;
    @FXML
    public Button btnDown;
    // ----
    @FXML
    private TableView<Song> tbvContentTable;
    @FXML
    private TableColumn<Song, String> tbvCol1;
    @FXML
    private TableColumn<Song, String> tbvCol2;

    @FXML
    private Label lblNameOfSong;
    @FXML
    private Label lblArtist;
    @FXML
    private Button btnPrevious;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnSongs;
    @FXML
    private Button btnPlaylists;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblCurrentLocation;
    @FXML
    private TextField txfSearchBar;
    @FXML
    private AnchorPane centerContent;
    //endregion

    private SongModel songModel;
    private PlaylistModel playlistModel;
    private SongController songCont;
    private PlaylistController playlistCont;
    private SongOnPlaylistController songOnPlaylistCont;


    private void updateCurrentSongNameLabel(){
        //TODO display the song that is played currently on lblNameOfSong
        //update

    }

    private void updateArtistLabel(){
        //TODO display the artist for the song on lblArtist
    }


    @FXML
    private void switchToSongInterface(ActionEvent actionEvent) throws Exception {
        ShowInterface(actionEvent,"Songs");
        showSearchBar();
        btnSongs.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Songs.png")));
        btnPlaylists.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Playlists.png")));
        songCont.show(centerContent);

    }
    @FXML
    private void switchToPlaylistInterface(ActionEvent actionEvent) throws Exception{
        ShowInterface(actionEvent,"Playlists");
        showSearchBar();
        btnSongs.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Songs2.png")));
        btnPlaylists.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Playlists2.png")));
        playlistCont.show(centerContent);
    }

    public void switchToSongOnPlaylistInterface(ActionEvent actionEvent,Playlist playlist) throws Exception {
        ShowInterface(actionEvent,"Songs in Playlist");//implement playlist.getName() smart display
        hideSearchBar();
        songOnPlaylistCont.Show(centerContent,playlist);
    }

    public void CleanCenterContent(){
        centerContent.getChildren().removeAll(centerContent.getChildren());
    }

    public void ShowInterface(ActionEvent actionEvent,String name) {
        CleanCenterContent();
        lblCurrentLocation.setText(name);
    }

    public void showSearchBar(){
        txfSearchBar.setVisible(true);
        btnDown.setVisible(false);
        btnUp.setVisible(false);
        btnAdd.setVisible(true);
    }

    public void hideSearchBar(){
        txfSearchBar.setVisible(false);
        btnDown.setVisible(true);
        btnUp.setVisible(true);
        btnAdd.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.songModel = new SongModel();
            this.playlistModel = new PlaylistModel();
            songCont = new SongController(contentWindow,songModel,playlistModel);
            playlistCont = new PlaylistController(contentWindow,playlistModel,this);
            songOnPlaylistCont = new SongOnPlaylistController(contentWindow,playlistModel);
            switchToSongInterface(new ActionEvent());
        } catch (Exception e) {
            AlertNotification.showAlertWindow(e.getMessage(), Alert.AlertType.ERROR);
        }
        btnGoBack.setVisible(false);

    }

    private void setSearch() {
        txfSearchBar.textProperty().addListener((obs,oldValue,newValue)-> {
            try{
                if (lblCurrentLocation.getText().equals("Playlists")) { // not so type safe but works for now
                    playlistModel.searchPlaylist(newValue);
                } else {
                    songModel.searchSongs(newValue);
                }
            }catch(Exception e){
                throw new RuntimeException();
            }
        } );
    }

    public void NewItem(ActionEvent actionEvent) {
        if(lblCurrentLocation.getText().equals("Songs"))
            songCont.newSong();
        else
            playlistCont.newPlaylist();
    }

    public void btnUpAction(ActionEvent actionEvent) {
        songOnPlaylistCont.moveUp();
    }

    public void btnDownAction(ActionEvent actionEvent) {
        songOnPlaylistCont.moveDown();
    }
}
