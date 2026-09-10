package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.BrandProfile
import com.example.data.repository.ChannelDataRepository
import com.example.data.repository.PlanGeneratorEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("مدیا پلنر هوشمند", appName)
    }

    @Test
    fun `test all 53 telegram channels are loaded with exact tariffs`() {
        val channels = ChannelDataRepository.allChannels
        assertEquals(53, channels.size)
        
        val khabarFori = channels.find { it.name == "خبرفوری" }
        assertNotNull(khabarFori)
        assertEquals(7_450_000L, khabarFori?.specialTariff)
        assertEquals(18_900_000L, khabarFori?.reportageTariff)

        val akhbarMashhad = channels.find { it.name == "اخبارمشهد" }
        assertNotNull(akhbarMashhad)
        assertEquals(5_500_000L, akhbarMashhad?.specialTariff)
    }

    @Test
    fun `test plan generator produces 4 phases and real-time roi`() {
        val profile = BrandProfile(
            brandName = "هتل نمونه گردشگری",
            industry = "هتل و گردشگری",
            geography = "مشهد",
            mainGoal = "افزایش رزرو و اقامت",
            audienceGroup1 = "زائران",
            audienceGroup1Percent = 50,
            audienceGroup2 = "ساکنین",
            audienceGroup2Percent = 50,
            budget = 80_000_000L,
            avgOrderValue = 4_000_000L,
            eventInterest = true
        )

        val plan = PlanGeneratorEngine.generateFullPlan(profile)
        assertEquals(4, plan.phases.size)
        assertTrue(plan.mediaPlanChannels.isNotEmpty())
        assertTrue(plan.metrics.totalEstimatedViews > 0)
        assertTrue(plan.metrics.roiPercent != 0f)

        // Verify personalized creative content package
        val creative = plan.creativePackage
        assertTrue(creative.brandSlogans.isNotEmpty())
        assertTrue(creative.videoTeaserScene.scene1Hook.isNotEmpty())
        assertTrue(creative.reelsShortVideoScene.onScreenHookText.isNotEmpty())
        assertTrue(creative.reportageDraft.catchyHeadline.isNotEmpty())
        assertTrue(creative.viralEngagementIdea.contestTitle.isNotEmpty())
    }
}
