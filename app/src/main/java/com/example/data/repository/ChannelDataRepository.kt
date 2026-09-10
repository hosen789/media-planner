package com.example.data.repository

import com.example.data.model.ChannelCategory
import com.example.data.model.ProductionItem
import com.example.data.model.TelegramChannel

object ChannelDataRepository {

    val allChannels: List<TelegramChannel> = listOf(
        TelegramChannel(1, "اخبار تهران", "https://t.me/akhbartehran", 950_000, 48_000, 1_800_000, 4_000_000, 2_000_000, 1_000_000, 1_300_000, ChannelCategory.TEHRAN_ALBORZ, "تهران"),
        TelegramChannel(2, "اخبار البرز", "https://t.me/akhbare_Alborz", 61_800, 1_900, 700_000, 2_000_000, 1_500_000, 500_000, 1_000_000, ChannelCategory.TEHRAN_ALBORZ, "البرز"),
        TelegramChannel(3, "فردای تهران", "https://t.me/fardaye_tehrann", 42_100, 1_100, 800_000, 1_600_000, 800_000, null, null, ChannelCategory.TEHRAN_ALBORZ, "تهران"),
        TelegramChannel(4, "خبرفوری", "https://t.me/akhbarefori", 2_350_000, 85_000, 7_450_000, 18_900_000, 7_500_000, null, null, ChannelCategory.NATIONAL, "سراسری"),
        TelegramChannel(5, "تیتر تجارت", "https://t.me/titretejarat", 310_000, 12_000, 1_000_000, 2_000_000, 1_500_000, null, null, ChannelCategory.BUSINESS, "اقتصادی"),
        TelegramChannel(6, "علم و فناوری", "https://t.me/it_24", 120_000, 5_500, 250_000, 500_000, 300_000, null, null, ChannelCategory.TECH, "فناوری"),
        TelegramChannel(7, "اخبار خوزستان", "https://t.me/Akhbar_khozestan", 280_000, 14_000, 430_000, 1_290_000, 530_000, null, null, ChannelCategory.PROVINCE, "خوزستان"),
        TelegramChannel(8, "اخبار اصفهان", "https://t.me/akhbareisfahan", 450_000, 25_000, 630_000, 1_890_000, 830_000, null, null, ChannelCategory.PROVINCE, "اصفهان"),
        TelegramChannel(9, "اخبار سیستان و بلوچستان", "https://t.me/Akhbar_sob", 190_000, 9_500, 330_000, 990_000, 430_000, null, null, ChannelCategory.PROVINCE, "سیستان و بلوچستان"),
        TelegramChannel(10, "اخبار کرمانشاه", "https://t.me/akhbare_kermanshah", 160_000, 8_000, 230_000, 690_000, 330_000, null, null, ChannelCategory.PROVINCE, "کرمانشاه"),
        TelegramChannel(11, "اخبار گیلان", "https://t.me/akhbaregilan", 240_000, 12_000, 380_000, 1_140_000, 430_000, null, null, ChannelCategory.PROVINCE, "گیلان"),
        TelegramChannel(12, "اخبار مازندران", "https://t.me/AkhbarMazandaran", 310_000, 16_000, 430_000, 1_290_000, 480_000, null, null, ChannelCategory.PROVINCE, "مازندران"),
        TelegramChannel(13, "اخبار آذربایجان شرقی", "https://t.me/Azarbaijan_sharghi", 360_000, 19_000, 530_000, 1_590_000, 630_000, null, null, ChannelCategory.PROVINCE, "آذربایجان شرقی"),
        TelegramChannel(14, "اخبار فارس", "https://t.me/Akhbarfars", 330_000, 18_000, 430_000, 1_290_000, 530_000, null, null, ChannelCategory.PROVINCE, "فارس"),
        TelegramChannel(15, "اخبار یزد", "https://t.me/Akhbar_yazd", 140_000, 7_000, 230_000, 690_000, 330_000, null, null, ChannelCategory.PROVINCE, "یزد"),
        TelegramChannel(16, "اخبار هرمزگان", "https://t.me/akhbare_hormozgan", 150_000, 7_500, 230_000, 690_000, 280_000, null, null, ChannelCategory.PROVINCE, "هرمزگان"),
        TelegramChannel(17, "اخبار سمنان", "https://t.me/akhbar_semnan", 110_000, 5_000, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "سمنان"),
        TelegramChannel(18, "اخبار گلستان", "https://t.me/Akhbaregolestan", 160_000, 8_000, 230_000, 690_000, 330_000, null, null, ChannelCategory.PROVINCE, "گلستان"),
        TelegramChannel(19, "اخبار ایلام", "https://t.me/akhbarilam", 95_000, 4_500, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "ایلام"),
        TelegramChannel(20, "اخبار همدان", "https://t.me/Akhbarehamedan", 130_000, 6_500, 180_000, 540_000, 280_000, null, null, ChannelCategory.PROVINCE, "همدان"),
        TelegramChannel(21, "اخبار لرستان", "https://t.me/akhbarlorestan", 140_000, 7_000, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "لرستان"),
        TelegramChannel(22, "اخبار کردستان", "https://t.me/akhbarkordestan", 145_000, 7_200, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "کردستان"),
        TelegramChannel(23, "اخبار مرکزی", "https://t.me/akhbar_markazi", 125_000, 6_000, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "مرکزی"),
        TelegramChannel(24, "اخبار آذربایجان غربی", "https://t.me/azarbaijan_gharbi", 170_000, 8_500, 230_000, 690_000, 280_000, null, null, ChannelCategory.PROVINCE, "آذربایجان غربی"),
        TelegramChannel(25, "اخبار خراسان شمالی", "https://t.me/akhbarkhorasanshomali", 105_000, 5_200, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "خراسان شمالی"),
        TelegramChannel(26, "اخبار کرمان", "https://t.me/kerman_news", 260_000, 13_000, 430_000, 1_290_000, 530_000, null, null, ChannelCategory.PROVINCE, "کرمان"),
        TelegramChannel(27, "اخبار چهارمحال بختیاری", "https://t.me/akhbarchaharmahalvabakhtiari", 98_000, 4_800, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "چهارمحال بختیاری"),
        TelegramChannel(28, "اخبار خراسان جنوبی", "https://t.me/akhbarkhorasanjonubi", 135_000, 6_800, 230_000, 690_000, 330_000, null, null, ChannelCategory.PROVINCE, "خراسان جنوبی"),
        TelegramChannel(29, "اخبار قم", "https://t.me/Akhbareghom", 180_000, 9_000, 230_000, 690_000, 280_000, null, null, ChannelCategory.PROVINCE, "قم"),
        TelegramChannel(30, "اخبار قزوین", "https://t.me/Akhbarghazvin", 140_000, 7_000, 230_000, 690_000, 330_000, null, null, ChannelCategory.PROVINCE, "قزوین"),
        TelegramChannel(31, "اخبار اردبیل", "https://t.me/Akhbarardebill", 130_000, 6_500, 210_000, 630_000, 280_000, null, null, ChannelCategory.PROVINCE, "اردبیل"),
        TelegramChannel(32, "اخبار زنجان", "https://t.me/Akhbarzanjan", 115_000, 5_800, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "زنجان"),
        TelegramChannel(33, "اخبار بوشهر", "https://t.me/Akhbarboushehr", 125_000, 6_200, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "بوشهر"),
        TelegramChannel(34, "اخبار کهگیلویه و بویر احمد", "https://t.me/akhbar_Kohgiluyevaboyerahmad", 92_000, 4_600, 180_000, 540_000, 230_000, null, null, ChannelCategory.PROVINCE, "کهگیلویه و بویر احمد"),
        TelegramChannel(35, "اخبار کیش", "https://t.me/akhbare_kish", 110_000, 6_000, 200_000, 600_000, 300_000, null, null, ChannelCategory.PROVINCE, "کیش و مناطق آزاد"),
        TelegramChannel(36, "هم صدا", "https://t.me/hamseair", 1_200_000, 18_000, 4_000_000, 8_000_000, 6_000_000, null, null, ChannelCategory.NATIONAL, "سراسری"),
        TelegramChannel(37, "خبرفردا", "https://t.me/khabarfarda_ir", 1_800_000, 22_000, 4_000_000, 7_000_000, 6_000_000, null, null, ChannelCategory.NATIONAL, "سراسری"),
        TelegramChannel(38, "ورزش فوری", "https://t.me/fori_sport", 66_000, 8_500, 700_000, 1_200_000, 1_500_000, null, null, ChannelCategory.SPORTS, "ورزشی"),
        TelegramChannel(39, "اکو نیوز", "https://t.me/econews_net", 930_000, 20_000, 4_000_000, 7_000_000, 6_000_000, null, null, ChannelCategory.BUSINESS, "اقتصادی"),
        TelegramChannel(40, "روزنیوز", "https://t.me/ruz_news1", 620_000, 24_000, 1_500_000, 2_500_000, 2_000_000, 1_500_000, null, ChannelCategory.NATIONAL, "سراسری"),
        TelegramChannel(41, "خبرهای فوری و مهم", "https://t.me/khabarforui", 580_000, 22_000, 1_500_000, 2_500_000, 2_000_000, 1_500_000, null, ChannelCategory.NATIONAL, "سراسری"),
        TelegramChannel(42, "ایران تایمز", "https://t.me/irantimes_com", 720_000, 28_000, 2_000_000, 3_000_000, 2_500_000, 2_000_000, null, ChannelCategory.NATIONAL, "سراسری"),
        TelegramChannel(43, "عصرفردا", "https://t.me/asrefarda_ir", 690_000, 26_000, 2_000_000, 3_000_000, 2_500_000, 2_000_000, null, ChannelCategory.NATIONAL, "سراسری"),
        TelegramChannel(44, "جریان", "https://t.me/jarian_com", 850_000, 32_000, 2_900_000, 5_000_000, 3_500_000, 2_900_000, null, ChannelCategory.NATIONAL, "سراسری"),
        TelegramChannel(45, "اخبارمشهد", "https://t.me/Akhbarmashhad", 890_000, 45_000, 5_500_000, 9_500_000, 6_000_000, null, null, ChannelCategory.MASHHAD, "مشهد"),
        TelegramChannel(46, "حوادث مشهد", "https://t.me/havadesmashhad", 67_000, 10_000, 800_000, 1_600_000, 600_000, null, null, ChannelCategory.MASHHAD, "مشهد"),
        TelegramChannel(47, "بانوان مشهد", "https://t.me/banovanmashaad", 14_500, 2_200, 400_000, 800_000, 600_000, null, null, ChannelCategory.MASHHAD, "مشهد"),
        TelegramChannel(48, "اجتماعی مشهد", "https://t.me/ejtemaeemashhad", 13_400, 1_800, 400_000, 800_000, 600_000, null, null, ChannelCategory.MASHHAD, "مشهد"),
        TelegramChannel(49, "آب و هوای مشهد", "https://t.me/HavashenasiMashhad", 32_800, 4_800, 400_000, 800_000, 600_000, null, null, ChannelCategory.MASHHAD, "مشهد"),
        TelegramChannel(50, "اقتصاد مشهد", "https://t.me/EghtesadMashhad", 12_700, 2_100, 400_000, 800_000, 600_000, null, null, ChannelCategory.MASHHAD, "مشهد"),
        TelegramChannel(51, "صدای خراسان", "https://t.me/sedayekhorasaniha", 12_000, 1_900, 400_000, 800_000, 600_000, null, null, ChannelCategory.MASHHAD, "مشهد"),
        TelegramChannel(52, "فردای مشهد", "https://t.me/fardaye_mashhad", 397_000, 24_000, 600_000, 1_200_000, 900_000, null, null, ChannelCategory.MASHHAD, "مشهد"),
        TelegramChannel(53, "سیاسی مشهد", "https://t.me/siasimashhad", 7_400, 900, 400_000, 800_000, 600_000, null, null, ChannelCategory.MASHHAD, "مشهد")
    )

