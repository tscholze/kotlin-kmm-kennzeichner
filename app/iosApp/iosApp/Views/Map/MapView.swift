//
//  MapView.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 18.04.23.
//  Copyright ©2023 Tobias Scholze. All rights reserved.
//

import MapKit
import shared
import SwiftUI

extension Ui.Features.Map {
    /// Renders a map-based representation of all available regions
    /// for license plate IDs.
    struct MapView: View {
        // MARK: - Private consants -

        private static let germanyCentered = MKCoordinateRegion(
            center: .init(latitude: 50.8113684, longitude: 10.7489082),
            span: .init(latitudeDelta: 8, longitudeDelta: 8)
        )

        // MARK: - Private properties -

        @ObservedObject private(set) var viewModel: ViewModel
        @State private var selectedRegion: Region?
        @State private var coordindate = germanyCentered

        // MARK: - UI -

        var body: some View {
            ZStack(alignment: .topTrailing) {
                Map(
                    coordinateRegion: $coordindate,
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
                            coordindate = region.coordinate.toCoordinateRegion(withSpanDelta: 1)
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
                .ignoresSafeArea(edges: [.top])
                .sheet(
                    item: $selectedRegion,
                    onDismiss: { selectedRegion = nil }
                ) { region in
                    SheetView(region: region)
                        .presentationDetents([.fraction(0.4)])
                }

                // Button
                Button {
                    coordindate = Self.germanyCentered
                } label: {
                    Image(systemName: "map.circle")
                        .resizable()
                        .frame(width: 30, height: 30)
                }
                .shadow(radius: 2)
                .padding(12)
            }
        }
    }
}

// MARK: - SheetView -

extension Ui.Features.Map.MapView {
    fileprivate struct SheetView: View {
        // MARK: - Private properties -

        private let region: Region
        @State private var coordindate: MKCoordinateRegion

        // MARK: - Init -

        /// Initializes a new view model with given repository.
        ///
        /// - Parameter repository: Required license plate repository
        init(region: Region) {
            self.region = region
            coordindate = region.coordinate.toCoordinateRegion(withSpanDelta: 0.05)
        }

        // MARK: - UI -

        var body: some View {
            VStack(alignment: .leading) {
                VStack(alignment: .leading, spacing: 8) {
                    // Texts
                    HStack {
                        Text(region.id)
                            .font(.largeTitle)
                            .fontDesign(.monospaced)

                        VStack(alignment: .leading) {
                            if let leader = region.leader {
                                Text("Region.Detail.Leader.Format \(leader)")
                                    .lineLimit(1)
                                    .font(.caption)
                            }

                            if let inhabitants = region.inhabitants {
                                Text("Region.Detail.Inhabitants.Format \(inhabitants)")
                                    .font(.caption)
                            }

                            if let area = region.area {
                                Text("Region.Detail.Area.Format \(area)")
                                    .font(.caption)
                            }
                        }
                    }

                    Text(region.name)
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

extension Ui.Features.Map.MapView {
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
