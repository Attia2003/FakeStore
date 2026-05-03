package com.example.fakestore.core.peresention.screens.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun HoldToConfirmButton(
    text: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    successText: String = "Success",
    enabled: Boolean = true,
    successColor: Color = Color.Green
) {
    val latestOnConfirm by rememberUpdatedState(onConfirm)
    val progress = remember { Animatable(0f) }
    val animationScope = rememberCoroutineScope()
    var isSuccess by rememberSaveable { mutableStateOf(false) }
    var buttonWidthPx by remember { mutableFloatStateOf(0f) }
    var activeAnimationJob by remember { mutableStateOf<Job?>(null) }

    val shape = RoundedCornerShape(12.dp)
    val idleBackground = MaterialTheme.colorScheme.surfaceVariant
    val progressBackground = MaterialTheme.colorScheme.primary
    val successContentColor = contentColorFor(successColor).takeOrElse {
        MaterialTheme.colorScheme.onPrimary
    }

    LaunchedEffect(isSuccess) {
        progress.snapTo(if (isSuccess) 1f else 0f)
    }

    val backgroundColor = if (isSuccess) successColor else idleBackground
    val label = if (isSuccess) successText else text
    val fillFraction = if (isSuccess) 1f else progress.value
    val fillWidthPx = buttonWidthPx * fillFraction

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape)
            .background(backgroundColor)
            .onSizeChanged { buttonWidthPx = it.width.toFloat() }
            .semantics { role = Role.Button }
            .pointerInput(enabled, isSuccess) {
                if (!enabled || isSuccess) {
                    return@pointerInput
                }

                awaitEachGesture {
                    awaitFirstDown(requireUnconsumed = false)

                    activeAnimationJob?.cancel()
                    activeAnimationJob = animationScope.launch {
                        progress.snapTo(0f)


                        progress.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(
                                durationMillis = 1500,
                                easing = LinearEasing
                            )
                        )
                        isSuccess = true
                        latestOnConfirm()
                    }


                    waitForUpOrCancellation()

                    if (!isSuccess) {
                        activeAnimationJob?.cancel()
                        activeAnimationJob = animationScope.launch {
                            progress.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(
                                    durationMillis = 180,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        }
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fillFraction)
                .background(if (isSuccess) successColor else progressBackground)
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (isSuccess) successContentColor else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.Center)
        )

        if (!isSuccess && fillWidthPx > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        clipRect(right = fillWidthPx) {
                            this@drawWithContent.drawContent()
                        }
                    }
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
