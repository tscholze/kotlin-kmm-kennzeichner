import MapKit
import shared
import SwiftUI

struct ContentView: View {
    // MARK: - Private (shared) repositories -

    private let repository = LicensePlateRepository()

    // MARK: - UI -

    var body: some View {
        TabView {
            ListView(viewModel: .init(repository: repository))
                .tabItem {
                    Label("Liste", systemImage: "list.bullet")
                }

            MapView(viewModel: .init(repository: repository))
                .tabItem {
                    Label("Karte", systemImage: "map")
                }
        }
    }
}
