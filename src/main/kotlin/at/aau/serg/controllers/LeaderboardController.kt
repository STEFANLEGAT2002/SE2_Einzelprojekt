package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/leaderboard")
class LeaderboardController(
    private val gameResultService: GameResultService
) {


    @GetMapping
    fun getLeaderboard(@RequestParam(required = false) rank: Int?): List<GameResult> {
        var ld_board = gameResultService.getGameResults()
        if(rank == null){
            return ld_board

        }

        if(rank < 1 || rank > gameResultService.getGameResults().size){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }

        return gameResultService.getGameResultByRank(rank)


    }


}