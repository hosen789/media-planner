package com.example.data.repository

import com.example.BuildConfig
import com.example.data.model.BrandProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiCampaignService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    fun isConfigured(): Boolean {
        val key = BuildConfig.GEMINI_API_KEY
        return key.isNotBlank() && key != "MY_GEMINI_API_KEY"
    }

    suspend fun generateCreativeEnhancement(profile: BrandProfile): Result<String> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (!isConfigured()) {
            return@withContext Result.failure(
                IllegalStateException("کلید هوش مصنوعی در تنظیمات ست نشده است. در پنل Secrets اضافه کنید.")
            )
        }

        try {
            val prompt = """
                شما یک مدیر ارشد بازاریابی و استراتژیست رسانه‌ای خبره هستید.
                اطلاعات برند مشتری به شرح زیر است:
                - نام برند: ${profile.brandName}
                - حوزه فعالیت: ${profile.industry}
                - محدوده جغرافیایی هدف: ${profile.geography}
                - هدف اصلی کمپین: ${profile.mainGoal}
                - گروه مخاطبان ۱: ${profile.audienceGroup1}
                - گروه مخاطبان ۲: ${profile.audienceGroup2}
                - بودجه: ${profile.budget} تومان
                
                لطفاً یک بسته خلاقانه شامل موارد زیر به زبان فارسی شیک و حرفه‌ای ارائه دهید:
                ۱. سه شعار تبلیغاتی خلاقانه و گیرا (Slogan)
                ۲. قلاب‌های مناسبتی تقویم شمسی و قمری (مانند نوروز، فطر، اعیاد، مناسبت‌های فصلی مرتبط با این برند)
                ۳. یک ایده وایرال برای پست شبانه کانال‌های تلگرام با نرخ تعامل بالا
                ۴. توصیه استراتژیک برای بیشینه‌سازی نرخ تبدیل (Conversion) در این صنعت
            """.trimIndent()

            val jsonBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                }
                put("contents", contents)
            }

            val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("خطا در ارتباط با هوش مصنوعی: ${response.code}"))
            }

            val parsed = JSONObject(responseBody)
            val candidates = parsed.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text")

            if (text.isNullOrBlank()) {
                Result.failure(Exception("پاسخی از هوش مصنوعی دریافت نشد."))
            } else {
                Result.success(text)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
