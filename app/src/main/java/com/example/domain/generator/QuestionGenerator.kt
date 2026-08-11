package com.example.domain.generator

import com.example.domain.model.CategoryGroup
import com.example.domain.model.Difficulty
import com.example.domain.model.GeneratedQuestion
import com.example.domain.model.Topic
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

interface IQuestionGenerator {
    fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion
}

object TopicsCatalog {
    val ALL_TOPICS = listOf(
        // Basic Calculation
        Topic("addition", "Addition", CategoryGroup.BASIC_CALCULATION, "Speed addition of 2 to 4 digit numbers"),
        Topic("subtraction", "Subtraction", CategoryGroup.BASIC_CALCULATION, "Fast subtraction with positive results"),
        Topic("multiplication", "Multiplication", CategoryGroup.BASIC_CALCULATION, "Tables and multi-digit multiplication"),
        Topic("division", "Division", CategoryGroup.BASIC_CALCULATION, "Quotient and division calculations"),

        // Number Skills
        Topic("simplification", "Simplification", CategoryGroup.NUMBER_SKILLS, "BODMAS rule & expression solving"),
        Topic("number_system", "Number System", CategoryGroup.NUMBER_SKILLS, "Divisibility, factors, units digit"),
        Topic("lcm_hcf", "LCM & HCF", CategoryGroup.NUMBER_SKILLS, "Least common multiple & highest common factor"),
        Topic("squares_cubes", "Squares & Cubes", CategoryGroup.NUMBER_SKILLS, "Squares up to 50 & Cubes up to 30"),
        Topic("roots", "Square & Cube Roots", CategoryGroup.NUMBER_SKILLS, "Finding exact roots rapidly"),
        Topic("surds_indices", "Surds & Indices", CategoryGroup.NUMBER_SKILLS, "Powers and exponent rules"),

        // Arithmetic
        Topic("percentage", "Percentage", CategoryGroup.ARITHMETIC, "Percent calculations & increase/decrease"),
        Topic("ratio_proportion", "Ratio & Proportion", CategoryGroup.ARITHMETIC, "Dividing quantities & finding proportions"),
        Topic("average", "Average", CategoryGroup.ARITHMETIC, "Mean values & weighted averages"),
        Topic("profit_loss", "Profit & Loss", CategoryGroup.ARITHMETIC, "Cost price, selling price, profit/loss %"),
        Topic("discount", "Discount", CategoryGroup.ARITHMETIC, "Marked price & successive discounts"),
        Topic("simple_interest", "Simple Interest", CategoryGroup.ARITHMETIC, "P × R × T / 100 calculations"),
        Topic("compound_interest", "Compound Interest", CategoryGroup.ARITHMETIC, "Interest compounded annually"),
        Topic("time_work", "Time & Work", CategoryGroup.ARITHMETIC, "Individual efficiency & group work"),
        Topic("speed_time_distance", "Speed, Time & Distance", CategoryGroup.ARITHMETIC, "Relative speed & unit conversions"),
        Topic("boats_streams", "Boats & Streams", CategoryGroup.ARITHMETIC, "Upstream & downstream speeds"),
        Topic("ages", "Ages", CategoryGroup.ARITHMETIC, "Ratios of ages in past, present & future"),

        // Advanced
        Topic("linear_equations", "Linear Equations", CategoryGroup.ARITHMETIC, "Solving for single & double variables"),
        Topic("quadratic_equations", "Quadratic Equations", CategoryGroup.ADVANCED, "Finding roots of ax² + bx + c = 0"),
        Topic("mensuration", "Mensuration", CategoryGroup.ADVANCED, "Perimeter, area & volume formulas")
    )

    fun getTopicById(id: String): Topic {
        return ALL_TOPICS.find { it.id == id } ?: ALL_TOPICS.first()
    }
}

class SpeedMathGeneratorEngine {

    fun generateQuestions(
        topicId: String,
        difficulty: Difficulty,
        count: Int
    ): List<GeneratedQuestion> {
        val effectiveDifficulty = if (difficulty == Difficulty.MIXED) {
            listOf(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD).random()
        } else difficulty

        val questions = mutableListOf<GeneratedQuestion>()
        val generator = getGeneratorForTopic(topicId)

        for (i in 1..count) {
            val currentDiff = if (difficulty == Difficulty.MIXED) {
                listOf(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD).random()
            } else difficulty
            
            questions.add(generator.generateQuestion(i, currentDiff))
        }
        return questions
    }

