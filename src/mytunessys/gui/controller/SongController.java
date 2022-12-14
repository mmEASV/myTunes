package mytunessys.gui.controller;

import java.io.File;

import java.net.URL;
import java.util.*;

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
import javafx.scene.media.MediaPlayer;
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
import mytunessys.gui.models.GenreModel;
import mytunessys.gui.models.PlaylistModel;

import mytunessys.gui.models.SongModel;

/**
 * @author Bálint, Matej & Tomas
 */

public class SongController {
    private final AnchorPane window;
    private AnchorPane popUpContent;

    private TableView<Song> table;
    private TextField filePath,
        songName,
        songDuration,
        artistName;

    private ComboBox genreOptions;
    private final SongModel songModel;
    private final PlaylistModel playlistModel;
    private MouseEvent mouseEventType;
    private Button submitButton;
    private File selectedFile;

    private final BaseController baseController;

    public SongController(AnchorPane contentWindow, SongModel model, BaseController baseController, PlaylistModel playlistModel) {
        window = contentWindow;
        this.playlistModel = playlistModel;
        this.songModel = model;
        this.baseController = baseController;
    }

    public TableView<Song> getTable() {
        return table;
    }

    public void fillTable() throws Exception {
        table.setItems(songModel.getAllSongs());
    }

