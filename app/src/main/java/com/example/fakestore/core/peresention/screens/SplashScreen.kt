package com.example.fakestore.core.peresention.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fakestore.ui.theme.FakeStoreTheme
import com.example.fakestore.ui.theme.LightPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    isLoggedIn: Boolean,
    onNavigate: (isLoggedIn: Boolean) -> Unit
) {

    var isButtonEnabled by remember { mutableStateOf(false) }


    val logoScale = remember { Animatable(0f) }
    val contentAlpha = remember { Animatable(0f) }


    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )


    val buttonAlpha by animateFloatAsState(
        targetValue = if (isButtonEnabled) 1f else 0.45f,
        animationSpec = tween(800),
        label = "buttonAlpha"
    )


    LaunchedEffect(Unit) {

        logoScale.animateTo(1f,   animationSpec = tween(700, easing = FastOutSlowInEasing))
        contentAlpha.animateTo(1f, animationSpec = tween(600))

        val totalMs   = 7_000L
        val stepMs    = 50L
        val steps     = totalMs / stepMs

        repeat(steps.toInt()) { i ->
            delay(stepMs)


            if ((i + 1) * stepMs >= 3_000L) {
                isButtonEnabled = true
            }
        }


        onNavigate(isLoggedIn)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B1220),
                        Color(0xFF111A2E),
                        Color(0xFF1A2744)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {


        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.TopEnd)
                .alpha(0.07f)
                .background(LightPrimary, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomStart)
                .alpha(0.05f)
                .background(LightPrimary, CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(contentAlpha.value)
                .padding(horizontal = 32.dp, vertical = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(Modifier.height(48.dp))


                Box(
                    modifier = Modifier
                        .scale(logoScale.value)
                        .size(90.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    LightPrimary.copy(alpha = glowAlpha),
                                    Color(0xFF1D4ED8)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingBag,
                        contentDescription = "Store",
                        tint = Color.White,
                        modifier = Modifier.size(44.dp)
                    )
                }

                Spacer(Modifier.height(28.dp))

                Text(
                    text = "FAKESTORE",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 4.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "YOUR PREMIUM SHOPPING DESTINATION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF9CA3AF),
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {


                Spacer(Modifier.height(24.dp))


                Button(
                    onClick = { if (isButtonEnabled) onNavigate(isLoggedIn) },
                    enabled = isButtonEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .alpha(buttonAlpha),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightPrimary,
                        disabledContainerColor = LightPrimary.copy(alpha = 0.5f),
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(alpha = 0.6f)
                    )
                ) {
                    Text(
                        text = "Start Exploring  →",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
@Preview(name = "Phone", device = "spec:width=411dp,height=891dp")
@Preview(name = "Foldable", device = "spec:width=673.5dp,height=841dp,dpi=480")
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun SplashScreenPreview() {

    FakeStoreTheme {
        SplashScreen(
            isLoggedIn = false,
            onNavigate = {}
        )
    }
}