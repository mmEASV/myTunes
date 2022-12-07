package mytunessys.gui.controller;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.GenreManager;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.utilities.AlertNotification;
import mytunessys.gui.models.SongModel;

import java.io.Console;


/**
 * @author Bálint, Matej & Tomas
 */

public class SongController {
    // TODO: why some of them are first cap and some of them are not ?
    private AnchorPane Window;
    private AnchorPane popUpContent;
    private TextField FilePath;
    private TextField SongName;
    private TextField SongDuration;
    private TextField ArtistName;

    private ComboBox GenreOptions;
    private SongModel songModel;
    private Button SubmitButton;
    private File selectedFile;
    private int SongId;

    public SongController(AnchorPane contentWindow,SongModel model){
        Window = contentWindow;
        this.songModel = model;
    }

    public void Show(AnchorPane centerContent) throws ApplicationException {

        TableView<Song> Table = new TableView<>();
        Table.setFocusTraversable(false);

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
                                MenuItem addToPlaylist = new MenuItem("add to playlist");
                                MenuItem deleteSong = new MenuItem("delete song");
                                var menu = new ContextMenu(editItem,addToPlaylist, deleteSong);
                                editItem.setOnAction(event -> {
                                    EditSong(getTableRow().getItem());
                                    event.consume();
                                });
                                deleteSong.setOnAction(event -> {
                                    DeleteSong(getTableRow().getItem());
                                    event.consume();
                                });
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

        OptionsColumn.setCellFactory(cellFactory);
        Table.editableProperty().set(false);
        Table.getColumns().addAll(TitleColumn,GenreColumn,DurationColumn,OptionsColumn);
        Table.setFocusTraversable(false);

        centerContent.getChildren().add(Table);

        Table.setItems(songModel.getAllSongs());


    }

    public void NewSong() {
        DisplayEditPopUp(null);
        SubmitButton.setOnAction(event -> {
            var _song = new Song(2000,SongName.getText(),SongDuration.getText(),ArtistName.getText(),FilePath.getText(),(Genre) GenreOptions.getSelectionModel().getSelectedItem());

            try {
                songModel.createSong(_song);
            } catch (ApplicationException e) {
                throw new RuntimeException(e);
            }
            Window.getChildren().remove(popUpContent);
            event.consume();
        });
    }
    public void EditSong(Song song){
        DisplayEditPopUp(song);
        SubmitButton.setOnAction(event -> {
            var _song = new Song(SongId,SongName.getText(),SongDuration.getText(),ArtistName.getText(),FilePath.getText(),(Genre) GenreOptions.getSelectionModel().getSelectedItem());

            try {
                songModel.updateSong(_song);
            } catch (ApplicationException e) {
                throw new RuntimeException(e);
            }
            Window.getChildren().remove(popUpContent);
            event.consume();
        });
    }

    public void DeleteSong(Song song){
        DisplayedDeleteConfirmation(song);
    }

    private void DisplayedDeleteConfirmation(Song songToDelete){

        var confirm = AlertNotification.showAlertWindow("Delete Song", "You are about to delete this song.",
                Alert.AlertType.CONFIRMATION);
        if(confirm.get().equals(ButtonType.OK)){
            try {
                songModel.deleteSong(songToDelete);
            } catch (ApplicationException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void DisplayEditPopUp(Song content){
        popUpContent = new AnchorPane();
        popUpContent.setMinSize(400,470);
        popUpContent.getStyleClass().add("new");
        Window.getChildren().add(popUpContent);

        var FormHolder = new AnchorPane();
        FormHolder.setLayoutX(36);
        FormHolder.setLayoutY(100);
        FormHolder.setMinSize(300,250);
        FormHolder.getStyleClass().add("form");
        popUpContent.getChildren().add(FormHolder);

        var vBoxHolder = new VBox();
        vBoxHolder.setPadding(new Insets(10));
        FormHolder.getChildren().add(vBoxHolder);

        var TopRow = new HBox();
        var songLabel = new Label("Add new Song");
        var BackButton = new Button();
        BackButton.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Close.png")));
        BackButton.setAlignment(Pos.CENTER_RIGHT);
        var Space = new Region();
        HBox.setHgrow(Space, Priority.ALWAYS);
        BackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Window.getChildren().remove(popUpContent);
            }
        });
        TopRow.getChildren().addAll(songLabel,Space,BackButton);
        vBoxHolder.getChildren().add(TopRow);

        var FileRow = new HBox();
        FilePath = new TextField();
        var GetFileButton = new Button();
        GetFileButton.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Folder.png")));
        var chooseFile = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SoundFiles files (*.mp3)", "*.mp3");
        chooseFile.setSelectedExtensionFilter(extFilter);
        FileRow.getChildren().addAll(FilePath,GetFileButton);
        vBoxHolder.getChildren().add(FileRow);

        var songRow = new HBox();
        var SongNameLabel = new Label("Song");
        SongName = new TextField();
        songRow.getChildren().addAll(SongNameLabel,SongName);
        vBoxHolder.getChildren().add(songRow);

        var DurationRow = new HBox();
        var DurationLabel = new Label("Duration");
        SongDuration = new TextField();
        DurationRow.getChildren().addAll(DurationLabel,SongDuration);
        vBoxHolder.getChildren().add(DurationRow);

        var ArtistRow = new HBox();
        var ArtistNameLabel = new Label("Artist");
        ArtistName = new TextField();
        ArtistRow.getChildren().addAll(ArtistNameLabel,ArtistName);
        vBoxHolder.getChildren().add(ArtistRow);

        var GenreRow = new HBox();
        var GenreNameLabel = new Label("Genre");
        ObservableList<Genre> Items =
                null;
        try {
            Items = FXCollections.observableArrayList(new GenreManager().getAllObject());
        } catch (ApplicationException e) {
            throw new RuntimeException(e);
        }
        GenreOptions = new ComboBox(Items);
        GenreRow.getChildren().addAll(GenreNameLabel,GenreOptions);
        vBoxHolder.getChildren().add(GenreRow);

        var SubmitRow = new HBox();
        SubmitButton = new Button("Submit");
        SubmitRow.getChildren().addAll(SubmitButton);
        vBoxHolder.getChildren().add(SubmitRow);

        if(content != null){
            SongId = content.getId();
            FilePath.setText(content.getAbsolutePath());
            SongName.setText(content.getTitle());
            ArtistName.setText(content.getArtist());
            GenreOptions.setValue(content.getGenre());
        }

        GetFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedFile = chooseFile.showOpenDialog(new Stage());
                FilePath.setText(selectedFile.toURI().toString());
                var media = new Media(selectedFile.toURI().toString().replace("\\","/"));
                media.getMetadata().addListener(new MapChangeListener<String, Object>() {
                    @Override
                    public void onChanged(Change<? extends String, ? extends Object> ch) {
                        if (ch.wasAdded()) {
                            var key = ch.getKey();
                            var value = ch.getValueAdded();
                            System.out.println(key+"  "+value);
                            if (key.equals("artist"))
                                ArtistName.setText(value.toString());
                            if (key.equals("title"))
                                SongName.setText(value.toString());
                        }
                    }
                });
                SongDuration.setText(media.getDuration().toString());
                if(SongName.getText().isBlank())
                    SongName.setText(selectedFile.getName());
            }
        });

    }

    private void CreateSong()
    {

    }


}
