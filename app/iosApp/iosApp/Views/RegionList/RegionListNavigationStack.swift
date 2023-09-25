//
//  RegionListNavigationStack.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 10.08.2023.
//

import shared
import SwiftUI

/// Flow for the list feature
struct RegionListNavigationStack: View {
    // MARK: - Private properties -

    @EnvironmentObject private var appStore: AppStore
    @State private var searchText = ""

    // MARK: - UI -

    var body: some View {
        NavigationStack {
            ScrollView {
                LazyVGrid(columns: [.init(.adaptive(minimum: 150), alignment: .top)], spacing: 20) {
                    ForEach(appStore.regions(forSearchQuery: searchText)) { region in
                        NavigationLink {
                            RegionListDetail(region: region)
                        } label: {
                            RegionGridItem(region: region)
                        }
                    }
                }
                .padding()
                .searchable(text: $searchText)
                .navigationTitle("List.Navigation.Title")
            }
        }
    }
}
