package pl.rsokol.step2;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.util.List;
import pl.rsokol.Note;

public class NotesStorageMock implements NotesStorage {

    private final Multimap<String, Note> notes = ArrayListMultimap.create();

    @Override
    public void add(final Note note) throws IOException {
        notes.put(note.getName(), note);
    }

    @Override
    public List<Note> getAllNotesOf(final String name) {
        return (List<Note>) notes.get(name);
    }

    @Override
    public void clear() {
        notes.clear();
    }


}
