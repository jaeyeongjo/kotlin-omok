package omok.model.game

import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class WhiteOmokRuleAdapterTest {
    @ParameterizedTest
    @CsvSource("H8, H9, H11, H12, H10", "F10, G10, I10, J10, H10", "F12, G11, I9, J8, H10")
    fun `5개의 같은 색의 돌이 어느 방향이던 연이어 놓이면 승리이다`(mark1: String, mark2: String, mark3: String, mark4: String, mark5: String) {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark(mark1)))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark(mark2)))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark(mark3)))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark(mark4)))
        }
        val goStone = GoStone(GoStoneColor.WHITE, Coordinate.createWithMark(mark5))
        val rule = WhiteOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkWin(goStone.coordinate)).isEqualTo(PlacementState.WIN)
    }

    @Test
    fun `연달아 놓여져 있는 5개의 돌들의 색이 서로 다르면 승리가 아니다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H8")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H9")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H11")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H12")))
        }
        val goStone = GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H10"))
        val rule = WhiteOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkWin(goStone.coordinate)).isEqualTo(PlacementState.STAY)
    }

    @Test
    fun `놓여져 있는 돌들이 5개보다 적으면 승리가 아니다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H8")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H9")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H11")))
        }
        val goStone = GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H10"))
        val rule = WhiteOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkWin(goStone.coordinate)).isEqualTo(PlacementState.STAY)
    }

    @Test
    fun `6개의 같은 색의 돌이 가로로 연이어 놓이면 승리가 아니다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H8")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H9")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H11")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H12")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H13")))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H10"))
        val rule = WhiteOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkWin(goStone.coordinate)).isEqualTo(PlacementState.STAY)
    }

    @Test
    fun `백돌이 장목이면 승리처리된다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("C10")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("C11")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("C12")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("C14")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("C15")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H8")))
        }
        val goStone = GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("C13"))
        val rule = WhiteOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkWin(goStone.coordinate)).isEqualTo(PlacementState.WIN)
    }
}