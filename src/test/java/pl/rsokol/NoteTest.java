package pl.rsokol;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoteTest {

    @Test
    public void create() {
        final Note note = Note.of("Jan Kowalski", 4.5f);
        assertEquals("Jan Kowalski", note.getName());
        assertEquals(4.5f, note.getNote(), 0.01f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_nullName() {
        Note.of(null, 4.5f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_emptyName() {
        Note.of("   ", 4.5f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_noteTooLow() {
        Note.of("Jan Kowalski", 1.9f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_noteTooHigh() {
        Note.of("Jan Kowalski", 6.1f);
    }

}
