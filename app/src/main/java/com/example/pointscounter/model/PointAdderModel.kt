package com.example.pointscounter.model

import androidx.compose.runtime.MutableState

interface PointAdderModel {
    fun getTeam1Points(): Int
    fun getTeam2Points(): Int
}

interface UpdatePointsModel : PointAdderModel {
    fun setPlayer1Points(game: Int, points: Int)
    fun getPlayer1Points(game: Int): Int
    fun setPlayer2Points(game: Int, points: Int)
    fun getPlayer2Points(game: Int): Int
}

fun pointAdderModelOf(grid: Array<Array<MutableState<Int>>>): PointAdderModel {
    return object : PointAdderModel {
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

fun updatePointsModelOf(games: Int): UpdatePointsModel {
    return object : UpdatePointsModel {
        val team1Points = Array(games) { _ -> 0 }
        val team2Points = Array(games) { _ -> 0 }

        override fun setPlayer1Points(game: Int, points: Int) {
            team1Points[game] = points
        }

        override fun getPlayer1Points(game: Int): Int {
            return team1Points[game]
        }

        override fun setPlayer2Points(game: Int, points: Int) {
            team2Points[game] = points
        }

        override fun getPlayer2Points(game: Int): Int {
            return team1Points[game]
        }

        override fun getTeam1Points(): Int {
            return team1Points.sumOf { v -> v }
        }

        override fun getTeam2Points(): Int {
            return team2Points.sumOf { v -> v }
        }

    }
}
