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
import EdgeList from "./EdgeList";
import Map from "./Map";
import {Edge} from "./Edge";
import PointList from "./PointList";
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
          points: [],
      };
  }

  setLines(value: Edge[]) {
      this.setState({
          lines: value,
      });
  }

  setPoints(value: Point[]) {
      this.setState({
          points: value,
      })
  }

  render() {
    return (
      <div>
        <h1 id="app-title">Line Mapper!</h1>
        <div>
          <Map
              drawEdges = {this.state.lines}
              drawPoints = {this.state.points}
          />
        </div>
        <EdgeList
          onChange={(value) => {
            this.setLines(value);
          }}
        />
        <PointList
            onChange={(value) => {
                this.setPoints(value);
            }}
        />
      </div>
    );
  }
}

export default App;
