package com.masdiq.repository.pelayananDokter.evaluasi

import com.masdiq.model.pelayananDokter.evaluasi.PerilakuBeresiko
import com.masdiq.model.persalinanIbu.pelayananPersalinan.BayiSaatLahir
import com.masdiq.repository.DATABASE
import org.bson.types.ObjectId
import org.litote.kmongo.eq

val colPerilakuBeresiko = DATABASE.getCollection<PerilakuBeresiko>()

class PerilakuBeresikoImplement : PerilakuBeresikoRepository {
    override suspend fun getPerilakuBeresiko(reqId: String): PerilakuBeresiko? {
        return colPerilakuBeresiko.findOneById(reqId)
    }

    override suspend fun createOrUpdatePerilakuBeresiko(perilakuBeresiko: PerilakuBeresiko): Boolean {
        val dataFound = colPerilakuBeresiko.findOneById(perilakuBeresiko.id) != null

        return if (dataFound) {
            colPerilakuBeresiko.updateOneById(perilakuBeresiko.id, perilakuBeresiko).wasAcknowledged()
        } else {
            perilakuBeresiko.id = ObjectId().toString()
            colPerilakuBeresiko.insertOne(perilakuBeresiko).wasAcknowledged()
        }
    }

    override suspend fun deletePerilakuBeresiko(reqId: String): Boolean {
        val dataDelete = colPerilakuBeresiko.findOne(BayiSaatLahir::id eq reqId)
        dataDelete?.let { tablet ->
            return colPerilakuBeresiko.deleteOneById(tablet.id).wasAcknowledged()
        } ?: return false
    }
}