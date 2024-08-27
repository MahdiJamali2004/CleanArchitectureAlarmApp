package com.example.alarmko.feature_alarmClock.presentation.alarmRingScreen.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.alarmko.R

@Composable
fun SwipeUpAnimation(
    modifier: Modifier = Modifier
) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.swipe_up7))
    val lottieAnimation by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        restartOnPlay = true
    )


        LottieAnimation(
            modifier = modifier.size(32.dp),
            composition = composition,
            progress = {lottieAnimation},

        )



}

@Preview
@Composable
private fun PreviewSwipeUpAnimation() {
    SwipeUpAnimation()

}