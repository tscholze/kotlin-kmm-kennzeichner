//
//  RegionGridItem.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 10.08.2023.
//

import MapKit
import shared
import SwiftUI

/// Renders a grid item with overview information of given
/// `Region`.
struct RegionGridItem: View {
    // MARK: - Properties -

    /// Region to show
    let region: Region

    @State private var position = MapCameraPosition.automatic

    // MARK: - UI -

    var body: some View {
        VStack {
            // Map
            Map(position: $position)
                .disabled(true)
                .frame(height: /*@START_MENU_TOKEN@*/100/*@END_MENU_TOKEN@*/)

            // Text Box
            VStack {
                Text(region.id)
                    .foregroundStyle(.primary)
                    .font(.title)

                Text(region.name)
                    .foregroundStyle(.primary)
                    .font(.caption)
            }
            .padding([.leading, .trailing, .bottom], .init(4))
        }
        .overlay(
            RoundedRectangle(cornerRadius: 8)
                .stroke(Color.black, lineWidth: 1)
        )
        .onAppear {
            position = .region(
                .init(
                    center: region.coordinate.toLocationCoordinate(),
                    span: .init(latitudeDelta: 0.1, longitudeDelta: 0.1)
                )
            )
        }
    }
}
