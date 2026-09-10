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
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.LinearProgressIndicator
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

private data class NavItem(
    val label: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
)

private data class WorkspacePage(
    val eyebrow: String,
    val title: String,
    val subtitle: String,
)

private val workspacePages = listOf(
    WorkspacePage("01  /  DISCOVER", "شناخت و تعریف برند", "اطلاعات پایه، مخاطب، بودجه و هدف کمپین"),
    WorkspacePage("02  /  STRATEGY", "استراتژی و سناریو", "روایت، مدل راهبردی و خروجی هوش مصنوعی"),
    WorkspacePage("03  /  MEDIA", "پلن رسانه‌ای", "انتخاب کانال، بودجه‌بندی و تولید محتوا"),
    WorkspacePage("04  /  PERFORMANCE", "اقتصاد و ROI", "برآورد بازده، هزینه و شاخص‌های عملکرد"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(selectedTab: Int) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(13.dp),
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.AutoAwesome, null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(21.dp))
                    }
                }
                Spacer(Modifier.width(10.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text("MEDIA PLANNER", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, letterSpacing = 1.sp))
                    Text("AI CAMPAIGN WORKSPACE", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 0.7.sp)
                }
                Spacer(Modifier.width(10.dp))
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                ) {
                    Row(Modifier.padding(horizontal = 9.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(7.dp).clip(CircleShape).background(MaterialTheme.colorScheme.tertiary))
                        Spacer(Modifier.width(5.dp))
                        Text("ONLINE AI", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                    }
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
        ),
    )
}

@Composable
private fun WorkspaceHeader(selectedTab: Int, brandName: String) {
    val page = workspacePages[selectedTab.coerceIn(0, 3)]
    val progress = (selectedTab + 1) / 4f

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
    ) {
        Column(Modifier.padding(horizontal = 18.dp, vertical = 15.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(page.eyebrow, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.1.sp), color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(3.dp))
                    Text(page.title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black))
                    Spacer(Modifier.height(2.dp))
                    Text(page.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer) {
                    Text("${selectedTab + 1}/4", Modifier.padding(horizontal = 11.dp, vertical = 8.dp), fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            Spacer(Modifier.height(13.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(50.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            )
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                workspacePages.forEachIndexed { index, item ->
                    val active = index <= selectedTab
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(6.dp).clip(CircleShape).background(if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant))
                        Spacer(Modifier.width(4.dp))
                        if (index == selectedTab || (index == 0 && brandName.isNotBlank())) {
                            Text(if (index == selectedTab) item.title else "✓", style = MaterialTheme.typography.labelSmall, color = if (active) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                        }
                    }
                }
            }
        }
    }
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
                                tonalElevation = 4.dp,
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
                        Column(
                            modifier = Modifier.fillMaxSize().padding(innerPadding),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            WorkspaceHeader(state.selectedTab, state.brandProfile.brandName)
                            Box(Modifier.fillMaxWidth().weight(1f)) {
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
}
