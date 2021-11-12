import java.lang.Exception

const val constantString: String = "This string is constant"

object Lab {

    @JvmStatic
    fun main(args:Array<String>){
        println("**********2 Task*********")
        val imInt = 14
        val imDouble = 14.11
        val imString = "This is a Big string"

        var exInt: Int = 10
        var exDouble: Double = 12.34
        var exString: String = "This is a large explicit string"

        val byteVal: Byte = 9
        val intValForByte: Int = byteVal.toInt()

        val intValForString: Int = 14
        val stringVal: String = intValForString.toString()

        var nullableInt: Int? = null

        println("ImplicitInt: $imInt")
        println("ImplicitDouble: $imDouble")
        println("ImplicitString: $imString")

        println("\nExplicitInt: $exInt")
        println("ExplicitDouble: $exDouble")
        println("ExplicitString: $exString")

        println("\nByte to Int: $byteVal - $intValForByte")
        println("Int to String: $intValForString - $stringVal")

        println("Constant string: $constantString")
        println("NullableInt value: $nullableInt")
        print("Enter number: ")
        nullableInt = readLine()?.toInt()
        println("NullableInt value: $nullableInt")


        println("**********3 Task*********")
        println("Is valid: " + isValid("user@mail.ru", "pass1234").toString())
        println("Is valid: " + isValid("usernb", "pass1234").toString())
        println("Is valid: " + isValid("user@mail.ru", "123 4435").toString())
        println("Is valid: " + isValid("user@mail.ru", "123").toString())
        println("Is valid: " + isValid("us6er53@gmail.com", "pass12341").toString())
        println("Is valid: " + isValid(null, "").toString())
        println("Is valid: " + isValid("", null).toString())

        println("\nIs holiday: ${isHoliday("6.5.2020")}")
        println("Is holiday: ${isHoliday("8.3.2020")}")
        println("Is holiday: ${isHoliday("31.12.2020")}")
        println("Is holiday: ${isHoliday("")}")
        println("Is holiday: ${isHoliday(null)}")

        println("\n+: ${doOperation(5, 6, '+')}")
        println("-: ${doOperation(6, 1, '-')}")
        println("*: ${doOperation(5, 6, '*')}")
        println("/: ${doOperation(6, 5, '/')}")
        println("%: ${doOperation(6, 5, '%')}")

        val nullIntArray: IntArray? = null
        println("\nMax index: ${nullIntArray.indexOfMax()}")
        println("Max index: ${intArrayOf(1, 2, 3, 5, 78,10).indexOfMax()}")
        println("Max index: ${intArrayOf(1, 78, 5, 78, 4,10).indexOfMax()}")

        println("\ncoincidence: ${"privet".coincidence("poka")}")
        println("coincidence: ${"privet".coincidence("priver")}")


        println("**********4 Task*********")
        println("\nFactorial cycle: ${factorialCycle(5)}")
        println("Factorial range: ${factorialRange(5)}")
        println("Recursive factorial: ${recursiveFactorial(5)}")

        isPrimeFun(10000)

        println("**********5 Task*********")
        val numberList = ArrayList<Int>()
        numberList.add(2)
        numberList.add(4)
        numberList.add(5)
        numberList.add(8)

        val firstResult: Boolean = containsIn(numberList) { x -> x % 3 == 0 }
        println("First lambda result: $firstResult")

        val secondResult: Boolean = containsIn(numberList) { x -> x - 5 > 0 }
        println("Second lambda result: $secondResult\n")
        listOf()
        mapTest()
    }

