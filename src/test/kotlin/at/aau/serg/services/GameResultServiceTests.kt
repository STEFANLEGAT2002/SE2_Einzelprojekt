package at.aau.serg.services

import at.aau.serg.models.GameResult
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GameResultServiceTests {

    private lateinit var service: GameResultService

    @BeforeEach
    fun setup() {
        service = GameResultService()
    }

    @Test
    fun test_getGameResults_emptyList() {
        val result = service.getGameResults()

        assertEquals(emptyList<GameResult>(), result)
    }

    @Test
    fun test_addGameResult_getGameResults_containsSingleElement() {
        val gameResult = GameResult(1, "player1", 17, 15.3)

        service.addGameResult(gameResult)
        val res = service.getGameResults()

        assertEquals(1, res.size)
        assertEquals(gameResult, res[0])
    }

    @Test
    fun test_getGameResultById_existingId_returnsObject() {
        val gameResult = GameResult(1, "player1", 17, 15.3)
        service.addGameResult(gameResult)

        val res = service.getGameResult(1)

        assertEquals(gameResult, res)
    }

    @Test
    fun test_getGameResultById_nonexistentId_returnsNull() {
        val gameResult = GameResult(1, "player1", 17, 15.3)
        service.addGameResult(gameResult)

        val res = service.getGameResult(22)

        assertNull(res)
    }

    @Test
    fun test_addGameResult_multipleEntries_correctId() {
        val gameResult1 = GameResult(0, "player1", 17, 15.3)
        val gameResult2 = GameResult(0, "player2", 25, 16.0)

        service.addGameResult(gameResult1)
        service.addGameResult(gameResult2)

        val res = service.getGameResults()

        assertEquals(2, res.size)

        assertEquals(gameResult1, res[0])
        assertEquals(1, res[0].id)

        assertEquals(gameResult2, res[1])
        assertEquals(2, res[1].id)
    }

    @Test
    fun test_getAllSorted_sortedByScoreDescending() {
        val a = GameResult(1, "A", 100, 50.0)
        val b = GameResult(2, "B", 200, 40.0)
        val c = GameResult(3, "C", 150, 60.0)

        service.addGameResult(a)
        service.addGameResult(b)
        service.addGameResult(c)

        val res = service.getAllSorted()

        assertEquals(b, res[0])
        assertEquals(c, res[1])
        assertEquals(a, res[2])
    }

    @Test
    fun test_getAllSorted_sameScore_sortedByTime() {
        val a = GameResult(1, "A", 100, 50.0)
        val b = GameResult(2, "B", 100, 40.0)

        service.addGameResult(a)
        service.addGameResult(b)

        val res = service.getAllSorted()

        assertEquals(b, res[0])
        assertEquals(a, res[1])
    }



    @Test
    fun test_getAllSorted_multipleEntries_correctOrder() {
        val a = GameResult(1, "A", 120, 45.0)
        val b = GameResult(2, "B", 200, 30.0)
        val c = GameResult(3, "C", 150, 60.0)
        val d = GameResult(4, "D", 200, 50.0)

        service.addGameResult(a)
        service.addGameResult(b)
        service.addGameResult(c)
        service.addGameResult(d)

        val res = service.getAllSorted()

        assertEquals(b, res[0])
        assertEquals(d, res[1])
        assertEquals(c, res[2])
        assertEquals(a, res[3])
    }

    @Test
    fun test_getGameResultByRank_returns6Players_rank3() {
        // Arrange
        val alice = GameResult(0, "Alice", 120, 45.0)
        val bob = GameResult(0, "Bob", 200, 30.0)
        val charlie = GameResult(0, "Charlie", 150, 60.0)
        val diana = GameResult(0, "Diana", 200, 50.0)
        val eve = GameResult(0, "Eve", 90, 20.0)
        val frank = GameResult(0, "Frank", 120, 70.0)
        val grace = GameResult(0, "Grace", 180, 40.0)
        val heidi = GameResult(0, "Heidi", 160, 35.0)
        val ivan = GameResult(0, "Ivan", 140, 55.0)
        val judy = GameResult(0, "Judy", 110, 25.0)

        listOf(alice, bob, charlie, diana, eve, frank, grace, heidi, ivan, judy).forEach {
            service.addGameResult(it)
        }

        // Act
        val res = service.getGameResultByRank(3)

        // Assert
        // Erwartete Sortierung (absteigend Score, aufsteigend Zeit):
        // Bob (200, 30.0), Diana (200, 50.0), Grace (180, 40.0), Heidi (160, 35.0), Charlie (150, 60.0), Ivan (140, 55.0)...
        assertEquals(6, res.size)
        assertEquals("Bob", res[0].playerName)
        assertEquals("Diana", res[1].playerName)
        assertEquals("Grace", res[2].playerName)
        assertEquals("Heidi", res[3].playerName)
        assertEquals("Charlie", res[4].playerName)
        assertEquals("Ivan", res[5].playerName)
    }

    @Test
    fun test_getGameResultByRank_returns6Players_rank8() {
        // Arrange
        val alice = GameResult(0, "Alice", 120, 45.0)
        val bob = GameResult(0, "Bob", 200, 30.0)
        val charlie = GameResult(0, "Charlie", 150, 60.0)
        val diana = GameResult(0, "Diana", 200, 50.0)
        val eve = GameResult(0, "Eve", 90, 20.0)
        val frank = GameResult(0, "Frank", 120, 70.0)
        val grace = GameResult(0, "Grace", 180, 40.0)
        val heidi = GameResult(0, "Heidi", 160, 35.0)
        val ivan = GameResult(0, "Ivan", 140, 55.0)
        val judy = GameResult(0, "Judy", 110, 25.0)

        listOf(alice, bob, charlie, diana, eve, frank, grace, heidi, ivan, judy).forEach {
            service.addGameResult(it)
        }

        // Act
        val res = service.getGameResultByRank(8)

        // Assert
        assertEquals(6, res.size)
        assertEquals("Charlie", res[0].playerName)
        assertEquals("Ivan", res[1].playerName)
        assertEquals("Alice", res[2].playerName)
        assertEquals("Frank", res[3].playerName)
        assertEquals("Judy", res[4].playerName)
        assertEquals("Eve", res[5].playerName)
    }

    @Test
    fun test_getGameResultByRank_returns7Players_rank5() {
        // Arrange
        val alice = GameResult(0, "Alice", 120, 45.0)
        val bob = GameResult(0, "Bob", 200, 30.0)
        val charlie = GameResult(0, "Charlie", 150, 60.0)
        val diana = GameResult(0, "Diana", 200, 50.0)
        val eve = GameResult(0, "Eve", 90, 20.0)
        val frank = GameResult(0, "Frank", 120, 70.0)
        val grace = GameResult(0, "Grace", 180, 40.0)
        val heidi = GameResult(0, "Heidi", 160, 35.0)
        val ivan = GameResult(0, "Ivan", 140, 55.0)
        val judy = GameResult(0, "Judy", 110, 25.0)

        listOf(alice, bob, charlie, diana, eve, frank, grace, heidi, ivan, judy).forEach {
            service.addGameResult(it)
        }

        // Act
        val res = service.getGameResultByRank(5)

        // Assert
        assertEquals(7, res.size)
        assertEquals("Diana", res[0].playerName)
        assertEquals("Grace", res[1].playerName)
        assertEquals("Heidi", res[2].playerName)
        assertEquals("Charlie", res[3].playerName)
        assertEquals("Ivan", res[4].playerName)
        assertEquals("Alice", res[5].playerName)
        assertEquals("Frank", res[6].playerName)
    }

    @Test
    fun test_delete_Player_By_Id(){
        val alice = GameResult(0, "Alice", 120, 45.0)
        val bob = GameResult(0, "Bob", 200, 30.0)
        val charlie = GameResult(0, "Charlie", 150, 60.0)



        service.addGameResult(alice)
        service.addGameResult(bob)
        service.addGameResult(charlie)

        service.deleteGameResult(1)

        val res = service.getGameResults();

        assertEquals(2, res.size)
        assertEquals("Bob", res[0].playerName)
        assertEquals("Charlie", res[1].playerName)


    }



}