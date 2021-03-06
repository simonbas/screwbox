package de.suzufa.screwbox.playground.debo.systems;

import de.suzufa.screwbox.core.Engine;
import de.suzufa.screwbox.core.Vector;
import de.suzufa.screwbox.core.entityengine.Archetype;
import de.suzufa.screwbox.core.entityengine.Entity;
import de.suzufa.screwbox.core.entityengine.EntitySystem;
import de.suzufa.screwbox.core.entityengine.components.PhysicsBodyComponent;
import de.suzufa.screwbox.core.entityengine.components.TransformComponent;
import de.suzufa.screwbox.core.physics.Borders;
import de.suzufa.screwbox.playground.debo.components.PatrollingMovementComponent;
import de.suzufa.screwbox.playground.debo.components.PlayerMarkerComponent;

public class PatrollingMovementSystem implements EntitySystem {

    private static final Archetype PATROLLING = Archetype.of(PatrollingMovementComponent.class,
            PhysicsBodyComponent.class);

    @Override
    public void update(final Engine engine) {
        for (final Entity entity : engine.entityEngine().fetchAll(PATROLLING)) {
            final var physicsBodyComponent = entity.get(PhysicsBodyComponent.class);
            final var patrollingMovementComponent = entity.get(PatrollingMovementComponent.class);

            if (isOnEdge(engine, entity) || spotsWall(engine, entity)) {
                invertSpeed(entity);
            }

            physicsBodyComponent.momentum = Vector.of(
                    patrollingMovementComponent.right ? 20 : -20,
                    physicsBodyComponent.momentum.y());
        }
    }

    private boolean spotsWall(final Engine engine, final Entity entity) {
        final var bounds = entity.get(TransformComponent.class).bounds;
        final var slimeComp = entity.get(PatrollingMovementComponent.class);
        return engine.physics()
                .raycastFrom(bounds.position())
                .ignoringEntities(entity)
                .ignoringEntitiesHaving(PlayerMarkerComponent.class)
                .ignoringEntitesNotIn(bounds.inflated(8))
                .checkingBorders(Borders.VERTICAL_ONLY)
                .castingHorizontal(slimeComp.right ? 8 : -8).hasHit();
    }

    private boolean isOnEdge(final Engine engine, final Entity entity) {
        final var bounds = entity.get(TransformComponent.class).bounds;
        final var slimeComp = entity.get(PatrollingMovementComponent.class);
        final Vector start = slimeComp.right
                ? Vector.of(bounds.maxX(), bounds.position().y())
                : Vector.of(bounds.minX(), bounds.position().y());

        return engine.physics()
                .raycastFrom(start)
                .checkingBorders(Borders.TOP_ONLY)
                .ignoringEntitesNotIn(bounds.inflated(8))
                .castingVertical(8)
                .noHit();
    }

    private void invertSpeed(final Entity entity) {
        final var physicsBodyComponent = entity.get(PhysicsBodyComponent.class);
        final var pattrolling = entity.get(PatrollingMovementComponent.class);
        physicsBodyComponent.momentum = physicsBodyComponent.momentum.invertX();
        pattrolling.right = !pattrolling.right;
    }
}