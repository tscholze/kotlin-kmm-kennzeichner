package io.github.tscholze.kennzeichner.data

import io.github.tscholze.kennzeichner.data.dto.RegionDTO

/**
 * Business model of a region.
 */
class Region(
    val id: String,
    val name: String,
    val coordinate: Coordinate,
    val inhabitants: Int?,
    val leader: String?,
    val area: Int?
) {
     companion object {
         /**
          * Creates a new Region object from DTO.
          *
          * @param dto: DTO to transfer from.
          * @return Created instance or null if transferring failed.
          */
         fun fromDto(dto: RegionDTO): Region? {
             // Validate DTO
             if (dto.lat == 0.0 || dto.long == 0.0) {
                 return null
             }

             // Create region instance
             return Region(
                 dto.id,
                 dto.name,
                 Coordinate(dto.lat, dto.long),
                 if (dto.inhabitants == 0) null else dto.inhabitants,
                 dto.leader,
                 if (dto.area == 0) null else dto.area,
             )
         }
     }
 }

data class Coordinate(
    val latitude: Double,
    val longitude: Double
)