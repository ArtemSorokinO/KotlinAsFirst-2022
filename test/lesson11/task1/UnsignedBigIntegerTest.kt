package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import java.lang.ArithmeticException

internal class UnsignedBigIntegerTest {

    @Test
    @Tag("8")
    fun ubiPlus() {
        assertEquals(UnsignedBigInteger(4), UnsignedBigInteger(2) + UnsignedBigInteger(2))
        assertEquals(UnsignedBigInteger("9087654330"), UnsignedBigInteger("9087654329") + UnsignedBigInteger(1))
    }

    @Test
    @Tag("8")
    fun ubiMinus() {
        assertEquals(UnsignedBigInteger(5), UnsignedBigInteger(19) - UnsignedBigInteger(14))
        assertEquals(UnsignedBigInteger(2), UnsignedBigInteger(4) - UnsignedBigInteger(2))
        assertEquals(UnsignedBigInteger("9087654329"), UnsignedBigInteger("9087654330") - UnsignedBigInteger(1))
        assertEquals(UnsignedBigInteger("0"), UnsignedBigInteger("1") - UnsignedBigInteger(1))
        assertThrows(ArithmeticException::class.java) {
            UnsignedBigInteger(2) - UnsignedBigInteger(4)
        }
    }

    @Test
    @Tag("12")
    fun ubiTimes() {
        assertEquals(
            UnsignedBigInteger("18446744073709551616"),
            UnsignedBigInteger("4294967296") * UnsignedBigInteger("4294967296")
        )
    }

    @Test
    @Tag("16")
    fun ubiDiv() {
        assertEquals(
            UnsignedBigInteger("4294967296"),
            UnsignedBigInteger("18446744073709551616") / UnsignedBigInteger("4294967296")
        )
        assertEquals(
            UnsignedBigInteger("5"),
            UnsignedBigInteger("29") / UnsignedBigInteger("5")
        )
    }

    @Test
    @Tag("16")
    fun ubiRem() {
        assertEquals(UnsignedBigInteger(5), UnsignedBigInteger(19) % UnsignedBigInteger(7))
        assertEquals(
            UnsignedBigInteger(0),
            UnsignedBigInteger("18446744073709551616") % UnsignedBigInteger("4294967296")
        )
    }

    @Test
    @Tag("8")
    fun ubiEquals() {
        assertEquals(UnsignedBigInteger(123456789), UnsignedBigInteger("123456789"))
    }

    @Test
    @Tag("8")
    fun ubiCompareTo() {
        assertTrue(UnsignedBigInteger("184467440737") > UnsignedBigInteger("494967296"))
        assertFalse(UnsignedBigInteger("1") > UnsignedBigInteger("1"))
        assertFalse(UnsignedBigInteger("1") < UnsignedBigInteger("1"))
        assertFalse(UnsignedBigInteger("19") < UnsignedBigInteger("14"))
        assertTrue(UnsignedBigInteger(123456789) < UnsignedBigInteger("9876543210"))
        assertTrue(UnsignedBigInteger("9876543210") > UnsignedBigInteger(123456789))
        assertTrue(UnsignedBigInteger("99999999999999999999999999999999999999999999876543210") > UnsignedBigInteger(123456789))
        assertTrue(UnsignedBigInteger("9") > UnsignedBigInteger(1))
        assertTrue(UnsignedBigInteger("19") > UnsignedBigInteger(18))
        assertFalse(UnsignedBigInteger("1844674407") > UnsignedBigInteger("4294967296"))
    }

    @Test
    @Tag("8")
    fun ubiToInt() {
        assertEquals(123456789, UnsignedBigInteger("123456789").toInt())
        assertThrows(ArithmeticException::class.java) { UnsignedBigInteger("99999999999999999").toInt() }
        assertThrows(ArithmeticException::class.java) { UnsignedBigInteger("999999999999999999999999999999999999999999999999").toInt() }
    }
}