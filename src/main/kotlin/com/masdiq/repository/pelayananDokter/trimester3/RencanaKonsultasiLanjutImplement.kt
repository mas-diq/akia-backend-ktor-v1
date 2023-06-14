package com.masdiq.repository.pelayananDokter.trimester3

import com.masdiq.model.pelayananDokter.trimester3.RencanaKonsultasiLanjut
import com.masdiq.model.persalinanIbu.pelayananPersalinan.BayiSaatLahir
import com.masdiq.repository.DATABASE
import org.bson.types.ObjectId
import org.litote.kmongo.eq

val colRencanaKonsultasiLanjut = DATABASE.getCollection<RencanaKonsultasiLanjut>()

class RencanaKonsultasiLanjutImplement : RencanaKonsultasiLanjutRepository {
    override suspend fun getRencanaKonsultasiLanjut(reqId: String): RencanaKonsultasiLanjut? {
        return colRencanaKonsultasiLanjut.findOneById(reqId)
    }

    override suspend fun createOrUpdateRencanaKonsultasiLanjut(rencanaKonsultasiLanjut: RencanaKonsultasiLanjut): Boolean {
        val dataFound = colRencanaKonsultasiLanjut.findOneById(rencanaKonsultasiLanjut.id) != null

        return if (dataFound) {
            colRencanaKonsultasiLanjut.updateOneById(rencanaKonsultasiLanjut.id, rencanaKonsultasiLanjut)
                .wasAcknowledged()
        } else {
            rencanaKonsultasiLanjut.id = ObjectId().toString()
            colRencanaKonsultasiLanjut.insertOne(rencanaKonsultasiLanjut).wasAcknowledged()
        }
    }

    override suspend fun deleteRencanaKonsultasiLanjut(reqId: String): Boolean {
        val dataDelete = colRencanaKonsultasiLanjut.findOne(BayiSaatLahir::id eq reqId)
        dataDelete?.let { tablet ->
            return colRencanaKonsultasiLanjut.deleteOneById(tablet.id).wasAcknowledged()
        } ?: return false
    }
}