package com.masdiq.route.pelayananDokter.evaluasi

import com.masdiq.model.pelayananDokter.evaluasi.PemeriksaanKhusus
import com.masdiq.repository.pelayananDokter.evaluasi.PemeriksaanKhususRepository
import com.masdiq.template.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.pemeriksaanKhususRoute() {
    val pemeriksaanKhususRepository: PemeriksaanKhususRepository by inject()

    get("$URL_PEMERIKSAAN_KHUSUS/get-all") {
        val tabletList = pemeriksaanKhususRepository.getAllPemeriksaanKhusus()
        call.respond(
            DefaultResponse(
                "${HttpStatusCode.OK}",
                dataSuccessRetrieved, "${call.processingTimeMillis().times(0.001)} seconds", tabletList
            )
        )
    }

    get("$URL_PEMERIKSAAN_KHUSUS/get") { it ->
        val reqId = call.receive<PemeriksaanKhusus>().id
        val obj = pemeriksaanKhususRepository.getPemeriksaanKhusus(reqId)

        obj?.let {
            call.respond(
                DefaultResponse(
                    "${HttpStatusCode.OK}",
                    dataSuccessRetrieved, "${call.processingTimeMillis().times(0.001)} seconds", it
                )
            )
        } ?: call.respond(
            DefaultResponse(
                "${HttpStatusCode.NotFound}",
                dataNotFound, "${call.processingTimeMillis().times(0.001)} seconds", it
            )
        )
    }

    post("$URL_PEMERIKSAAN_KHUSUS/create-update") {
        val request = try {
            call.receive<PemeriksaanKhusus>()
        } catch (e: ContentTransformationException) {
            call.respond(
                DefaultResponse(
                    "${HttpStatusCode.BadRequest}",
                    dataJsonError, "${call.processingTimeMillis().times(0.001)} seconds", it
                )
            )
            return@post
        }

        if (pemeriksaanKhususRepository.createOrUpdatePemeriksaanKhusus(request)) {
            call.respond(
                DefaultResponse(
                    "${HttpStatusCode.OK}",
                    dataSuccessCreated, "${call.processingTimeMillis().times(0.001)} seconds", request
                )
            )
        } else {
            call.respond(
                DefaultResponse(
                    "${HttpStatusCode.Conflict}",
                    dataConflict, "${call.processingTimeMillis().times(0.001)} seconds", it
                )
            )
        }
    }

    delete("$URL_PEMERIKSAAN_KHUSUS/delete") post@{
        val request = try {
            call.receive<DefaultRequest>()
        } catch (e: ContentTransformationException) {
            call.respond(
                DefaultResponse(
                    "${HttpStatusCode.BadRequest}",
                    dataJsonError, "${call.processingTimeMillis().times(0.001)} seconds", it
                )
            )
            return@post
        }

        if (pemeriksaanKhususRepository.deletePemeriksaanKhusus(request.id)) {
            call.respond(
                DefaultResponse(
                    "${HttpStatusCode.OK}",
                    dataSuccessDeleted, "${call.processingTimeMillis().times(0.001)} seconds", request
                )
            )
        } else {
            call.respond(
                DefaultResponse(
                    "${HttpStatusCode.BadRequest}",
                    dataNotFound, "${call.processingTimeMillis().times(0.001)} seconds", it
                )
            )
        }
    }
}
