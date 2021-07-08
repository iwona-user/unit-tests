package pl.rsokol.step2;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import pl.rsokol.Note;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.spy;

public class NotesServiceImplTest {

    @Before
    public void before() {
        notesStorage = spy(new NotesStorageMock());
        notesService = NotesServiceImpl.createWith(notesStorage);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_nullNote() throws Exception {
        notesService.add(null);
    }

    @Test
    public void add() throws Exception {
        notesService.add(Note.of("Jan Kowalski", 5.0f));
    }

    @Test(expected = IOException.class)
    public void add_persistenceError() throws Exception {
        Mockito.doThrow(new IOException()).when(notesStorage).add(any(Note.class));
        notesService.add(Note.of("Jan Kowalski", 5.0f));
    }

    @Test(expected = IllegalArgumentException.class)
    public void averageOf_null() {
        notesService.averageOf(null);
    }

    @Test
    public void averageOf_nonexistent() {
        assertEquals(0.0f, notesService.averageOf("Maria Curie"), EQUALITY_DELTA);
    }

    @Test
    public void averageOf_existing() throws Exception {
        notesService.add(Note.of("Jan Kowalski", 4.0f));
        notesService.add(Note.of("Jan Kowalski", 2.0f));
        notesService.add(Note.of("Maria Curie", 5.0f));
        notesService.add(Note.of("Maria Curie", 4.5f));
        notesService.add(Note.of("Maria Curie", 4.0f));
        assertEquals(4.5f, notesService.averageOf("Maria Curie"), EQUALITY_DELTA);
        assertEquals(3.0f, notesService.averageOf("Jan Kowalski"), EQUALITY_DELTA);
    }

    @Test
    public void clear() throws Exception {
        notesService.add(Note.of("Jan Kowalski", 4.0f));
        notesService.add(Note.of("Jan Kowalski", 2.0f));
        assertEquals(3.0f, notesService.averageOf("Jan Kowalski"), EQUALITY_DELTA);
        notesService.clear();
        assertEquals(0.0f, notesService.averageOf("Jan Kowalski"), EQUALITY_DELTA);
    }

    private static final float EQUALITY_DELTA = 0.01f;

    private NotesStorage notesStorage;
    private NotesService notesService;

}
