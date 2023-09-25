//
//  AppTabView.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 10.08.2023.
//

import shared
import SwiftUI

/// Renders a tabview-based app structure.
struct AppTabView: View {
    // MARK: - Propertiers -

    /// Selected screen
    @State private var selection: AppScreen? = .regionList

    // MARK: - UI -

    var body: some View {
        TabView(selection: $selection) {
            ForEach(AppScreen.tabScreens) { screen in
                screen.destination
                    .tag(screen as AppScreen?)
                    .tabItem { screen.label }
            }
        }
    }
}
