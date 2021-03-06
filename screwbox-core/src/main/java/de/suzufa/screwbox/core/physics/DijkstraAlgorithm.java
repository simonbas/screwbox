package de.suzufa.screwbox.core.physics;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

import de.suzufa.screwbox.core.physics.Grid.Node;

public class DijkstraAlgorithm implements PathfindingAlgorithm {

    @Override
    public List<Node> findPath(final Grid grid, final Node start, final Node end) {
        final var usedNodes = new ArrayList<Node>();
        usedNodes.add(start);

        while (true) {
            final List<Node> openNodes = calculateOpenNodes(grid, usedNodes);

            for (final Node point : openNodes) {
                usedNodes.add(point);
                if (end.equals(point)) {
                    return usedNodes.get(usedNodes.size() - 1).backtrackPath();
                }
            }

            if (openNodes.isEmpty()) {
                return emptyList();
            }
        }
    }

    private List<Node> calculateOpenNodes(final Grid grid, final ArrayList<Node> usedNodes) {
        final List<Node> openNodes = new ArrayList<>();
        for (final var usedNode : usedNodes) {
            for (final Node neighbor : grid.findNeighbors(usedNode)) {
                if (!usedNodes.contains(neighbor) && !openNodes.contains(neighbor)) {
                    openNodes.add(neighbor);
                }
            }
        }
        return openNodes;
    }

}
