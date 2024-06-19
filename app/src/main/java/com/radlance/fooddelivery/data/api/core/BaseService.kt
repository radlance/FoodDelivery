package com.radlance.fooddelivery.data.api.core

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object BaseService {

    operator fun invoke(token: String? = null): Service {

        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        var client = OkHttpClient.Builder().addInterceptor(interceptor)

        token?.let {
            client = client.addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .header("Authorization", "Bearer token")
                    .build()
                return@addInterceptor chain.proceed(request)
            }
        }


        /** settings for working with the api without an ssl certificate */
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            }
            )
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            client.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            client.hostnameVerifier { _, _ -> true }
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        return retrofit.create(Service::class.java)
    }

    private const val BASE_URL = "https://192.168.0.108:5250/"
}