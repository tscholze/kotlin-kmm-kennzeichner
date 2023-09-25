//
//  AppScreens.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 10.08.2023.
//

import shared
import SwiftUI

/// Describes all navigatable App screens
enum AppScreen {
    case regionMap
    case regionList
}

// MARK: - Identifiable -

extension AppScreen: Identifiable {
    var id: AppScreen { self }
}

// MARK: - Splittings -

extension AppScreen {
    /// List of tab items
    static var tabScreens = [
        AppScreen.regionList,
        AppScreen.regionMap,
    ]
}

// MARK: - UI -

extension AppScreen {
    /// Label representation of the screen,
    /// Used for e.g. the tab view item label
    @ViewBuilder
    var label: some View {
        switch self {
        case .regionMap:
            Label("Map.Navigation.Title", systemImage: "globe")
        case .regionList:
            Label("List.Navigation.Title", systemImage: "list.bullet.rectangle")
        }
    }

    /// Destination of the app screen aka the content of it.
    @ViewBuilder
    var destination: some View {
        switch self {
        case .regionMap:
            RegionMapNavigationStack()
        case .regionList:
            RegionListNavigationStack()
        }
    }
}
