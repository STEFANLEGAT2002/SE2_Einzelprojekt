package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import org.mockito.Mockito.`when` as whenever

class GameResultControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: GameResultController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = GameResultController(mockedService)
    }

    @Test
    fun test_getGameResult_existingId_returnsResultFromService() {
        // Arrange
        val dummyResult = GameResult(1L, "Alice", 120, 45.0)
        whenever(mockedService.getGameResult(1L)).thenReturn(dummyResult)

        // Act
        val res = controller.getGameResult(1L)

        // Assert
        verify(mockedService).getGameResult(1L)
        assertEquals(dummyResult, res)
    }

    @Test
    fun test_getGameResult_nonExistingId_returnsNull() {
        // Arrange
        whenever(mockedService.getGameResult(99L)).thenReturn(null)

        // Act
        val res = controller.getGameResult(99L)

        // Assert
        verify(mockedService).getGameResult(99L)
        assertNull(res)
    }

    @Test
    fun test_getAllGameResults_returnsListFromService() {
        // Arrange
        val dummyList = listOf(
            GameResult(1L, "first", 20, 20.0),
            GameResult(2L, "second", 15, 10.0)
        )
        whenever(mockedService.getGameResults()).thenReturn(dummyList)

        // Act
        val res = controller.getAllGameResults()

        // Assert
        verify(mockedService).getGameResults()
        assertEquals(dummyList, res)
    }

    @Test
    fun test_addGameResult_delegatesToService() {
        // Arrange
        val newPlayer = GameResult(0L, "NewPlayer", 100, 50.0)

        // Act
        controller.addGameResult(newPlayer)

        // Assert
        // Wir prüfen nur, ob der Controller die Methode im Service exakt 1x mit dem richtigen Objekt aufgerufen hat
        verify(mockedService).addGameResult(newPlayer)
    }

    @Test
    fun test_deleteGameResult_delegatesToService() {
        // Act
        controller.deleteGameResult(1L)

        // Assert
        // Wir prüfen nur, ob der Controller dem Service gesagt hat: "Lösche ID 1L"
        verify(mockedService).deleteGameResult(1L)
    }



}