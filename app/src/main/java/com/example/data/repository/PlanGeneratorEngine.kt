package com.example.data.repository

import com.example.data.model.AdFormat
import com.example.data.model.BoothCategory
import com.example.data.model.BrandProfile
import com.example.data.model.CampaignModelType
import com.example.data.model.CreativeContentPackage
import com.example.data.model.FinancialSubCategory
import com.example.data.model.ProductionItem
import com.example.data.model.ReelsScenario
import com.example.data.model.ReportageDraft
import com.example.data.model.RoiPerformanceMetrics
import com.example.data.model.SelectedChannelItem
import com.example.data.model.StrategicPhase
import com.example.data.model.SwotAnalysis
import com.example.data.model.TelegramChannel
import com.example.data.model.TimelineItem
import com.example.data.model.VideoTeaserScenario
import com.example.data.model.ViralEngagementIdea

data class CompleteCampaignPlan(
    val profile: BrandProfile,
    val effectiveModelType: CampaignModelType,
    val executiveSummary: String,
    val campaignSlogan: String,
    val communicationPillars: List<String>,
    val swotAnalysis: SwotAnalysis,
    val targetAudienceAnalysis: String,
    val phases: List<StrategicPhase>,
    val eventScenarioTitle: String,
    val eventScenarioStructure: String,
    val booths: List<BoothCategory>,
    val stageAndProgramHighlights: List<String>,
    val awarenessTimeline: List<TimelineItem>,
    val financialBreakdown: List<FinancialSubCategory>,
    val totalBudgetRequired: Long,
    val mediaPlanChannels: List<SelectedChannelItem>,
    val productionItems: List<ProductionItem>,
    val metrics: RoiPerformanceMetrics,
    val postEventReportingItems: List<String>,
    val expectedOutcomes: List<String>,
    val creativePackage: CreativeContentPackage,
    val aiGeneratedCopy: String? = null
)

object PlanGeneratorEngine {

