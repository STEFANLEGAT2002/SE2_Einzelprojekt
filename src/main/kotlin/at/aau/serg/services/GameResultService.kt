package at.aau.serg.services

import at.aau.serg.models.GameResult
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.max
import kotlin.math.min

@Service
class GameResultService {

    private val gameResults = mutableListOf<GameResult>()
    private var nextId = AtomicLong(1)

    fun addGameResult(gameResult: GameResult) {
        gameResult.id = nextId.getAndIncrement()
        gameResults.add(gameResult)
    }

    fun getAllSorted(): List<GameResult> {
        return gameResults.sortedWith(
            compareByDescending<GameResult> { it.score }
                .thenBy { it.timeInSeconds }
        )
    }



    fun getGameResult(id: Long): GameResult? = gameResults.find { it.id == id } // ? allows null

    fun getGameResults(): List<GameResult> = gameResults.toList()
    // returns immutable list copy

    fun getGameResultByRank(rank: Int): List<GameResult> {
        val sorted = getAllSorted();
         var start = max(0,rank-4)
         var end = min(rank+3, gameResults.size)
        return sorted.subList(start, end)

    }


    /**
     * Kotlin-idiomatic for:
     * fun deleteGameResult(gameResultId: Long) {
     *     gameResults.removeIf({ gameResult -> gameResult.id == gameResultId })
     * }
     */
    fun deleteGameResult(id: Long) = gameResults.removeIf { it.id == id }

}