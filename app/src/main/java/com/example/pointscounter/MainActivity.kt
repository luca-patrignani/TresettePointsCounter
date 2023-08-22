/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.pointscounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pointscounter.ui.theme.TipTimeTheme
import com.example.pointscounter.model.UpdatePointsModel
import com.example.pointscounter.model.updatePointsModelOf
import java.lang.NumberFormatException

const val DEFAULT_VALUE = 0
const val GAMES = 6
val inputGrid = Array(GAMES) { _ -> Array(4) { _ -> mutableStateOf(DEFAULT_VALUE) } }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PointsCounterLayout(GAMES, updatePointsModelOf(GAMES), modifier = Modifier.padding(0.dp, 10.dp))
                }
            }
        }
    }
}

@Composable
fun PointsCounterLayout(length: Int, model: UpdatePointsModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1F)) {
            TeamText(text = stringResource(R.string.team_1), modifier = Modifier.fillMaxWidth())
            PointsSumText(sum = model::getTeam1Points, modifier = Modifier.fillMaxWidth())
            Row {
                Column(modifier = Modifier.weight(1F)) {
                    PointsLabelText(stringResource(id = R.string.points), Modifier.fillMaxWidth())
                    val j = 0
                    repeat(length) {
                        EditPointField(Modifier.padding(5.dp), {p -> model.setPlayer1Points(it, p)}, { -> model.getPlayer1Points(it)})
                    }
                }
                Column(modifier = Modifier.weight(1F)) {
                    PointsLabelText(stringResource(id = R.string.extra_points), Modifier.fillMaxWidth())
                    repeat(length) {
                        val j = 1
                        EditExtraPointField(Modifier.padding(5.dp), inputGrid[it][j])
                    }
                }
            }
        }
        Column(modifier = Modifier.weight(1F)) {
            TeamText(text = stringResource(R.string.team_2), modifier = Modifier.fillMaxWidth())
            PointsSumText(sum = model::getTeam2Points, modifier = Modifier.fillMaxWidth())
            Row {
                Column(modifier = Modifier.weight(1F)) {
                    PointsLabelText(stringResource(id = R.string.points), Modifier.fillMaxWidth())
                    repeat(length) {
                        val j = 2
                        EditPointField(Modifier.padding(5.dp), {p -> model.setPlayer2Points(it, p)}, { -> model.getPlayer2Points(it)})
                    }
                }
                Column(modifier = Modifier.weight(1F)) {
                    PointsLabelText(stringResource(id = R.string.extra_points), Modifier.fillMaxWidth())
                    repeat(length) {
                        val j = 3
                        EditExtraPointField(Modifier.padding(5.dp), inputGrid[it][j])
                    }
                }
            }
        }
    }
}

@Composable
private fun PointsLabelText(text: String, modifier: Modifier) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
private fun TeamText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun EditPointField(modifier: Modifier = Modifier, thisTeamPointsSetter: (Int) -> Unit, thisTeamPointsGetter: () -> Int) {
    TextField(
        onValueChange = {
            try {
                thisTeamPointsSetter(Integer.parseInt(it))
            } catch (_: NumberFormatException) {
                thisTeamPointsSetter(DEFAULT_VALUE)
            }
        },
        value = if (thisTeamPointsGetter() == DEFAULT_VALUE)  "" else thisTeamPointsGetter().toString(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
        textStyle = TextStyle(textAlign = TextAlign.Center),
        minLines = 1,
        maxLines = 1,
    )
}

@Composable
fun EditExtraPointField(modifier: Modifier = Modifier, mutableState: MutableState<Int>) {
    TextField(
        onValueChange = {
            try {
                mutableState.value = Integer.parseInt(it)
            } catch (_: NumberFormatException) {
                mutableState.value = DEFAULT_VALUE
            }
        },
        value = if (mutableState.value == DEFAULT_VALUE)  "" else mutableState.value.toString(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
        textStyle = TextStyle(textAlign = TextAlign.Center),
        minLines = 1,
        maxLines = 1,
    )
}

@Composable
fun PointsSumText(modifier: Modifier = Modifier, sum: () -> Int) {
    Text(
        text = sum().toString(),
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_3A, showSystemUi = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        PointsCounterLayout(GAMES, updatePointsModelOf(GAMES), Modifier.padding(0.dp, 10.dp))
    }
}
