package omok.model
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CoordsNumberTest {
    @Test
    fun `유효한 값으로 CoordsNumber 객체 생성 성공`() {
        val validCoordsNumber = CoordsNumber.fromNumber(5)
        assertNotNull(validCoordsNumber)
    }

    @Test
    fun `유효하지 않은 값으로 CoordsNumber 객체 생성 실패`() {
        val invalidCoordsNumber = CoordsNumber.fromNumber(15)
        assertNull(invalidCoordsNumber)
    }
}
