package mytunessys.gui.controller;


import java.io.File;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseButton;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.GenreManager;
import mytunessys.bll.exceptions.ApplicationException;

import mytunessys.bll.utilities.MusicPlayer;

import mytunessys.bll.utilities.AlertNotification;
import mytunessys.gui.models.PlaylistModel;

import mytunessys.gui.models.SongModel;
/**
 * @author Bálint, Matej & Tomas
 */


public class SongController {
    private AnchorPane window;

    private AnchorPane popUpContent;
    private TableView<Song> table;
    private TextField filePath;
    private TextField songName;
    private TextField songDuration;
    private TextField artistName;
    private ComboBox genreOptions;
    private SongModel songModel;
    private PlaylistModel playlistModel;
    private MouseEvent mouseEventType;
    private Button submitButton;
    private File selectedFile;

    private int songId;
    private BaseController baseController;



    public SongController(AnchorPane contentWindow,SongModel model,BaseController baseController, PlaylistModel playlistModel){
        window = contentWindow;
        this.playlistModel = playlistModel;
        this.songModel = model;
        this.baseController = baseController;
    }

    public TableView<Song> getTable(){
        return table;
    }
    public void fillTable() throws ApplicationException {
        table.setItems(songModel.getAllSongs());
    }
    public void show(AnchorPane centerContent) throws ApplicationException {




        table = new TableView<>();
        table.setFocusTraversable(false);


        TableColumn<Song, String> TitleColumn = new TableColumn<>();
        TitleColumn.setText("Title");
        TitleColumn.prefWidthProperty().set(203);
        TitleColumn.setResizable(false);
        TitleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));

        TableColumn<Song, String> GenreColumn = new TableColumn<>();
        GenreColumn.setText("Genre");
        GenreColumn.prefWidthProperty().set(47);
        GenreColumn.setResizable(false);
        GenreColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));


        TableColumn<Song, String> DurationColumn = new TableColumn<>();
        DurationColumn.setText("Duration");
        DurationColumn.prefWidthProperty().set(47);
        DurationColumn.setResizable(false);
        DurationColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("duration"));

        TableColumn<Song, String> OptionsColumn = new TableColumn<>();
        OptionsColumn.prefWidthProperty().set(47);
        OptionsColumn.setResizable(false);


        List<Playlist> allPlaylists = playlistModel.getAllPlaylists();

        Callback<TableColumn<Song, String>, TableCell<Song, String>> cellFactory
            = //
            new Callback<TableColumn<Song, String>, TableCell<Song, String>>() {
                @Override
                public TableCell call(final TableColumn<Song, String> param) {
                    final TableCell<Song, String> cell = new TableCell<Song, String>() {

                        final Button btn = new Button("...");
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                MenuItem editItem = new MenuItem("edit song");
                                Menu addToPlaylist = new Menu("add to playlist");
                                MenuItem deleteSong = new MenuItem("delete song");

                                List<MenuItem> playlistMenuItems = new ArrayList<>();
                                allPlaylists.forEach((x) -> playlistMenuItems.add(new MenuItem(x.getPlaylistName())));
                                addToPlaylist.getItems().addAll(playlistMenuItems);
                                // creating a separator
                                SeparatorMenuItem sep = new SeparatorMenuItem();
                                // adding all items to context menu
                                var menu = new ContextMenu(editItem,addToPlaylist, sep,deleteSong);
                                editItem.setOnAction(event -> {
                                    editSong(getTableRow().getItem());
                                    event.consume();
                                });
                                deleteSong.setOnAction(event -> {
                                    deleteSong(getTableRow().getItem());
                                    event.consume();
                                });
                                playlistMenuItems.forEach(x -> x.setOnAction(event -> {
                                    Playlist currentPlaylist = allPlaylists.stream()
                                            .filter(y -> y.getPlaylistName()
                                                    .equals(x.getText()))
                                            .findFirst()
                                            .get();
                                    addSongToPlaylist(getTableRow().getItem(),currentPlaylist);
                                }));
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

        table.setRowFactory(new Callback<TableView<Song>, TableRow<Song>>() {
            @Override
            public TableRow<Song> call(TableView<Song> param) {

                TableRow<Song> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getClickCount() == 2 && (!row.isEmpty())){
                            baseController.updatePlayerUI(param);
                            baseController.playSong(param);
                        }
                    }
                });
                return row;
            }
        });

        OptionsColumn.setCellFactory(cellFactory);

        table.editableProperty().set(false);
        table.getColumns().addAll(TitleColumn,GenreColumn,DurationColumn,OptionsColumn);
        table.setFocusTraversable(false);

        centerContent.getChildren().add(table);
        fillTable();
    }

    /**
     * void method that sets on action submit button that will fire event to validate all user inputs and if true then it will
     * try to create new song from song model
     * implements alert notifications of the state that the method of creating song is
     */
    public void newSong() {
        displayEditPopUp(null);
        submitButton.setOnAction(event -> {
            if(validation()){
            var _song = new Song(songName.getText().trim(), songDuration.getText().trim(), artistName.getText().trim(), filePath.getText().trim(),(Genre) genreOptions.getSelectionModel().getSelectedItem());
                try {
                    songModel.createSong(_song);
                    fillTable();
                } catch (ApplicationException e) {
                    AlertNotification.showAlertWindow(e.getMessage() + " " + e.getCause(), Alert.AlertType.WARNING);
                }
            window.getChildren().remove(popUpContent);
            AlertNotification.showAlertWindow("Song with name: " + _song.getTitle() + " was created !", Alert.AlertType.INFORMATION);
            event.consume();
            }else {
                AlertNotification.showAlertWindow("Please make sure all the field are filled", Alert.AlertType.WARNING);
            }
        });
    }

    /**
     * Very simple validation method that check whenever all the fields are filled up or not
     * @return false if any of the field are null or empty where required
     */
    private boolean validation() {
        if(songName == null ||
                songName.getText().isEmpty()){
            return false;
        }
        if(songDuration.getText() == null ||
                songDuration.getText().isEmpty()){
            return false;
        }
        if(genreOptions.getSelectionModel().getSelectedItem() == null ||
                genreOptions.getSelectionModel() == null ||
                genreOptions == null){
            return false;
        }
        if(filePath.getText() == null ||
                filePath.getText().isEmpty()){
            return false;
        }
        if(artistName.getText() == null ||
                artistName.getText().isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * void method that sets on action submit button that will fire event to validate all user inputs and if true then it will
     * try to create new song from song model
     * implements alert notifications of the state that the method of creating song is
     */
    public void editSong(Song song){
        displayEditPopUp(song);
        submitButton.setOnAction(event -> {
            if(validation()){
                var _song = new Song(song.getId(), songName.getText(), songDuration.getText(), artistName.getText(), filePath.getText(),(Genre) genreOptions.getSelectionModel().getSelectedItem());
                try {
                    songModel.updateSong(_song);
                    fillTable();
                } catch (ApplicationException e) {
                    AlertNotification.showAlertWindow(e.getMessage() + " " + e.getCause(), Alert.AlertType.WARNING);
                }
                AlertNotification.showAlertWindow("Song with name: " + _song.getTitle() + " was successfully updated!", Alert.AlertType.INFORMATION);
                window.getChildren().remove(popUpContent);
                event.consume();
            }else {
                AlertNotification.showAlertWindow("Please make sure all the field are filled", Alert.AlertType.WARNING);
            }
        });
    }

    private void deleteSong(Song song){
        displayedDeleteConfirmation(song);
    }
    private void displayedDeleteConfirmation(Song songToDelete){
        var confirm = AlertNotification.showAlertWindow("You are about to delete this song.", Alert.AlertType.CONFIRMATION);
        var result = false;
        if(confirm.get().equals(ButtonType.OK)){
            try {
                result = songModel.deleteSong(songToDelete);
                if(result){
                    fillTable();
                    AlertNotification.showAlertWindow("Song with id: " + songToDelete.getId() + " successfully deleted ", Alert.AlertType.INFORMATION);
                } else {
                    AlertNotification.showAlertWindow("Could not delete song with id: " + songToDelete.getId(), Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                AlertNotification.showAlertWindow(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void displayEditPopUp(Song content){
        popUpContent = new AnchorPane();
        popUpContent.setMinSize(400,470);
        popUpContent.getStyleClass().add("new");
        window.getChildren().add(popUpContent);

        var formHolder = new AnchorPane();
        formHolder.setLayoutX(36);
        formHolder.setLayoutY(100);
        formHolder.setMinSize(300,250);
        formHolder.getStyleClass().add("form");
        popUpContent.getChildren().add(formHolder);

        var vBoxHolder = new VBox();
        vBoxHolder.setPadding(new Insets(10));
        formHolder.getChildren().add(vBoxHolder);

        var topRow = new HBox();
        var songLabel = new Label("Add new Song");
        var backButton = new Button();
        backButton.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Close.png")));
        backButton.setAlignment(Pos.CENTER_RIGHT);
        var Space = new Region();
        HBox.setHgrow(Space, Priority.ALWAYS);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.getChildren().remove(popUpContent);
            }
        });
        topRow.getChildren().addAll(songLabel,Space,backButton);
        vBoxHolder.getChildren().add(topRow);

        var fileRow = new HBox();
        filePath = new TextField();
        var getFileButton = new Button();
        getFileButton.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Folder.png")));
        var chooseFile = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SoundFiles files (*.mp3)", "*.mp3");
        chooseFile.setSelectedExtensionFilter(extFilter);
        fileRow.getChildren().addAll(filePath,getFileButton);
        vBoxHolder.getChildren().add(fileRow);

        var songRow = new HBox();
        var songNameLabel = new Label("Song");
        songName = new TextField();
        songRow.getChildren().addAll(songNameLabel, songName);
        vBoxHolder.getChildren().add(songRow);

        var durationRow = new HBox();
        var durationLabel = new Label("Duration");
        songDuration = new TextField();
        durationRow.getChildren().addAll(durationLabel, songDuration);
        vBoxHolder.getChildren().add(durationRow);

        var artistRow = new HBox();
        var artistNameLabel = new Label("Artist");
        artistName = new TextField();
        artistRow.getChildren().addAll(artistNameLabel, artistName);
        vBoxHolder.getChildren().add(artistRow);

        var genreRow = new HBox();
        var genreNameLabel = new Label("Genre");
        ObservableList<Genre> Items =
                null;
        try {
            Items = FXCollections.observableArrayList(new GenreManager().getAllObject());
        } catch (ApplicationException e) {
            throw new RuntimeException(e);
        }
        genreOptions = new ComboBox(Items);
        genreRow.getChildren().addAll(genreNameLabel, genreOptions);
        vBoxHolder.getChildren().add(genreRow);

        var submitRow = new HBox();
        submitButton = new Button("Add Song...");
        submitRow.getChildren().addAll(submitButton);
        vBoxHolder.getChildren().add(submitRow);

        if(content != null){
            songId = content.getId();
            filePath.setText(content.getAbsolutePath());
            songName.setText(content.getTitle());
            songDuration.setText(content.getDuration());
            artistName.setText(content.getArtist());
            genreOptions.setValue(content.getGenre());
        }

        getFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedFile = chooseFile.showOpenDialog(new Stage());
                filePath.setText(selectedFile.toURI().toString());
                var media = new Media(selectedFile.toURI().toString().replace("\\","/"));
                media.getMetadata().addListener(new MapChangeListener<String, Object>() {
                    @Override
                    public void onChanged(Change<? extends String, ? extends Object> ch) {
                        if (ch.wasAdded()) {
                            var key = ch.getKey();
                            var value = ch.getValueAdded();
                            System.out.println(key+"  "+value);
                            if (key.equals("artist"))
                                artistName.setText(value.toString());
                            if (key.equals("title"))
                                songName.setText(value.toString());
                        }
                    }
                });
                songDuration.setText(media.getDuration().toString());
                if(songName.getText().isBlank())
                    songName.setText(selectedFile.getName());
            }
        });

    }


    private void addSongToPlaylist(Song songToBeAdded,Playlist playlistToBeFilled) {
        boolean finalResult;
        try {
            List<Song> fetchedPlaylist =  playlistModel.getPlaylistById(playlistToBeFilled);
            if(findSongInPlaylist(fetchedPlaylist,songToBeAdded.getTitle())){
                finalResult = false;
            } else {
                finalResult = playlistModel.addSongToPlaylist(songToBeAdded,playlistToBeFilled);
            }
        } catch (ApplicationException e) {
            throw new RuntimeException(e);
        }
        if(finalResult){
            AlertNotification.showAlertWindow("Successfully added song with id " + playlistToBeFilled.getId() + " to playlist " + playlistToBeFilled.getPlaylistName(), Alert.AlertType.INFORMATION);
        }else {
            AlertNotification.showAlertWindow("Could not add song " + songToBeAdded.getTitle() + " to playlist with id :" + playlistToBeFilled.getId(), Alert.AlertType.WARNING);
        }
    }
    private boolean findSongInPlaylist(List<Song> fetchedPlaylist,String songTitle){
        return fetchedPlaylist.stream()
                .anyMatch(row -> row.getTitle()
                        .contains(songTitle));
    }
}


