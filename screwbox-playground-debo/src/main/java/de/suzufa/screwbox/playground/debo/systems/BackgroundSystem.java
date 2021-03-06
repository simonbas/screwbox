package de.suzufa.screwbox.playground.debo.systems;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.suzufa.screwbox.core.Engine;
import de.suzufa.screwbox.core.entityengine.Archetype;
import de.suzufa.screwbox.core.entityengine.Entity;
import de.suzufa.screwbox.core.entityengine.EntitySystem;
import de.suzufa.screwbox.core.entityengine.UpdatePriority;
import de.suzufa.screwbox.core.entityengine.components.SpriteComponent;
import de.suzufa.screwbox.core.graphics.Offset;
import de.suzufa.screwbox.core.graphics.Window;
import de.suzufa.screwbox.playground.debo.components.BackgroundComponent;

public class BackgroundSystem implements EntitySystem {

    private static final Archetype BACKGROUNDS = Archetype.of(BackgroundComponent.class, SpriteComponent.class);

    private static final Comparator<Entity> BACKGROUND_COMPARATOR = (o1, o2) -> Double
            .compare(o1.get(SpriteComponent.class).drawOrder, o2.get(SpriteComponent.class).drawOrder);

    @Override
    public void update(final Engine engine) {
        final var cameraPosition = engine.graphics().cameraPosition();
        final List<Entity> backgroundEntities = engine.entityEngine().fetchAll(BACKGROUNDS);

        Collections.sort(backgroundEntities, BACKGROUND_COMPARATOR);
        for (final var entity : backgroundEntities) {
            final var background = entity.get(BackgroundComponent.class);
            final var sprite = entity.get(SpriteComponent.class);

            final Offset offset = Offset.at(
                    cameraPosition.x() * -1 * background.parallaxX,
                    cameraPosition.y() * -1 * background.parallaxY);
            final Window window = engine.graphics().window();
            window.fillWith(offset, sprite.sprite, background.zoom, sprite.opacity);
        }
    }

    @Override
    public UpdatePriority updatePriority() {
        return UpdatePriority.PRESENTATION_BACKGROUND;
    }
}