    val standardProductionItems: List<ProductionItem> = listOf(
        ProductionItem("real_motion_teaser", "تیزر رئال موشن حرفه‌ای (۴۵ تا ۹۰ ثانیه)", "سناریونویسی داستانی، فیلم‌برداری رئال با بازیگر، تدوین موشن و صداگذاری تخصصی", 45_000_000L, true),
        ProductionItem("motion_graphic", "موشن گرافیک اختصاصی (۴۵ تا ۹۰ ثانیه)", "تصویرسازی دوبعدی اختصاصی، نریشن گوینده حرفه‌ای و انیمیت تخصصی برند", 25_000_000L, true),
        ProductionItem("reels_short_video", "ویدئو کوتاه موقعیت‌محور / ریلز (۳۰ تا ۶۰ ثانیه)", "روایت سناریو دغدغه مخاطب، رفع چالش و نمایش سریع راهکار در فضایی صمیمی", 10_000_000L, true),
        ProductionItem("infographic_roadmap", "اینفوگرافیک اختصاصی و نقشه راه خدمات", "طراحی داده‌محور مزیت‌های کلیدی برند در قالب نقشه یکپارچه قابل اشتراک", 7_000_000L, true),
        ProductionItem("banner_set", "ست بنرهای گرافیکی و پوستر دیجیتال (۳ سایز)", "بنر استوری، پست تلگرام، پوستر با رعایت کال‌تو‌اکشن (CTA) و گایدلاین هویت بصری", 2_500_000L, true),
        ProductionItem("video_comment", "ویدئو کامنت و تحلیل روز (۱۲۰ تا ۲۴۰ ثانیه)", "تحلیل و پاسخ کارشناسی به دغدغه و ابهامات ذهنی مخاطبان با زبانی ساده و صمیمی", 11_000_000L, false),
        ProductionItem("reportage_copy", "نگارش رپرتاژ آگهی تحلیلی و کپی‌رایتینگ", "نگارش محتوای ارزش‌آفرین و سئو شده بر اساس اصول روانشناسی مخاطب با ۳ لینک اختصاصی", 2_000_000L, true),
        ProductionItem("talk_show_studio", "برنامه استودیویی و میزگرد تخصصی (۱۵ تا ۳۰ دقیقه)", "برگزاری میزگرد با کارشناسان و مدیران همراه با ۵ کات ویدیویی برگزیده برای شبکه‌ها", 30_000_000L, false),
        ProductionItem("documentary_story", "مستند کوتاه برند و روایت دستاوردها", "مستندسازی رویداد، مصاحبه با مشتریان و مدیران جهت تثبیت هویت و اعتبار عمیق", 40_000_000L, false),
        ProductionItem("social_monitoring", "رصد، پایش بازخوردها و تحلیل هفتگی رسانه‌ها", "پایش کامنت‌ها، سوژه‌یابی، تحلیل لحن افکار عمومی و بهینه‌سازی مداوم محتوا", 5_000_000L, false),
        ProductionItem("sms_broadcast", "کمپین ارسال انبوه پیامک منطقه‌ای و اصناف (۱۰ هزار)", "ارسال با خطوط خدماتی بدون بلک‌لیست به مخاطبین تفکیک‌شده شهری و شغلی", 2_000_000L, true)
    )

