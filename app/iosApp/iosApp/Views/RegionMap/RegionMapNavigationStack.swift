//
//  RegionMapNavigationStack.swift
//  Kennzeichner
//
//  Created by Tobias Scholze on 10.08.2023.
//

import MapKit
import shared
import SwiftUI

struct RegionMapNavigationStack: View {
    // MARK: - Constants -

    private static let germanyCentered = CLLocationCoordinate2D(
        latitude: 50.8113684,
        longitude: 10.7489082
    )

    // MARK: - Properties -

    @EnvironmentObject private var appStore: AppStore
    @State private var coordindate = germanyCentered
    @State private var selectedRegion: Region?

    // MARK: - UI -

    var body: some View {
        NavigationStack {
            ZStack(alignment: .topTrailing) {
                // Map
                Map {
                    ForEach(appStore.regions()) { region in
                        makeAnnotation(for: region)
                    }
                }

                // Button
                makeCenterButton()
            }
            .navigationTitle("Map.Navigation.Title")
            .onChange(of: selectedRegion, onSelectedRegionChanged(oldValue:newValue:))
            .sheet(
                item: $selectedRegion,
                onDismiss: { selectedRegion = nil }
            ) { region in
                SheetView(region: region)
                    .presentationDetents([.fraction(0.4)])
            }
        }
    }

    // MARK: - UI Builders -

    private func makeAnnotation(for region: Region) -> Annotation<Text, Button<some View>> {
        Annotation(coordinate: region.coordinate.toLocationCoordinate()) {
            Button {
                selectedRegion = region
            } label: {
                VStack {
                    Text(region.id)
                        .fontWeight(Font.Weight.bold)

                    Text(region.name)
                }
                .foregroundStyle(Color.black)
                .padding(8)
                .background {
                    RoundedRectangle(cornerRadius: 4, style: .continuous)
                        .fill(Color("TownSignYellow"))
                }
                .overlay(
                    RoundedRectangle(cornerRadius: 4, style: .continuous)
                        .strokeBorder(Color.black, lineWidth: 2)
                        .padding(4)
                        .shadow(radius: 3)
                )
            }
        } label: {
            // This is bogus ...
            Text("Map.Annotation.Label")
        }
    }

    private func makeCenterButton() -> some View {
        Button {
            coordindate = Self.germanyCentered
        } label: {
            Image(systemName: "map.circle")
                .resizable()
                .frame(width: 30, height: 30)
        }
        .padding(12)
    }
    
    // MARK: - Private helpers -
    
    private func onSelectedRegionChanged(oldValue: Region?, newValue: Region?) {
        if let selectedRegion = newValue {
            coordindate = selectedRegion.coordinate.toLocationCoordinate()
        }
        else {
            coordindate = Self.germanyCentered
        }
    }
}

private struct SheetView: View {
    // MARK: - Properties -

    let region: Region

    // MARK: - UI -

    var body: some View {
        VStack(alignment: .leading) {
            // Texts
            RegionMetaText(region: region)

            // Map with button
            Button {
                openMaps(for: region)
            } label: {
                Map(initialPosition: .camera(.init(centerCoordinate: region.coordinate.toLocationCoordinate(), distance: 1000)))
                    .disabled(true)
                    .ignoresSafeArea()
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
    }

    // MARK: - Private helper -

    private func openMaps(for region: Region) {
        let string = "maps://?sll=\(region.coordinate.latitude),\(region.coordinate.longitude)"
        guard let url = URL(string: string) else { return }
        UIApplication.shared.open(url)
    }
}
