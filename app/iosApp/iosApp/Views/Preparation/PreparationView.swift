//
//  PreparationView.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 10.08.2023.
//

import shared
import SwiftUI

/// This view is reponsable for preparing all required data for the app.
struct PreparationView: View {
    // MARK: - Private properties -

    @EnvironmentObject private var appStore: AppStore

    // MARK: - UI -

    var body: some View {
        VStack {
            switch appStore.state {
            case .fetching:
                makePreparingUi()
            case .filled:
                makeFilledUi()
            case .failed:
                makeFailedUi()
            default:
                EmptyView()
            }
        }
        .task {
            appStore.fetchData()
        }
        .padding(60)
    }

    // MARK: - Private helpers -

    private func makePreparingUi() -> some View {
        VStack(spacing: 30) {
            // Image
            Image(systemName: "car.2")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .foregroundColor(.accentColor)
                .frame(width: /*@START_MENU_TOKEN@*/100/*@END_MENU_TOKEN@*/)

            // Text
            Text("Preparation.State.Preparing.Title")
                .multilineTextAlignment(.center)
                .font(.title)
                .foregroundStyle(.secondary)
        }
    }

    private func makeFilledUi() -> some View {
        VStack(spacing: 30) {
            // Image
            Image(systemName: "checkmark.circle")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .foregroundColor(.green)
                .frame(width: /*@START_MENU_TOKEN@*/100/*@END_MENU_TOKEN@*/)

            // Text
            Text("Preparation.State.Filled.Title")
                .multilineTextAlignment(.center)
                .font(.title)
                .foregroundStyle(.secondary)
        }
    }

    private func makeFailedUi() -> some View {
        ContentUnavailableView(
            label: { Text("Preparation.State.Failed.Title") },
            description: { Text("Preparation.State.Failed.Message") },
            actions: {
                VStack {
                    Button("Misc.Retry") {
                        Task {
                            appStore.fetchData()
                        }
                    }
                }
            }
        )
    }
}