    public void show(AnchorPane centerContent) throws Exception {
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
        OptionsColumn.prefWidthProperty().set(40);
        OptionsColumn.setResizable(false);

        List<Playlist> allPlaylists = playlistModel.getAllPlaylists();
        ImageView addToPlaylistImage = new ImageView("mytunessys/gui/icons/Add.png");
        ImageView editImage = new ImageView("mytunessys/gui/icons/Edit.png");
        ImageView removeImage = new ImageView("mytunessys/gui/icons/Remove.png");

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
                                Menu addToPlaylist = new Menu("Add to playlist", addToPlaylistImage);
                                MenuItem editItem = new MenuItem("edit song", editImage);
                                MenuItem deleteSong = new MenuItem("delete song", removeImage);

                                List<MenuItem> playlistMenuItems = new ArrayList<>();
                                allPlaylists.forEach((x) -> playlistMenuItems.add(new MenuItem(x.getPlaylistName())));
                                addToPlaylist.getItems().addAll(playlistMenuItems);
                                // adding all items to context menu
                                var menu = new ContextMenu(editItem, addToPlaylist, deleteSong);
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
                                    addSongToPlaylist(getTableRow().getItem(), currentPlaylist);
                                }));
                                btn.setOnAction(event -> {
                                    menu.show(btn, Side.BOTTOM, -95, 0);
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
                        if (event.getClickCount() == 2 && (!row.isEmpty())) {
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
        table.getColumns().addAll(TitleColumn, GenreColumn, DurationColumn, OptionsColumn);
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
            if (validation()) {
                var _song = new Song(
                    songName.getText().trim(),
                    songDuration.getText().trim(),
                    artistName.getText().trim(),
                    filePath.getText().trim(), (Genre)
                    genreOptions.getSelectionModel().getSelectedItem()
                );
                try {
                    songModel.createSong(_song);
                    fillTable();
                } catch (Exception e) {
                    AlertNotification.showAlertWindow(e.getMessage() + " " + e.getCause(), Alert.AlertType.WARNING);
                }
                AlertNotification.showAlertWindow("Song with name: " + _song.getTitle() + " was created !", Alert.AlertType.INFORMATION);
                window.getChildren().remove(popUpContent);
                event.consume();
            } else {
                AlertNotification.showAlertWindow("Please make sure all the field are filled", Alert.AlertType.WARNING);
            }
        });
    }

    /**
     * void method that sets on action submit button that will fire event to validate all user inputs and if true then it will
     * try to create new song from song model
     * implements alert notifications of the state that the method of creating song is
     */
    public void editSong(Song song) {
        displayEditPopUp(song);
        submitButton.setOnAction(event -> {
            if (validation()) {
                var _song = new Song(
                    song.getId(),
                    songName.getText().trim(),
                    songDuration.getText().trim(),
                    artistName.getText().trim(),
                    filePath.getText().trim(),
                    (Genre) genreOptions.getSelectionModel().getSelectedItem()
                );
                try {
                    songModel.updateSong(_song);
                    fillTable();
                } catch (Exception e) {
                    AlertNotification.showAlertWindow(e.getMessage() + " " + e.getCause(), Alert.AlertType.WARNING);
                }
                AlertNotification.showAlertWindow("Song with name: " + _song.getTitle() + " was successfully updated!", Alert.AlertType.INFORMATION);
                window.getChildren().remove(popUpContent);
                event.consume();
            } else {
                AlertNotification.showAlertWindow("Please make sure all the field are filled", Alert.AlertType.WARNING);
            }
        });
    }

    /**
     * Simple validation method that check whenever all the fields are filled up or not
     *
     * @return false if any of the field are null or empty where required and not filled
     */
    private boolean validation() {
        if (songName == null ||
            songName.getText().isEmpty()) {
            return false;
        }
        if (songDuration.getText() == null ||
            songDuration.getText().isEmpty()) {
            return false;
        }
        if (genreOptions.getSelectionModel().getSelectedItem() == null ||
            genreOptions.getSelectionModel() == null ||
            genreOptions == null) {
            return false;
        }
        if (filePath.getText() == null ||
            filePath.getText().isEmpty()) {
            return false;
        }
        return artistName.getText() != null &&
            !artistName.getText().isEmpty();
    }

    public void deleteSong(Song song) {
        displayedDeleteConfirmation(song);
    }

    /**
     * method that takes control of implementing
     * delete functionality for selected song that needs to be deleted
     * displays custom alert notification if result boolean fails to delete song in the database
     *
     * @param songToDelete object that must contain id in order to be deleted
     */
    private void displayedDeleteConfirmation(Song songToDelete) {
        var confirm = AlertNotification.showAlertWindow("You are about to delete this song.", Alert.AlertType.CONFIRMATION);
        var result = false;
        if (confirm.get().equals(ButtonType.OK)) {
            try {
                result = songModel.deleteSong(songToDelete);
                if (result) {
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

    /**
     * Creates a new popup window that allows the user to edit a song.
     *
     * @param content Must be a Song.
     */
    private void displayEditPopUp(Song content) {
        popUpContent = new AnchorPane();
        popUpContent.setMinSize(400, 470);
        popUpContent.getStyleClass().add("new");
        window.getChildren().add(popUpContent);

        var formHolder = new AnchorPane();
        formHolder.setLayoutX(36);
        formHolder.setLayoutY(100);
        formHolder.setMinSize(300, 250);
        formHolder.getStyleClass().add("form");
        popUpContent.getChildren().add(formHolder);

        var vBoxHolder = new VBox();
        vBoxHolder.setPadding(new Insets(10));
        formHolder.getChildren().add(vBoxHolder);

        var topRow = new HBox();
        topRow.setMinWidth(280);
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
        topRow.getChildren().addAll(songLabel, Space, backButton);
        vBoxHolder.getChildren().add(topRow);

        var fileRow = new HBox();
        VBox.setMargin(fileRow, new Insets(0, 0, 4, 0));
        filePath = new TextField();
        filePath.getStyleClass().add("form-input");
        var getFileButton = new Button();
        HBox.setMargin(getFileButton, new Insets(2, 0, 0, -40));
        getFileButton.setStyle("-fx-start-margin: -20");
        getFileButton.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Folder.png")));
        var chooseFile = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SoundFiles files (*.mp3,*.wav)", "*.mp3", "*.wav");
        chooseFile.getExtensionFilters().add(extFilter);
        fileRow.getChildren().addAll(filePath, getFileButton);
        vBoxHolder.getChildren().add(fileRow);

        var songRow = new AnchorPane();
        VBox.setMargin(songRow, new Insets(0, 0, 4, 0));
        var songNameLabel = new Label("Song");
        songNameLabel.getStyleClass().add("form-label");
        songName = new TextField();
        songName.getStyleClass().add("form-input");
        songRow.getChildren().addAll(songName, songNameLabel);
        vBoxHolder.getChildren().add(songRow);

        var durationRow = new AnchorPane();
        VBox.setMargin(durationRow, new Insets(0, 0, 4, 0));
        var durationLabel = new Label("Duration");
        durationLabel.getStyleClass().add("form-label");
        songDuration = new TextField();
        songDuration.getStyleClass().add("form-input");
        durationRow.getChildren().addAll(songDuration, durationLabel);
        vBoxHolder.getChildren().add(durationRow);

        var artistRow = new AnchorPane();
        VBox.setMargin(artistRow, new Insets(0, 0, 4, 0));
        var artistNameLabel = new Label("Artist");
        artistNameLabel.getStyleClass().add("form-label");
        artistName = new TextField();
        artistName.getStyleClass().add("form-input");
        artistRow.getChildren().addAll(artistName, artistNameLabel);
        vBoxHolder.getChildren().add(artistRow);

        var genreRow = new AnchorPane();
        VBox.setMargin(genreRow, new Insets(0, 0, 4, 0));
        var genreNameLabel = new Label("Genre");
        genreNameLabel.getStyleClass().add("form-label");
        ObservableList<Genre> Items =
            null;
        try {
            Items = FXCollections.observableArrayList(new GenreModel().getAllGenres());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        genreOptions = new ComboBox(Items);
        genreOptions.getStyleClass().add("form-input");
        genreRow.getChildren().addAll(genreOptions, genreNameLabel);
        vBoxHolder.getChildren().add(genreRow);

        var submitRow = new HBox();
        var leftSpace = new Region();
        var rightSpace = new Region();
        HBox.setHgrow(leftSpace, Priority.ALWAYS);
        HBox.setHgrow(rightSpace, Priority.ALWAYS);
        submitButton = new Button("Submit");
        submitButton.getStyleClass().add("submit-Button");
        submitRow.getChildren().addAll(leftSpace, submitButton, rightSpace);
        vBoxHolder.getChildren().add(submitRow);

        if (content != null) {
            int songId = content.getId();
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
                if (selectedFile != null) {
                    filePath.setText(selectedFile.toURI().toString());
                    var media = new Media(selectedFile.toURI().toString().replace("\\", "/"));
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setOnReady(new Runnable() {
                        @Override
                        public void run() {
                            var _songDuration = (int) media.getDuration().toSeconds();
                            songDuration.setText(_songDuration / 60 + ":" + _songDuration % 60);
                            for (Map.Entry<String, Object> entry : media.getMetadata().entrySet()) {
                                if (entry.getKey().equals("artist"))
                                    artistName.setText(entry.getValue().toString());
                                if (entry.getKey().equals("title"))
                                    songName.setText(entry.getValue().toString());
                            }
                            if (songName.getText().isBlank())
                                songName.setText(selectedFile.getName());
                        }
                    });
                }
            }
        });

    }

    /**
     * void method that takes song that will be added and playlist that song goes into
     * try to find if song already exist in the playlist and if not will be added to the database
     *
     * @param songToBeAdded      object that needs to have title and its id in order to work
     * @param playlistToBeFilled object of the playlist that song will be inserted into
     */

    private void addSongToPlaylist(Song songToBeAdded, Playlist playlistToBeFilled) {
        boolean finalResult;

        try {
            List<Song> fetchedPlaylist = playlistModel.getPlaylistById(playlistToBeFilled);
            if (findSongInPlaylist(fetchedPlaylist, songToBeAdded.getTitle())) {
                finalResult = false;
            } else {
                finalResult = playlistModel.addSongToPlaylist(songToBeAdded, playlistToBeFilled);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (finalResult) {
            AlertNotification.showAlertWindow(
                "Successfully added song with id " + playlistToBeFilled.getId() + " to playlist " + playlistToBeFilled.getPlaylistName(),
                Alert.AlertType.INFORMATION);
        } else {
            AlertNotification.showAlertWindow("Could not add song " + songToBeAdded.getTitle() + " to playlist with id :" + playlistToBeFilled.getId(),
                Alert.AlertType.WARNING);
        }
    }

    /**
     * iterates through the list of song fetched from database and find its by its title
     *
     * @param fetchedPlaylist to be iterated
     * @param songTitle       that needs to be find in the list
     * @return false if not found
     */
    private boolean findSongInPlaylist(List<Song> fetchedPlaylist, String songTitle) {
        return fetchedPlaylist.stream()
            .anyMatch(row -> row.getTitle()
                .contains(songTitle));
    }

}


