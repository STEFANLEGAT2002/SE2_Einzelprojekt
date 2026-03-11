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
import org.mockito.Mockito.`when` as whenever // when is a reserved keyword in Kotlin

class LeaderboardControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: LeaderboardController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = LeaderboardController(mockedService)
    }

    @Test
    fun test_getLeaderboard_correctScoreSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 15, 10.0)
        val third = GameResult(3, "third", 10, 15.0)

        whenever(mockedService.getAllSorted()).thenReturn(listOf(first, second, third))

        val res: List<GameResult> = controller.getLeaderboard(null)

        verify(mockedService).getAllSorted()
        assertEquals(3, res.size)
        assertEquals(first, res[0])
        assertEquals(second, res[1])
        assertEquals(third, res[2])
    }

    @Test
    fun test_getLeaderboard_sameScore_CorrectPlayTimeSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)

        whenever(mockedService.getAllSorted()).thenReturn(listOf(second, third, first))

        val res: List<GameResult> = controller.getLeaderboard(null)

        verify(mockedService).getAllSorted()
        assertEquals(3, res.size)
        assertEquals(second, res[0])
        assertEquals(third, res[1])
        assertEquals(first, res[2])
    }


    @Test
    fun test_getLeaderboard_ByRank_6PlayersTotal_2above_3under(){
        val alice = (GameResult(1, "Alice", 120, 45.0))
        val bob = (GameResult(2, "Bob", 200, 30.0))
        val charlie = (GameResult(3, "Charlie", 150, 60.0))
        val diana = (GameResult(4, "Diana", 200, 50.0))
        val eve = (GameResult(5, "Eve", 90, 20.0))
        val frank = (GameResult(6, "Frank", 120, 70.0))
        val grace = (GameResult(7, "Grace", 180, 40.0))
        val heidi = (GameResult(8, "Heidi", 160, 35.0))
        val ivan = (GameResult(9, "Ivan", 140, 55.0))
        val judy = (GameResult(10, "Judy", 110, 25.0))

        whenever(mockedService.getAllSorted()).thenReturn(listOf(bob, diana, grace, heidi, charlie, ivan, alice, frank, judy, eve))

        whenever(mockedService.getGameResultByRank(3)).thenReturn(listOf(bob,diana,grace,heidi,charlie,ivan))

        val res: List<GameResult> = controller.getLeaderboard(3)

        verify(mockedService).getAllSorted()
        verify(mockedService).getGameResultByRank(3)
        assertEquals(6,res.size)
        assertEquals(bob, res[0])
        assertEquals(diana, res[1])
        assertEquals(grace, res[2])
        assertEquals(heidi, res[3])
        assertEquals(charlie, res[4])
        assertEquals(ivan, res[5])

    }



    @Test
    fun test_getLeaderboard_ByRank_6PlayersTotal_3above_2under(){
        val alice = (GameResult(1, "Alice", 120, 45.0))
        val bob = (GameResult(2, "Bob", 200, 30.0))
        val charlie = (GameResult(3, "Charlie", 150, 60.0))
        val diana = (GameResult(4, "Diana", 200, 50.0))
        val eve = (GameResult(5, "Eve", 90, 20.0))
        val frank = (GameResult(6, "Frank", 120, 70.0))
        val grace = (GameResult(7, "Grace", 180, 40.0))
        val heidi = (GameResult(8, "Heidi", 160, 35.0))
        val ivan = (GameResult(9, "Ivan", 140, 55.0))
        val judy = (GameResult(10, "Judy", 110, 25.0))

        whenever(mockedService.getAllSorted()).thenReturn(listOf(bob, diana, grace, heidi, charlie, ivan, alice, frank, judy, eve))

        whenever(mockedService.getGameResultByRank(8)).thenReturn(listOf(charlie,ivan,alice,frank,judy, eve))

        val res: List<GameResult> = controller.getLeaderboard(8)

        verify(mockedService).getAllSorted()
        verify(mockedService).getGameResultByRank(8)
        assertEquals(6,res.size)
        assertEquals(charlie, res[0])
        assertEquals(ivan, res[1])
        assertEquals(alice, res[2])
        assertEquals(frank, res[3])
        assertEquals(judy, res[4])
        assertEquals(eve, res[5])

    }


    @Test
    fun test_getLeaderboard_ByRank_7PlayersTotal_3above_3under(){
        val alice = (GameResult(1, "Alice", 120, 45.0))
        val bob = (GameResult(2, "Bob", 200, 30.0))
        val charlie = (GameResult(3, "Charlie", 150, 60.0))
        val diana = (GameResult(4, "Diana", 200, 50.0))
        val eve = (GameResult(5, "Eve", 90, 20.0))
        val frank = (GameResult(6, "Frank", 120, 70.0))
        val grace = (GameResult(7, "Grace", 180, 40.0))
        val heidi = (GameResult(8, "Heidi", 160, 35.0))
        val ivan = (GameResult(9, "Ivan", 140, 55.0))
        val judy = (GameResult(10, "Judy", 110, 25.0))

        whenever(mockedService.getAllSorted()).thenReturn(listOf(bob, diana, grace, heidi, charlie, ivan, alice, frank, judy, eve))

        whenever(mockedService.getGameResultByRank(5)).thenReturn(listOf(diana,grace,heidi,charlie,ivan,alice, frank))

        val res: List<GameResult> = controller.getLeaderboard(5)

        verify(mockedService).getAllSorted()
        verify(mockedService).getGameResultByRank(5)
        assertEquals(7,res.size)
        assertEquals(diana, res[0])
        assertEquals(grace, res[1])
        assertEquals(heidi, res[2])
        assertEquals(charlie, res[3])
        assertEquals(ivan, res[4])
        assertEquals(alice, res[5])
        assertEquals(frank, res[6])

    }



    @Test
    fun test_getLeaderboard_ByRank_invalidRank(){
        val alice = (GameResult(1, "Alice", 120, 45.0))
        val bob = (GameResult(2, "Bob", 200, 30.0))
        val charlie = (GameResult(3, "Charlie", 150, 60.0))
        val diana = (GameResult(4, "Diana", 200, 50.0))
        val eve = (GameResult(5, "Eve", 90, 20.0))
        val frank = (GameResult(6, "Frank", 120, 70.0))
        val grace = (GameResult(7, "Grace", 180, 40.0))
        val heidi = (GameResult(8, "Heidi", 160, 35.0))
        val ivan = (GameResult(9, "Ivan", 140, 55.0))
        val judy = (GameResult(10, "Judy", 110, 25.0))

        whenever(mockedService.getAllSorted()).thenReturn(listOf(bob, diana, grace, heidi, charlie, ivan, alice, frank, judy, eve))



        val exception = assertThrows<ResponseStatusException> {
            controller.getLeaderboard(12)
        }

        verify(mockedService).getAllSorted()

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)

    }







}