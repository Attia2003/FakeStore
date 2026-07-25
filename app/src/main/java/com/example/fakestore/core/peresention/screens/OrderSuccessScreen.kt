package com.example.fakestore.core.peresention.screens

import android.graphics.Paint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

private enum class AnimationState {
    ENTERING,
    PAUSED,
    EXITING,
    FINISHED
}

@Composable
fun AnimatedDeliveryVan(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val bodyColor = colorScheme.primary
    val accentColor = colorScheme.secondary
    val panelColor = colorScheme.surfaceVariant
    val textColor = colorScheme.onPrimary
    val outlineColor = colorScheme.outline
    val roadColor = colorScheme.surfaceVariant.copy(alpha = 0.55f)

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val vanWidth = canvasWidth * 0.34f
        val vanHeight = canvasHeight * 0.28f
        val wheelRadius = vanHeight * 0.18f
        val baseY = canvasHeight * 0.62f
        val centerStopX = (canvasWidth - vanWidth) / 2f

        val vanX = when {
            progress <= 0.5f -> lerp(
                start = -vanWidth * 1.15f,
                stop = centerStopX,
                fraction = progress / 0.5f
            )
            progress <= 0.75f -> centerStopX
            else -> lerp(
                start = centerStopX,
                stop = canvasWidth + vanWidth * 1.15f,
                fraction = (progress - 0.75f) / 0.25f
            )
        }

        drawRoundRect(
            color = roadColor,
            topLeft = Offset(0f, baseY + wheelRadius * 1.3f),
            size = Size(canvasWidth, max(canvasHeight - (baseY + wheelRadius * 1.3f), 6f)),
            cornerRadius = CornerRadius(24f, 24f)
        )

        repeat(4) { index ->
            val dashWidth = canvasWidth * 0.12f
            val gap = canvasWidth * 0.08f
            val startX = gap + index * (dashWidth + gap)
            drawRoundRect(
                color = outlineColor.copy(alpha = 0.4f),
                topLeft = Offset(startX, baseY + wheelRadius * 1.72f),
                size = Size(dashWidth, wheelRadius * 0.22f),
                cornerRadius = CornerRadius(20f, 20f)
            )
        }

        drawVanBody(
            vanX = vanX,
            baseY = baseY,
            vanWidth = vanWidth,
            vanHeight = vanHeight,
            wheelRadius = wheelRadius,
            bodyColor = bodyColor,
            accentColor = accentColor,
            panelColor = panelColor,
            outlineColor = outlineColor,
            textColor = textColor
        )
    }
}

private fun DrawScope.drawVanBody(
    vanX: Float,
    baseY: Float,
    vanWidth: Float,
    vanHeight: Float,
    wheelRadius: Float,
    bodyColor: Color,
    accentColor: Color,
    panelColor: Color,
    outlineColor: Color,
    textColor: Color
) {
    val cargoWidth = vanWidth * 0.58f
    val cabWidth = vanWidth * 0.28f
    val cargoHeight = vanHeight * 0.76f
    val cabHeight = vanHeight * 0.54f
    val bodyTop = baseY - vanHeight
    val cargoTop = baseY - cargoHeight
    val cabTop = baseY - cabHeight

    drawRoundRect(
        color = bodyColor,
        topLeft = Offset(vanX, cargoTop),
        size = Size(cargoWidth, cargoHeight),
        cornerRadius = CornerRadius(vanHeight * 0.14f, vanHeight * 0.14f)
    )

    drawRoundRect(
        color = bodyColor,
        topLeft = Offset(vanX + cargoWidth - vanWidth * 0.04f, cabTop),
        size = Size(cabWidth, cabHeight),
        cornerRadius = CornerRadius(vanHeight * 0.16f, vanHeight * 0.16f)
    )

    drawRoundRect(
        color = accentColor,
        topLeft = Offset(vanX + cargoWidth * 0.12f, cargoTop + cargoHeight * 0.18f),
        size = Size(cargoWidth * 0.22f, cargoHeight * 0.12f),
        cornerRadius = CornerRadius(14f, 14f)
    )

    drawRoundRect(
        color = panelColor,
        topLeft = Offset(vanX + cargoWidth + vanWidth * 0.01f, cabTop + cabHeight * 0.16f),
        size = Size(cabWidth * 0.48f, cabHeight * 0.3f),
        cornerRadius = CornerRadius(18f, 18f)
    )

    drawRoundRect(
        color = accentColor,
        topLeft = Offset(vanX + cargoWidth * 0.78f, bodyTop + vanHeight * 0.72f),
        size = Size(vanWidth * 0.22f, vanHeight * 0.08f),
        cornerRadius = CornerRadius(20f, 20f)
    )

    val firstWheelCenter = Offset(vanX + cargoWidth * 0.28f, baseY + wheelRadius * 0.42f)
    val secondWheelCenter = Offset(vanX + cargoWidth + cabWidth * 0.74f, baseY + wheelRadius * 0.42f)

    drawCircle(color = outlineColor, radius = wheelRadius, center = firstWheelCenter)
    drawCircle(color = outlineColor, radius = wheelRadius, center = secondWheelCenter)
    drawCircle(color = panelColor, radius = wheelRadius * 0.48f, center = firstWheelCenter)
    drawCircle(color = panelColor, radius = wheelRadius * 0.48f, center = secondWheelCenter)

    drawContext.canvas.nativeCanvas.drawText(
        "FS",
        vanX + cargoWidth * 0.5f,
        cargoTop + cargoHeight * 0.58f,
        Paint().apply {
            color = textColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = cargoHeight * 0.42f
            isFakeBoldText = true
            isAntiAlias = true
        }
    )
}

@Composable
fun OrderSuccessScreen(
    onTrackOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animationState by rememberSaveable { mutableStateOf(AnimationState.ENTERING) }
    val vanProgress = remember { Animatable(0f) }
    val backgroundProgress by animateFloatAsState(
        targetValue = if (animationState == AnimationState.FINISHED) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "order-success-background"
    )

    LaunchedEffect(Unit) {
        animationState = AnimationState.ENTERING
        vanProgress.snapTo(0f)

        vanProgress.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
        )

        animationState = AnimationState.PAUSED
        vanProgress.animateTo(
            targetValue = 0.75f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )

        animationState = AnimationState.EXITING
        vanProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )

        animationState = AnimationState.FINISHED
    }

    val containerShape = RoundedCornerShape(28.dp)
    val finalCardBackground = MaterialTheme.colorScheme.surface
    val screenBackground = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            androidx.compose.ui.graphics.lerp(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.primaryContainer,
                min(0.18f + backgroundProgress * 0.1f, 0.28f)
            )
        )
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(screenBackground)
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            if (animationState != AnimationState.FINISHED) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Preparing your order",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Your FakeStore van is on the move.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    AnimatedDeliveryVan(
                        progress = vanProgress.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = animationState == AnimationState.FINISHED,
                enter = fadeIn(animationSpec = tween(durationMillis = 450)) +
                    scaleIn(
                        animationSpec = tween(durationMillis = 500),
                        initialScale = 0.92f
                    ),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Surface(
                    shape = containerShape,
                    color = finalCardBackground,
                    tonalElevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape
                                )
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Order successful",
                                tint = contentColorFor(MaterialTheme.colorScheme.primaryContainer),
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Success!",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Your order is confirmed and the delivery is already underway.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        Button(
                            onClick = onTrackOrder,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                text = "Track Your Order",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction.coerceIn(0f, 1f)
}
