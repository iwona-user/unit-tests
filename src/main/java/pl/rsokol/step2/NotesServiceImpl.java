package pl.rsokol.step2;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.Collection;
import pl.rsokol.Note;

public class NotesServiceImpl implements NotesService {

    private final NotesStorage storageService;

    private NotesServiceImpl(final NotesStorage storageService) {
        this.storageService = storageService;
    }

    public static NotesService createWith(final NotesStorage storageService) {
        return new NotesServiceImpl(storageService);
    }

    @Override
    public void add(final Note note) throws IOException {
        Preconditions.checkArgument(note != null);
        storageService.add(note);
    }

    @Override
    public float averageOf(final String name) {
        Preconditions.checkArgument(name != null);
        float sum = 0.0f;
        final Collection<Note> notes = storageService.getAllNotesOf(name);
        if (!notes.isEmpty()) {
            for (final Note note : notes) {
                sum += note.getNote();
            }
            sum /= notes.size();
        }
        return sum;
    }

    @Override
    public void clear() {
        storageService.clear();
    }


}
