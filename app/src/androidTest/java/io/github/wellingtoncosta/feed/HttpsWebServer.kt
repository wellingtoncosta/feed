package io.github.wellingtoncosta.feed

import com.github.kittinunf.fuel.core.FuelManager
import okhttp3.mockwebserver.MockWebServer
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.HeldCertificate
import java.net.InetAddress

private fun String.asHost() = InetAddress.getByName(this).canonicalHostName

fun startHttpsServer() = createCertificateFor("localhost".asHost())
    .let { localhostCertificate ->
        MockWebServer().apply {
            useHttps(createServerCertificates(localhostCertificate).sslSocketFactory(), false)
        }.also {
            FuelManager.instance.apply {
                basePath = it.url("/").toString()
                socketFactory = createClientCertificates(localhostCertificate).sslSocketFactory()
            }
        }
    }

private fun createCertificateFor(host: String) =
    HeldCertificate.Builder()
        .addSubjectAlternativeName(host)
        .build()

private fun createServerCertificates(hostCertificate: HeldCertificate) =
    HandshakeCertificates.Builder()
        .heldCertificate(hostCertificate)
        .build()

private fun createClientCertificates(hostCertificate: HeldCertificate) =
    HandshakeCertificates.Builder()
        .addTrustedCertificate(hostCertificate.certificate)
        .build()
