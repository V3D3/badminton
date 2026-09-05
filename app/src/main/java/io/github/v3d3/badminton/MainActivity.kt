package io.github.v3d3.badminton

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText

class MainActivity : ComponentActivity() {
    private var ourTeamScore by mutableStateOf(0)
    private var forwardTeamScore by mutableStateOf(0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WearApp(
                ourTeamScore = ourTeamScore,
                forwardTeamScore = forwardTeamScore,
                onResetScores = {
                    ourTeamScore = 0
                    forwardTeamScore = 0
                }
            )
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_STEM_1 -> {
                forwardTeamScore++
                true
            }
            KeyEvent.KEYCODE_STEM_2 -> {
                ourTeamScore++
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }
}

@Composable
fun WearApp(
    ourTeamScore: Int,
    forwardTeamScore: Int,
    onResetScores: () -> Unit,
) {
    BadmintonScoreTracker(
        ourTeamScore = ourTeamScore,
        forwardTeamScore = forwardTeamScore,
        onResetScores = onResetScores,
    )
}

@Composable
fun BadmintonScoreTracker(
    ourTeamScore: Int,
    forwardTeamScore: Int,
    onResetScores: () -> Unit,
) {
    val textColor = Color(0xFFF44336)
    val ourTeamTextColor = Color(0xFF2196F3)

    Scaffold(
        timeText = { TimeText() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onResetScores()
                        }
                    )
                },
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$forwardTeamScore",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$ourTeamScore",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ourTeamTextColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true)
@Composable
fun BadmintonScoreTrackerPreview() {
    BadmintonScoreTracker(ourTeamScore = 5, forwardTeamScore = 7, onResetScores = {})
}
