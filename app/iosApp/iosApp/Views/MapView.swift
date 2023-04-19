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
                    .padding(8)
                    .background {
                        RoundedRectangle(cornerRadius: 4, style: .continuous)
                            .fill(Color("TownSignYellow"))
                    }
                    .overlay(
                        RoundedRectangle(cornerRadius: 4, style: .continuous)
                            .strokeBorder(Color.black, lineWidth: 2)
                            .padding(4)
                    )
                }
            }
        }
        .ignoresSafeArea()
        .sheet(item: $selectedRegion, onDismiss: { selectedRegion = nil }) { region in
            SheetView(region: region)
                .presentationDetents([.medium])
        }
    }
}

extension MapView {
    struct SheetView: View {
        // MARK: - Private properties -

        private let region: Region
        @State private var coordindate: MKCoordinateRegion

        // MARK: - Init -

        init(region: Region) {
            self.region = region
            coordindate = .init(
                center: .init(latitude: region.coordinate.latitude, longitude: region.coordinate.longitude),
                span: .init(latitudeDelta: 0.05, longitudeDelta: 0.05)
            )
        }

        // MARK: - UI -

        var body: some View {
            VStack(alignment: .leading) {
                // Texts
                HStack {
                    Text(region.id)
                        .font(.largeTitle)
                        .fontDesign(.monospaced)

                    VStack(alignment: .leading) {
                        if region.leader.isEmpty == false {
                            Text("Bürgermeister*in: \(region.leader)")
                                .lineLimit(1)
                                .font(.caption)
                        }

                        if region.inhabitants != 0 {
                            Text("Einwohner: \(region.inhabitants)")
                                .font(.caption)
                        }

                        if region.area != 0 {
                            Text("Fläche: \(region.area) qkm")
                                .font(.caption)
                        }
                    }
                }
                .padding()

                // Map with button
                Button {
                    openMaps(for: region)
                } label: {
                    Map(coordinateRegion: $coordindate)
                        .disabled(true)
                        .ignoresSafeArea()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                }
            }
        }

        // MARK: - Private helper -

        private func openMaps(for region: Region) {
            let string = "maps://?sll=\(region.coordinate.latitude),\(region.coordinate.longitude)"
            guard let url = URL(string: string) else { return }
            UIApplication.shared.open(url)
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
