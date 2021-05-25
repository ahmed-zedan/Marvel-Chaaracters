package com.zedan.example.marvelcharacters.domain.remote
import androidx.annotation.Keep

import com.squareup.moshi.Json
import com.zedan.example.marvelcharacters.domain.local.*
import com.zedan.example.marvelcharacters.domain.models.CharacterWithDetails
import com.zedan.example.marvelcharacters.domain.models.Image


@Keep
data class CharacterResponse(
    @Json(name = "code") val code: Int? = null, // 200
    @Json(name = "status") val status: String? = null, // Ok
    @Json(name = "copyright") val copyright: String? = null, // © 2021 MARVEL
    @Json(name = "attributionText") val attributionText: String? = null, // Data provided by Marvel. © 2021 MARVEL
    @Json(name = "attributionHTML") val attributionHTML: String? = null, // <a href="http://marvel.com">Data provided by Marvel. © 2021 MARVEL</a>
    @Json(name = "etag") val etag: String? = null, // c1de6d757e9484e8d1845e739dc70f149c70cf94
    @Json(name = "data") val `data`: Data? = null
)

@Keep
data class Data(
    @Json(name = "offset") val offset: Int? = null, // 0
    @Json(name = "limit") val limit: Int? = null, // 20
    @Json(name = "total") val total: Int? = null, // 1493
    @Json(name = "count") val count: Int? = null, // 20
    @Json(name = "results") val results: List<Result>? = null
)

@Keep
data class Result(
    @Json(name = "id") val id: Int? = null, // 1011334
    @Json(name = "name") val name: String? = null, // 3-D Man
    @Json(name = "description") val description: String? = null,
    @Json(name = "modified") val modified: String? = null, // 2014-04-29T14:18:17-0400
    @Json(name = "thumbnail") val thumbnail: Thumbnail? = null,
    @Json(name = "resourceURI") val resourceURI: String? = null, // http://gateway.marvel.com/v1/public/characters/1011334
    @Json(name = "comics") val comics: Comics? = null,
    @Json(name = "series") val series: Series? = null,
    @Json(name = "stories") val stories: Stories? = null,
    @Json(name = "events") val events: Events? = null,
    @Json(name = "urls") val urls: List<Url>? = null
){
    fun toCharacterEntity(): CharacterWithDetails{
        val character =  CharacterEntity(
            id = id?.toLong() ?: Long.MIN_VALUE,
            name = name,
            image = thumbnail?.let { Image(path = it.path, extension = it.extension) }
        )

        val comics = comics?.items?.mapNotNull { c ->
            c.resourceURI?.getIdAfterSlash()?.let { id ->  ComicEntity(characterId = character.id, id = id, name = c.name) }
        } ?: emptyList()

        val events = events?.items?.mapNotNull { e ->
            e.resourceURI?.getIdAfterSlash()?.let { id -> EventEntity(characterId = character.id, id = id, name = e.name)  }
        }

        val stories = stories?.items?.mapNotNull { s ->
            s.resourceURI?.getIdAfterSlash()?.let { id -> StoryEntity(characterId = character.id, id = id, name = s.name)  }
        }

        val series = series?.items?.mapNotNull { s ->
            s.resourceURI?.getIdAfterSlash()?.let { id -> SeriesEntity(characterId = character.id, id = id, name = s.name)  }
        }

        return CharacterWithDetails(
            character = character,
            comics = comics,
            series = series,
            stories = stories,
            events = events
        )
    }

    private fun String.getIdAfterSlash(): Long?{
        return substringAfterLast("/", "").toLongOrNull()
    }
}

@Keep
data class Thumbnail(
    @Json(name = "path") val path: String? = null, // http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784
    @Json(name = "extension") val extension: String? = null // jpg
)

@Keep
data class Comics(
    @Json(name = "available") val available: Int? = null, // 12
    @Json(name = "collectionURI") val collectionURI: String? = null, // http://gateway.marvel.com/v1/public/characters/1011334/comics
    @Json(name = "items") val items: List<ComicSummary>? = null,
    @Json(name = "returned") val returned: Int? = null // 12
)

@Keep
data class Series(
    @Json(name = "available") val available: Int? = null, // 3
    @Json(name = "collectionURI") val collectionURI: String? = null, // http://gateway.marvel.com/v1/public/characters/1011334/series
    @Json(name = "items") val items: List<SeriesSummary>? = null,
    @Json(name = "returned") val returned: Int? = null // 3
)

@Keep
data class Stories(
    @Json(name = "available") val available: Int? = null, // 21
    @Json(name = "collectionURI") val collectionURI: String? = null, // http://gateway.marvel.com/v1/public/characters/1011334/stories
    @Json(name = "items") val items: List<StoriesSummary>? = null,
    @Json(name = "returned") val returned: Int? = null // 20
)

@Keep
data class Events(
    @Json(name = "available") val available: Int? = null, // 1
    @Json(name = "collectionURI") val collectionURI: String? = null, // http://gateway.marvel.com/v1/public/characters/1011334/events
    @Json(name = "items") val items: List<EventsSummary>? = null,
    @Json(name = "returned") val returned: Int? = null // 1
)

@Keep
data class Url(
    @Json(name = "type") val type: String? = null, // detail
    @Json(name = "url") val url: String? = null // http://marvel.com/characters/74/3-d_man?utm_campaign=apiRef&utm_source=607f29ecd5a4570df7010de843ccab3e
)

@Keep
data class ComicSummary(
    @Json(name = "resourceURI") val resourceURI: String? = null, // http://gateway.marvel.com/v1/public/comics/21366
    @Json(name = "name") val name: String? = null // Avengers: The Initiative (2007) #14
)

@Keep
data class SeriesSummary(
    @Json(name = "resourceURI") val resourceURI: String? = null, // http://gateway.marvel.com/v1/public/series/1945
    @Json(name = "name") val name: String? = null // Avengers: The Initiative (2007 - 2010)
)

@Keep
data class StoriesSummary(
    @Json(name = "resourceURI") val resourceURI: String? = null, // http://gateway.marvel.com/v1/public/stories/19947
    @Json(name = "name") val name: String? = null, // Cover #19947
    @Json(name = "type") val type: String? = null // cover
)

@Keep
data class EventsSummary(
    @Json(name = "resourceURI") val resourceURI: String? = null, // http://gateway.marvel.com/v1/public/events/269
    @Json(name = "name") val name: String? = null // Secret Invasion
)