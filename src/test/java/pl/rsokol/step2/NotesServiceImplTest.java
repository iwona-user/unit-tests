package pl.rsokol.step2;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.rsokol.Note;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class NotesServiceImplTest {

    private static final float EQUALITY_DELTA = 0.01f;
    @Mock
    private NotesStorage notesStorage;
    private NotesService notesService;

    @Before
    public void before() {
        notesStorage = spy(new NotesStorageMock());
        notesService = NotesServiceImpl.createWith(notesStorage);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddNullNote() throws Exception {
        // when
        notesService.add(null);

        //then
        then(notesStorage).should(never()).add(any());
    }

    @Test
    public void shouldAddNoteWhenCorrectNote() throws Exception {
        // given
        Note note = Note.of("Jan Kowalski", 5.0f);

        // when
        notesService.add(note);

        //then
        then(notesStorage).should(times(1)).add(note);
    }

    @Test(expected = IOException.class)
    public void add_persistenceError() throws Exception {
        // given
        Note note = Note.of("Jan Kowalski", 5.0f);

        Mockito.doThrow(new IOException()).when(notesStorage).add(note);

        // when
        notesService.add(note);

    }

    @Test(expected = IllegalArgumentException.class)
    public void averageOf_null() {
        notesService.averageOf(null);
    }

    @Test
    public void averageOf_nonexistent() {
        // given
        final String name = "Maria Curie";

        // when
        final float avg = notesService.averageOf(name);

        // then
        assertEquals(0.0f, avg, EQUALITY_DELTA);
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

}
