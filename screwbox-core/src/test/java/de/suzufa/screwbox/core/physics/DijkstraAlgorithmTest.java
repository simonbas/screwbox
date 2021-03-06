package de.suzufa.screwbox.core.physics;

import static de.suzufa.screwbox.core.Bounds.$$;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.suzufa.screwbox.core.physics.Grid.Node;

class DijkstraAlgorithmTest {

    DijkstraAlgorithm algorithm;

    @BeforeEach
    void beforeEach() {
        algorithm = new DijkstraAlgorithm();
    }

    @Test
    void findPath_noPath_returnsEmpty() {
        Grid grid = new Grid($$(0, 0, 5, 5), 1, true);
        grid.blockArea($$(2, 0, 1, 5));

        Node start = grid.nodeAt(0, 0);
        Node end = grid.nodeAt(4, 4);

        List<Node> path = algorithm.findPath(grid, start, end);

        assertThat(path).isEmpty();
    }

    @Test
    void findPath_pathPresent_returnsShortestPath() {
        Grid grid = new Grid($$(0, 0, 5, 5), 1, true);
        grid.blockArea($$(2, 2, 2, 2));

        Node start = grid.nodeAt(0, 0);
        Node end = grid.nodeAt(4, 4);

        List<Node> path = algorithm.findPath(grid, start, end);

        assertThat(path).containsExactly(
                grid.nodeAt(0, 1),
                grid.nodeAt(0, 2),
                grid.nodeAt(0, 3),
                grid.nodeAt(1, 4),
                grid.nodeAt(2, 4),
                grid.nodeAt(3, 4),
                grid.nodeAt(4, 4));
    }
}
