//
//  Shared+Models+Extensions.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 19.04.23.
//  Copyright ©2023 Tobias Scholze. All rights reserved.
//

import shared
import MapKit
import Foundation

// MARK: - Region -

extension Region: Identifiable {}

extension Coordinate {
    /// Transforms given object to a `MapKit.CLLocationCoordinate2D`
    func toLocationCoordinate() -> CLLocationCoordinate2D {
        return .init(latitude: latitude, longitude: longitude)
    }

    /// Transforms given object to a `MapKit.MKCoordinateRegion` with given detla.
    ///
    /// - Parameter delta: The amount of north-to-south distance (measured in degrees) to use for the span.
    func toCoordinateRegion(withSpanDelta delta: Double) -> MKCoordinateRegion {
        return .init(center: toLocationCoordinate(), span: .init(latitudeDelta: delta, longitudeDelta: delta))
    }
}
