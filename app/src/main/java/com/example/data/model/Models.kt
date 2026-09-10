package com.example.data.model

enum class ChannelCategory(val titleFa: String) {
    NATIONAL("سراسری و خبری"),
    MASHHAD("مشهد و خراسان"),
    TEHRAN_ALBORZ("تهران و البرز"),
    PROVINCE("استانی"),
    BUSINESS("اقتصادی و تجاری"),
    TECH("فناوری و دانش‌بنیان"),
    SPORTS("ورزشی")
}

enum class AdFormat(val titleFa: String, val descFa: String) {
    SPECIAL("تعرفه ویژه", "پست روزانه با بازدید هدفمند"),
    REPORTAGE("رپرتاژ آگهی", "محتوای متنی و تصویری دائمی با لینک"),
    NIGHT_POST_1("پست ۱ شبانه", "پین شبانه ۲۴ ساعته در صدر کانال"),
    NIGHT_POST_2("پست ۲ شبانه", "پست شبانه در جایگاه دوم"),
    NIGHT_POST_3("پست ۳ شبانه", "پست شبانه اقتصادی")
}

data class TelegramChannel(
    val id: Int,
    val name: String,
    val tag: String,
    val audience: Long,
    val views: Long,
    val specialTariff: Long,
    val reportageTariff: Long,
    val nightPost1Tariff: Long,
    val nightPost2Tariff: Long? = null,
    val nightPost3Tariff: Long? = null,
    val category: ChannelCategory,
    val province: String = ""
) {
    fun getPriceForFormat(format: AdFormat): Long {
        return when (format) {
            AdFormat.SPECIAL -> specialTariff
            AdFormat.REPORTAGE -> reportageTariff
            AdFormat.NIGHT_POST_1 -> nightPost1Tariff
            AdFormat.NIGHT_POST_2 -> nightPost2Tariff ?: nightPost1Tariff
            AdFormat.NIGHT_POST_3 -> nightPost3Tariff ?: nightPost1Tariff
        }
    }
}

data class SelectedChannelItem(
    val channel: TelegramChannel,
    val selectedFormat: AdFormat = AdFormat.SPECIAL,
    val isSelected: Boolean = true
) {
    val cost: Long get() = channel.getPriceForFormat(selectedFormat)
}

data class ProductionItem(
    val id: String,
    val titleFa: String,
    val descFa: String,
    val baseCost: Long,
    val isIncluded: Boolean = true
)

data class BrandProfile(
    val brandName: String = "",
    val industry: String = "هتل و گردشگری",
    val geography: String = "مشهد و سراسری",
    val mainGoal: String = "افزایش اقامت و جذب مخاطب",
    val audienceGroup1: String = "زائران و مسافران داخلی",
    val audienceGroup1Percent: Int = 50,
    val audienceGroup2: String = "ساکنین و مجاوران بومی",
    val audienceGroup2Percent: Int = 50,
    val budget: Long = 85_000_000L,
    val avgOrderValue: Long = 3_500_000L,
    val eventInterest: Boolean = true,
    val selectedModelType: CampaignModelType = CampaignModelType.AUTO
)

enum class CampaignModelType(val titleFa: String, val descFa: String) {
    AUTO("هوشمند (تشخیص خودکار بر اساس صنعت و هدف)", "انتخاب بهترین الگو مطابق اهداف سفارش"),
    FOUR_PHASE_BRANDING("الگوی ۴ فازی تثبیت و مرجعیت برند", "معرفی، تثبیت، توسعه و مرجعیت بازار"),
    FIVE_STEP_NARRATIVE("الگوی ۵ گام پیوسته روایتی و جذب مخاطب", "طرح مسئله، معرفی ملموس، اثبات با دستاورد، اعتماد انسانی و اقدام"),
    CONTENT_IMPACT_CYCLE("الگوی چرخه اثرگذاری محتوایی و اعتمادسازی", "جذب، درگیرسازی، طرح مسئله، تبیین و اقناع"),
    CRISIS_AND_TRUST("الگوی مدیریت بحران و روایت حقیقت", "ملموس‌سازی چالش، آمار مستند، میزگرد تبیینی و مستند اقناع"),
    EVENT_EXHIBITION("الگوی رویداد ترکیبی، نمایشگاه و استیج", "۲۵ غرفه موضوعی، استیج موسیقی زنده، مسابقه و قرعه‌کشی"),
    PRESS_AND_PR("الگوی نشست خبری و هدایت افکار عمومی", "مدیریت خبرنگاران، بسته خبری، مصاحبه و خبرگزاری‌ها")
}

data class CreativeContentPackage(
    val brandSlogans: List<String>,
    val videoTeaserScene: VideoTeaserScenario,
    val reelsShortVideoScene: ReelsScenario,
    val reportageDraft: ReportageDraft,
    val viralEngagementIdea: ViralEngagementIdea,
    val seasonalHooks: List<String>
)

data class VideoTeaserScenario(
    val title: String,
    val duration: String,
    val scene1Hook: String,
    val scene2Conflict: String,
    val scene3Resolution: String,
    val scene4CallToAction: String
)

data class ReelsScenario(
    val title: String,
    val onScreenHookText: String,
    val actorAction: String,
    val voiceoverOrDialogue: String,
    val suggestedCaption: String
)

data class ReportageDraft(
    val catchyHeadline: String,
    val storytellingLead: String,
    val bodyAngle: String,
    val callToAction: String
)

data class ViralEngagementIdea(
    val contestTitle: String,
    val mechanism: String,
    val rewardHook: String,
    val expectedEngagement: String
)

data class SwotAnalysis(
    val strengths: List<String>,
    val weaknesses: List<String>,
    val opportunities: List<String>,
    val threats: List<String>
)

data class StrategicPhase(
    val phaseNumber: Int,
    val phaseTitle: String,
    val targetAudienceName: String,
    val targetObjective: String,
    val eventCount: Int,
    val keyActions: List<String>
)

data class BoothCategory(
    val name: String,
    val boothCount: Int,
    val subItems: String
)

data class TimelineItem(
    val title: String,
    val dateOrPeriod: String,
    val channelOrPlatform: String,
    val phaseType: String // پیش از رویداد، حین رویداد، پس از رویداد
)

data class FinancialSubCategory(
    val title: String,
    val items: List<Pair<String, Long>>
) {
    val total: Long get() = items.sumOf { it.second }
}

data class RoiPerformanceMetrics(
    val totalAudienceReach: Long,
    val totalEstimatedViews: Long,
    val totalMediaCost: Long,
    val totalProductionCost: Long,
    val totalCampaignCost: Long,
    val cpm: Long, // هزینه به ازای ۱۰۰۰ بازدید
    val ctrPercent: Float, // نرخ کلیک
    val estimatedClicks: Long,
    val conversionRatePercent: Float, // نرخ تبدیل به مشتری
    val estimatedCustomers: Long,
    val estimatedRevenue: Long,
    val netProfit: Long,
    val roiPercent: Float,
    val assessmentGrade: String // عالی، سودآور، نیازمند اصلاح
)
