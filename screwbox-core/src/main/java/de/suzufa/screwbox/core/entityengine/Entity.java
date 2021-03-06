package de.suzufa.screwbox.core.entityengine;

import static java.util.Objects.isNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    private final Integer id;
    private transient List<EntityListener> listeners;

    public Entity() {
        this(null);
    }

    public Entity(final Integer id) {
        this.id = id;
    }

    public Optional<Integer> id() {
        return Optional.ofNullable(id);
    }

    public Entity add(final Component... components) {
        for (var component : components) {
            add(component);
        }
        return this;
    }

    public Entity add(Component component) {
        if (components.containsKey(component.getClass())) {
            throw new IllegalArgumentException("component already present: " + component.getClass().getSimpleName());
        }
        components.put(component.getClass(), component);

        for (final var listener : getListeners()) {
            listener.componentAdded(this);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T get(final Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    public Collection<Component> getAll() {
        return components.values();
    }

    public int componentCount() {
        return components.size();
    }

    public Collection<Class<? extends Component>> getComponentClasses() {
        return components.keySet();
    }

    /**
     * Registers the specified {@link EntityListener} to receive Events for this
     * {@link Entity}. The {@link EntityListener} is notified on adding or removing
     * {@link Component}.
     * 
     * {@link EntityListener}s don't survive serialization of the {@link Entity},
     * because it's very unlikely that a listener-class can be serialized itself.
     */
    public void registerListener(final EntityListener listener) {
        getListeners().add(listener);
    }

    /**
     * Checks if the given {@link Component}-class is present in this
     * {@link Entity}.
     * 
     * @param componentClass the {@link Component}-class to check
     * @return {@code true} if the {@link Component}-class is present
     */
    public boolean hasComponent(final Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }

    public void remove(final Class<? extends Component> componentClass) {
        components.remove(componentClass);
        for (final var listener : getListeners()) {
            listener.componentRemoved(this);
        }
    }

    private List<EntityListener> getListeners() {
        if (isNull(listeners)) {
            listeners = new ArrayList<>();
        }
        return listeners;
    }

}
