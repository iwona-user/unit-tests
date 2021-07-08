package pl.rsokol.step2;

import java.util.List;
import pl.rsokol.Note;

public class NotesStorageDbImpl implements NotesStorage {

    @Override
    public void add(final Note note) {
        // Połączenie z bazą danych w celu dodania pozycji.
    }

    @Override
    public List<Note> getAllNotesOf(final String name) {
        // Połączenie z bazą danych w celu pobrania wszystkich pozycji odpowiadających
        // danemu uczniowi.
return null;
    }

    @Override
    public void clear() {
        // Połączenie z bazą danych w celu usunięcia wszystkich pozycji.
    }

}
