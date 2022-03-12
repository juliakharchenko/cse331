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

import React, { Component } from "react";
import BuildingList from "./BuildingList";
import Map from "./Map";
import {Edge} from "./Edge";
import {Point} from "./Point";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    lines: Edge[];
    points: Point[];
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            lines: [],
            points: []
        };
    }

    //sets the state of lines to given array of edges
    setLines(value: Edge[]) {
        this.setState({lines: value});
    }

    //sets the state of points to the given array of points
    setPoints(value: Point[]) {
        this.setState({points: value});
    }

    render() {
        return (
            <div id="uw-mapper">
                <h1 id="app-title">UW Mapper!</h1>
                <BuildingList
                    onChange={(edges,points) => {
                        this.setLines(edges);
                        this.setPoints(points);
                    }}
                />
                <div>
                    <Map
                        drawEdges = {this.state.lines}
                        drawPoints = {this.state.points}
                    />
                </div>

            </div>
        );
    }
}

export default App;
