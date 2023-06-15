package com.masdiq.repository.pemantauanIbu.ibuHamil

import com.masdiq.model.pemantauanIbu.ibuHamil.PemantauanHarianIbuHamil
import com.masdiq.model.persalinanIbu.pelayananPersalinan.BayiSaatLahir
import com.masdiq.repository.DATABASE
import org.bson.types.ObjectId
import org.litote.kmongo.eq

private val colPemantauanHarianIbuHamil = DATABASE.getCollection<PemantauanHarianIbuHamil>()

class PemantauanHarianIbuHamilImplement : PemantauanHarianIbuHamilRepository {
    override suspend fun getAllPemantauanHarianIbuHamil(): List<PemantauanHarianIbuHamil> {
        return colPemantauanHarianIbuHamil.find().toList()
    }

    override suspend fun getPemantauanHarianIbuHamil(reqId: String): PemantauanHarianIbuHamil? {
        return colPemantauanHarianIbuHamil.findOneById(reqId)
    }

    override suspend fun createOrUpdatePemantauanHarianIbuHamil(pemantauanHarianIbuHamil: PemantauanHarianIbuHamil): Boolean {
        val dataFound = colPemantauanHarianIbuHamil.findOneById(pemantauanHarianIbuHamil.id) != null

        return if (dataFound) {
            colPemantauanHarianIbuHamil.updateOneById(pemantauanHarianIbuHamil.id, pemantauanHarianIbuHamil)
                .wasAcknowledged()
        } else {
            pemantauanHarianIbuHamil.id = ObjectId().toString()
            colPemantauanHarianIbuHamil.insertOne(pemantauanHarianIbuHamil).wasAcknowledged()
        }
    }

    override suspend fun deletePemantauanHarianIbuHamil(reqId: String): Boolean {
        val dataDelete = colPemantauanHarianIbuHamil.findOne(BayiSaatLahir::id eq reqId)
        dataDelete?.let { tablet ->
            return colPemantauanHarianIbuHamil.deleteOneById(tablet.id).wasAcknowledged()
        } ?: return false
    }
}