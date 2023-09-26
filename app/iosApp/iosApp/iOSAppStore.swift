//
//  iOSAppStore.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 26.09.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import Foundation

/// A store that contains all app-wide persisted data.
/// It works as a wrapper around the KMP repository.
@MainActor
class AppStore: ObservableObject {
    // MARK: - Internal properties -

    @Published var state = AppStoreState.empty

    // MARK: - Properties -

    private let repository = LicensePlateRepository()

    // MARK: - Internal methods -

    /// Tries to fetch data from remote.
    /// Result of the try is represented in the observable `state` property.
    func fetchData() {
        state = .fetching

        repository.fetchRegions { [weak self] regions, error in
            DispatchQueue.main.async {
                if regions == nil || regions?.isEmpty == true {
                    self?.state = .failed
                }

                else if error != nil {
                    self?.state = .failed
                }

                else {
                    self?.state = .filled
                }
            }
        }
    }

    /// Gets fetched regions for optional search query
    ///
    /// - Parameter query: Search query
    /// - Returns: Found regions, if search string is empty, all will be returned.
    func regions(forSearchQuery query: String = "") -> [Region] {
        return repository.regionsForSearchQuery(searchQuery: query).filter { $0.id.starts(with: "W") == false }
    }

    // MARK: - State -

    /// Represents all states the app store could have.
    enum AppStoreState {
        /// Initial value
        case empty

        /// Store is fetching data
        case fetching

        /// Store is filled (aka prepared)
        case filled

        /// Some store actions failed
        case failed
    }
}
