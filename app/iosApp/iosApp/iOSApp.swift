import SwiftUI

/// Entry point of the runable.
@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

// MARK: - App-wide constants -

enum Constants {
    static var githubUrl: URL {
        guard let url = URL(string: "https://github.com") else { fatalError("Could no happen") }
        return url
    }
}
