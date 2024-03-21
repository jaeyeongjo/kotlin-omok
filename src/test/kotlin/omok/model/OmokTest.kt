@file:Suppress("ktlint:standard:no-wildcard-imports")

package omok.model

import omok.model.board.CoordsNumber
import omok.model.board.Stone
import omok.model.omokGame.Omok
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OmokTest {
    @Test
    fun `게임 상태가 실행 중인지 확인`() {
        val omok = Omok()
        assertTrue(omok.isRunning())
    }

    @Test
    fun `게임 종료 상태로 변경`() {
        val omok = Omok()
        omok.gameFinish()
        assertFalse(omok.isRunning())
    }

    @Test
    fun `돌을 올바르게 놓는 경우`() {
        val omok = Omok()
        val x = CoordsNumber(0)
        val y = CoordsNumber(0)
        omok.setStone(x, y, Stone.BLACK)
        assertEquals(Stone.BLACK, omok.gameBoard[y.number][x.number])
    }

    @Test
    fun `돌을 놓은 위치가 비어 있지 않은 경우 확인`() {
        val omok = Omok()
        val x = CoordsNumber(0)
        val y = CoordsNumber(0)
        omok.setStone(x, y, Stone.WHITE)
        assertTrue(omok.isNotEmpty(x, y))
    }

    @Test
    fun `금지된 위치에 돌을 놓으려는 경우 확인`() {
        val omok = Omok()
        val forbiddenPositions = listOf(CoordsNumber(0) to CoordsNumber(1))
        assertTrue(omok.isForbidden(CoordsNumber(1), CoordsNumber(0), forbiddenPositions))
    }

    @Test
    fun `돌을 놓은 후 게임이 종료되는 경우 확인`() {
        val omok = Omok()
        for (i in 0 until 4) {
            omok.setStone(CoordsNumber(i), CoordsNumber(0), Stone.BLACK)
        }
        omok.setStone(CoordsNumber(4), CoordsNumber(0), Stone.BLACK)
        omok.isGameOver(CoordsNumber(4), CoordsNumber(0), Stone.BLACK)
        assertEquals(omok.isRunning(), false) // 게임이 끝났는지 확인
    }

    @Test
    fun `33 금수 확인`() {
        val omok = Omok()
        for (i in 2 until 4) {
            omok.setStone(CoordsNumber(i), CoordsNumber(1), Stone.BLACK)
        }
        for (j in 2 until 4) {
            omok.setStone(CoordsNumber(1), CoordsNumber(j), Stone.BLACK)
        }
        val forbids = omok.checkBoard(Stone.BLACK)
        Assertions.assertThat(forbids).isEqualTo(listOf(CoordsNumber(1) to CoordsNumber(1)))
    }

    @Test
    fun `43은 금수아니다`() {
        val omok = Omok()
        for (i in 2 until 5) {
            omok.setStone(CoordsNumber(i), CoordsNumber(1), Stone.BLACK)
        }
        for (j in 2 until 4) {
            omok.setStone(CoordsNumber(1), CoordsNumber(j), Stone.BLACK)
        }
        val forbids = omok.checkBoard(Stone.BLACK)
        Assertions.assertThat(forbids.size).isEqualTo(0)
    }

    @Test
    fun `44는 한쪽이 막혀있어도 금수다`() {
        val omok = Omok()
        for (i in 2 until 5) {
            omok.setStone(CoordsNumber(i), CoordsNumber(1), Stone.BLACK)
        }
        for (j in 2 until 5) {
            omok.setStone(CoordsNumber(1), CoordsNumber(j), Stone.BLACK)
        }
        omok.setStone(CoordsNumber(1), CoordsNumber(5), Stone.WHITE)
        val forbids = omok.checkBoard(Stone.BLACK)
        Assertions.assertThat(forbids).isEqualTo(listOf(CoordsNumber(1) to CoordsNumber(1)))
    }

    @Test
    fun `두쪽이 다 막힌 44는 금수아니다`() {
        val omok = Omok()
        for (i in 2 until 5) {
            omok.setStone(CoordsNumber(i), CoordsNumber(1), Stone.BLACK)
        }
        for (j in 2 until 5) {
            omok.setStone(CoordsNumber(1), CoordsNumber(j), Stone.BLACK)
        }
        omok.setStone(CoordsNumber(1), CoordsNumber(5), Stone.WHITE)
        omok.setStone(CoordsNumber(1), CoordsNumber(1), Stone.WHITE)
        val forbids = omok.checkBoard(Stone.BLACK)
        Assertions.assertThat(forbids.size).isEqualTo(0)
    }
}