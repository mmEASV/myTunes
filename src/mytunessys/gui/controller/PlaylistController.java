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
        table.setLayoutX(14);

        var NameColumn = new TableColumn<>();
        NameColumn.setText("Playlist Name");
        NameColumn.prefWidthProperty().set(173);

        var CurrentlyPlayingColumn = new TableColumn<>();
        CurrentlyPlayingColumn.setText("Currently Playing");
        CurrentlyPlayingColumn.prefWidthProperty().set(107);

        var NumberOfSongsColumn = new TableColumn<>();
        NumberOfSongsColumn.setText("Number of Songs");
        NumberOfSongsColumn.prefWidthProperty().set(64);

        table.editableProperty().set(false);
        table.getColumns().add(NameColumn);
        table.getColumns().add(CurrentlyPlayingColumn);
        table.getColumns().add(NumberOfSongsColumn);
        table.setFocusTraversable(false);
        centerContent.getChildren().add(table);
    }




}
