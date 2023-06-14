package com.masdiq.model.pelayananDokter.evaluasi

import com.masdiq.model.pelayananDokter.PelayananDokter
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class KondisiKesehatan(
    val tanggal: String? = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString().trim(),
    val jam: String? = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString().trim(),
    val tinggiBadan: Int? = 0,
    val beratBadan: Int? = 0,
    val lingkarLengan: Int? = 0,
    val imt: Double? = 0.0,
    @BsonId
    var id: String = ObjectId().toString()
)