    fun generateFullPlan(
        profile: BrandProfile,
        customChannels: List<SelectedChannelItem>? = null,
        customProduction: List<ProductionItem>? = null,
        ctrPercent: Float = 2.4f,
        conversionRatePercent: Float = 1.8f,
        aiGeneratedCopy: String? = null
    ): CompleteCampaignPlan {
        val brand = if (profile.brandName.isBlank()) "برند منتخب" else profile.brandName
        val industry = profile.industry
        val geo = profile.geography
        val goal = profile.mainGoal

        // Auto-detect or use user-specified model archetype
        val effectiveModel = if (profile.selectedModelType != CampaignModelType.AUTO) {
            profile.selectedModelType
        } else {
            when {
                industry.contains("آموزش") || industry.contains("دانشگاه") || industry.contains("مهاجرت") || goal.contains("جذب دانشجو") ->
                    CampaignModelType.FIVE_STEP_NARRATIVE
                industry.contains("مالی") || industry.contains("ارز") || industry.contains("فین‌تک") || industry.contains("بانک") || industry.contains("رمزارز") ->
                    CampaignModelType.CONTENT_IMPACT_CYCLE
                goal.contains("بحران") || goal.contains("اعتمادسازی") || industry.contains("عمومی") || industry.contains("آب") || industry.contains("انرژی") ->
                    CampaignModelType.CRISIS_AND_TRUST
                goal.contains("نشست خبری") || goal.contains("روابط عمومی") || industry.contains("سازمان") || industry.contains("وزارت") ->
                    CampaignModelType.PRESS_AND_PR
                profile.eventInterest || industry.contains("نمایشگاه") || industry.contains("سوغات") ->
                    CampaignModelType.EVENT_EXHIBITION
                else ->
                    CampaignModelType.FOUR_PHASE_BRANDING
            }
        }

        // Slogan and Communication Pillars
        val slogan = when (effectiveModel) {
            CampaignModelType.CONTENT_IMPACT_CYCLE -> "دسترسی امن و بدون مرز در خدمت شما"
            CampaignModelType.FIVE_STEP_NARRATIVE -> "آینده‌ای روشن، انتخابی آگاهانه و مطمئن"
            CampaignModelType.CRISIS_AND_TRUST -> "روایت حقیقت؛ ایستادگی در برابر چالش‌ها"
            CampaignModelType.PRESS_AND_PR -> "پاسخگویی شفاف، روایت دستاوردها و همراهی با افکار عمومی"
            CampaignModelType.EVENT_EXHIBITION -> "تجربه‌ای ماندگار و خاطره‌انگیز برای تمام خانواده"
            else -> "درخشش کیفیت، تمایز در خدمت و میزبانی اصیل"
        }

        val communicationPillars = when (effectiveModel) {
            CampaignModelType.CONTENT_IMPACT_CYCLE -> listOf(
                "دسترسی بدون مرز: رفع موانع و محدودیت‌ها با راهکارهای مطمئن",
                "امنیت و سابقه اثبات‌شده: جریان امن و شفاف مبادلات در بستر قانونی",
                "جامعیت خدمات: تمام نیازهای کاربر در یک درگاه یکپارچه",
                "سادگی و سرعت: فرآیند سریع، روان و بدون پیچیدگی‌های فرساینده",
                "پشتیبانی مستمر و لحظه‌ای: همراهی گام‌به‌گام کارشناسان در تمامی ساعات"
            )
            CampaignModelType.FIVE_STEP_NARRATIVE -> listOf(
                "انتخاب آگاهانه: پاسخ شفاف به ابهامات، شهریه و ظرفیت‌های مهارتی",
                "شاهد عینی و محیط واقعی: نمایش آزمایشگاه‌ها، اساتید و زیرساخت‌های ملموس",
                "اثبات با دستاورد: تبیین خروجی‌های واقعی، اشتغال و پیوند با بازار کار",
                "اعتماد از مسیر انسان: روایت تجربه زیسته دانشجویان و دانش‌آموختگان موفق",
                "مسیر روشن اقدام: راهنمایی گام‌به‌گام از مشاوره تا ثبت‌نام نهایی"
            )
            CampaignModelType.CRISIS_AND_TRUST -> listOf(
                "ملموس‌سازی چالش: بیان صادقانه سختی شرایط با تکیه بر داده‌های غیرقابل انکار",
                "روایت مقاومت و تعهد: نمایش اقدامات شبانه‌روزی و زیرساخت‌های پایدار",
                "تفکیک نقش‌ها: جداسازی تکالیف سیاست‌گذار از عملکرد میدانی خادمان مجموعه",
                "تبیین کارشناسی: استفاده از میزگردها و کارشناسان بی‌طرف جهت ابهام‌زدایی",
                "اعتماد از مقایسه: ایجاد تصویر سپر ایمن در ذهن جامعه هدف"
            )
            CampaignModelType.PRESS_AND_PR -> listOf(
                "شفافیت و پاسخگویی: ارائه آمار دقیق و رسمی به خبرنگاران و رسانه‌ها",
                "هم‌افزایی چندرسانه‌ای: توزیع همزمان در خبرگزاری‌ها، شبکه‌های اجتماعی و آنلاین",
                "تولید محتوای گزیده: تدوین کات‌های ویدیویی، اینفوگرافیک و گزارش مکتوب",
                "تکریم اهالی رسانه: بسته‌های خبری مدون و تعامل احترام‌آمیز با خبرنگاران",
                "هدایت روایت افکار عمومی: بازنمایی منصفانه دستاوردهای سازمانی"
            )
            else -> listOf(
                "اصالت و تمایز: ارائه ارزش پیشنهادی متمایز نسبت به سایر رقبا",
                "کیفیت و تجربه ممتاز: حس رضایت پایدار در نخستین تجربه تعامل با برند",
                "ارتباط دوسویه و وفادارسازی: پاداش‌ها و تسهیلات ویژه مخاطبان همراه",
                "همکاری‌های توسعه‌ای B2B: ایجاد ارزش افزوده مشترک با سایر فعالان صنف",
                "مرجعیت و رهبری صنف: تبدیل به انتخاب نخست و مطمئن در منطقه هدف"
            )
        }

        // SWOT Analysis Matrix
        val swot = SwotAnalysis(
            strengths = listOf(
                "سابقه درخشان، اعتبار انباشته و زیرساخت‌های فنی/فیزیکی آماده",
                "سبد خدمات متنوع و پاسخگویی یکپارچه به نیازهای جامعه هدف",
                "تیم پشتیبانی متخصص و فرآیندهای شفاف و قابل اتکا"
            ),
            weaknesses = listOf(
                "محدود ماندن تصویر ذهنی بخشی از مخاطبان به برخی خدمات سنتی",
                "کمبود روایت‌های داستانی از تجارب واقعی کاربران در تبلیغات گذشته",
                "نیاز به معرفی عمیق‌تر خدمات نوین و پرتقاضا در رسانه‌های جمعی"
            ),
            opportunities = listOf(
                "رشد نیاز بازار و افزایش مراجعات در منطقه جغرافیایی $geo",
                "استفاده از ظرفیت شبکه‌های اجتماعی و کانال‌های پرمخاطب تلگرام برای جریان‌سازی",
                "نارضایتی مخاطبان از واسطه‌های غیررسمی و غیرحرفه‌ای (فرصت تمایز)"
            ),
            threats = listOf(
                "رقابت روزافزون رقبا با تمرکز بر جنگ قیمت و پروموشن‌های کاذب",
                "بدبینی عمومی ناشی از تجارب ناموفق در برخی مجموعه‌های غیرمجاز",
                "نوسانات اقتصادی و تغییر اولویت‌های بودجه‌ای خانوارها و سازمان‌ها"
            )
        )

        // Strategic Phases according to effective model
        val phases = when (effectiveModel) {
            CampaignModelType.FIVE_STEP_NARRATIVE -> listOf(
                StrategicPhase(1, "گام یکم: شنیدن و طرح مسئله", profile.audienceGroup1, "رصد پرسش‌ها، ابهامات و دغدغه‌های اصلی مخاطبان در رسانه‌ها", 2, listOf("تولید ویدئو کامنت‌های پاسخ به پرسش‌های پرتکرار", "انتشار گزارش متنی مقایسه‌ای و بی‌طرفانه", "رصد هفتگی کامنت‌ها و بازخوردهای افکار عمومی")),
                StrategicPhase(2, "گام دوم: معرفی قابل لمس", profile.audienceGroup1, "معرفی ملموس ظرفیت‌ها، امکانات و مسیرهای دسترسی با زبان ساده", 2, listOf("گزارش‌های میدانی ۶۰ ثانیه‌ای از محیط و امکانات واقعی", "طراحی اینفوگرافیک نقشه راه و مسیرهای پذیرش/خدمات", "انتشار پوسترهای راهنمای زمان‌بندی و مراحل گام‌به‌گام")),
                StrategicPhase(3, "گام سوم: اثبات با دستاورد", profile.audienceGroup2, "تبیین دستاوردهای عملی، پروژه‌های موفق و پیوند با بازار کار", 3, listOf("تولید مستندهای کوتاه از خروجی‌های واقعی و موفقیت‌ها", "میزگرد استودیویی با حضور صاحب‌نظران و مدیران متخصص", "انتشار موشن‌گرافیک فرآیند خلق ارزش و استانداردهای کیفی")),
                StrategicPhase(4, "گام چهارم: اعتماد از مسیر انسان", "عموم جامعه هدف", "اعتمادسازی از طریق روایت تجربه زیسته و واقعی مخاطبان قبلی", 2, listOf("عکس‌نوشت و مصاحبه با چهره‌های واقعی و رضایت‌مندان", "نقل قول‌های مستند و صمیمی بدون شعارزدگی و اغراق", "ایجاد حس نزدیکی و اطمینان عاطفی با برند")),
                StrategicPhase(5, "گام پنجم: راهنمای اقدام و جذب قطعی", "لیدهای آماده اقدام", "هدایت مستقیم مخاطب به درگاه ثبت‌نام، مشاوره رایگان و سفارش", 2, listOf("انتشار اینفوگرافیک اقدام سریع با درج شماره تماس و لینک", "ارسال پیامک هدفمند با کد تخفیف/هدیه ثبت‌نام", "پایش لحظه‌ای نرخ تبدیل و ارجاع لیدها به سامانه CRM"))
            )
            CampaignModelType.CONTENT_IMPACT_CYCLE -> listOf(
                StrategicPhase(1, "فاز اول: جذب حداکثری (Attraction)", profile.audienceGroup1, "تمرکز بر موضوعات پرمخاطب روزمره و جذب نگاه‌ها با محتوای وایرال", 2, listOf("انتشار ریلز و ویدیوهای طنز/موقعیت‌محور روزمره", "اینفوگرافیک‌های کاربردی برای مدیریت هزینه‌ها و فرصت‌ها", "تثبیت هویت بصری و نام تجاری در کانال‌های بزرگ خبری")),
                StrategicPhase(2, "فاز دوم: درگیرسازی و تعامل (Engagement)", profile.audienceGroup1, "دعوت به تعامل دوسویه، نظرسنجی و به چالش کشیدن گزینه‌های رایج", 3, listOf("برگزاری نظرسنجی‌های پرچالش در کانال‌های تلگرامی", "ویدئو کامنت‌های پاسخ به دغدغه‌های مالی و معیشتی", "طراحی مسابقات تعاملی با جوایز انگیزشی")),
                StrategicPhase(3, "فاز سوم: طرح مسئله و تبیین (Clarification)", profile.audienceGroup2, "طرح پیش‌فرض‌های ذهنی و پاسخ مستند و ساده به شبهات", 2, listOf("موشن گرافیک تبیین مکانیزم‌های عملکردی و ارزش‌آفرینی", "تفکیک واقعیت از شایعات و بزرگ‌نمایی‌های رایج در بازار", "ارائه مثال‌های واقعی از دستاوردهای مجموعه")),
                StrategicPhase(4, "فاز چهارم: اقناع و تثبیت (Conversion & Trust)", "عموم جامعه هدف", "روایت تجارب موفق، ایجاد قطعیت در انتخاب و ثبت سفارش", 2, listOf("گزارش‌های مستند از پروژه‌ها و مشتریان راضی", "پروموشن و آفر ویژه زمان‌دار جهت تبدیل به تراکنش قطعی", "ارزیابی شاخص‌های تعاملی و سنجش بازگشت سرمایه"))
            )
            CampaignModelType.CRISIS_AND_TRUST -> listOf(
                StrategicPhase(1, "گام اول: ملموس‌سازی صادقانه بحران", "عموم افکار عمومی", "ورود به گفت‌وگوی عمومی با داده‌های واقعی و مقایسه تطبیقی", 2, listOf("ویدئو کامنت مقایسه وضعیت با بحران‌های مشابه جهانی", "بیان صادقانه ریشه‌ها و ابعاد چالش‌ها بدون کوچک‌نمایی", "جلب توجه رسانه‌ها و خبرنگاران به عمق موضوع")),
                StrategicPhase(2, "گام دوم: ارائه آمار و اقدامات مستند", profile.audienceGroup1, "نمایش عملکرد در قالب خبر مستند نه تبلیغ بازاری", 2, listOf("اینفوگرافیک داده‌محور از خدمات و اقدامات انجام‌شده", "تحلیل روز با بیان اعداد واقعی و غیرقابل انکار", "بازنشر در خبرگزاری‌ها و کانال‌های پرمخاطب کشوری")),
                StrategicPhase(3, "گام سوم: تبیین و ابهام‌زدایی استودیویی", profile.audienceGroup2, "برگزاری میزگرد کارشناسی و گفت‌وگو با متخصصان بی‌طرف", 2, listOf("میزگرد استودیویی با حضور اساتید، نخبگان و مدیران", "پاسخ صریح به انتقادات رایج و شفاف‌سازی ابهامات", "تقطیع ویدئوهای کوتاه کلیدی جهت وایرال در شبکه‌ها")),
                StrategicPhase(4, "گام چهارم: اقناع و همراهی افکار عمومی", "جامعه هدف و ذینفعان", "مستندسازی روایی و تثبیت نقش سپر حمایتی سازمان در جامعه", 2, listOf("تولید مستند چندروایتی از تلاش نیروهای صف و ستاد", "ایجاد حس همدلی و مسئولیت مشترک در افکار عمومی", "ارزیابی تغییر نگرش و سنجش شاخص اعتماد عمومی"))
            )
            CampaignModelType.PRESS_AND_PR -> listOf(
                StrategicPhase(1, "مرحله اول: برنامه‌ریزی و سناریونویسی", "ستاد و خبرنگاران", "طراحی سناریوی نشست، زمان‌بندی سخنرانان و بخش پرسش و پاسخ", 1, listOf("تدوین سین برنامه‌ها و ترتیب سخنرانی مدیران ارشد", "تهیه بسته خبری، آمار رسمی و بیانیه مطبوعاتی", "هماهنگی سالن، صوت، تصویربرداری چنددوربینه")),
                StrategicPhase(2, "مرحله دوم: دعوت و مدیریت اهالی رسانه", "خبرنگاران و دبیران خبر", "ارسال دعوت‌نامه‌های رسمی به رسانه‌های شاخص و خبرگزاری‌ها", 2, listOf("تهیه لیست خبرنگاران سرویس‌های تخصصی و عمومی", "پیگیری حضور و ثبت‌نام نمایندگان خبرگزاری‌ها و روزنامه‌ها", "آماده‌سازی بسته‌های پذیرایی و هدایای رسانه‌ای")),
                StrategicPhase(3, "مرحله سوم: اجرای نشست و پوشش زنده", "رسانه‌های حاضر", "مدیریت حرفه‌ای جلسه، نظم در پرسش‌ها و تصویربرداری کامل", 2, listOf("مدیریت سالن و کنترل زمانبندی پرسش و پاسخ خبرنگاران", "تصویربرداری فول اچ‌دی و پوشش زنده در بستر آنلاین", "انجام مصاحبه‌های اختصاصی مدیران با خبرگزاری‌های مرجع")),
                StrategicPhase(4, "مرحله چهارم: انتشار سراسری و رصد بازتاب‌ها", "عموم جامعه و رسانه‌ها", "توزیع گسترده اخبار، گزارش‌های ویدیویی و تدوین آلبوم", 2, listOf("انتشار فوری گزارش مکتوب در بیش از ۱۵ خبرگزاری رسمی", "تدوین و نشر تیزرهای ۳۰ تا ۶۰ ثانیه‌ای در کانال‌های تلگرام", "تهیه گزارش جامع بازتاب رسانه‌ای و بایگانی مستندات"))
            )
            else -> listOf(
                StrategicPhase(1, "فاز اول: معرفی و آشنایی (Awareness)", "${profile.audienceGroup1} و ${profile.audienceGroup2}", "ایجاد شناخت اولیه عمیق از هویت و تمایزهای «$brand»", 2, listOf("رونمایی رسانه‌ای با انتشار تیزر و رپرتاژ اختصاصی", "برگزاری افتتاحیه یا ارائه آفر ویژه فصلی", "مسابقه تعاملی در شبکه‌های اجتماعی")),
                StrategicPhase(2, "فاز دوم: تثبیت برند (Trust & Loyalty)", profile.audienceGroup1, "تقویت اعتماد، تصویر ذهنی مثبت و اثبات کیفیت", 3, listOf("انتشار ویدئوهای بازخورد و رضایت‌مندی واقعی", "معرفی پکیج‌های تجربه‌محور با تضمین کیفیت", "باشگاه وفاداری و پیامک‌های شخصی‌سازی شده")),
                StrategicPhase(3, "فاز سوم: توسعه برند (Expansion & B2B)", profile.audienceGroup2, "گسترش سهم بازار از طریق همکاری‌های B2B و خدمات گروهی", 2, listOf("برگزاری نشست‌های تخصصی B2B با شرکای تجاری", "ارائه بسته‌های اختصاصی گروهی با ارزش افزوده بالا", "حضور فعال در رپرتاژهای تخصصی صنعت $industry")),
                StrategicPhase(4, "فاز چهارم: مرجعیت بازار (Market Leadership)", "عموم جامعه هدف", "تبدیل برند به انتخاب اول و بی‌رقیب در $geo", 2, listOf("انتشار گزارش عملکرد سالانه و رکوردهای موفقیت", "میزبانی رویدادهای فصلی معتبر با قرعه‌کشی بزرگ", "ثبت برند به عنوان نماد استاندارد و اصالت خدمات"))
            )
        }

        val executiveSummary = "تدوین استراتژی جامع بازاریابی و برنامه رسانه‌ای برای «$brand» در حوزه $industry با بهره‌گیری از ${effectiveModel.titleFa}. این ساختار، پیام محوری («$slogan») را در ۵ محور کلیدی در بسترهای پرمخاطب تلگرام و رسانه‌های هدف بازنمایی کرده و بازگشت سرمایه شفاف و اندازه‌پذیر را تضمین می‌نماید."

        val audienceAnalysis = "تقسیم‌بندی هدفمند جامعه مخاطبان:\n" +
                "• گروه اول (${profile.audienceGroup1}): سهم ${profile.audienceGroup1Percent}٪ - مخاطبان مستقیم با هدف آگاهی آنی، رفع ابهام و تبدیل به اقدام قطعی.\n" +
                "• گروه دوم (${profile.audienceGroup2}): سهم ${profile.audienceGroup2Percent}٪ - مخاطبان رابطه‌ای، سازمانی و ارجاعی با هدف تثبیت اعتماد و ماندگاری تصویر ذهنی."

        // Event / Booth Scenario
        val eventTitle = if (profile.eventInterest) "رویداد و جشنواره ویژه: درخشش $brand" else "کمپین رسانه‌ای و یکپارچه فروش $brand"
        val eventStructure = "سازماندهی محیطی با غرفه‌های کارکردی، صحنه اجرای زنده و پوشش همزمان مستندات رسانه‌ای."

        val booths = listOf(
            BoothCategory("معرفی محصولات اصلی و خدمات ویژه", 8, "بسته‌بندی‌های لوکس، سمپلینگ و معرفی ملموس ارزش پیشنهادی برند"),
            BoothCategory("بخش هنری و دست‌سازه‌ها", 3, "آثار فاخر، نمادهای اصالت و جلوه‌های بصری متناسب با هویت برند"),
            BoothCategory("سرگرمی، بازی و هیجان", 2, "مسابقات چالش، بازی‌های تعاملی و اهدای جوایز آنی به برندگان"),
            BoothCategory("کودکان و نوجوانان", 2, "ایستگاه نقاشی، بازی‌های فکری و ساخت خاطره مثبت برای خانواده‌ها"),
            BoothCategory("خوراکی و چایخانه سنتی", 3, "پذیرایی با دمنوش‌های گیاهی و پذیرایی اصیل ایرانی"),
            BoothCategory("پوشاک و اقلام یادبود", 3, "ارائه اقلام اختصاصی برند با تخفیف ویژه دوره رویداد"),
            BoothCategory("کارگاه‌های آموزشی و مهارتی", 1, "برگزاری ورکشاپ‌های کوتاه رایگان جهت اعتمادسازی عمیق"),
            BoothCategory("نشست‌های هم‌افزایی و مذاکرات B2B", 2, "میز جلسات اختصاصی جهت قراردادهای سازمانی و جذب همکار"),
            BoothCategory("اطلاعات، لید جنریشن و قرعه‌کشی", 1, "ثبت اطلاعات بازدیدکنندگان جهت قرعه‌کشی شبانه و ورود به CRM")
        )

        val stageHighlights = listOf(
            "اجرای موسیقی زنده سنتی و محلی در ساعات پربازدید",
            "استیج زنده با حضور مجری پرانرژی و مصاحبه با مدیران و مهمانان ویژه",
            "برگزاری قرعه‌کشی بزرگ شبانه و اعطای هدایای نفیس به شرکت‌کنندگان",
            "پخش تیزرها و کپسول‌های ویدیویی همزمان بر روی نمایشگرهای رویداد و شبکه‌های اجتماعی"
        )

        val awarenessTimeline = listOf(
            TimelineItem("طراحی پوستر و هویت بصری کمپین", "۱۰ روز پیش از اجرا", "استودیو طراحی و گرافیک", "پیش از رویداد"),
            TimelineItem("تولید و تدوین تیزر رئال/موشن و ریلزها", "۸ روز پیش از اجرا", "واحد تولید محتوا و کپی‌رایتینگ", "پیش از رویداد"),
            TimelineItem("آغاز انتشار تیزرها در کانال‌های تلگرام و بله", "۵ روز پیش از اجرا", "کانال‌های پرمخاطب و استانی", "پیش از رویداد"),
            TimelineItem("ارسال کمپین هدفمند پیامکی (SMS)", "۲ روز پیش از اجرا", "بانک شماره‌های اصناف و شهری", "پیش از رویداد"),
            TimelineItem("پوشش زنده و استوری‌های میدانی", "روزهای اول تا پایان", "فضای مجازی و کانال‌های همکار", "حین رویداد"),
            TimelineItem("تولید گزارش‌های روز و ویدئو کامنت‌ها", "میان‌دوره کمپین", "کانال‌های خبری و اقتصادی", "حین رویداد"),
            TimelineItem("تدوین مستند نهایی و انتشار آلبوم دستاوردها", "پس از اتمام دوره", "ارائه به مدیریت و انتشار گزارش", "پس از رویداد")
        )

        // Recommended Channels
        val mediaChannels = customChannels ?: run {
            val recommended = ChannelDataRepository.getRecommendedChannels(geo, industry, profile.budget)
            recommended.map { channel ->
                SelectedChannelItem(
                    channel = channel,
                    selectedFormat = if (channel.views > 20_000) AdFormat.REPORTAGE else AdFormat.SPECIAL,
                    isSelected = true
                )
            }
        }

        // Production Items
        val productionItems = customProduction ?: ChannelDataRepository.standardProductionItems

        val mediaSum = mediaChannels.filter { it.isSelected }.sumOf { it.cost }
        val productionSum = productionItems.filter { it.isIncluded }.sumOf { it.baseCost }

        val executiveItems = listOf(
            "تیم مدیریت کمپین و هماهنگی ستاد" to (profile.budget * 0.08).toLong().coerceAtLeast(3_500_000L),
            "نیروهای اجرایی، غرفه‌داران و راهنمایان" to (profile.budget * 0.05).toLong().coerceAtLeast(2_000_000L),
            "مجری برنامه‌ها و استیج زنده" to (profile.budget * 0.04).toLong().coerceAtLeast(2_500_000L),
            "گروه موسیقی و اجرای سنتی" to (profile.budget * 0.04).toLong().coerceAtLeast(2_000_000L),
            "خدمات، پشتیبانی و لجستیک" to (profile.budget * 0.02).toLong().coerceAtLeast(1_000_000L)
        )

        val decorItems = listOf(
            "سازه و غرفه‌بندی استاندارد" to (profile.budget * 0.12).toLong().coerceAtLeast(4_000_000L),
            "سن اصلی، نورپردازی و صوت" to (profile.budget * 0.05).toLong().coerceAtLeast(2_000_000L),
            "فضاسازی محیطی و لایت‌باکس‌ها" to (profile.budget * 0.03).toLong().coerceAtLeast(1_200_000L),
            "هدایا، اقلام پروموشن و جوایز قرعه‌کشی" to (profile.budget * 0.06).toLong().coerceAtLeast(3_000_000L)
        )

        val hospitalityItems = listOf(
            "پذیرایی از مهمانان ویژه (VIP)" to (profile.budget * 0.04).toLong().coerceAtLeast(1_800_000L),
            "میان‌وعده، آبمیوه و چایخانه سنتی" to (profile.budget * 0.03).toLong().coerceAtLeast(1_200_000L)
        )

        val mediaAndContentItems = listOf(
            "تولید محتوا و اقلام چندرسانه‌ای" to productionSum,
            "انتشار در رسانه‌ها و کانال‌های تلگرام" to mediaSum,
            "ارسال هدفمند کمپین پیامکی" to 2_000_000L
        )

        val financialBreakdown = listOf(
            FinancialSubCategory("۱. برآورد عوامل اجرایی و ستادی", executiveItems),
            FinancialSubCategory("۲. برآورد دکور، سازه و فضاسازی", decorItems),
            FinancialSubCategory("۳. برآورد پذیرایی و تشریفات", hospitalityItems),
            FinancialSubCategory("۴. برآورد محتوا، تبلیغات و انتشار رسانه‌ای", mediaAndContentItems)
        )

        val totalBudget = financialBreakdown.sumOf { it.total }

        // Metrics & ROI
        val selectedActiveChannels = mediaChannels.filter { it.isSelected }
        val totalReach = selectedActiveChannels.sumOf { it.channel.audience }
        val totalViews = selectedActiveChannels.sumOf { it.channel.views }

        val totalCampaignCost = totalBudget.coerceAtLeast(10_000_000L)
        val totalMediaAndProdCost = mediaSum + productionSum

        val effectiveViews = totalViews.coerceAtLeast(5_000L)
        val cpm = if (effectiveViews > 0) (totalMediaAndProdCost * 1000) / effectiveViews else 0L

        val estimatedClicks = ((effectiveViews * (ctrPercent / 100f))).toLong().coerceAtLeast(50L)
        val estimatedCustomers = ((estimatedClicks * (conversionRatePercent / 100f))).toLong().coerceAtLeast(2L)
        val estimatedRevenue = estimatedCustomers * profile.avgOrderValue
        val netProfit = estimatedRevenue - totalCampaignCost
        val roi = if (totalCampaignCost > 0) ((netProfit.toFloat() / totalCampaignCost.toFloat()) * 100f) else 0f

        val grade = when {
            roi >= 120f -> "بازدهی فوق‌العاده (A+)"
            roi >= 40f -> "سودآور و مطلوب (A)"
            roi >= 0f -> "سربه‌سر و رشد آگاهی (B)"
            else -> "نیازمند بهینه‌سازی تارگتینگ (C)"
        }

        val metrics = RoiPerformanceMetrics(
            totalAudienceReach = totalReach,
            totalEstimatedViews = effectiveViews,
            totalMediaCost = mediaSum,
            totalProductionCost = productionSum,
            totalCampaignCost = totalCampaignCost,
            cpm = cpm,
            ctrPercent = ctrPercent,
            estimatedClicks = estimatedClicks,
            conversionRatePercent = conversionRatePercent,
            estimatedCustomers = estimatedCustomers,
            estimatedRevenue = estimatedRevenue,
            netProfit = netProfit,
            roiPercent = roi,
            assessmentGrade = grade
        )

        val postReporting = listOf(
            "تهیه آمار تفصیلی ریزبازدید کانال‌های تلگرام و پیام‌رسان‌ها با اسکرین‌شات و ترافیک لحظه‌ای",
            "تولید گزارش رسمی رسانه‌ای (متنی، کات‌های ویدیویی، اینفوگرافیک و بایگانی کامل)",
            "تحلیل دقیق استقبال مخاطبان، تفکیک جغرافیایی و نرخ تبدیل به ثبت‌نام/سفارش قطعی",
            "ثبت و دسته‌بندی لیدها در CRM جهت بهره‌برداری در کمپین‌های بازگشتی (Retargeting)"
        )

        val outcomes = listOf(
            "تثبیت هویت متمایز «$brand» و اصلاح تصویر ذهنی در حوزه $industry",
            "دسترسی موثر به بیش از ${totalReach.formatPersian()} مخاطب هدفمند در رسانه‌های مرجع",
            "جذب حداقل $estimatedCustomers مشتری/لید جدید با گردش مالی تقریبی ${estimatedRevenue.formatPersian()} تومان",
            "ایجاد پایگاه مخاطبان وفادار و بسترسازی برای تداوم کمپین‌های آینده"
        )

        val brandSlogans = listOf(
            "«$brand؛ $slogan»",
            "«انتخاب مطمئن، تجربه ماندگار با $brand»",
            "«راهکاری متمایز که شایسته شماست؛ همراه با $brand»",
            "«$brand؛ تفاوت در جزئیات، درخشش در کیفیت و خدمت»"
        )

        val videoTeaserScene = VideoTeaserScenario(
            title = "سناریوی تیزر رئال‌موشن ۶۰ ثانیه‌ای «روایت تحول و اعتماد با $brand»",
            duration = "۶۰ ثانیه (مناسب استوری، ریلز و تلگرام)",
            scene1Hook = "نمای بسته از چهره درگیر و سردرگم یک کاربر (${profile.audienceGroup1}) در حال کلنجار با پیچیدگی‌های سنتی و اتلاف وقت در حوزه $industry در $geo. صدای تیک‌تاک ساعت و افکت استرس محیطی.",
            scene2Conflict = "برش سریع به مواجهه با عدم شفافیت یا وعده‌های غیرواقعی در گذشته. صدای نریشن با لحن تامل‌برانگیز: «آیا تا به حال حس کرده‌اید یافتن یک راهکار مطمئن و سریع در $industry این‌قدر فرساینده شده؟»",
            scene3Resolution = "موسیقی به یکباره پرانرژی و الهام‌بخش می‌شود. نمای مدرن و حرفه‌ای ورود راهکار «$brand» به تصویر با المان‌های گرافیکی پویا. لبخند رضایت و آسودگی خیال مخاطب (${profile.audienceGroup2}). نریشن: «با $brand، کیفیت، سرعت و شفافیت کنار هم قرار گرفته‌اند تا دغدغه‌ای باقی نماند.»",
            scene4CallToAction = "لوگوموشن اختصاصی و شیک «$brand» همراه با آدرس کانال تلگرام، وب‌سایت و پیشنهاد ویژه: «همین امروز به خانواده همراهان $brand بپیوندید و با استفاده از آفر ویژه کمپین، تجربه‌ای متفاوت رقم بزنید.»"
        )

        val reelsScene = ReelsScenario(
            title = "سناریوی ریلز و ویدیوی وایرال پرتعامل «چرا همه دارند درباره $brand صحبت می‌کنند؟»",
            onScreenHookText = "🛑 «اگه به خدمات $industry نیاز داری، دست نگه دار تا این راز رو بهت بگم!»",
            actorAction = "مجری پرانرژی مستقیم رو به دوربین نگاه می‌کند و با گوشی یا تبلت یک مقایسه جذاب بین روش‌های خسته‌کننده سنتی و خدمات سریع و مطمئن $brand انجام می‌دهد.",
            voiceoverOrDialogue = "«خیلی‌ها هنوز نمی‌دونن چطور در $geo بدون اتلاف هزینه به نتیجه برسند. بچه‌های $brand کاری کردن که تمام مراحل ظرف چند دقیقه انجام بشه! این ویدیو رو بفرست برای کسی که دنبال بهترین خدمات $industry می‌گرده.»",
            suggestedCaption = "دیگه وقتشه برای $goal یک انتخاب مطمئن داشته باشید! 🎯✨ در مجموعه $brand کیفیت حق شماست. جهت کسب اطلاعات بیشتر و تخفیف افتتاحیه به بیو سر بزنید 👇 #$brand #کمپین_اختصاصی #$industry"
        )

        val reportageDraft = ReportageDraft(
            catchyHeadline = "تحول نوآورانه در بازار $industry؛ چگونه «$brand» توانست استانداردهای جدیدی در $geo بنا نهد؟",
            storytellingLead = "در بازاری که روزانه ده‌ها پیشنهاد تبلیغاتی پیش روی ${profile.audienceGroup1} قرار می‌گیرد، تنها مجموعه‌هایی ماندگار می‌شوند که اعتماد را به عنوان سرمایه اصلی خود تعریف کرده‌اند. گزارش میدانی ما از عملکرد اخیر «$brand» نشان‌دهنده یک جهش کیفی است.",
            bodyAngle = "روایت داستان برند، تبیین فناوری و نیروی متخصص پشت صحنه $brand، بازخورد رضایت‌بخش کاربران (${profile.audienceGroup2}) و پاسخ مستند به این پرسش که چرا $brand گزینه‌ای متمایز و اقتصادی است.",
            callToAction = "جهت مطالعه پرونده کامل دستاوردها، دریافت مشاوره تخصصی رایگان و عضویت در کانال رسمی «$brand»، از لینک انتهای مطلب دیدن فرمایید."
        )

        val viralIdea = ViralEngagementIdea(
            contestTitle = "مسابقه بزرگ تعاملی تلگرام: «چالش هوشمندانه با جوایز ویژه $brand»",
            mechanism = "انتشار یک سوال چالشی یا نظرسنجی جذاب پیرامون یک نیاز مبرم در $industry به عنوان پست ۱ شبانه در کانال‌های منتخب، با شرط عضویت در کانال $brand و ارسال پاسخ به بات مسابقه.",
            rewardHook = "اهدای ۵ پکیج نفیس از خدمات اختصاصی $brand به برندگان قرعه‌کشی + بن تخفیف ۳۰ درصدی برای تمامی شرکت‌کنندگان در نظرسنجی.",
            expectedEngagement = "بیش از ۳۵٪ افزایش تعامل نسبت به پست‌های عادی و جذب گسترده مخاطب واقعی و مستعد خرید."
        )

        val seasonalHooks = listOf(
            "بهره‌برداری از تقویم فصلی و اعیاد پیش‌رو جهت برانگیختن انگیزه خرید و ثبت‌نام در $geo",
            "استفاده هوشمند از ساعات اوج حضور کاربران تلگرام در ساعات پایانی شب (۲۱ الی ۲۴) برای انتشار وایرال",
            "هم‌افزایی محتوایی با رویدادهای تخصصی و صنفی مرتبط با $industry در رسانه‌های استانی",
            "طراحی آفر محدود زمانی (FOMO) متناسب با بودجه و توان خرید جامعه هدف"
        )

        val creativePackage = CreativeContentPackage(
            brandSlogans = brandSlogans,
            videoTeaserScene = videoTeaserScene,
            reelsShortVideoScene = reelsScene,
            reportageDraft = reportageDraft,
            viralEngagementIdea = viralIdea,
            seasonalHooks = seasonalHooks
        )

        return CompleteCampaignPlan(
            profile = profile,
            effectiveModelType = effectiveModel,
            executiveSummary = executiveSummary,
            campaignSlogan = slogan,
            communicationPillars = communicationPillars,
            swotAnalysis = swot,
            targetAudienceAnalysis = audienceAnalysis,
            phases = phases,
            eventScenarioTitle = eventTitle,
            eventScenarioStructure = eventStructure,
            booths = booths,
            stageAndProgramHighlights = stageHighlights,
            awarenessTimeline = awarenessTimeline,
            financialBreakdown = financialBreakdown,
            totalBudgetRequired = totalBudget,
            mediaPlanChannels = mediaChannels,
            productionItems = productionItems,
            metrics = metrics,
            postEventReportingItems = postReporting,
            expectedOutcomes = outcomes,
            creativePackage = creativePackage,
            aiGeneratedCopy = aiGeneratedCopy
        )
    }
}

fun Long.formatPersian(): String {
    val s = String.format(java.util.Locale.US, "%,d", this)
    return s.map { ch ->
        when (ch) {
            '0' -> '۰'
            '1' -> '۱'
            '2' -> '۲'
            '3' -> '۳'
            '4' -> '۴'
            '5' -> '۵'
            '6' -> '۶'
            '7' -> '۷'
            '8' -> '۸'
            '9' -> '۹'
            else -> ch
        }
    }.joinToString("")
}

fun Float.formatPersian1(): String {
    val s = String.format(java.util.Locale.US, "%.1f", this)
    return s.map { ch ->
        when (ch) {
            '0' -> '۰'
            '1' -> '۱'
            '2' -> '۲'
            '3' -> '۳'
            '4' -> '۴'
            '5' -> '۵'
            '6' -> '۶'
            '7' -> '۷'
            '8' -> '۸'
            '9' -> '۹'
            '.' -> '٫'
            else -> ch
        }
    }.joinToString("")
}
