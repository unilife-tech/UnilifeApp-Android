package unilife.com.unilife.socketConnection

import com.github.nkzawa.socketio.client.IO
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class SocketSSL {
    fun certificate(): IO.Options? {
        var opts: IO.Options? = null
        try {

            val mySSLContext = SSLContext.getInstance("TLS")
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }
            })

            mySSLContext.init(null, trustAllCerts, null)

            val myHostnameVerifier = HostnameVerifier { hostname, session -> true }

            opts = IO.Options()
            opts.forceNew=true
            opts.reconnection=true
            opts.sslContext = mySSLContext
            opts.hostnameVerifier = myHostnameVerifier
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return opts
    }
}