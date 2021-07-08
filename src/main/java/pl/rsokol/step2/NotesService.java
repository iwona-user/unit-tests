package pl.rsokol.step2;

import java.io.IOException;
import pl.rsokol.Note;

public interface NotesService {

    void add(Note note) throws IOException;

    float averageOf(String name);

    void clear();

}
