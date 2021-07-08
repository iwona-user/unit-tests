package pl.rsokol.step3;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.rsokol.Note;
import pl.rsokol.step2.NotesService;
import pl.rsokol.step2.NotesServiceImpl;
import pl.rsokol.step2.NotesStorage;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class NotesServiceImplTest {

    private static final float EQUALITY_DELTA = 0.01f;

    private NotesStorage storage;
    private Multimap<String, Note> notes;
    private NotesService notesService;

    @Before
    public void before() throws Exception {
        notes = ArrayListMultimap.create();
        storage = createMockedNotesStorage();
        notesService = NotesServiceImpl.createWith(storage);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_nullNote() throws Exception {
        notesService.add(null);
    }

    @Test
    public void add() throws Exception {
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

    private NotesStorage createMockedNotesStorage() throws Exception {
        final NotesStorage notesStorage = mock(NotesStorage.class);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(final InvocationOnMock invocation) throws Throwable {
                final Note note = (Note) invocation.getArgument(0);
                notes.put(note.getName(), note);
                return null;
            }
        }).when(notesStorage).add(any(Note.class));
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(final InvocationOnMock invocation) throws Throwable {
                notes.clear();
                return null;
            }
        }).when(notesStorage).clear();
        doAnswer(new Answer<List<Note>>() {
            @Override
            public List<Note> answer(final InvocationOnMock invocation) throws Throwable {
                final String name = (String) invocation.getArgument(0);
                return (List<Note>) notes.get(name);
            }
        }).when(notesStorage).getAllNotesOf(any(String.class));
        return notesStorage;
    }


}
