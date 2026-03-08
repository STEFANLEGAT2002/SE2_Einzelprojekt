package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/game-results")
class GameResultController(
    private val gameResultService: GameResultService
) {

    @GetMapping("/{gameResultId}")
    fun getGameResult(@PathVariable gameResultId: Long): GameResult? {
        return gameResultService.getGameResult(gameResultId);
    }

    @GetMapping
    fun getAllGameResults(): List<GameResult> {
        return gameResultService.getGameResults();
    }

    @PostMapping
    fun addGameResult(@RequestBody gameResult: GameResult) {
        gameResultService.addGameResult(gameResult)
    }

    @DeleteMapping("/{gameResultId}")
    fun deleteGameResult(@PathVariable gameResultId: Long) {
        gameResultService.deleteGameResult(gameResultId)
    }


    @PostMapping("/init")
    fun initMockData(): String {

        gameResultService.addGameResult(GameResult(1, "Alice", 120, 45.0))
        gameResultService.addGameResult(GameResult(2, "Bob", 200, 30.0))
        gameResultService.addGameResult(GameResult(3, "Charlie", 150, 60.0))
        gameResultService.addGameResult(GameResult(4, "Diana", 200, 50.0))
        gameResultService.addGameResult(GameResult(5, "Eve", 90, 20.0))
        gameResultService.addGameResult(GameResult(6, "Frank", 120, 70.0))
        gameResultService.addGameResult(GameResult(7, "Grace", 180, 40.0))
        gameResultService.addGameResult(GameResult(8, "Heidi", 160, 35.0))
        gameResultService.addGameResult(GameResult(9, "Ivan", 140, 55.0))
        gameResultService.addGameResult(GameResult(10, "Judy", 110, 25.0))

        return "Mock data initialized"
    }
    
}