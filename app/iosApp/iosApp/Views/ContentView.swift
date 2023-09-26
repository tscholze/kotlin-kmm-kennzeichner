//
//  ContentView.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 10.08.2023.
//

import shared
import SwiftUI

/// UI-related entry point of the app.
struct ContentView: View {
    // MARK: - Private properties -

    @ObservedObject private var appStore = AppStore()

    // MARK: - UI -

    var body: some View {
        Group {
            switch appStore.state {
            case .filled: AppTabView()
            default: PreparationView()
            }
        }
        .environmentObject(appStore)
    }
}
