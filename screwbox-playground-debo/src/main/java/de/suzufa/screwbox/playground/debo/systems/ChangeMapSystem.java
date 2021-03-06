package de.suzufa.screwbox.playground.debo.systems;

import static de.suzufa.screwbox.core.utils.ListUtil.randomFrom;

import java.util.List;

import de.suzufa.screwbox.core.Duration;
import de.suzufa.screwbox.core.Engine;
import de.suzufa.screwbox.core.Time;
import de.suzufa.screwbox.core.entityengine.Archetype;
import de.suzufa.screwbox.core.entityengine.Entity;
import de.suzufa.screwbox.core.entityengine.EntitySystem;
import de.suzufa.screwbox.core.entityengine.UpdatePriority;
import de.suzufa.screwbox.core.entityengine.components.ScreenTransitionComponent;
import de.suzufa.screwbox.core.entityengine.components.SignalComponent;
import de.suzufa.screwbox.core.graphics.transitions.CircleTransition;
import de.suzufa.screwbox.core.graphics.transitions.FadeOutTransition;
import de.suzufa.screwbox.core.graphics.transitions.FadingScreenTransition;
import de.suzufa.screwbox.core.graphics.transitions.HorizontalLinesTransition;
import de.suzufa.screwbox.core.graphics.transitions.MosaikTransition;
import de.suzufa.screwbox.core.graphics.transitions.ScreenTransition;
import de.suzufa.screwbox.core.graphics.transitions.SwipeTransition;
import de.suzufa.screwbox.core.keyboard.Key;
import de.suzufa.screwbox.playground.debo.components.ChangeMapComponent;
import de.suzufa.screwbox.playground.debo.scenes.GameScene;

public class ChangeMapSystem implements EntitySystem {

    private static final Archetype CHANGE_MAP_ZONES = Archetype.of(ChangeMapComponent.class, SignalComponent.class);

    private static final List<ScreenTransition> TRANSITIONS = List.of(
            new FadeOutTransition(new HorizontalLinesTransition(20)),
            new FadeOutTransition(new SwipeTransition()),
            new FadeOutTransition(new FadingScreenTransition()),
            new FadeOutTransition(new CircleTransition()),
            new FadeOutTransition(new MosaikTransition(30, 20)));

    @Override
    public void update(Engine engine) {
        for (Entity entity : engine.entityEngine().fetchAll(CHANGE_MAP_ZONES)) {
            ChangeMapComponent changeMapComponent = entity.get(ChangeMapComponent.class);
            if (changeMapComponent.time.isSet()) {
                if (Time.now().isAfter(changeMapComponent.time)) {
                    engine.scenes().add(new GameScene(entity.get(ChangeMapComponent.class).fileName));
                    engine.scenes().switchTo(GameScene.class);
                }
            } else if (engine.keyboard().justPressed(Key.SPACE) && entity.get(SignalComponent.class).isTriggered) {
                engine.entityEngine().add(new Entity().add(
                        new ScreenTransitionComponent(randomFrom(TRANSITIONS), Duration.ofMillis(3200))));
                changeMapComponent.time = Time.now().plusSeconds(3);
            }

        }
    }

    @Override
    public UpdatePriority updatePriority() {
        return UpdatePriority.SIMULATION_BEGIN;
    }

}
