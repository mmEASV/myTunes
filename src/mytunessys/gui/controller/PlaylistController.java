package mytunessys.gui.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * @author Bálint & Matej
 */

public class PlaylistController {

    public void Show(AnchorPane centerContent){
        var table = new TableView<>();

        var NameCollumn = new TableColumn<>();
        NameCollumn.setText("Playlist Name");
        NameCollumn.prefWidthProperty().set(283);

        var CurrentlyPlayingCollumn = new TableColumn<>();
        CurrentlyPlayingCollumn.setText("Currently Playing");

        var NumberOfSongsCollumn = new TableColumn<>();
        NumberOfSongsCollumn.setText("Number of Songs");

        table.editableProperty().set(false);
        table.getColumns().add(NameCollumn);
        table.getColumns().add(CurrentlyPlayingCollumn);
        table.getColumns().add(NumberOfSongsCollumn);
        table.setFocusTraversable(false);
        centerContent.getChildren().add(table);
    }




}
