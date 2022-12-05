package mytunessys.bll.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    /*
    * ^               # start-of-string
    [A-Z]             # single character between A-Z
    [a-zA-Z]          # matches any characters between a-z or A-Z
    {3,}              # matches at least 3 consecutive `a` characters.
    (?:               # matches everything enclosed
    {0,2}             # between 0-2 characters
    $                 # end-of-string
    *
    * */
    public boolean validateSongName(String name) {
        return name.matches("^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}$");
    }

    public boolean validatePlaylistName(String name) {
        return false;
    }

    public boolean isFilePath(String filePath) {
        return Pattern.compile("([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?").matcher(filePath).find(); // just trying stuff with validation of file path
    }
}