    fun isValid(email:String?, password:String?):Boolean{
        fun notNull(email: String?, password: String?):Boolean = email == null || password == null|| email == ""|| password == ""

        if (notNull(email, password)) { return false }
        if (!Regex(pattern = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").containsMatchIn(email!!)) { return false }
        if (password!!.length !in 6..12 || password.contains(" ")) {return false}
        return true
    }

    enum class Holidays(var date: String) {
        CHRISTMAS_DAY("25.12.2020"),
        WOMENS_DAY("8.3.2020"),
        NEW_YEAR("31.12.2020"),
        NATIONAL_DAY("3.7.2020"),
    }

    fun isHoliday(date: String?): Boolean {
        return when (date) {
            Holidays.NEW_YEAR.date -> true
            Holidays.WOMENS_DAY.date -> true
            Holidays.NATIONAL_DAY.date -> true
            Holidays.CHRISTMAS_DAY.date -> true
            else -> false
        }
    }

    fun doOperation(a: Int, b: Int, operation: Char): Double {
        return when(operation) {
            '+' -> (a + b).toDouble()
            '-' -> (a - b).toDouble()
            '*' -> (a * b).toDouble()
            '%' -> (a % b).toDouble()
            '/' -> (a / b).toDouble()
            else -> throw Exception("Unable to perform operation")
        }
    }

    fun IntArray?.indexOfMax(): Int? {
        if (this == null) {
            return null
        }

        var maxIndex: Int = 0
        var maxValue: Int = 0
        var countMax: Int = 0

        for (value in this) {
            if (maxValue == value) {
                countMax += 1
            }

            if (maxValue < value) {
                maxValue = value
                countMax = 0
            }
            maxIndex += 1
        }

        return if (countMax == 0) maxIndex else null
    }

    fun String.coincidence(comparer: String): Int {
        var counter: Int = 0

        for (i in 0 until this.count()) {
            for (j in 0 until comparer.count()) {
                if (i == j && this[i].toLowerCase() == comparer[j].toLowerCase()) {
                    counter++
                }
            }
        }

        return counter
    }


    fun factorialCycle(n: Int): Double {
        var counter: Int = 0
        var result: Double = 1.0
        while (counter != n) {
            counter++
            result *= counter
        }
        return  result
    }

    fun factorialRange(n: Int): Double {
        var result: Double = 1.0
        for (i in 1..n) {
            result *= i
        }
        return result
    }

    fun recursiveFactorial(n: Int): Double {
        return if (n == 1) n.toDouble()
        else n * recursiveFactorial(n - 1)
    }

    fun isPrimeFun(number: Int) {
        fun isPrimeLocal(number: Int): Boolean {
            for (i in 2..number/2) {
                if (number % i == 0) {
                    return false
                }
            }
            return true
        }

        val list: MutableList<Int> = mutableListOf()
        val arrayInt: Array<Int?> = arrayOfNulls(10)
        var index = 0

        for (num in 2..number) {
            if (isPrimeLocal(num)) {
                if (list.size < 20) {
                    list.add(num)
                } else if (index in 20..29) {
                    arrayInt[index - 20] = num
                }
                index++
            }
        }

        println("\nTotal items: $index")
        print("List: ")
        list.forEach { x -> print(" $x") }

        print("\nArray: ")
        arrayInt.forEach { x -> print(" $x") }
    }

    fun containsIn(collection: Collection<Int>, operation: (item: Int) -> Boolean): Boolean =
        collection.any { item -> operation(item) }


    fun listOf() {
        val intList: MutableList<Int> = mutableListOf(1, 2, 3, 2, 7, 7, 9)

        intList.plus(5)
        intList += 10

        val distinctElements: List<Int> = intList.distinct()
        print("Distinct:")
        distinctElements.forEach { item -> print(" $item") }

        val filteredElements: List<Int> = intList.filter { item -> item % 2 != 0 }
        print("\nFiltered:")
        filteredElements.forEach { item -> print(" $item") }

        val onlyPrimeNumbers: List<Int> = intList.filter(::isPrime)
        print("\nPrime numbers:")
        onlyPrimeNumbers.forEach { item -> print(" $item") }

        val findOnlyValueThree: Int? = intList.find { item -> item == 3 }
        println("\nDoes list contain item: $findOnlyValueThree")

        val groupByNums: Map<Boolean, List<Int>> = intList.groupBy { item -> item % 2 == 0 }
        print("Map of items:")
        println(groupByNums.forEach { item -> print(" ${item.key}: ${item.value} ") })

        val areAllItemsHigherThanFour: Boolean = intList.all { item -> item > 4 }
        println("Are all list items higher than 4: $areAllItemsHigherThanFour")

        val isThereOneItemLowerThanSix: Boolean = intList.any { item -> item < 6 }
        println("Is there item lower than 6: $isThereOneItemLowerThanSix")

        val (firstItem: Int, secondItem: Int) = intList
        print("First and second items: $firstItem and $secondItem")
    }

    fun isPrime(value: Int): Boolean {
        var flag = false
        for (i in 2..value / 2) {
            if (value % i == 0) {
                flag = true
                break
            }
        }
        return flag
    }


    fun mapTest() {
        val testResult: Map<String, Int> = mapOf(
            "Olentsevich" to 29,
            "Denisyk" to 37,
            "Hz" to 10,
            "Nikolaenkov" to 39,
            "Yakunovich" to 9
        )

        val mappedMap: Map<String, Int> = testResult.mapValues { when(it.value) {
            40 -> 10
            39 -> 9
            38 -> 8
            in 35..37 -> 7
            in 32..34 -> 6
            in 29..31 -> 5
            in 25..28 -> 4
            in 22..24 -> 3
            in 19..21 -> 2
            else -> 1
        } }

        println("\n\nResults: ")
        for ((key, value) in mappedMap) {
            println("$key = $value")
        }

        val uniqueMarks: HashSet<Int> = mappedMap.values.toHashSet()
        println("\nMarks: ")
        for (mark in uniqueMarks) {
            val marksCount: Int = mappedMap.count { it.value == mark }
            println("$mark: $marksCount")
        }

        val amountOfBadMarks: Boolean = mappedMap.any { it.value < 4 }
        println("\nAre there any fails: $amountOfBadMarks")
    }
}