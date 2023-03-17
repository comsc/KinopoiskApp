package com.example.newsproject.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Doc(
    val alternativeName: String?,
    val color: String?,
    @SerializedName("country_tbl")
    @Embedded
    val countries: Country?,
    val description: String?,
    @Embedded
    val externalId: ExternalId?,
    @SerializedName("genre_tbl")
    @Embedded
    val genres: Genre?,
    @PrimaryKey
    val id: Int?,
    @Embedded
    val logo: Logo?,
    val movieLength: Int?,
    val name: String?,
    @SerializedName("name_tbl")
    @Embedded
    val names: Name?,
    @Embedded
    val poster: Poster?,
    @Embedded
    val rating: Rating?,
    val shortDescription: String?,
    val type: String?,
    @Embedded
    val votes: Votes?,
    val year: Int?,
    var isFavorite: Boolean = false
):java.io.Serializable