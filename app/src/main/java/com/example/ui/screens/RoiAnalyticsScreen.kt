package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CompleteCampaignPlan
import com.example.data.repository.formatPersian
import com.example.data.repository.formatPersian1
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.viewmodel.MediaPlannerViewModel

@Composable
fun RoiAnalyticsScreen(
    viewModel: MediaPlannerViewModel,
    plan: CompleteCampaignPlan,
    showExportDialog: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val metrics = plan.metrics
    val isProfitable = metrics.netProfit >= 0

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card: Real-time ROI
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isProfitable) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (isProfitable) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isProfitable) EmeraldPrimary else MaterialTheme.colorScheme.error)
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = metrics.assessmentGrade,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "نرخ بازگشت سرمایه لحظه‌ای (ROI)",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "${metrics.roiPercent.formatPersian1()}٪+",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 44.sp
                        ),
                        color = if (isProfitable) EmeraldPrimary else MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "سود خالص برآوردی", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "${metrics.netProfit.formatPersian()} تومان",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (isProfitable) EmeraldPrimary else MaterialTheme.colorScheme.error
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "درآمد فروش تخمینی", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "${metrics.estimatedRevenue.formatPersian()} تومان",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "کل هزینه کمپین", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "${metrics.totalCampaignCost.formatPersian()} تومان",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }

        // Real-Time Interactive Sensitivity Sliders
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Speed, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "شبیه‌ساز و تنظیم حساسیت لحظه‌ای ROI",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Text(
                        text = "با حرکت اسلایدرها، نرخ کلیک، تبدیل و سود را در لحظه تغییر داده و نتیجه را بسنجید:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Slider 1: CTR
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "نرخ کلیک و تعامل مخاطب (CTR):", style = MaterialTheme.typography.bodySmall)
                            Text(
                                text = "${metrics.ctrPercent.formatPersian1()}٪",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Slider(
                            value = metrics.ctrPercent,
                            onValueChange = { viewModel.setCtrPercent(it) },
                            valueRange = 0.5f..8.0f,
                            steps = 14
                        )
                    }

                    // Slider 2: Conversion Rate
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "نرخ تبدیل به مشتری قطعی (CR):", style = MaterialTheme.typography.bodySmall)
                            Text(
                                text = "${metrics.conversionRatePercent.formatPersian1()}٪",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Slider(
                            value = metrics.conversionRatePercent,
                            onValueChange = { viewModel.setConversionRatePercent(it) },
                            valueRange = 0.2f..6.0f,
                            steps = 28
                        )
                    }

                    // Slider 3: Average Ticket / Customer Value
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "ارزش سبد خرید / سفارش:", style = MaterialTheme.typography.bodySmall)
                            Text(
                                text = "${plan.profile.avgOrderValue.formatPersian()} تومان",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        Slider(
                            value = plan.profile.avgOrderValue.toFloat(),
                            onValueChange = { viewModel.setAvgOrderValue(it.toLong()) },
                            valueRange = 500_000f..15_000_000f,
                            steps = 28
                        )
                    }
                }
            }
        }

        // Metrics Grid (Audience, Views, CPM, CPC, Leads, CPA)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Insights, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "شاخص‌های کلیدی عملکرد رسانه‌ای (KPIs)",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Visibility,
                            title = "مجموع بازدیدها",
                            value = metrics.totalEstimatedViews.formatPersian(),
                            unit = "ایمپرشن"
                        )
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.MonetizationOn,
                            title = "شاخص CPM",
                            value = metrics.cpm.formatPersian(),
                            unit = "تومان / ۱۰۰۰ ویو"
                        )
                    }

                    val cpc = if (metrics.estimatedClicks > 0) metrics.totalCampaignCost / metrics.estimatedClicks else 0L
                    val cpa = if (metrics.estimatedCustomers > 0) metrics.totalCampaignCost / metrics.estimatedCustomers else 0L

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.TouchApp,
                            title = "تعداد کلیک (CTR)",
                            value = metrics.estimatedClicks.formatPersian(),
                            unit = "کلیک و تعامل"
                        )
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.PriceCheck,
                            title = "هزینه هر کلیک (CPC)",
                            value = cpc.formatPersian(),
                            unit = "تومان / کلیک"
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.CurrencyExchange,
                            title = "خریداران جدید",
                            value = metrics.estimatedCustomers.formatPersian(),
                            unit = "مشتری / رزرو"
                        )
                        MetricBox(
                            modifier = Modifier.weight(1f),
                            icon = Icons.AutoMirrored.Filled.TrendingUp,
                            title = "هزینه جذب (CPA)",
                            value = cpa.formatPersian(),
                            unit = "تومان به ازای جذب"
                        )
                    }
                }
            }
        }

        // Section: Post-Event Reporting & Expected Deliverables (Doc 2 pattern)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Assessment, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "برنامه گزارش‌دهی و تحویل عملکرد پس از کمپین",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    plan.postEventReportingItems.forEach { item ->
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = item, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    Text(
                        text = "نتایج مورد انتظار از کمپین:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    plan.expectedOutcomes.forEach { outcome ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text(text = "• ", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                            Text(text = outcome, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }

        // Section: Export & Copy Plan
        item {
            Button(
                onClick = { viewModel.setExportDialog(true) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("export_summary_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.ContentCopy, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "دریافت و کپی متن کامل پروپوزال و پلن رسانه‌ای",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showExportDialog) {
        val fullReportText = buildExportReport(plan)

        AlertDialog(
            onDismissRequest = { viewModel.setExportDialog(false) },
            title = {
                Text(text = "خلاصه پروپوزال پلن رسانه‌ای و ROI", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            },
            text = {
                Text(
                    text = fullReportText,
                    style = MaterialTheme.typography.bodySmall,
                    lineHeight = 18.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("MediaPlanReport", fullReportText)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "متن پروپوزال در حافظه کپی شد", Toast.LENGTH_SHORT).show()
                        viewModel.setExportDialog(false)
                    }
                ) {
                    Text("کپی در کلیپ‌بورد")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.setExportDialog(false) }) {
                    Text("بستن")
                }
            }
        )
    }
}

@Composable
fun MetricBox(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    value: String,
    unit: String
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = unit,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

fun buildExportReport(plan: CompleteCampaignPlan): String {
    val b = plan.profile
    val m = plan.metrics
    return """
        📋 پروپوزال جامع کمپین برندینگ، پلن رسانه‌ای و برآورد ROI
        نام برند: ${b.brandName}
        صنعت / حوزه: ${b.industry} | قلمرو جغرافیایی: ${b.geography}
        هدف سفارش: ${b.mainGoal}
        
        📊 پیش‌بینی شاخص‌های عملکرد و بازگشت سرمایه (ROI):
        • مجموع بازدیدهای رسانه‌ای: ${m.totalEstimatedViews.formatPersian()} ویو
        • تعداد کلیک و ارجاع برآوردی: ${m.estimatedClicks.formatPersian()} کلیک (CTR: ${m.ctrPercent.formatPersian1()}٪)
        • مشتریان و سفارش‌های جدید: ${m.estimatedCustomers.formatPersian()} خریدار
        • درآمد ناخالص برآوردی: ${m.estimatedRevenue.formatPersian()} تومان
        • کل هزینه کمپین و رسانه‌ها: ${m.totalCampaignCost.formatPersian()} تومان
        • سود خالص: ${m.netProfit.formatPersian()} تومان
        • نرخ بازگشت سرمایه (ROI): ${m.roiPercent.formatPersian1()}٪+ (${m.assessmentGrade})
        
        🚀 ساختار ۴ مرحله‌ای استراتژی:
        ۱. معرفی و آشنایی (۲ ایونت/کمپین)
        ۲. تثبیت برند و اعتماد (۳ ایونت/کمپین)
        ۳. توسعه برند و B2B (۲ ایونت/کمپین)
        ۴. مرجعیت بازار (۲ ایونت/کمپین)
        
        📡 رسانه‌های تلگرام منتخب:
        ${plan.mediaPlanChannels.filter { it.isSelected }.joinToString("\n") { "• ${it.channel.name} (${it.selectedFormat.titleFa}: ${it.cost.formatPersian()} ت)" }}
    """.trimIndent()
}
