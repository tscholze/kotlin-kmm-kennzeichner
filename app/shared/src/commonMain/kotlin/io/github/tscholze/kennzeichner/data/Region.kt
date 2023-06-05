package io.github.tscholze.kennzeichner.data

import io.github.tscholze.kennzeichner.data.dto.RegionDTO

/**
 * Business model of a region.
 */
class Region(
    val id: String,
    val name: String,
    val coordinate: Coordinate,
    val inhabitants: Int,
    val leader: String,
    val area: Int
) {
     companion object {
         /**
          * Creates a new Region object from DTO.
          *
          * @param dto: DTO to transfer from.
          * @return Created instance or null if transferring failed.
          */
         fun fromDto(dto: RegionDTO): Region? {
             return try {
                 Region(
                     dto.id,
                     dto.name,
                     Coordinate.fromDto(dto),
                     Int.fromDtoString(dto.inhabitants),
                     dto.leader,
                     Int.fromDtoString(dto.area)
                 )
             } catch (error: Exception) {
                 null
             }
         }
     }
 }

data class Coordinate(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
       @Throws(NumberFormatException::class)
        fun fromDto(dto: RegionDTO): Coordinate {
           val latitude = dto.lat.replace(",", ".").toDouble()
           val longitude = dto.long.replace(",", ".").toDouble()

           if(latitude == 0.0 || longitude == 0.0) {
               throw NumberFormatException("Lat or Long value of 0.0 is not supported.")
           }

            return Coordinate(
                latitude,
                longitude
            )
        }
    }
}

@Throws(NumberFormatException::class)
private fun Int.Companion.fromDtoString(value: String): Int {
    val intValue = value
        .replace(",", "")
        .replace(".", "")
        .toInt()

    return if(intValue == 0) {
      throw NumberFormatException("Int value of zero is not supported.")
    } else {
        intValue
    }
}