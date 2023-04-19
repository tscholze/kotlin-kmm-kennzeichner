import SwiftUI

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

enum Constants {
    static var githubUrl: URL {
        guard let url = URL(string: "https://github.com") else { fatalError("Could no happen") }
        return url
    }
}
