//
//  ListView.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 18.04.23.
//  Copyright ©2023 Tobias Scholze. All rights reserved.
//

import shared
import MapKit
import SwiftUI

extension Ui.Features.List {
    /// Renders a list-based representation of all available regions
    /// for license plate IDs.
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
                .searchable(text: $searchQuery, prompt: Text("ListView.Search.Placeholder"))
                .navigationTitle("ListView.Navigation.Title")
                .toolbar {
                    ToolbarItem {
                        Button {
                            UIApplication.shared.open(Constants.githubUrl)
                        } label: {
                            Image(systemName: "globe")
                        }
                    }
                }
            }
        }
    }
}

// MARK: - ViewModel -

extension Ui.Features.List.ListView {
    /// View model for a region list view.
    /// Subscribe to the `regions` property to get informed
    /// about fetched data items.
    @MainActor class ViewModel: ObservableObject {
        // MARK: - Internal properties -

        @Published var regions = [Region]()

        // MARK: - Private properties -

        private let repository: LicensePlateRepository

        // MARK: - Init -

        /// Initializes a new view model with given repository.
        ///
        /// - Parameter repository: Required license plate repository
        init(repository: LicensePlateRepository) {
            self.repository = repository

            Task { regions = try await repository.fetchRegions() }
        }

        // MARK: - Internal helper -

        /// Filters data set for given string
        ///
        /// - Parameter query: Search query
        func filteredRegions(for query: String) -> [Region] {
            return repository.regionsForSearchQuery(searchQuery: query)
        }
    }
}

private struct RegionListItemView: View {
    // MARK: - Private properties -

    private let region: Region
    @State private var coordindate: MKCoordinateRegion

    // MARK: - Init -

    init(region: Region) {
        self.region = region
        coordindate = region.coordinate.toCoordinateRegion(withSpanDelta: 0.05)
    }

    // MARK: - UI -

    var body: some View {
        HStack {
            // 1. Map
            Map(coordinateRegion: $coordindate)
                .frame(width: 125, height: 100)
                .disabled(true)

            // 2. Text container
            VStack(alignment: .leading) {
                // 2.1 Title container
                HStack {
                    Text(region.id)
                        .font(.largeTitle)
                        .fontDesign(.monospaced)

                    Text(region.name)
                        .lineLimit(2)
                        .font(.caption)
                }

                // 2.2. Meta container
                VStack(alignment: .leading) {
                    // 2.2.1 Leader
                    if let leader = region.leader {
                        Text("Region.Detail.Leader.Format \(leader)")
                            .lineLimit(1)
                            .font(.caption)
                            .foregroundStyle(.secondary)
                    }

                    // 2.2.2 Inhabitants
                    if let inhabitants = region.inhabitants {
                        Text("Region.Detail.Inhabitants.Format \(inhabitants)")
                            .font(.caption)
                            .foregroundStyle(.secondary)
                    }

                    // 2.2.3 Area
                    if let area = region.area {
                        Text("Region.Detail.Area.Format \(area)")
                            .font(.caption)
                            .foregroundStyle(.secondary)
                    }
                }
            }
            .padding([.leading, .trailing], 4)
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .background(
            RoundedRectangle(cornerRadius: 4)
                .fill(Color.white)
        )
        .overlay(
            RoundedRectangle(cornerRadius: 4.0)
                .stroke(Color.accentColor, lineWidth: 2)
        )
    }
}
