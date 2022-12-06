package mytunessys.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.gui.models.SongModel;
import javafx.stage.Window;


/**
 * @author Bálint, Matej & Tomas
 */
public class SongController{

    private AnchorPane popUpContent;
    private TextField FilePath;
    private TextField SongName;
    private TextField ArtistName;

    private ComboBox GenreOptions;
    SongModel songModel = new SongModel();

    public void Show(AnchorPane centerContent) throws ApplicationException {



        TableView<Song> Table = new TableView<>();
        Table.setFocusTraversable(false);

        TableColumn<Song, String> TitleColumn = new TableColumn<>();
        TitleColumn.setText("Title");
        TitleColumn.prefWidthProperty().set(203);
        TitleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));

        TableColumn<Song, String> GenreColumn = new TableColumn<>();
        GenreColumn.setText("Genre");
        GenreColumn.prefWidthProperty().set(47);
        GenreColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));


        TableColumn<Song, String> DurationColumn = new TableColumn<>();
        DurationColumn.setText("Duration");
        DurationColumn.prefWidthProperty().set(47);
        DurationColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("duration"));



        TableColumn<Song, String> OptionsColumn = new TableColumn<>();
        OptionsColumn.prefWidthProperty().set(47);

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

        OptionsColumn.setCellFactory(cellFactory);
        Table.editableProperty().set(false);
        Table.getColumns().addAll(TitleColumn,GenreColumn,DurationColumn,OptionsColumn);
        Table.setFocusTraversable(false);

        centerContent.getChildren().add(Table);

        Table.setItems(songModel.getAllSongs());


    }
    public void NewSong(AnchorPane pageContent){
        popUpContent = new AnchorPane();
        popUpContent.setMinSize(400,470);
        popUpContent.getStyleClass().add("new");
        pageContent.getChildren().add(popUpContent);

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
                pageContent.getChildren().remove(popUpContent);
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
        GetFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var selectedFile =  chooseFile.showOpenDialog(new Stage());
                FilePath.setText(selectedFile.getPath());
            }
        });
        FileRow.getChildren().addAll(FilePath,GetFileButton);
        vBoxHolder.getChildren().add(FileRow);

        var songRow = new HBox();
        var SongNameLabel = new Label("Song");
        SongName = new TextField();
        songRow.getChildren().addAll(SongNameLabel,SongName);
        vBoxHolder.getChildren().add(songRow);

        var ArtistRow = new HBox();
        var ArtistNameLabel = new Label("Artist");
        ArtistName = new TextField();
        ArtistRow.getChildren().addAll(ArtistNameLabel,ArtistName);
        vBoxHolder.getChildren().add(ArtistRow);

        var GenreRow = new HBox();
        var GenreNameLabel = new Label("Genre");
        ObservableList<String> Items =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );
        GenreOptions = new ComboBox(Items);
        GenreRow.getChildren().addAll(GenreNameLabel,GenreOptions);
        vBoxHolder.getChildren().add(GenreRow);

        var SubmitRow = new HBox();
        var SubmitButton = new Button("Submit");
        SubmitRow.getChildren().addAll(SubmitButton);
        vBoxHolder.getChildren().add(SubmitRow);
    }

    private void CreateSong()
    {

    }
}
