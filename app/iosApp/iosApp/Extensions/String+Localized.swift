//
//  String+Localized.swift
//  iosApp
//
//  Created by Tobias Scholze on 19.04.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

extension String {
    var localized: String {
        return NSLocalizedString(self, comment: "")
    }
}
