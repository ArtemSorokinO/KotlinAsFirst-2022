package lesson11.task1

import ru.spbstu.wheels.Integer
import java.lang.StringBuilder

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная, общая ценность в баллах -- 32.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
class UnsignedBigInteger : Comparable<UnsignedBigInteger> {

    private var number = mutableListOf<Int>()

    fun getNumber(): MutableList<Int> = number.toMutableList()
    private fun fromStr(s: String): MutableList<Int> {
        var list = mutableListOf<Int>()
        var s = s
        while (s != "") {
            list.add(s.substring(0, 1).toInt())
            s = s.removeRange(0, 1)
        }
        return list
    }
    //"123" -> [1] [2] [3]

    private fun fromInt(i: Int): MutableList<Int> {
        val list = mutableListOf<Int>()
        var i = i
        if (i == 0) return mutableListOf(0)
        while (i != 0) {
            list.add((i % 10))
            i /= 10
        }
        list.reverse()
        return list
    }
    //123 -> [1] [2] [3]

    private fun toStrBack(s: MutableList<Int>): String = s.reversed().joinToString(separator = "")
    // [3] [2] [1] -> "123"

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        number = fromStr(s)
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        number = fromInt(i)
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        val otherNumber = other.number.reversed()
        val timedNumber = mutableListOf<Int>()
        val number = number.reversed()
        var k = 0

        for (i in 0 until maxOf(otherNumber.size, number.size)) {
            var t = otherNumber.getOrElse(i) { 0 } + number.getOrElse(i) { 0 } + k
            if (t >= 10) {
                t -= 10
                timedNumber.add(t)
                k = 1
            } else {
                timedNumber.add(t)
                k = 0
            }
        }
        while (timedNumber.last() == 0) timedNumber.removeLast()
        return UnsignedBigInteger(toStrBack(timedNumber))
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (this < other) throw ArithmeticException()
        val otherNumber = other.number.reversed()
        val number = number.reversed()
        val timedNumber = mutableListOf<Int>()
        var k = 0
        var t = 0

        for (i in 0..number.size - 1) {
            if (number[i] - k < otherNumber.getOrElse(i) { 0 }) {
                t = number[i] + 10 - otherNumber.getOrElse(i) { 0 } - k
                k = 1
            } else {
                t = number[i] - otherNumber.getOrElse(i) { 0 } - k
                k = 0
            }
            timedNumber.add(t)
        }
        while (timedNumber.last() == 0 && timedNumber.size != 1) timedNumber.removeLast()
        return UnsignedBigInteger(toStrBack(timedNumber))
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        val otherNumber = other.number.reversed()
        val timedNumber = mutableListOf<Int>()
        val number = number.reversed()
        var k = 0
        var j = 0
        var ost = 0
        val ln = number.size + otherNumber.size
        do {
            for (i in 0..j) {
                k += number.getOrElse(i) { 0 } * otherNumber.getOrElse(j - i) { 0 }
            }
            k += ost
            ost = k / 10
            timedNumber.add(k % 10)
            k = 0
            j++
        } while (j < ln)
        while (timedNumber.size > 1 && timedNumber.last() == 0) timedNumber.removeLast()
        return UnsignedBigInteger(toStrBack(timedNumber))
    }

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger {
        val timedNumber = mutableListOf<Int>()
        val thisNumber = number.toMutableList()
        var litle = UnsignedBigInteger("")

        while (thisNumber.isNotEmpty()) {
            while (litle < other && thisNumber.isNotEmpty()) {
                println(litle < other)
                litle = UnsignedBigInteger((litle.toString() + thisNumber.first().toString()))
                thisNumber.removeFirst()
            }
            var count = 0
            while (litle > other || litle == other) {
                count++
                litle -= other
            }
            timedNumber.add(count)

        }
        while (timedNumber.size > 1 && timedNumber.last() == 0) timedNumber.removeLast()
        return UnsignedBigInteger(toStrBack(timedNumber.reversed().toMutableList()))
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = this - ((this / other) * other)

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean = if (other is UnsignedBigInteger) number == other.getNumber() else false

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int =
        when {
            this == other -> 0
            number.size < other.number.size -> -1
            number.size > other.number.size -> 1
            else -> {
                var bol = false
                for (i in 0 until number.size) {
                    if (number[i] != other.number[i]) {
                        bol = number[i] > other.number[i]
                        break
                    }
                }
                if (bol) 1 else -1
            }
        }


    /**
     * Преобразование в строку
     */
    override fun toString(): String = buildString { for (i in number) append("$i") }


    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        // 2,147,483,647 - max int
        // 9,223,372,036,854,775,807 - max long, not so long
        if (this.getNumber().joinToString(separator = "").length > 18) throw ArithmeticException()
        else if (this.getNumber().joinToString(separator = "").toLong() > 2147483647) throw ArithmeticException()
        //о нет, это жа запрещённый условием лонг, ужас
        else return number.joinToString(separator = "").toInt()
    }


}