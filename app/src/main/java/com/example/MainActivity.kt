package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Apartment
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets.safeDrawing,
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        text = "مدیا پلنر هوشمند برند و رسانه",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 17.sp
                                        )
                                    )
                                },
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            )
                        },
                        bottomBar = {
                            NavigationBar(
                                modifier = Modifier.testTag("main_navigation_bar"),
                                containerColor = MaterialTheme.colorScheme.surface,
                                tonalElevation = 6.dp
                            ) {
                                NavigationBarItem(
                                    selected = state.selectedTab == 0,
                                    onClick = { viewModel.setTab(0) },
                                    icon = { Icon(Icons.Default.Apartment, contentDescription = "اطلاعات برند") },
                                    label = { Text("اطلاعات برند", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary
                                    )
                                )

                                NavigationBarItem(
                                    selected = state.selectedTab == 1,
                                    onClick = { viewModel.setTab(1) },
                                    icon = { Icon(Icons.Default.Festival, contentDescription = "استراتژی و سناریو") },
                                    label = { Text("استراتژی و سناریو", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary
                                    )
                                )

                                NavigationBarItem(
                                    selected = state.selectedTab == 2,
                                    onClick = { viewModel.setTab(2) },
                                    icon = { Icon(Icons.Default.Campaign, contentDescription = "پلن رسانه‌ای") },
                                    label = { Text("پلن رسانه‌ای", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary
                                    )
                                )

                                NavigationBarItem(
                                    selected = state.selectedTab == 3,
                                    onClick = { viewModel.setTab(3) },
                                    icon = { Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = "تحلیل ROI") },
                                    label = { Text("تحلیل ROI", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            when (state.selectedTab) {
                                0 -> BrandIntakeScreen(
                                    viewModel = viewModel,
                                    profile = state.brandProfile,
                                    onNavigateToPlan = { viewModel.setTab(1) }
                                )
                                1 -> state.currentPlan?.let { plan ->
                                    StrategicPlanScreen(
                                        viewModel = viewModel,
                                        plan = plan,
                                        aiState = state.aiState,
                                        onNavigateToMediaPlan = { viewModel.setTab(2) }
                                    )
                                }
                                2 -> MediaPlanScreen(
                                    viewModel = viewModel,
                                    channels = state.selectedChannels,
                                    productionItems = state.productionItems,
                                    searchQuery = state.channelSearchQuery,
                                    selectedCategory = state.selectedCategoryFilter,
                                    targetBudget = state.brandProfile.budget,
                                    brandName = state.brandProfile.brandName,
                                    onNavigateToRoi = { viewModel.setTab(3) }
                                )
                                3 -> state.currentPlan?.let { plan ->
                                    RoiAnalyticsScreen(
                                        viewModel = viewModel,
                                        plan = plan,
                                        showExportDialog = state.showExportDialog
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
