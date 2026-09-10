package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AdFormat
import com.example.data.model.BrandProfile
import com.example.data.model.CampaignModelType
import com.example.data.model.ChannelCategory
import com.example.data.model.ProductionItem
import com.example.data.model.SelectedChannelItem
import com.example.data.repository.ChannelDataRepository
import com.example.data.repository.CompleteCampaignPlan
import com.example.data.repository.GeminiCampaignService
import com.example.data.repository.PlanGeneratorEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AiState {
    data object Idle : AiState
    data object Loading : AiState
    data class Success(val content: String) : AiState
    data class Error(val message: String) : AiState
}

data class PlannerUiState(
    val brandProfile: BrandProfile = BrandProfile(
        brandName = "برند تجاری سپهر",
        industry = "خدمات مالی و پرداخت آنلاین",
        geography = "سراسری و کلان‌شهرها",
        mainGoal = "ارتقای جایگاه برند، اعتمادسازی و جذب کاربران فعال",
        audienceGroup1 = "کاربران و فعالان آنلاین",
        audienceGroup1Percent = 60,
        audienceGroup2 = "سازمان‌ها و کسب‌وکارهای همکار",
        audienceGroup2Percent = 40,
        budget = 90_000_000L,
        avgOrderValue = 3_800_000L,
        eventInterest = false,
        selectedModelType = CampaignModelType.AUTO
    ),
    val selectedTab: Int = 0,
    val channelSearchQuery: String = "",
    val selectedCategoryFilter: ChannelCategory? = null,
    val selectedChannels: List<SelectedChannelItem> = emptyList(),
    val productionItems: List<ProductionItem> = emptyList(),
    val ctrPercent: Float = 2.5f,
    val conversionRatePercent: Float = 1.8f,
    val currentPlan: CompleteCampaignPlan? = null,
    val aiState: AiState = AiState.Idle,
    val showExportDialog: Boolean = false,
    val isGeneratingPlan: Boolean = false,
    val generationStep: Int = 0,
    val generationStatusText: String = ""
)

class MediaPlannerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PlannerUiState())
    val uiState: StateFlow<PlannerUiState> = _uiState.asStateFlow()

    init {
        initializeWithDefaults()
    }

    private fun initializeWithDefaults() {
        val initialChannels = ChannelDataRepository.allChannels.map { channel ->
            val shouldSelect = channel.name in listOf("خبرفوری", "اخبار تهران", "تیتر تجارت", "اکو نیوز", "اخبارمشهد", "ایران تایمز")
            val format = if (channel.views > 20_000) AdFormat.REPORTAGE else AdFormat.SPECIAL
            SelectedChannelItem(
                channel = channel,
                selectedFormat = format,
                isSelected = shouldSelect
            )
        }

        val initialProduction = ChannelDataRepository.standardProductionItems

        val profile = _uiState.value.brandProfile
        val generatedPlan = PlanGeneratorEngine.generateFullPlan(
            profile = profile,
            customChannels = initialChannels,
            customProduction = initialProduction,
            ctrPercent = _uiState.value.ctrPercent,
            conversionRatePercent = _uiState.value.conversionRatePercent
        )

        _uiState.update {
            it.copy(
                selectedChannels = initialChannels,
                productionItems = initialProduction,
                currentPlan = generatedPlan
            )
        }
    }

    fun setTab(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    fun updateBrandProfile(newProfile: BrandProfile) {
        _uiState.update { it.copy(brandProfile = newProfile) }
        recalculatePlan()
    }

    fun setChannelSearch(query: String) {
        _uiState.update { it.copy(channelSearchQuery = query) }
    }

    fun setCategoryFilter(category: ChannelCategory?) {
        _uiState.update { it.copy(selectedCategoryFilter = category) }
    }

    fun toggleChannelSelection(channelId: Int) {
        _uiState.update { current ->
            val updated = current.selectedChannels.map { item ->
                if (item.channel.id == channelId) {
                    item.copy(isSelected = !item.isSelected)
                } else item
            }
            current.copy(selectedChannels = updated)
        }
        recalculatePlan()
    }

    fun updateChannelFormat(channelId: Int, format: AdFormat) {
        _uiState.update { current ->
            val updated = current.selectedChannels.map { item ->
                if (item.channel.id == channelId) {
                    item.copy(selectedFormat = format)
                } else item
            }
            current.copy(selectedChannels = updated)
        }
        recalculatePlan()
    }

    fun toggleProductionItem(itemId: String) {
        _uiState.update { current ->
            val updated = current.productionItems.map { item ->
                if (item.id == itemId) {
                    item.copy(isIncluded = !item.isIncluded)
                } else item
            }
            current.copy(productionItems = updated)
        }
        recalculatePlan()
    }

    fun setCtrPercent(ctr: Float) {
        _uiState.update { it.copy(ctrPercent = ctr) }
        recalculatePlan()
    }

    fun setConversionRatePercent(cr: Float) {
        _uiState.update { it.copy(conversionRatePercent = cr) }
        recalculatePlan()
    }

    fun setAvgOrderValue(aov: Long) {
        _uiState.update { current ->
            val updatedProfile = current.brandProfile.copy(avgOrderValue = aov)
            current.copy(brandProfile = updatedProfile)
        }
        recalculatePlan()
    }

    fun setBudget(budget: Long) {
        _uiState.update { current ->
            val updatedProfile = current.brandProfile.copy(budget = budget)
            current.copy(brandProfile = updatedProfile)
        }
        recalculatePlan()
    }

    fun setModelType(modelType: CampaignModelType) {
        _uiState.update { current ->
            val updatedProfile = current.brandProfile.copy(selectedModelType = modelType)
            current.copy(brandProfile = updatedProfile)
        }
        recalculatePlan()
    }

    // Generic Industry Archetype Presets (No specific confidential brand names)
    fun loadPreset(presetKey: String) {
        when (presetKey) {
            "FINTECH" -> {
                val profile = BrandProfile(
                    brandName = "پلتفرم مالی و پرداخت بین‌الملل",
                    industry = "فناوری‌های مالی و ارزی",
                    geography = "سراسری و کلان‌شهرها",
                    mainGoal = "ارتقای جایگاه برند، تسهیل پرداخت‌ها و اعتمادسازی عمیق",
                    audienceGroup1 = "فریلنسرها، خریداران آنلاین و کاربران بین‌الملل",
                    audienceGroup1Percent = 65,
                    audienceGroup2 = "کسب‌وکارها، تریدرها و شرکت‌های واردکننده",
                    audienceGroup2Percent = 35,
                    budget = 110_000_000L,
                    avgOrderValue = 4_200_000L,
                    eventInterest = false,
                    selectedModelType = CampaignModelType.CONTENT_IMPACT_CYCLE
                )
                selectChannelsByNames(listOf("خبرفوری", "خبرفردا", "تیتر تجارت", "اکو نیوز", "اخبار تهران", "فردای تهران"))
                updateBrandProfile(profile)
            }
            "EDUCATION" -> {
                val profile = BrandProfile(
                    brandName = "مجتمع آموزش عالی و مهارتی",
                    industry = "آموزش، دانشگاه و مهارت‌آموزی",
                    geography = "استانی و منطقه‌ای",
                    mainGoal = "جذب دانشجو، آگاهی‌بخشی به خانواده‌ها و مشاوره انتخاب رشته",
                    audienceGroup1 = "داوطلبان، فارغ‌التحصیلان و دانش‌آموزان",
                    audienceGroup1Percent = 60,
                    audienceGroup2 = "والدین و تصمیم‌گیران خانواده",
                    audienceGroup2Percent = 40,
                    budget = 85_000_000L,
                    avgOrderValue = 5_500_000L,
                    eventInterest = false,
                    selectedModelType = CampaignModelType.FIVE_STEP_NARRATIVE
                )
                selectChannelsByNames(listOf("اخبارمشهد", "فردای مشهد", "صدای خراسان", "حوادث مشهد", "خبرفوری"))
                updateBrandProfile(profile)
            }
            "HOSPITALITY" -> {
                val profile = BrandProfile(
                    brandName = "مجتمع اقامتی و هتلینگ لوکس",
                    industry = "هتل، گردشگری و تفریح",
                    geography = "مقصد گردشگری و مسافران سراسری",
                    mainGoal = "افزایش نرخ اقامت، توسعه خدمات رویدادی و پذیرش مخاطبان محلی",
                    audienceGroup1 = "مسافران و گردشگران ورودی (خانواده‌ها و زوج‌ها)",
                    audienceGroup1Percent = 50,
                    audienceGroup2 = "ساکنان محلی (کافه، رستوران و مراسمات)",
                    audienceGroup2Percent = 50,
                    budget = 130_000_000L,
                    avgOrderValue = 4_800_000L,
                    eventInterest = true,
                    selectedModelType = CampaignModelType.FOUR_PHASE_BRANDING
                )
                selectChannelsByNames(listOf("خبرفوری", "اخبارمشهد", "فردای مشهد", "بانوان مشهد", "اجتماعی مشهد", "تیتر تجارت"))
                updateBrandProfile(profile)
            }
            "EXHIBITION" -> {
                val profile = BrandProfile(
                    brandName = "جشنواره فرهنگی و نمایشگاه صنایع دستی",
                    industry = "نمایشگاه، سوغات و گردشگری",
                    geography = "شهری و مسافران فصلی",
                    mainGoal = "ایجاد ارتباط اولیه، جذب حداکثری بازدیدکنندگان و رونق ۲۵ غرفه",
                    audienceGroup1 = "خانواده‌ها و گردشگران فصلی",
                    audienceGroup1Percent = 60,
                    audienceGroup2 = "مجاوران و خریداران عمده و سازمانی",
                    audienceGroup2Percent = 40,
                    budget = 160_000_000L,
                    avgOrderValue = 2_200_000L,
                    eventInterest = true,
                    selectedModelType = CampaignModelType.EVENT_EXHIBITION
                )
                selectChannelsByNames(listOf("اخبارمشهد", "فردای مشهد", "حوادث مشهد", "خبرفوری", "ورزش فوری", "آب و هوای مشهد"))
                updateBrandProfile(profile)
            }
            "PUBLIC_TRUST" -> {
                val profile = BrandProfile(
                    brandName = "سازمان خدمات شهری و منابع عمومی",
                    industry = "خدمات شهری، زیرساخت و انرژی",
                    geography = "کلان‌شهر پایتخت",
                    mainGoal = "مدیریت افکار عمومی، تبیین چالش‌ها و اعتمادسازی از طریق واقعیت",
                    audienceGroup1 = "شهروندان و مشترکان خانگی",
                    audienceGroup1Percent = 70,
                    audienceGroup2 = "نخبگان رسانه‌ای، کارشناسان و مسئولان",
                    audienceGroup2Percent = 30,
                    budget = 120_000_000L,
                    avgOrderValue = 1_000_000L,
                    eventInterest = false,
                    selectedModelType = CampaignModelType.CRISIS_AND_TRUST
                )
                selectChannelsByNames(listOf("اخبار تهران", "فردای تهران", "خبرفوری", "جریان", "روزنیوز"))
                updateBrandProfile(profile)
            }
            "PRESS_PR" -> {
                val profile = BrandProfile(
                    brandName = "نهاد توسعه‌ای و سازمانی",
                    industry = "سلامت، خدمات عمومی و اداری",
                    geography = "پایتخت و سراسری",
                    mainGoal = "برگزاری نشست خبری تخصصی، مدیریت خبرنگاران و انعکاس شفاف دستاوردها",
                    audienceGroup1 = "خبرنگاران خبرگزاری‌ها و پایگاه‌های آنلاین",
                    audienceGroup1Percent = 50,
                    audienceGroup2 = "افکار عمومی و پیگیران اخبار تخصصی",
                    audienceGroup2Percent = 50,
                    budget = 100_000_000L,
                    avgOrderValue = 1_000_000L,
                    eventInterest = false,
                    selectedModelType = CampaignModelType.PRESS_AND_PR
                )
                selectChannelsByNames(listOf("خبرفوری", "خبرفردا", "اخبار تهران", "جریان", "هم صدا", "ایران تایمز"))
                updateBrandProfile(profile)
            }
        }
    }

    private fun selectChannelsByNames(names: List<String>) {
        _uiState.update { current ->
            val updated = current.selectedChannels.map { item ->
                item.copy(isSelected = item.channel.name in names)
            }
            current.copy(selectedChannels = updated)
        }
    }

    fun generatePersonalizedCampaignPlan() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isGeneratingPlan = true,
                    generationStep = 1,
                    generationStatusText = "در حال واکاوی هویت برند، مزیت رقابتی و رفتار مخاطب هدف..."
                )
            }
            kotlinx.coroutines.delay(450)

            _uiState.update {
                it.copy(
                    generationStep = 2,
                    generationStatusText = "تدوین پیام محوری، شعار اختصاصی و ۵ ستون ارتباطی برند..."
                )
            }
            kotlinx.coroutines.delay(450)

            _uiState.update {
                it.copy(
                    generationStep = 3,
                    generationStatusText = "خلق سناریوهای تیزر ویدیویی، ریلز و رپرتاژ داستانی با هوش مصنوعی..."
                )
            }
            kotlinx.coroutines.delay(450)

            _uiState.update {
                it.copy(
                    generationStep = 4,
                    generationStatusText = "تطبیق و بهینه‌سازی سبد رسانه‌های تلگرامی همخوان با بودجه و جغرافیا..."
                )
            }
            kotlinx.coroutines.delay(400)

            _uiState.update {
                it.copy(
                    generationStep = 5,
                    generationStatusText = "محاسبه شاخص‌های مالی، نرخ تبدیل و پیش‌بینی بازگشت سرمایه (ROI)..."
                )
            }
            kotlinx.coroutines.delay(350)

            // Auto fit channels intelligently to budget and geography
            val budget = _uiState.value.brandProfile.budget
            val geo = _uiState.value.brandProfile.geography
            val productionCost = _uiState.value.productionItems.filter { it.isIncluded }.sumOf { it.baseCost }
            var remainingBudget = (budget - productionCost).coerceAtLeast(15_000_000L)

            val sortedChannels = _uiState.value.selectedChannels.sortedWith(
                compareByDescending<SelectedChannelItem> {
                    val matchesGeo = geo.contains(it.channel.province) || it.channel.province.contains(geo)
                    val isNational = it.channel.province.contains("سراسری") || it.channel.province.contains("تهران")
                    if (matchesGeo) 2 else if (isNational) 1 else 0
                }.thenByDescending { it.channel.views }
            )

            val autoSelected = sortedChannels.map { item ->
                val cost = item.cost
                if (remainingBudget >= cost) {
                    remainingBudget -= cost
                    item.copy(isSelected = true)
                } else {
                    item.copy(isSelected = false)
                }
            }

            var aiCopy: String? = null
            if (GeminiCampaignService.isConfigured()) {
                val res = GeminiCampaignService.generateCreativeEnhancement(_uiState.value.brandProfile)
                aiCopy = res.getOrNull()
            }

            val newPlan = PlanGeneratorEngine.generateFullPlan(
                profile = _uiState.value.brandProfile,
                customChannels = autoSelected,
                customProduction = _uiState.value.productionItems,
                ctrPercent = _uiState.value.ctrPercent,
                conversionRatePercent = _uiState.value.conversionRatePercent,
                aiGeneratedCopy = aiCopy
            )

            _uiState.update {
                it.copy(
                    selectedChannels = autoSelected,
                    currentPlan = newPlan,
                    aiState = if (aiCopy != null) AiState.Success(aiCopy) else it.aiState,
                    isGeneratingPlan = false,
                    generationStep = 5,
                    selectedTab = 1 // Navigate to Strategic & Creative Plan Screen
                )
            }
        }
    }

    fun autoFitChannelsToBudget() {
        val budget = _uiState.value.brandProfile.budget
        val geo = _uiState.value.brandProfile.geography
        val productionCost = _uiState.value.productionItems.filter { it.isIncluded }.sumOf { it.baseCost }
        var remainingBudget = (budget - productionCost).coerceAtLeast(10_000_000L)

        val sortedChannels = _uiState.value.selectedChannels.sortedWith(
            compareByDescending<SelectedChannelItem> {
                val matchesGeo = geo.contains(it.channel.province) || it.channel.province.contains(geo)
                val isNational = it.channel.province.contains("سراسری") || it.channel.province.contains("تهران")
                if (matchesGeo) 2 else if (isNational) 1 else 0
            }.thenByDescending { it.channel.views }
        )

        val updated = sortedChannels.map { item ->
            if (remainingBudget >= item.cost) {
                remainingBudget -= item.cost
                item.copy(isSelected = true)
            } else {
                item.copy(isSelected = false)
            }
        }

        _uiState.update { it.copy(selectedChannels = updated) }
        recalculatePlan()
    }

    fun selectAllChannels() {
        _uiState.update { current ->
            current.copy(selectedChannels = current.selectedChannels.map { it.copy(isSelected = true) })
        }
        recalculatePlan()
    }

    fun deselectAllChannels() {
        _uiState.update { current ->
            current.copy(selectedChannels = current.selectedChannels.map { it.copy(isSelected = false) })
        }
        recalculatePlan()
    }

    fun requestAiEnhancement() {
        viewModelScope.launch {
            _uiState.update { it.copy(aiState = AiState.Loading) }
            val result = GeminiCampaignService.generateCreativeEnhancement(_uiState.value.brandProfile)
            result.fold(
                onSuccess = { content ->
                    _uiState.update { it.copy(aiState = AiState.Success(content)) }
                    recalculatePlan()
                },
                onFailure = { err ->
                    _uiState.update { it.copy(aiState = AiState.Error(err.message ?: "خطا در برقراری ارتباط")) }
                }
            )
        }
    }

    fun setExportDialog(show: Boolean) {
        _uiState.update { it.copy(showExportDialog = show) }
    }

    private fun recalculatePlan() {
        val current = _uiState.value
        val aiCopy = when (val state = current.aiState) {
            is AiState.Success -> state.content
            else -> current.currentPlan?.aiGeneratedCopy
        }
        val plan = PlanGeneratorEngine.generateFullPlan(
            profile = current.brandProfile,
            customChannels = current.selectedChannels,
            customProduction = current.productionItems,
            ctrPercent = current.ctrPercent,
            conversionRatePercent = current.conversionRatePercent,
            aiGeneratedCopy = aiCopy
        )
        _uiState.update { it.copy(currentPlan = plan) }
    }
}
