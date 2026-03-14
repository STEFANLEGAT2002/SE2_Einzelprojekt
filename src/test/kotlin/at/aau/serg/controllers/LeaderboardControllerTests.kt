package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.Mockito.`when` as whenever

class LeaderboardControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: LeaderboardController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = LeaderboardController(mockedService)
    }

    @Test
    fun test_getLeaderboard_withoutRank_GetAllSorted() {
        val dummyList = listOf(
            GameResult(1, "first", 20, 20.0),
            GameResult(2, "second", 15, 10.0)
        )
        whenever(mockedService.getAllSorted()).thenReturn(dummyList)

        val res: List<GameResult> = controller.getLeaderboard(null)

        verify(mockedService).getAllSorted()
        assertEquals(dummyList, res)
    }

    @Test
    fun test_getLeaderboard_withValidRank_GetByRank() {
        // Wir simulieren, dass es insgesamt 10 Einträge gibt
        val dummyListAll = List(10) { GameResult(it.toLong(), "Player", 100, 50.0) }
        val dummySubList = dummyListAll.take(5)

        whenever(mockedService.getAllSorted()).thenReturn(dummyListAll)
        whenever(mockedService.getGameResultByRank(5)).thenReturn(dummySubList)

        val res: List<GameResult> = controller.getLeaderboard(5)

        verify(mockedService).getAllSorted()
        verify(mockedService).getGameResultByRank(5)
        assertEquals(dummySubList, res)
    }

    @Test
    fun test_getLeaderboard_invalidRank_tooHigh_throwsBadRequest() {
        val dummyListAll = List(10) { GameResult(it.toLong(), "Player", 100, 50.0) }
        whenever(mockedService.getAllSorted()).thenReturn(dummyListAll)

        val exception = assertThrows<ResponseStatusException> {
            controller.getLeaderboard(12) // Rang 12 bei 10 Spielern
        }

        verify(mockedService).getAllSorted()
        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
    }

    @Test
    fun test_getLeaderboard_invalidRank_isZero_throwsBadRequest() {
        val dummyListAll = List(4) { GameResult(it.toLong(), "Player", 100, 50.0) }
        whenever(mockedService.getAllSorted()).thenReturn(dummyListAll)

        val exception = assertThrows<ResponseStatusException> {
            controller.getLeaderboard(0) // Rang 0 gibt es nicht
        }

        verify(mockedService).getAllSorted()
        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
    }
}