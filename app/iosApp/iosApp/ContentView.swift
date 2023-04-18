import MapKit
import shared
import SwiftUI

struct ContentView: View {
    private let repository = LicensePlateRepository()

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
