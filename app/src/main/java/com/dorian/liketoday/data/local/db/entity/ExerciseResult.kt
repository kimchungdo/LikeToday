package com.dorian.liketoday.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dorian.liketoday.domain.type.BodyPart
import com.dorian.liketoday.domain.type.DetailedType
import com.dorian.liketoday.domain.type.ExerciseType
import java.time.OffsetDateTime
import java.util.UUID

@Entity
data class ExerciseResult(
    @PrimaryKey val dataUuid: String = UUID.randomUUID().toString(),
    val createdTime: OffsetDateTime,

    val exerciseType: ExerciseType,
    val detailedType: DetailedType,
    val stimulatePoint: List<BodyPart>,
    val totalDuration: Long,
    val workoutLogs: String //json
)