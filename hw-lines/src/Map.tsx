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

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import MapPoint from "./MapPoint";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";
import {Edge} from "./Edge";
import {Point} from "./Point";

import L from 'leaflet';
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';

let defaultIcon = L.icon({
    iconUrl: icon,
    shadowUrl: iconShadow,
});

L.Marker.prototype.options.icon = defaultIcon;

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
  drawEdges: Edge[];
  drawPoints: Point[];
}

interface MapState {

}

class Map extends Component<MapProps, MapState> {
    constructor(props: MapProps) {
        super(props);
    };

  render() {
    return (
      <div id="map">
        <MapContainer
          center={position}
          zoom={15}
          scrollWheelZoom={false}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {
            //draws each edge on the map
            this.props.drawEdges.map((oneEdge) =>
              <MapLine
                color = {oneEdge.color}
                x1  = {oneEdge.x1}
                y1  = {oneEdge.y1}
                x2  = {oneEdge.x2}
                y2  = {oneEdge.y2}
                key = {oneEdge.key}
              />
            )
          }
            {
                // draws each point on the map
                this.props.drawPoints.map((onePoint) =>
                    <MapPoint
                        x1 = {onePoint.x1}
                        y1 = {onePoint.y1}
                    />

                )
            }
        </MapContainer>
      </div>
    );
  }
}

export default Map;
