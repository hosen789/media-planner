package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Festival
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.BrandIntakeScreen
import com.example.ui.screens.MediaPlanScreen
import com.example.ui.screens.RoiAnalyticsScreen
import com.example.ui.screens.StrategicPlanScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MediaPlannerViewModel

private data class NavItem(
    val label: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
)

@Composable
private fun AppTopBar(selectedTab: Int) {
    val subtitles = listOf("شناخت و تعریف برند", "استراتژی، روایت و سناریو", "انتخاب رسانه و مدیریت بودجه", "سنجش بازده و اقتصاد کمپین")
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    modifier = Modifier.size(38.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.AutoAwesome, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(21.dp))
                    }
                }
                Spacer(Modifier.width(10.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text("Media Planner", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold))
                    Text(subtitles[selectedTab.coerceIn(0, 3)], style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(Modifier.width(10.dp))
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        .padding(horizontal = 9.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(Modifier.size(7.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
                    Spacer(Modifier.width(5.dp))
                    Text("AI", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
        ),
    )
}

class MainActivity : ComponentActivity() {
    private val viewModel: MediaPlannerViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    val navItems = listOf(
                        NavItem("برند", "اطلاعات برند", Icons.Default.Apartment),
                        NavItem("استراتژی", "استراتژی و سناریو", Icons.Default.Festival),
                        NavItem("رسانه", "پلن رسانه‌ای", Icons.Default.Campaign),
                        NavItem("ROI", "تحلیل ROI", Icons.AutoMirrored.Filled.TrendingUp),
                    )
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                        contentWindowInsets = WindowInsets.safeDrawing,
                        topBar = { AppTopBar(state.selectedTab) },
                        bottomBar = {
                            NavigationBar(
                                modifier = Modifier.testTag("main_navigation_bar"),
                                containerColor = MaterialTheme.colorScheme.surface,
                                tonalElevation = 2.dp,
                            ) {
                                navItems.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = state.selectedTab == index,
                                        onClick = { viewModel.setTab(index) },
                                        icon = { Icon(item.icon, contentDescription = item.description) },
                                        label = { Text(item.label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.primary,
                                            selectedTextColor = MaterialTheme.colorScheme.primary,
                                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                        ),
                                    )
                                }
                            }
                        },
                    ) { innerPadding ->
                        Box(Modifier.fillMaxSize().padding(innerPadding)) {
                            when (state.selectedTab) {
                                0 -> BrandIntakeScreen(viewModel = viewModel, profile = state.brandProfile, onNavigateToPlan = { viewModel.setTab(1) })
                                1 -> state.currentPlan?.let {
                                    StrategicPlanScreen(viewModel = viewModel, plan = it, aiState = state.aiState, onNavigateToMediaPlan = { viewModel.setTab(2) })
                                }
                                2 -> MediaPlanScreen(
                                    viewModel = viewModel,
                                    channels = state.selectedChannels,
                                    productionItems = state.productionItems,
                                    searchQuery = state.channelSearchQuery,
                                    selectedCategory = state.selectedCategoryFilter,
                                    targetBudget = state.brandProfile.budget,
                                    brandName = state.brandProfile.brandName,
                                    onNavigateToRoi = { viewModel.setTab(3) },
                                )
                                3 -> state.currentPlan?.let {
                                    RoiAnalyticsScreen(viewModel = viewModel, plan = it, showExportDialog = state.showExportDialog)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