    fun getRecommendedChannels(
        geography: String,
        industry: String,
        budgetLimit: Long
    ): List<TelegramChannel> {
        val filtered = allChannels.filter { channel ->
            when {
                geography.contains("مشهد") && channel.category == ChannelCategory.MASHHAD -> true
                geography.contains("تهران") && channel.category == ChannelCategory.TEHRAN_ALBORZ -> true
                geography.contains("سراسری") && (channel.category == ChannelCategory.NATIONAL || channel.category == ChannelCategory.BUSINESS) -> true
                industry.contains("تجارت") || industry.contains("فروشگاه") || industry.contains("اقتصاد") -> channel.category == ChannelCategory.BUSINESS || channel.category == ChannelCategory.NATIONAL
                industry.contains("فناوری") || industry.contains("استارتاپ") -> channel.category == ChannelCategory.TECH || channel.category == ChannelCategory.NATIONAL
                else -> channel.category == ChannelCategory.NATIONAL || channel.category == ChannelCategory.MASHHAD || channel.category == ChannelCategory.TEHRAN_ALBORZ
            }
        }

        // Pick top performers prioritizing views and value until a sensible allocation within budget
        val sorted = filtered.sortedByDescending { it.views.toDouble() / (it.specialTariff.coerceAtLeast(100_000)) }
        var currentCost = 0L
        val selected = mutableListOf<TelegramChannel>()

        for (channel in sorted) {
            if (currentCost + channel.specialTariff <= budgetLimit * 0.75) {
                selected.add(channel)
                currentCost += channel.specialTariff
            }
        }

        // Ensure at least 3-6 channels are selected
        return if (selected.size >= 3) selected else sorted.take(5)
    }
}
