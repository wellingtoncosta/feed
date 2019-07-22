package io.github.wellingtoncosta.feed

import com.github.kittinunf.fuel.core.FuelManager
import okhttp3.mockwebserver.MockWebServer
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.HeldCertificate
import java.net.InetAddress

object WebServer {

    lateinit var server: MockWebServer private set

    fun start() {
        server = MockWebServer().apply {
            FuelManager.instance.basePath = url("/").toString()
        }
    }

    fun startHttps() {
        val localhost = InetAddress.getByName("localhost").canonicalHostName

        val localhostCertificate = HeldCertificate.Builder()
            .addSubjectAlternativeName(localhost)
            .build()

        val serverCertificates = HandshakeCertificates.Builder()
            .heldCertificate(localhostCertificate)
            .build()

        server = MockWebServer()

        server.useHttps(serverCertificates.sslSocketFactory(), false)

        val clientCertificates = HandshakeCertificates.Builder()
            .addTrustedCertificate(localhostCertificate.certificate)
            .build()

        FuelManager.instance.apply {
            basePath = server.url("/").toString()

            socketFactory = clientCertificates.sslSocketFactory()
        }

    }

    fun shutdown() {
        server.shutdown()
    }

}
