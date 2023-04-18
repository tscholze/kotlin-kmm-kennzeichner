//
//  MapView.swift
//  iosApp
//
//  Created by Tobias Scholze on 18.04.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import MapKit
import shared
import SwiftUI

struct MapView: View {
    // MARK: - Private properties -

    @ObservedObject private(set) var viewModel: ViewModel

    @State private var selectedRegion: Region?

    @State private var initialCoordinate = MKCoordinateRegion(
        center: .init(latitude: 50.8113684, longitude: 10.7489082),
        span: .init(latitudeDelta: 8, longitudeDelta: 8)
    )

    // MARK: - UI -

    var body: some View {
        Map(
            coordinateRegion: $initialCoordinate,
            interactionModes: .all,
            showsUserLocation: true,
            annotationItems: viewModel.regions
        ) { region in

            MapAnnotation(
                coordinate: .init(
                    latitude: region.coordinate.latitude,
                    longitude: region.coordinate.longitude
                )
            ) {
                Button {
                    selectedRegion = region
                } label: {
                    VStack {
                        Text(region.id)
                            .fontWeight(Font.Weight.bold)
                            .foregroundColor(.black)

                        Text(region.name)
                            .foregroundColor(.black)
                    }
                    .padding(4)
                    .background {
                        RoundedRectangle(cornerRadius: 4, style: .continuous)
                            .fill(Color.yellow)
                    }
                    .overlay(
                        RoundedRectangle(cornerRadius: 4, style: .continuous)
                            .strokeBorder(Color.black, lineWidth: 2)
                    )
                }
            }
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity
        )
        .sheet(item: $selectedRegion, onDismiss: { selectedRegion = nil }) { region in
            NavigationView {
                Text(region.leader)
                    .toolbar {
                        ToolbarItem(placement: .primaryAction) {
                            Button {
                                selectedRegion = nil
                            } label: {
                                Image(systemName: "xmark.circle")
                            }
                        }
                    }
            }
            .presentationDetents([.medium])
        }
    }
}

// MARK: - ViewModel -

extension MapView {
    @MainActor class ViewModel: ObservableObject {
        // MARK: - Internal properties -

        @Published var regions = [Region]()

        // MARK: - Private properties -

        private let repository: LicensePlateRepository

        // MARK: - Init -

        init(repository: LicensePlateRepository) {
            self.repository = repository

            Task { regions = try await repository.fetchRegions() }
        }
    }
}
