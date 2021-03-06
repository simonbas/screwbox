package de.suzufa.screwbox.core.graphics.world.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.suzufa.screwbox.core.Bounds;
import de.suzufa.screwbox.core.Segment;
import de.suzufa.screwbox.core.Vector;
import de.suzufa.screwbox.core.graphics.Color;
import de.suzufa.screwbox.core.graphics.Dimension;
import de.suzufa.screwbox.core.graphics.Offset;
import de.suzufa.screwbox.core.graphics.Window;
import de.suzufa.screwbox.core.graphics.internal.DefaultWorld;

@ExtendWith(MockitoExtension.class)
class DefaultWorldTest {

    private DefaultWorld world;

    @Mock
    private Window window;

    @BeforeEach
    void beforeEach() {
        when(window.size()).thenReturn(Dimension.of(1024, 768));
        world = new DefaultWorld(window);
    }

    @Test
    void updateCameraPosition_updatesVisibleArea() {
        world.updateCameraPosition(Vector.of(100, 20));

        assertThat(world.visibleArea())
                .isEqualTo(Bounds.atPosition(100, 20, 1024, 768));
    }

    @Test
    void drawRectangle_inBounds_callsWindow() {
        world.updateCameraPosition(Vector.zero());
        world.updateCameraZoom(2.5);

        world.drawRectangle(Bounds.atPosition(0, 0, 100, 100), Color.RED);

        verify(window).drawRectangle(Offset.at(387, 259), Dimension.of(250, 250), Color.RED);
    }

    @Test
    void drawColor_setsColorForDrawing() {
        world.drawColor(Color.BLUE);
        world.drawLine(Segment.between(Vector.zero(), Vector.zero()));

        assertThat(world.drawColor()).isEqualTo(Color.BLUE);
        verify(window).drawLine(Offset.at(512, 384), Offset.at(512, 384), Color.BLUE);
    }
}