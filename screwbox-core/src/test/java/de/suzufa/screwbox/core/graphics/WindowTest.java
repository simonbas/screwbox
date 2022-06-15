package de.suzufa.screwbox.core.graphics;

import static de.suzufa.screwbox.core.graphics.Offset.origin;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import de.suzufa.screwbox.core.Percentage;

@ExtendWith(MockitoExtension.class)
class WindowTest {

    @Spy
    Window window;

    @Test
    void fillWith_sprite_callsActualMethod() {
        Sprite sprite = sprite();

        window.fillWith(sprite);

        verify(window).fillWith(origin(), sprite, 1, Percentage.max());
    }

    @Test
    void fillWith_OffsetAndSprite_callsActualMethod() {
        Sprite sprite = sprite();

        window.fillWith(Offset.at(2, 3), sprite);

        verify(window).fillWith(Offset.at(2, 3), sprite, 1, Percentage.max());
    }

    @Test
    void drawLine_fromAndTo_callsActualMethod() {
        Offset from = Offset.at(2, 4);
        Offset to = Offset.at(1, 1);
        when(window.drawColor()).thenReturn(Color.BLUE);

        window.drawLine(from, to);

        verify(window).drawLine(from, to, Color.BLUE);
    }

    private Sprite sprite() {
        return Sprite.fromFile("tile.bmp");
    }
}