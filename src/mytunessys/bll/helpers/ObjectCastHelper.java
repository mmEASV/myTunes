package mytunessys.bll.helpers;

import mytunessys.be.Song;

import java.util.List;

public class ObjectCastHelper {

    /**
     * Tries to cast needed object specified by its id
     * Catches ClassCastException if not able to cast
     * @param o object that
     * @param clazz specified class that is needed to be converted
     * @return casted object if successful
     * @param <T> generic specified object
     */
    public static <T> T tryConvertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch(ClassCastException e) {
            return null;
        }
    }

    public static List<Song> tryParseObjectToList(List<Object> toBeParsed){
        return List.of();
    }

}
