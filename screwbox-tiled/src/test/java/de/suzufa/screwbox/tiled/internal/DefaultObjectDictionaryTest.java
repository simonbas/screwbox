package de.suzufa.screwbox.tiled.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.suzufa.screwbox.tiled.Map;
import de.suzufa.screwbox.tiled.ObjectDictionary;
import de.suzufa.screwbox.tiled.TiledSupport;

class DefaultObjectDictionaryTest {

    private ObjectDictionary objectDictionary;

    @BeforeEach
    void beforeEach() {
        Map map = TiledSupport.loadMap("underworld_map.json");
        objectDictionary = map.buildObjectDictionary();
    }

    @Test
    void allObjects_returnsAllObjects() {
        assertThat(objectDictionary.allObjects())
                .hasSize(7);
    }

    @Test
    void findByName_nonFound_returnsEmptyOptional() {
        assertThat(objectDictionary.findByName("unknown")).isEmpty();
    }

    @Test
    void findByName_oneFound_returnsObject() {
        assertThat(objectDictionary.findByName("testsquare")).isPresent();
    }

    @Test
    void findAllWithName_multipleFound_returnsAll() {
        assertThat(objectDictionary.findAllWithName("dummy"))
                .hasSize(2);
    }

    @Test
    void findAllWithName_noneFound_returnsEmptyList() {
        assertThat(objectDictionary.findAllWithName("unknown")).isEmpty();
    }

    @Test
    void findById_nonFound_returnsEmptyOptional() {
        assertThat(objectDictionary.findById(99)).isEmpty();
    }

    @Test
    void findById_found_returnsObject() {
        assertThat(objectDictionary.findById(7)).isPresent();
    }
}
