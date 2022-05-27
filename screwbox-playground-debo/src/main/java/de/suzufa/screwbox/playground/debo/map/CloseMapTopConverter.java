package de.suzufa.screwbox.playground.debo.map;

import de.suzufa.screwbox.core.Bounds;
import de.suzufa.screwbox.core.entityengine.Entity;
import de.suzufa.screwbox.core.entityengine.components.TransformComponent;
import de.suzufa.screwbox.core.resources.Converter;
import de.suzufa.screwbox.core.entityengine.components.ColliderComponent;
import de.suzufa.screwbox.tiled.Map;

public class CloseMapTopConverter implements Converter<Map> {

    @Override
    public boolean accepts(final Map map) {
        return map.properties().getBoolean("closed-top").orElse(false);
    }

    @Override
    public Entity convert(final Map map) {
        Bounds bounds = Bounds.atOrigin(0, -200, map.bounds().width(), 200);
        return new Entity().add(
                new TransformComponent(bounds),
                new ColliderComponent(500));
    }

}
