/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import pathfinder.parser.*;
import pathfinder.datastructures.*;
import graph.*;

import java.util.*;

/**
 * A CampusMap is a representation of the University of Washington's campus and the buildings
 * and paths within it. Any user of this map can get data about the buildings within the campus
 * and determine the shortest path between two buildings in the map.
 */
public class CampusMap implements ModelAPI {

    /**
     * Stores the name of the file containing all the buildings on campus
     */
    private final String campusBuildings = "campus_buildings.csv";

    /**
     * Stores the name of the file containing all the paths on campus between two points.
     */
    private final String campusPaths = "campus_paths.csv";

    /**
     * Stores the campus graph with nodes represented by Points and edges represented by
     * Doubles storing the distance between two Points.
     */
    private DLGraph<Point, Double> campusGraph;

    /**
     * Stores the names of every building on campus, mapping the short name of a building
     * to its long name.
     */
    private Map<String, String> buildingNames;

    /**
     * Stores the location of every building on campus, mapping the short name of the building
     * to the location of the building represented by a Point.
     */
    private Map<String, Point> buildingLocations;

    /**
     * Constructs a new CampusMap using the campus building and path data given in
     * the files.
     *
     * @spec.requires "campus_buildings.csv" is a valid, non-null csv file
     * @spec.requires "campus_paths.csv" is a valid, non-null csv file
     * @spec.effects stores the campus building and path data in a graph
     */
    public CampusMap()
    {
        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings(campusBuildings);
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths(campusPaths);
        campusGraph = new DLGraph<>();
        buildingNames = new HashMap<>();
        buildingLocations = new HashMap<>();
        for (CampusBuilding building : buildings)
        {
            buildingNames.put(building.getShortName(), building.getLongName());
            Point bCoords = new Point(building.getX(), building.getY());
            buildingLocations.put(building.getShortName(), bCoords);
            campusGraph.addNode(new Node<>(bCoords));
        }
        for (CampusPath path : paths) {
            Point first = new Point(path.getX1(), path.getY1());
            Point second = new Point(path.getX2(), path.getY2());
            campusGraph.addNode(new Node<>(first));
            campusGraph.addNode(new Node<>(second));
            campusGraph.addEdge(new Node<>(first), new Node<>(second), path.getDistance());
        }
    }

    /**
     *
     * @param shortName The short name of a building to query.
     * @return true if the map of building names contains the given name
     * @throws IllegalArgumentException if given name is null
     */
    @Override
    public boolean shortNameExists(String shortName) {
        if (shortName == null) throw new IllegalArgumentException("Name cannot be null.");
        return buildingNames.containsKey(shortName);
    }

    /**
     *
     * @param shortName The short name of a building to look up.
     * @return long name for the given short name in the map buildings
     * @throws IllegalArgumentException if short name doesn't exist in campus buildings
     *         or if given name is null.
     */
    @Override
    public String longNameForShort(String shortName) {
        if (shortName == null) throw new IllegalArgumentException("Name cannot be null.");
        if (!shortNameExists(shortName)) throw new IllegalArgumentException("Name isn't in campus buildings");
        return buildingNames().get(shortName);
    }


    /**
     * Returns a map of all the buildings on campus with keys as the short name and values as the long name
     * @return map of all buildings on campus
     */
    @Override
    public Map<String, String> buildingNames() {
        return new HashMap<>(buildingNames);
    }

    /**
     * Determines the shortest path between two buildings on campus using Dijkstra's algorithm.
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return a Path of Points representing the shortest path between two buildings
     * @throws IllegalArgumentException if either given name is null or either given name
     *         doesn't exist in the campus buildings graph.
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (startShortName == null || endShortName == null) {
            throw new IllegalArgumentException("Names cannot be null.");
        }
        if (!(shortNameExists(startShortName) && shortNameExists(startShortName))) {
            throw new IllegalArgumentException("Given names do not exist in campus buildings.");
        }
        return DijkstrasAlgorithm.dijkstrasPath(campusGraph, buildingLocations.get(startShortName),
                                                buildingLocations.get(endShortName));

    }


}
