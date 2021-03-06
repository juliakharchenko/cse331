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

import React, {Component} from 'react';
import {Edge} from "./Edge";

interface EdgeListProps {
    onChange(edges: Edge[]): void;  // called when a new edge list is ready
}

interface EdgeListState {
    textValue: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            textValue: "Type here... ",
        };
    }

    textChange(event: any) {
        this.setState({
            textValue: event.target.value,
        })
    }

    clearText() {
        this.setState({
            textValue: "",
        });
        this.props.onChange([]);
    }

    parseData(str: string) {
        // checks if no data has been entered into text box
        if (str === "") {
            alert("Please input coordinates!");
            return;
        }

        // stores data entered into edge test box as an array
        // where each element in data represents the string
        // representation of a single edge
        let data: string[] = str.split("\n");

        // stores an array of edges based on data given by user
        let edgeList: Edge[] = [];

        for (let i = 0; i < data.length; i++) {
            // stores the data of one single edge into an array
            let oneEdge: string[] = data[i].split(" ");

            for (let j = 0; j < oneEdge.length - 1; j++) {
                let parsedInt = parseInt(oneEdge[j]);

                //checks if the given coordinate is a valid integer.
                if (isNaN(parsedInt)) {
                    alert("Invalid coordinates! Coordinates must be integers.");
                    return;
                }

                // checks if the given coordinate is between 0 and 4000 (min and max values
                // for coordinates)
                if (parsedInt < 0 || parsedInt > 4000) {
                    alert("Invalid coordinates! Coordinates must be between 0 and 4000 (inclusive).");
                    return;
                }
            }
            if (typeof (oneEdge[oneEdge.length - 1]) !== 'string') {
                alert("Invalid color type! Color must be string!");
                return;
            }
            // uses given data from user to add an edge to the list of edges
            edgeList.push({x1:parseInt(oneEdge[0]), y1:parseInt(oneEdge[1]),
                x2: parseInt(oneEdge[2]), y2: parseInt(oneEdge[3]),color: oneEdge[4], key: i.toString()});
            console.log(oneEdge.toString());
        }
        console.log(edgeList.length);
        this.props.onChange(edgeList);
    }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    value={this.state.textValue}
                    onChange = {(event) => this.textChange(event)}
                /> <br/>
                <button onClick={() => this.parseData(this.state.textValue)}>Draw</button>
                <button onClick={() => this.clearText()}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
