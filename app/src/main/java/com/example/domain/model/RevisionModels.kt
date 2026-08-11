package com.example.domain.model

enum class RevisionCategory(val id: String, val displayName: String, val iconName: String) {
    ALL("all", "All Topics", "MenuBook"),
    TABLES_LISTS("tables_lists", "Tables & Memory", "GridOn"),
    SQUARES_TRIPLETS("squares_triplets", "Squares, Cubes & Triplets", "School"),
    FORMULAS("formulas", "Core Formulas", "Functions"),
    MENSURATION_GEOMETRY("mensuration_geometry", "Geometry & Mensuration", "Category"),
    TRIGONOMETRY("trigonometry", "Trigonometry", "ChangeHistory"),
    ARITHMETIC("arithmetic", "Arithmetic & Word Problems", "Percent"),
    SPEED_WORK("speed_work", "Speed, Time & Work", "Speed"),
    CALCULATION_TRICKS("calculation_tricks", "Exam & Mental Tricks", "Bolt")
}

enum class RevisionTopicTag(val id: String, val tagName: String) {
    ALL("all", "All"),
    SQUARES_CUBES("squares_cubes", "Squares & Cubes"),
    TABLES("tables", "Tables (1-30)"),
    TRIPLETS("triplets", "Pythagorean Triplets"),
    FRACTIONS("fractions", "Fractions & Decimals"),
    MENSURATION("mensuration", "Mensuration (2D & 3D)"),
    GEOMETRY("geometry", "Geometry"),
    TRIGONOMETRY("trigonometry", "Trigonometry"),
    ARITHMETIC("arithmetic", "Profit & Interest"),
    RATIO_AVERAGE("ratio_average", "Ratio & Average"),
    SPEED_WORK("speed_work", "Speed, Time & Work"),
    ALGEBRA("algebra", "Algebra"),
    ADDITION_SUBTRACTION("addition_subtraction", "Addition & Subtraction"),
    MULTIPLICATION_DIVISION("multiplication_division", "Multiplication & Division"),
    PERCENTAGE("percentage", "Percentages"),
    POWERS_ROOTS("powers_roots", "Powers & Roots"),
    SIMPLIFICATION("simplification", "Simplification"),
    EXAM_TRICKS("exam_tricks", "Exam Tricks")
}

data class RevisionExample(
    val problem: String,
    val stepByStep: List<String>,
    val answer: String
)

data class ReferenceTableEntry(
    val col1: String,
    val col2: String,
    val col3: String? = null,
    val col4: String? = null,
    val col5: String? = null,
    val col6: String? = null
)

data class RevisionItem(
    val id: String,
    val title: String,
    val category: RevisionCategory,
    val topicTag: RevisionTopicTag,
    val summary: String,
    val formulaOrRule: String? = null,
    val explanation: String,
    val tableData: List<ReferenceTableEntry> = emptyList(),
    val tableHeaders: List<String> = emptyList(),
    val examples: List<RevisionExample> = emptyList(),
    val tips: List<String> = emptyList(),
    val relatedTopicId: String? = null
)
