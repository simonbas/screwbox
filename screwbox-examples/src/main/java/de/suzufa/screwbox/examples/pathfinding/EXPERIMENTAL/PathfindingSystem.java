package de.suzufa.screwbox.examples.pathfinding.EXPERIMENTAL;

import static de.suzufa.screwbox.core.Duration.ofMillis;

import de.suzufa.screwbox.core.Bounds;
import de.suzufa.screwbox.core.Engine;
import de.suzufa.screwbox.core.entityengine.Archetype;
import de.suzufa.screwbox.core.entityengine.Entity;
import de.suzufa.screwbox.core.entityengine.components.ColliderComponent;
import de.suzufa.screwbox.core.entityengine.components.PhysicsBodyComponent;
import de.suzufa.screwbox.core.entityengine.components.TransformComponent;
import de.suzufa.screwbox.core.entityengine.components.WorldBoundsComponent;
import de.suzufa.screwbox.core.utils.Timer;

public class PathfindingSystem {

    private Engine engine;
    private Raster raster;

    private Timer update = Timer.withInterval(ofMillis(500));

    // TODO: merge with engine.physics()
    public PathfindingSystem(Engine engine) {
        this.engine = engine;
    }

    public void update() {
        if (update.isTick(engine.loop().metrics().timeOfLastUpdate()) || raster == null) {
            Entity worldBounds = engine.entityEngine().forcedFetch(WorldBoundsComponent.class,
                    TransformComponent.class);
            Bounds bounds = worldBounds.get(TransformComponent.class).bounds;
            raster = new Raster(bounds, 16);
            for (Entity entity : engine.entityEngine()
                    .fetchAll(Archetype.of(ColliderComponent.class, TransformComponent.class))) {
                if (!entity.hasComponent(PhysicsBodyComponent.class)) { // TODO: add special
                                                                        // NotBlockingPathfindingComponent
                    raster.markNonWalkable(entity.get(TransformComponent.class).bounds);
                }
            }
        }
    }

    public void debugDraw() {
        raster.debugDraw(engine.graphics().world());
    }

}
