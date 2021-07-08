package pl.rsokol.step2;

import java.io.IOException;
import java.util.List;
import pl.rsokol.Note;

public interface NotesStorage {

    void add(Note note) throws IOException;

    List<Note> getAllNotesOf(String name);

    void clear();

}
