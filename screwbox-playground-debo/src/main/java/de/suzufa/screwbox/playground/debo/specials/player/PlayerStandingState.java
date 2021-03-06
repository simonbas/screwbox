package de.suzufa.screwbox.playground.debo.specials.player;

import de.suzufa.screwbox.core.Duration;
import de.suzufa.screwbox.core.Engine;
import de.suzufa.screwbox.core.Time;
import de.suzufa.screwbox.core.entityengine.Entity;
import de.suzufa.screwbox.core.entityengine.EntityState;
import de.suzufa.screwbox.core.entityengine.components.PhysicsBodyComponent;
import de.suzufa.screwbox.core.entityengine.components.SpriteComponent;
import de.suzufa.screwbox.playground.debo.components.DeathEventComponent;
import de.suzufa.screwbox.playground.debo.components.PlayerControlComponent;

public class PlayerStandingState implements EntityState {

    private static final long serialVersionUID = 1L;

    private final Time started = Time.now();

    @Override
    public void enter(Entity entity, Engine engine) {
        entity.get(SpriteComponent.class).sprite = PlayerResources.STANDING_SPRITE;
    }

    @Override
    public EntityState update(Entity entity, Engine engine) {
        if (entity.hasComponent(DeathEventComponent.class)) {
            return new PlayerDeathState();
        }
        var momentum = entity.get(PhysicsBodyComponent.class).momentum;

        var controlComponent = entity.get(PlayerControlComponent.class);
        if (controlComponent.jumpDownPressed) {
            return new PlayerFallThroughState();
        } else if (controlComponent.jumpPressed) {
            return new PlayerJumpingStartedState();
        }

        if (Math.abs(momentum.x()) > 5) {
            return new PlayerRunningState();
        }

        if (Duration.since(started).isAtLeast(Duration.ofSeconds(5))) {
            return new PlayerIdleState();
        }
        return this;
    }

}
