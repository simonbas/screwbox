package de.suzufa.screwbox.core.mouse.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.suzufa.screwbox.core.Vector;
import de.suzufa.screwbox.core.graphics.Graphics;
import de.suzufa.screwbox.core.graphics.Offset;
import de.suzufa.screwbox.core.graphics.Window;
import de.suzufa.screwbox.core.mouse.MouseButton;

@ExtendWith(MockitoExtension.class)
class DefaultMouseTest {

    @Mock
    Window window;

    @Mock
    Graphics graphics;

    @InjectMocks
    DefaultMouse mouse;

    @Test
    void justPressed_mouseNotPressed_isFalse() {
        var isPressed = mouse.justPressed(MouseButton.LEFT);

        mouse.update();

        assertThat(isPressed).isFalse();
    }

    @Test
    void justPressed_mousePressed_isTrue() {
        mouse.mousePressed(rightMouseButtonEvent());
        mouse.update();

        var isPressed = mouse.justPressed(MouseButton.RIGHT);

        assertThat(isPressed).isTrue();
    }

    @Test
    void isButtonDown_pressedButAlreadyReleased_isFalse() {
        mouse.mousePressed(rightMouseButtonEvent());
        mouse.mouseReleased(rightMouseButtonEvent());

        var isButtonDown = mouse.isButtonDown(MouseButton.RIGHT);

        assertThat(isButtonDown).isFalse();
    }

    @Test
    void isCursorOnWindow_enteredAndExited_isFalse() {
        mouse.mouseEntered(null);
        mouse.mouseExited(null);

        assertThat(mouse.isCursorOnWindow()).isFalse();
    }

    @Test
    void isCursorOnWindow_entered_isTrue() {
        mouse.mouseEntered(null);

        assertThat(mouse.isCursorOnWindow()).isTrue();
    }

    @Test
    void position_returnsTheLatestPosition() {
        MouseEvent mouseEvent = mock(MouseEvent.class);
        when(mouseEvent.getXOnScreen()).thenReturn(151);
        when(mouseEvent.getYOnScreen()).thenReturn(242);
        when(graphics.window()).thenReturn(window);
        when(window.position()).thenReturn(Offset.at(40, 12));

        mouse.mouseMoved(mouseEvent);

        assertThat(mouse.position()).isEqualTo(Offset.at(111, 230));
    }

    @Test
    void worldPosition_returnsTheLatestWorldPosition() {
        MouseEvent mouseEvent = mock(MouseEvent.class);
        when(mouseEvent.getXOnScreen()).thenReturn(151);
        when(mouseEvent.getYOnScreen()).thenReturn(242);
        when(graphics.window()).thenReturn(window);
        when(window.position()).thenReturn(Offset.at(40, 12));
        when(graphics.screenToWorld(Offset.at(111, 230))).thenReturn(Vector.of(40, 90));
        mouse.mouseMoved(mouseEvent);

        assertThat(mouse.worldPosition()).isEqualTo(Vector.of(40, 90));
    }

    private MouseEvent rightMouseButtonEvent() {
        MouseEvent event = mock(MouseEvent.class);
        when(event.getButton()).thenReturn(3);
        return event;
    }
}