    fun generateDailyChallenge(): Pair<Topic, List<GeneratedQuestion>> {
        val topic = TopicsCatalog.ALL_TOPICS.random()
        val questions = generateQuestions(topic.id, Difficulty.MEDIUM, 20)
        return Pair(topic, questions)
    }

    fun generate60sSpeedChallenge(): List<GeneratedQuestion> {
        val topic = TopicsCatalog.getTopicById("addition") // Mixed basic calculation
        val questions = mutableListOf<GeneratedQuestion>()
        val basicTopics = listOf("addition", "subtraction", "multiplication", "division", "percentage")
        
        for (i in 1..100) {
            val randomTopicId = basicTopics.random()
            val generator = getGeneratorForTopic(randomTopicId)
            val diff = if (i < 10) Difficulty.EASY else if (i < 30) Difficulty.MEDIUM else Difficulty.HARD
            questions.add(generator.generateQuestion(i, diff))
        }
        return questions
    }

    private fun getGeneratorForTopic(topicId: String): IQuestionGenerator {
        return when (topicId) {
            "addition" -> AdditionGenerator()
            "subtraction" -> SubtractionGenerator()
            "multiplication" -> MultiplicationGenerator()
            "division" -> DivisionGenerator()
            "simplification" -> SimplificationGenerator()
            "number_system" -> NumberSystemGenerator()
            "lcm_hcf" -> LcmHcfGenerator()
            "squares_cubes" -> SquaresCubesGenerator()
            "roots" -> RootsGenerator()
            "surds_indices" -> SurdsIndicesGenerator()
            "percentage" -> PercentageGenerator()
            "ratio_proportion" -> RatioGenerator()
            "average" -> AverageGenerator()
            "profit_loss" -> ProfitLossGenerator()
            "discount" -> DiscountGenerator()
            "simple_interest" -> SimpleInterestGenerator()
            "compound_interest" -> CompoundInterestGenerator()
            "time_work" -> TimeWorkGenerator()
            "speed_time_distance" -> SpeedTimeDistanceGenerator()
            "boats_streams" -> BoatsStreamsGenerator()
            "ages" -> AgesGenerator()
            "linear_equations" -> LinearEquationsGenerator()
            "quadratic_equations" -> QuadraticEquationGenerator()
            "mensuration" -> MensurationGenerator()
            else -> GenericTopicGenerator(topicId)
        }
    }
}

class AdditionGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val a = Random.nextInt(12, 99)
                val b = Random.nextInt(12, 99)
                val ans = a + b
                GeneratedQuestion(
                    id = id,
                    questionText = "Add the numbers:",
                    formattedExpression = "$a + $b = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$a + $b = $ans"
                )
            }
            Difficulty.MEDIUM -> {
                val a = Random.nextInt(120, 899)
                val b = Random.nextInt(120, 899)
                val ans = a + b
                GeneratedQuestion(
                    id = id,
                    questionText = "Calculate the sum:",
                    formattedExpression = "$a + $b = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$a + $b = $ans"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val a = Random.nextInt(840, 4899)
                val b = Random.nextInt(350, 3899)
                val c = Random.nextInt(120, 990)
                val ans = a + b + c
                GeneratedQuestion(
                    id = id,
                    questionText = "Calculate the total sum:",
                    formattedExpression = "$a + $b + $c = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$a + $b + $c = $ans"
                )
            }
        }
    }
}

class SubtractionGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val a = Random.nextInt(40, 150)
                val b = Random.nextInt(10, a - 5)
                val ans = a - b
                GeneratedQuestion(
                    id = id,
                    questionText = "Subtract:",
                    formattedExpression = "$a − $b = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$a − $b = $ans"
                )
            }
            Difficulty.MEDIUM -> {
                val a = Random.nextInt(300, 1500)
                val b = Random.nextInt(100, a - 20)
                val ans = a - b
                GeneratedQuestion(
                    id = id,
                    questionText = "Find the difference:",
                    formattedExpression = "$a − $b = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$a − $b = $ans"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val a = Random.nextInt(2500, 9800)
                val b = Random.nextInt(1200, a - 100)
                val ans = a - b
                GeneratedQuestion(
                    id = id,
                    questionText = "Calculate the difference:",
                    formattedExpression = "$a − $b = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$a − $b = $ans"
                )
            }
        }
    }
}

class MultiplicationGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val a = Random.nextInt(6, 19)
                val b = Random.nextInt(6, 19)
                val ans = a * b
                GeneratedQuestion(
                    id = id,
                    questionText = "Multiply:",
                    formattedExpression = "$a × $b = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$a × $b = $ans"
                )
            }
            Difficulty.MEDIUM -> {
                val a = Random.nextInt(112, 350)
                val b = Random.nextInt(11, 29)
                val ans = a * b
                GeneratedQuestion(
                    id = id,
                    questionText = "Calculate product:",
                    formattedExpression = "$a × $b = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$a × $b = $ans"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val a = Random.nextInt(240, 899)
                val b = Random.nextInt(25, 99)
                val ans = a * b
                GeneratedQuestion(
                    id = id,
                    questionText = "Speed product:",
                    formattedExpression = "$a × $b = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$a × $b = $ans"
                )
            }
        }
    }
}

class DivisionGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val ans = Random.nextInt(5, 25)
                val divisor = Random.nextInt(3, 12)
                val dividend = ans * divisor
                GeneratedQuestion(
                    id = id,
                    questionText = "Divide:",
                    formattedExpression = "$dividend ÷ $divisor = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$dividend ÷ $divisor = $ans"
                )
            }
            Difficulty.MEDIUM -> {
                val ans = Random.nextInt(15, 85)
                val divisor = Random.nextInt(12, 35)
                val dividend = ans * divisor
                GeneratedQuestion(
                    id = id,
                    questionText = "Find quotient:",
                    formattedExpression = "$dividend ÷ $divisor = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$dividend ÷ $divisor = $ans"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val ans = Random.nextInt(45, 250)
                val divisor = Random.nextInt(25, 85)
                val dividend = ans * divisor
                GeneratedQuestion(
                    id = id,
                    questionText = "Calculate quotient:",
                    formattedExpression = "$dividend ÷ $divisor = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$dividend ÷ $divisor = $ans"
                )
            }
        }
    }
}

class SimplificationGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val a = Random.nextInt(10, 50)
        val b = Random.nextInt(2, 8)
        val c = Random.nextInt(5, 20)
        val ans = a + (b * c)
        return GeneratedQuestion(
            id = id,
            questionText = "Simplify using BODMAS:",
            formattedExpression = "$a + ($b × $c) = ?",
            correctAnswer = "$ans",
            numericAnswer = ans.toDouble(),
            explanation = "First multiply $b × $c = ${b*c}, then add $a + ${b*c} = $ans"
        )
    }
}

class SquaresCubesGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val isCube = difficulty == Difficulty.HARD || Random.nextBoolean()
        if (isCube) {
            val base = Random.nextInt(2, 15)
            val ans = base * base * base
            return GeneratedQuestion(
                id = id,
                questionText = "Find the cube:",
                formattedExpression = "$base³ = ?",
                correctAnswer = "$ans",
                numericAnswer = ans.toDouble(),
                explanation = "$base × $base × $base = $ans"
            )
        } else {
            val base = Random.nextInt(11, 40)
            val ans = base * base
            return GeneratedQuestion(
                id = id,
                questionText = "Find the square:",
                formattedExpression = "$base² = ?",
                correctAnswer = "$ans",
                numericAnswer = ans.toDouble(),
                explanation = "$base × $base = $ans"
            )
        }
    }
}

class RootsGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val ans = Random.nextInt(12, 35)
        val square = ans * ans
        return GeneratedQuestion(
            id = id,
            questionText = "Find the square root:",
            formattedExpression = "√$square = ?",
            correctAnswer = "$ans",
            numericAnswer = ans.toDouble(),
            explanation = "√$square = $ans (since $ans² = $square)"
        )
    }
}

class PercentageGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val percentList = listOf(5, 10, 15, 20, 25, 30, 40, 50, 60, 75)
        val pct = percentList.random()
        val base = Random.nextInt(4, 30) * 20
        val ans = (pct * base) / 100
        return GeneratedQuestion(
            id = id,
            questionText = "Calculate percentage:",
            formattedExpression = "What is $pct% of $base?",
            correctAnswer = "$ans",
            numericAnswer = ans.toDouble(),
            explanation = "($pct / 100) × $base = $ans"
        )
    }
}

class RatioGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val r1 = Random.nextInt(2, 7)
        val r2 = Random.nextInt(3, 9)
        val totalParts = r1 + r2
        val multiplier = Random.nextInt(10, 40)
        val total = totalParts * multiplier
        val ans = r1 * multiplier
        return GeneratedQuestion(
            id = id,
            questionText = "Share in ratio $r1 : $r2 of Total $total. Find 1st share:",
            formattedExpression = "1st share = ?",
            correctAnswer = "$ans",
            numericAnswer = ans.toDouble(),
            explanation = "1 part = $total / $totalParts = $multiplier. 1st share = $r1 × $multiplier = $ans"
        )
    }
}

class AverageGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val count = 4
        val avg = Random.nextInt(15, 60)
        val targetSum = avg * count
        val n1 = avg - Random.nextInt(2, 10)
        val n2 = avg + Random.nextInt(2, 10)
        val n3 = avg - Random.nextInt(1, 8)
        val n4 = targetSum - (n1 + n2 + n3)
        return GeneratedQuestion(
            id = id,
            questionText = "Find average of:",
            formattedExpression = "Average of $n1, $n2, $n3, $n4 = ?",
            correctAnswer = "$avg",
            numericAnswer = avg.toDouble(),
            explanation = "Sum = ${n1+n2+n3+n4}. Average = Sum / 4 = $avg"
        )
    }
}

class ProfitLossGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val cp = Random.nextInt(10, 50) * 10
        val profitPct = listOf(10, 15, 20, 25, 30, 50).random()
        val profit = (cp * profitPct) / 100
        val sp = cp + profit
        return GeneratedQuestion(
            id = id,
            questionText = "CP = ₹$cp, Profit = $profitPct%. Find SP:",
            formattedExpression = "Selling Price (SP) = ?",
            correctAnswer = "$sp",
            numericAnswer = sp.toDouble(),
            explanation = "Profit = $profitPct% of $cp = ₹$profit. SP = $cp + $profit = ₹$sp"
        )
    }
}

class SimpleInterestGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val p = Random.nextInt(10, 50) * 100
        val r = listOf(5, 8, 10, 12, 15).random()
        val t = Random.nextInt(1, 5)
        val si = (p * r * t) / 100
        return GeneratedQuestion(
            id = id,
            questionText = "Principal = ₹$p, Rate = $r%, Time = $t yrs. Find SI:",
            formattedExpression = "Simple Interest (SI) = ?",
            correctAnswer = "$si",
            numericAnswer = si.toDouble(),
            explanation = "SI = (P × R × T) / 100 = ($p × $r × $t) / 100 = ₹$si"
        )
    }
}

class QuadraticEquationGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val root1 = Random.nextInt(1, 9)
        val root2 = Random.nextInt(1, 9)
        val b = -(root1 + root2)
        val c = root1 * root2
        val bStr = if (b >= 0) "+ $b" else "- ${abs(b)}"
        return GeneratedQuestion(
            id = id,
            questionText = "Find the sum of roots for x² $bStr x + $c = 0:",
            formattedExpression = "Sum of roots = ?",
            correctAnswer = "${root1 + root2}",
            numericAnswer = (root1 + root2).toDouble(),
            explanation = "Sum of roots = -b/a = -($b)/1 = ${root1 + root2}. (Roots are $root1 and $root2)"
        )
    }
}

class GenericTopicGenerator(private val topicId: String) : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        val topic = TopicsCatalog.getTopicById(topicId)
        val a = Random.nextInt(12, 40)
        val b = Random.nextInt(5, 15)
        val ans = a * b
        return GeneratedQuestion(
            id = id,
            questionText = "${topic.name} practice problem:",
            formattedExpression = "$a × $b = ?",
            correctAnswer = "$ans",
            numericAnswer = ans.toDouble(),
            explanation = "$a × $b = $ans"
        )
    }
}

class NumberSystemGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val base = listOf(2, 3, 4, 7, 8, 9).random()
                val exp = Random.nextInt(2, 12)
                var ans = 1
                for (i in 1..exp) {
                    ans = (ans * base) % 10
                }
                GeneratedQuestion(
                    id = id,
                    questionText = "Find the unit digit:",
                    formattedExpression = "Unit digit of $base^$exp = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$base^$exp mod 10 = $ans"
                )
            }
            Difficulty.MEDIUM -> {
                val divisor = listOf(3, 5, 7, 9, 11).random()
                val ans = Random.nextInt(1, divisor - 1)
                val mult = Random.nextInt(12, 80)
                val dividend = mult * divisor + ans
                GeneratedQuestion(
                    id = id,
                    questionText = "Find the remainder:",
                    formattedExpression = "Remainder of $dividend ÷ $divisor = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "$dividend = ($divisor × $mult) + $ans. Remainder is $ans."
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val n = Random.nextInt(10, 50)
                val ans = n * (n + 1) / 2
                GeneratedQuestion(
                    id = id,
                    questionText = "Find the sum of first $n natural numbers:",
                    formattedExpression = "1 + 2 + 3 + ... + $n = ?",
                    correctAnswer = "$ans",
                    numericAnswer = ans.toDouble(),
                    explanation = "Sum = N(N + 1) / 2 = $n × ${n + 1} / 2 = $ans"
                )
            }
        }
    }
}

