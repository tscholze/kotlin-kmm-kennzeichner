//
//  RegionListDetail.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 10.08.2023.
//

import MapKit
import shared
import SwiftUI

struct RegionListDetail: View {
    // MARK: - Properties -

    let region: Region

    // MARK: - UI -

    var body: some View {
        VStack {
            // Text Meta Container
            RegionMetaText(region: region)

            // Map
            Map(
                initialPosition: .camera(
                    .init(
                        centerCoordinate: region.coordinate.toLocationCoordinate(),
                        distance: 1000
                    )
                )
            )
            .disabled(true)
            .ignoresSafeArea()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
        .navigationTitle(region.name)
    }
}

#Preview {
    Text("Reenable it")
    // RegionListDetail()
}
