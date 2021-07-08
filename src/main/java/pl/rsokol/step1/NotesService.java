package pl.rsokol.step1;

import pl.rsokol.Note;

public class NotesService {

    private static final NotesService INSTANCE = new NotesService();

    private NotesService() {
    }

    public static NotesService getInstance() {
        return INSTANCE;
    }

    public void add(final Note note) {
        // Połączenie z bazą danych w celu dodania pozycji.
    }

    public float averageOf(final String name) {
        // Połączenie z bazą danych w celu pobrania wszystkich ocen danego
        // ucznia i wyznaczenia średniej arytmetycznej.
        return 0.0f;
    }

    public void clear() {
        // Połączenie z bazą danych w celu usunięcia wszystkich pozycji.
    }


}