class LcmHcfGenerator : IQuestionGenerator {
    private fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
    private fun lcm(a: Int, b: Int): Int = (a * b) / gcd(a, b)

    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val g = listOf(2, 3, 4, 5, 6).random()
                val a = g * Random.nextInt(2, 6)
                val b = g * Random.nextInt(3, 8)
                val hcfVal = gcd(a, b)
                GeneratedQuestion(
                    id = id,
                    questionText = "Find HCF (Highest Common Factor):",
                    formattedExpression = "HCF($a, $b) = ?",
                    correctAnswer = "$hcfVal",
                    numericAnswer = hcfVal.toDouble(),
                    explanation = "Highest Common Factor dividing both $a and $b is $hcfVal."
                )
            }
            Difficulty.MEDIUM -> {
                val a = listOf(6, 8, 10, 12, 15).random()
                val b = listOf(8, 12, 15, 20, 24).random()
                val lcmVal = lcm(a, b)
                GeneratedQuestion(
                    id = id,
                    questionText = "Find LCM (Least Common Multiple):",
                    formattedExpression = "LCM($a, $b) = ?",
                    correctAnswer = "$lcmVal",
                    numericAnswer = lcmVal.toDouble(),
                    explanation = "Least Common Multiple of $a and $b is $lcmVal."
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val hcfVal = Random.nextInt(4, 15)
                val lcmVal = hcfVal * Random.nextInt(6, 20)
                val num1 = hcfVal * Random.nextInt(2, 6)
                val num2 = (hcfVal * lcmVal) / num1
                GeneratedQuestion(
                    id = id,
                    questionText = "HCF = $hcfVal, LCM = $lcmVal. If 1st number = $num1, find 2nd number:",
                    formattedExpression = "2nd number = ?",
                    correctAnswer = "$num2",
                    numericAnswer = num2.toDouble(),
                    explanation = "Num1 × Num2 = HCF × LCM => $num1 × Num2 = $hcfVal × $lcmVal => Num2 = $num2"
                )
            }
        }
    }
}

class SurdsIndicesGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val base = listOf(2, 3, 5).random()
                val p1 = Random.nextInt(2, 5)
                val p2 = Random.nextInt(2, 5)
                val ansExp = p1 + p2
                GeneratedQuestion(
                    id = id,
                    questionText = "Simplify (find power of $base):",
                    formattedExpression = "$base^$p1 × $base^$p2 = $base^?",
                    correctAnswer = "$ansExp",
                    numericAnswer = ansExp.toDouble(),
                    explanation = "Rule a^m × a^n = a^(m+n): $p1 + $p2 = $ansExp"
                )
            }
            Difficulty.MEDIUM -> {
                val base = listOf(2, 3, 4, 5).random()
                val exp = Random.nextInt(3, 6)
                var value = 1
                for (i in 1..exp) value *= base
                GeneratedQuestion(
                    id = id,
                    questionText = "Solve for x:",
                    formattedExpression = "If $base^x = $value, then x = ?",
                    correctAnswer = "$exp",
                    numericAnswer = exp.toDouble(),
                    explanation = "$base^$exp = $value, so x = $exp"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val base = listOf(2, 3).random()
                val x = Random.nextInt(2, 6)
                var rhs = 1
                for (i in 1..(x + 2)) rhs *= base
                GeneratedQuestion(
                    id = id,
                    questionText = "Solve for x:",
                    formattedExpression = "If $base^(x + 2) = $rhs, then x = ?",
                    correctAnswer = "$x",
                    numericAnswer = x.toDouble(),
                    explanation = "$base^(x + 2) = $base^${x + 2} => x + 2 = ${x + 2} => x = $x"
                )
            }
        }
    }
}

class DiscountGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val mp = Random.nextInt(10, 50) * 20
                val dPct = listOf(10, 15, 20, 25, 30, 50).random()
                val discountAmount = (mp * dPct) / 100
                val sp = mp - discountAmount
                GeneratedQuestion(
                    id = id,
                    questionText = "Marked Price = ₹$mp, Discount = $dPct%. Find Selling Price:",
                    formattedExpression = "Selling Price (SP) = ?",
                    correctAnswer = "$sp",
                    numericAnswer = sp.toDouble(),
                    explanation = "Discount = $dPct% of ₹$mp = ₹$discountAmount. SP = $mp - $discountAmount = ₹$sp"
                )
            }
            Difficulty.MEDIUM -> {
                val mp = Random.nextInt(10, 50) * 10
                val sp = mp - (Random.nextInt(1, 5) * 10)
                val discount = mp - sp
                val dPct = (discount * 100) / mp
                GeneratedQuestion(
                    id = id,
                    questionText = "MP = ₹$mp, SP = ₹$sp. Find Discount Percentage:",
                    formattedExpression = "Discount % = ?",
                    correctAnswer = "$dPct",
                    numericAnswer = dPct.toDouble(),
                    explanation = "Discount = ₹$discount. Discount % = ($discount / $mp) × 100 = $dPct%"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val d1 = listOf(10, 20, 30).random()
                val d2 = listOf(10, 20).random()
                val eqDiscount = d1 + d2 - (d1 * d2) / 100
                GeneratedQuestion(
                    id = id,
                    questionText = "Successive discounts of $d1% and $d2% equal single discount of:",
                    formattedExpression = "Equivalent Discount % = ?",
                    correctAnswer = "$eqDiscount",
                    numericAnswer = eqDiscount.toDouble(),
                    explanation = "Equivalent Discount = d1 + d2 - (d1×d2)/100 = $d1 + $d2 - ${(d1*d2)/100} = $eqDiscount%"
                )
            }
        }
    }
}

class CompoundInterestGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val p = Random.nextInt(10, 50) * 100
                val r = listOf(10, 20).random()
                val rateFactor = 1.0 + (r / 100.0)
                val amount = p * rateFactor * rateFactor
                val ci = (amount - p).roundToInt()
                GeneratedQuestion(
                    id = id,
                    questionText = "P = ₹$p, R = $r% p.a. (compounded annually), T = 2 yrs. Find CI:",
                    formattedExpression = "Compound Interest (CI) = ?",
                    correctAnswer = "$ci",
                    numericAnswer = ci.toDouble(),
                    explanation = "Amount = ₹${amount.roundToInt()}. CI = $amount - $p = ₹$ci"
                )
            }
            Difficulty.MEDIUM -> {
                val p = Random.nextInt(10, 40) * 100
                val r = listOf(10, 20).random()
                val amount = (p * (1 + r / 100.0) * (1 + r / 100.0)).roundToInt()
                GeneratedQuestion(
                    id = id,
                    questionText = "P = ₹$p, R = $r% p.a. CI for 2 yrs. Find total Amount:",
                    formattedExpression = "Amount (A) = ?",
                    correctAnswer = "$amount",
                    numericAnswer = amount.toDouble(),
                    explanation = "A = P(1 + R/100)² = ₹$amount"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val p = Random.nextInt(10, 50) * 1000
                val r = listOf(5, 10, 20).random()
                val diff = (p * (r / 100.0) * (r / 100.0)).roundToInt()
                GeneratedQuestion(
                    id = id,
                    questionText = "P = ₹$p, R = $r% p.a. Find difference between CI and SI for 2 years:",
                    formattedExpression = "CI - SI = ?",
                    correctAnswer = "$diff",
                    numericAnswer = diff.toDouble(),
                    explanation = "Difference = P × (R/100)² = ₹$diff"
                )
            }
        }
    }
}

class TimeWorkGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val niceA = listOf(10, 12, 20, 30).random()
                val niceB = when (niceA) {
                    10 -> 15
                    12 -> 24
                    20 -> 30
                    else -> 60
                }
                val days = (niceA * niceB) / (niceA + niceB)
                GeneratedQuestion(
                    id = id,
                    questionText = "A completes work in $niceA days, B in $niceB days. Together they take:",
                    formattedExpression = "Days required = ?",
                    correctAnswer = "$days",
                    numericAnswer = days.toDouble(),
                    explanation = "1/A + 1/B = 1/$niceA + 1/$niceB => Days = $days"
                )
            }
            Difficulty.MEDIUM -> {
                val together = listOf(4, 6, 8, 12).random()
                val aAlone = when (together) {
                    4 -> 12
                    6 -> 10
                    8 -> 12
                    else -> 20
                }
                val bAlone = (together * aAlone) / (aAlone - together)
                GeneratedQuestion(
                    id = id,
                    questionText = "A & B together take $together days. A alone takes $aAlone days. B alone takes:",
                    formattedExpression = "Days for B alone = ?",
                    correctAnswer = "$bAlone",
                    numericAnswer = bAlone.toDouble(),
                    explanation = "1/B = 1/$together - 1/$aAlone = 1/$bAlone => $bAlone days"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val men = Random.nextInt(4, 12)
                val days1 = Random.nextInt(10, 20)
                val men2 = men * listOf(2, 3).random()
                val days2 = (men * days1) / men2
                GeneratedQuestion(
                    id = id,
                    questionText = "$men men do work in $days1 days. How many days will $men2 men take?",
                    formattedExpression = "Days required = ?",
                    correctAnswer = "$days2",
                    numericAnswer = days2.toDouble(),
                    explanation = "M1 × D1 = M2 × D2 => $men × $days1 = $men2 × D2 => D2 = $days2 days"
                )
            }
        }
    }
}



class SpeedTimeDistanceGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val speed = Random.nextInt(4, 12) * 10
                val time = Random.nextInt(2, 6)
                val dist = speed * time
                GeneratedQuestion(
                    id = id,
                    questionText = "Speed = $speed km/h, Time = $time hours. Find Distance:",
                    formattedExpression = "Distance (D) = ?",
                    correctAnswer = "$dist",
                    numericAnswer = dist.toDouble(),
                    explanation = "Distance = Speed × Time = $speed × $time = $dist km"
                )
            }
            Difficulty.MEDIUM -> {
                val kmh = listOf(36, 54, 72, 90, 108).random()
                val ms = (kmh * 5) / 18
                GeneratedQuestion(
                    id = id,
                    questionText = "Convert $kmh km/h into m/s:",
                    formattedExpression = "Speed in m/s = ?",
                    correctAnswer = "$ms",
                    numericAnswer = ms.toDouble(),
                    explanation = "$kmh × (5 / 18) = $ms m/s"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val s1 = 40
                val s2 = 60
                val avgSpeed = (2 * s1 * s2) / (s1 + s2)
                GeneratedQuestion(
                    id = id,
                    questionText = "Car travels at $s1 km/h and returns at $s2 km/h. Find average speed:",
                    formattedExpression = "Average Speed = ?",
                    correctAnswer = "$avgSpeed",
                    numericAnswer = avgSpeed.toDouble(),
                    explanation = "Avg Speed = (2 × s1 × s2) / (s1 + s2) = (2 × 40 × 60) / 100 = $avgSpeed km/h"
                )
            }
        }
    }
}

class BoatsStreamsGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val b = Random.nextInt(10, 25)
                val s = Random.nextInt(2, 6)
                val ds = b + s
                GeneratedQuestion(
                    id = id,
                    questionText = "Boat speed = $b km/h, Stream speed = $s km/h. Find Downstream Speed:",
                    formattedExpression = "Downstream Speed = ?",
                    correctAnswer = "$ds",
                    numericAnswer = ds.toDouble(),
                    explanation = "Downstream Speed = Boat Speed + Stream Speed = $b + $s = $ds km/h"
                )
            }
            Difficulty.MEDIUM -> {
                val b = Random.nextInt(12, 22)
                val s = Random.nextInt(2, 5)
                val us = b - s
                GeneratedQuestion(
                    id = id,
                    questionText = "Boat speed = $b km/h, Stream speed = $s km/h. Find Upstream Speed:",
                    formattedExpression = "Upstream Speed = ?",
                    correctAnswer = "$us",
                    numericAnswer = us.toDouble(),
                    explanation = "Upstream Speed = Boat Speed - Stream Speed = $b - $s = $us km/h"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val ds = Random.nextInt(18, 30)
                val us = Random.nextInt(8, 16)
                val boatSpeed = (ds + us) / 2
                GeneratedQuestion(
                    id = id,
                    questionText = "Downstream speed = $ds km/h, Upstream speed = $us km/h. Find Boat speed in still water:",
                    formattedExpression = "Boat Speed = ?",
                    correctAnswer = "$boatSpeed",
                    numericAnswer = boatSpeed.toDouble(),
                    explanation = "Boat Speed = (Downstream + Upstream) / 2 = ($ds + $us) / 2 = $boatSpeed km/h"
                )
            }
        }
    }
}



class AgesGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val r1 = 3
                val r2 = 4
                val mult = Random.nextInt(4, 12)
                val ageA = r1 * mult
                val sumAges = (r1 + r2) * mult
                GeneratedQuestion(
                    id = id,
                    questionText = "Ratio of ages of A and B is $r1:$r2. Sum of ages is $sumAges. Find A's age:",
                    formattedExpression = "A's age = ?",
                    correctAnswer = "$ageA",
                    numericAnswer = ageA.toDouble(),
                    explanation = "A's age = ($r1 / ${r1 + r2}) × $sumAges = $ageA years"
                )
            }
            Difficulty.MEDIUM -> {
                val presentA = Random.nextInt(20, 45)
                val years = Random.nextInt(3, 10)
                val futureA = presentA + years
                GeneratedQuestion(
                    id = id,
                    questionText = "A is currently $presentA years old. What will be A's age after $years years?",
                    formattedExpression = "Future age = ?",
                    correctAnswer = "$futureA",
                    numericAnswer = futureA.toDouble(),
                    explanation = "Future age = $presentA + $years = $futureA years"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val ansYears = 5
                GeneratedQuestion(
                    id = id,
                    questionText = "Father is 40 yrs, Son is 10 yrs. In how many years will father be 3 times as old as son?",
                    formattedExpression = "Years = ?",
                    correctAnswer = "$ansYears",
                    numericAnswer = ansYears.toDouble(),
                    explanation = "40 + x = 3(10 + x) => 40 + x = 30 + 3x => 2x = 10 => x = $ansYears years"
                )
            }
        }
    }
}

class LinearEquationsGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val a = Random.nextInt(2, 8)
                val x = Random.nextInt(3, 15)
                val b = Random.nextInt(5, 25)
                val c = a * x + b
                GeneratedQuestion(
                    id = id,
                    questionText = "Solve for x:",
                    formattedExpression = "$a x + $b = $c",
                    correctAnswer = "$x",
                    numericAnswer = x.toDouble(),
                    explanation = "$a x = $c - $b = ${c - b} => x = ${c - b} / $a = $x"
                )
            }
            Difficulty.MEDIUM -> {
                val a = 5
                val c = 2
                val x = Random.nextInt(4, 12)
                val b = 3
                val rhs = a * x - b
                val d = rhs - c * x
                GeneratedQuestion(
                    id = id,
                    questionText = "Solve for x:",
                    formattedExpression = "$a x - $b = $c x + $d",
                    correctAnswer = "$x",
                    numericAnswer = x.toDouble(),
                    explanation = "(${a} - ${c})x = $d + $b => ${a-c}x = ${d+b} => x = $x"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val x = Random.nextInt(10, 35)
                val y = Random.nextInt(2, x - 1)
                val sum = x + y
                val diff = x - y
                GeneratedQuestion(
                    id = id,
                    questionText = "If x + y = $sum and x - y = $diff, find x:",
                    formattedExpression = "x = ?",
                    correctAnswer = "$x",
                    numericAnswer = x.toDouble(),
                    explanation = "2x = (x + y) + (x - y) = $sum + $diff = ${sum + diff} => x = $x"
                )
            }
        }
    }
}



class MensurationGenerator : IQuestionGenerator {
    override fun generateQuestion(id: Int, difficulty: Difficulty): GeneratedQuestion {
        return when (difficulty) {
            Difficulty.EASY -> {
                val l = Random.nextInt(5, 20)
                val w = Random.nextInt(4, 15)
                val area = l * w
                GeneratedQuestion(
                    id = id,
                    questionText = "Rectangle Length = $l, Width = $w. Find Area:",
                    formattedExpression = "Area = ?",
                    correctAnswer = "$area",
                    numericAnswer = area.toDouble(),
                    explanation = "Area = Length × Width = $l × $w = $area"
                )
            }
            Difficulty.MEDIUM -> {
                val r = 7 * Random.nextInt(1, 4)
                val area = (22 * r * r) / 7
                GeneratedQuestion(
                    id = id,
                    questionText = "Circle radius = $r. Find Area (take π = 22/7):",
                    formattedExpression = "Area = ?",
                    correctAnswer = "$area",
                    numericAnswer = area.toDouble(),
                    explanation = "Area = (22/7) × $r × $r = $area"
                )
            }
            Difficulty.HARD, Difficulty.MIXED -> {
                val triplets = listOf(
                    Triple(3, 4, 5),
                    Triple(5, 12, 13),
                    Triple(8, 15, 17),
                    Triple(7, 24, 25)
                )
                val mult = Random.nextInt(1, 3)
                val t = triplets.random()
                val a = t.first * mult
                val b = t.second * mult
                val c = t.third * mult
                GeneratedQuestion(
                    id = id,
                    questionText = "Right triangle legs are $a and $b. Find Hypotenuse:",
                    formattedExpression = "Hypotenuse = ?",
                    correctAnswer = "$c",
                    numericAnswer = c.toDouble(),
                    explanation = "c = √($a² + $b²) = √(${a*a} + ${b*b}) = $c"
                )
            }
        }
    }
}


