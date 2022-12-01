package mytunessys.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.gui.models.PlaylistModel;
import mytunessys.gui.models.SongModel;

/**
 * @author Bálint & Matej
 */

public class BaseController implements Initializable {


    public AnchorPane top;
    public AnchorPane contentWindow;
    @FXML
    private TableView<Song> tbvContentTable;
    @FXML
    private TableColumn<Song, String> tbvColTitle;
    @FXML
    private TableColumn<Song, String> tbvColGenre;

    @FXML
    private TableColumn<Song,String> tbvColOption;
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

    private SongModel songModel = new SongModel();
    private PlaylistModel playlistModel = new PlaylistModel();
    private SongController songCont;
    private PlaylistController playlistCont;

    private void updateCurrentSongNameLabel(){
        //TODO display the song that is played currently on lblNameOfSong
        //update

    }

    private void updateArtistLabel(){
        //TODO display the artist for the song on lblArtist
    }

    private void goBackToMainMenu(){
        //TODO go back to the main menu with btnPrevious
    }

    @FXML
    private void switchToSongInterface(ActionEvent actionEvent){
        ShowInterface(actionEvent,"Songs");
        btnSongs.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Songs.png")));
        btnPlaylists.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Playlists.png")));
        songCont.Show(centerContent);



        //TODO switch the ui to song with btnSongs
        //change list to display songs
        MenuItem menuItem = new MenuItem("here goes nothing");



        // tbvColTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        // tbvColOption.setCellFactory(cellData -> new TableCell<>());
        // tbvContentTable.setItems();



        // change the btnGoBack



        // change the btnAdd



    }
    @FXML
    private void switchToPlaylistInterface(ActionEvent actionEvent){
        ShowInterface(actionEvent,"Playlists");
        //btnSongs.setBackground(new Background(new BackgroundImage("mytunessys/gui/icons/Songs2.png")));
        btnSongs.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Songs2.png")));
        btnPlaylists.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Playlists2.png")));
        playlistCont.Show(centerContent);
        //TODO switch the ui to playlist with btnPlaylists


    }

    public void CleanCenterContent(){
        centerContent.getChildren().removeAll(centerContent.getChildren());
    }

    public void ShowInterface(ActionEvent actionEvent,String name) {
        CleanCenterContent();
        lblCurrentLocation.setText(name);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songCont = new SongController();
        playlistCont = new PlaylistController();
        btnGoBack.setVisible(false);
        switchToSongInterface(new ActionEvent());

//        btnGoBack.setVisible(false);
//        lblCurrentLocation.setText("Songs");
//        //clean up code smell
//        //tbvCol1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
//        tbvContentTable.setItems(songModel.getAllSongs());

/*
        Callback<TableColumn<Song, String>, TableCell<Song, String>> cellFactory
                = //
                new Callback<TableColumn<Song, String>, TableCell<Song, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Song, String> param) {
                        final TableCell<Song, String> cell = new TableCell<Song, String>() {

                            final Button btn = new Button("...");
                            final ContextMenu menu = new ContextMenu(new MenuItem("edit song"),new MenuItem("add to playlist"));
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        menu.show(btn, Side.BOTTOM,0,0);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        tbvColOption.setCellFactory(cellFactory);
        tbvContentTable.setItems(songModel.getAllSongs());
        tbvContentTable.getColumns().addAll(tbvColOption);

        */
    }

    public void NewItem(ActionEvent actionEvent) {
        if(lblCurrentLocation.getText().equals("Songs"))
            songCont.NewSong(contentWindow);
        else
            playlistCont.NewPlaylist(contentWindow);
    }
}
