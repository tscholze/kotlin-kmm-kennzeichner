import MapKit
import shared
import SwiftUI

/// Root ui view of the app.
struct ContentView: View {
    // MARK: - Private (shared) repositories -

    private let repository = LicensePlateRepository()

    // MARK: - UI -

    var body: some View {
        TabView {
            ListView(viewModel: .init(repository: repository))
                .tabItem {
                    Label(
                        "ListView.TabItem.Title",
                        systemImage: "list.bullet"
                    )
                }

            MapView(viewModel: .init(repository: repository))
                .tabItem {
                    Label(
                        "MapView.TabItem.Title",
                        systemImage: "map"
                    )
                }
        }
    }
}
