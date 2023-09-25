//
//  RegionMetaText.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 10.08.2023.
//

import shared
import SwiftUI

struct RegionMetaText: View {
    // MARK: - Properties -

    let region: Region

    // MARK: - UI -

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            // Texts
            HStack(spacing: 12) {
                Text(region.id)
                    .font(.largeTitle)
                    .fontDesign(.monospaced)

                VStack(alignment: .leading) {
                    if let leader = region.leader {
                        Text("Region.Detail.Leader.Format \(leader)")
                            .lineLimit(1)
                            .font(.caption)
                    }

                    if let inhabitants = region.inhabitants {
                        Text("Region.Detail.Inhabitants.Format \(inhabitants)")
                            .font(.caption)
                    }

                    if let area = region.area {
                        Text("Region.Detail.Area.Format \(area)")
                            .font(.caption)
                    }
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding()
    }
}
