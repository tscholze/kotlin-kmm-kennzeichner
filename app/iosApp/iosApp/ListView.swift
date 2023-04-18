//
//  ListView.swift
//  iosApp
//
//  Created by Tobias Scholze on 18.04.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import MapKit
import SwiftUI

struct ListView: View {
    // MARK: - Private properties -

    @ObservedObject private(set) var viewModel: ViewModel
    @State private var searchQuery = ""

    // MARK: - UI -

    var body: some View {
        NavigationView {
            ScrollView {
                LazyVStack {
                    ForEach(viewModel.filteredRegions(for: searchQuery)) { region in
                        RegionListItemView(region: region)
                    }
                }
                .frame(maxWidth: .infinity)
                .padding()
            }
            .searchable(text: $searchQuery, prompt: Text("Suche nach A oder Augsburg"))
            .background(Color.blue.opacity(0.2))
            .navigationTitle("Kennzeichen")
            .toolbar {
                ToolbarItem {
                    Button {
                        UIApplication.shared.open(URL(string: "https://github.com")!)
                    } label: {
                        Image(systemName: "globe")
                    }
                }
            }
        }
    }
}

// MARK: - ViewModel -

extension ListView {
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

        // MARK: - Internal helper -

        func filteredRegions(for query: String) -> [Region] {
            return repository.regionsForSearchQuery(searchQuery: query)
        }
    }
}

extension Region: Identifiable {}

struct RegionListItemView: View {
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
        HStack {
            Map(coordinateRegion: $coordindate)
                .frame(width: 125, height: 100)
                .disabled(true)

            VStack(alignment: .leading) {
                Text(region.id)
                    .font(.largeTitle)
                    .fontDesign(.monospaced)

                VStack(alignment: .leading) {
                    if region.leader.isEmpty == false {
                        Text("Bürgermeister*in: \(region.leader)")
                            .lineLimit(1)
                            .font(.caption)
                            .foregroundStyle(.secondary)
                    }

                    if region.inhabitants != 0 {
                        Text("Einwohner: \(region.inhabitants)")
                            .font(.caption)
                            .foregroundStyle(.secondary)
                    }

                    if region.area != 0 {
                        Text("Fläche: \(region.area) qkm")
                            .font(.caption)
                            .foregroundStyle(.secondary)
                    }
                }
            }
            .padding([.leading, .trailing], 4)
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .background(Color.white)
        .overlay(
            RoundedRectangle(cornerRadius: 4.0)
                .stroke(Color.blue, lineWidth: 1)
        )
    }
}
