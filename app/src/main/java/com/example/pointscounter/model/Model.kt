package com.example.pointscounter.model

import androidx.compose.runtime.MutableState

interface Model {
    fun getTeam1Points(): Int
    fun getTeam2Points(): Int
}

fun modelOf(grid: Array<Array<MutableState<Int>>>): Model {
    return object : Model {
        override fun getTeam1Points(): Int {
            var sum = 0
            for (row in grid) {
                for (j in 0..1) {
                    sum += row[j].value
                }
            }
            return sum
        }

        override fun getTeam2Points(): Int {
            var sum = 0
            for (row in grid) {
                for (j in 2..3) {
                    sum += row[j].value
                }
            }
            return sum
        }

    }
}
