package com.example.domain.repository

import com.example.domain.model.ReferenceTableEntry
import com.example.domain.model.RevisionCategory
import com.example.domain.model.RevisionExample
import com.example.domain.model.RevisionItem
import com.example.domain.model.RevisionTopicTag

object RevisionCatalog {

    val REVISION_ITEMS = listOf(
        // 1. SQUARES & CUBES
        RevisionItem(
            id = "squares_1_30",
            title = "Squares Reference Table (1 to 30)",
            category = RevisionCategory.SQUARES_TRIPLETS,
            topicTag = RevisionTopicTag.SQUARES_CUBES,
            summary = "Complete squares table from 1² to 30² with fast computation mental trick.",
            formulaOrRule = "N² = (N - d)(N + d) + d²   |   (a + b)² = a² + 2ab + b²",
            explanation = "Memorizing squares from 1 to 30 is essential for competitive math and speed calculations. For any 2-digit number (10a + b)², calculate: a² | 2ab | b² with carry overs.",
            tableHeaders = listOf("N", "N²", "N", "N²"),
            tableData = listOf(
                ReferenceTableEntry("1", "1", "16", "256"),
                ReferenceTableEntry("2", "4", "17", "289"),
                ReferenceTableEntry("3", "9", "18", "324"),
                ReferenceTableEntry("4", "16", "19", "361"),
                ReferenceTableEntry("5", "25", "20", "400"),
                ReferenceTableEntry("6", "36", "21", "441"),
                ReferenceTableEntry("7", "49", "22", "484"),
                ReferenceTableEntry("8", "64", "23", "529"),
                ReferenceTableEntry("9", "81", "24", "576"),
                ReferenceTableEntry("10", "100", "25", "625"),
                ReferenceTableEntry("11", "121", "26", "676"),
                ReferenceTableEntry("12", "144", "27", "729"),
                ReferenceTableEntry("13", "169", "28", "784"),
                ReferenceTableEntry("14", "196", "29", "841"),
                ReferenceTableEntry("15", "225", "30", "900")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Calculate 43² mentally using (a + b)²",
                    stepByStep = listOf(
                        "Let a = 4, b = 3",
                        "b² = 3² = 9 (Right digit)",
                        "2ab = 2 × 4 × 3 = 24 (Middle digit: keep 4, carry 2)",
                        "a² = 4² = 16 + carry 2 = 18",
                        "Combine: 1849"
                    ),
                    answer = "1,849"
                )
            ),
            tips = listOf(
                "Square of numbers ending in 5 (N5)² = N(N+1) followed by 25! (e.g. 65² = 6×7 | 25 = 4225)",
                "Square of numbers near 50: (50 ± x)² = (25 ± x) | x² (e.g., 53² = (25+3)|3² = 2809)"
            ),
            relatedTopicId = "squares_cubes"
        ),

        RevisionItem(
            id = "cubes_1_20",
            title = "Cubes Reference Table (1 to 20)",
            category = RevisionCategory.SQUARES_TRIPLETS,
            topicTag = RevisionTopicTag.SQUARES_CUBES,
            summary = "Complete cubes table from 1³ to 20³ for fast simplification & algebraic evaluation.",
            formulaOrRule = "(a + b)³ = a³ + 3a²b + 3ab² + b³",
            explanation = "Cubes grow rapidly. Memorizing cubes up to 20 allows instant cube root extractions and compound interest solutions.",
            tableHeaders = listOf("N", "N³", "N", "N³"),
            tableData = listOf(
                ReferenceTableEntry("1", "1", "11", "1,331"),
                ReferenceTableEntry("2", "8", "12", "1,728"),
                ReferenceTableEntry("3", "27", "13", "2,197"),
                ReferenceTableEntry("4", "64", "14", "2,744"),
                ReferenceTableEntry("5", "125", "15", "3,375"),
                ReferenceTableEntry("6", "216", "16", "4,096"),
                ReferenceTableEntry("7", "343", "17", "4,913"),
                ReferenceTableEntry("8", "512", "18", "5,832"),
                ReferenceTableEntry("9", "729", "19", "6,859"),
                ReferenceTableEntry("10", "1,000", "20", "8,000")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Find the cube root of 12,167 mentally",
                    stepByStep = listOf(
                        "Group last 3 digits: 167 (Ends in 7 → Cube root ends in 3 because 3³ = 27)",
                        "Remaining number is 12: Falls between 2³ (8) and 3³ (27)",
                        "Take the lower root: 2",
                        "Combine: 23"
                    ),
                    answer = "23"
                )
            ),
            tips = listOf(
                "Notice ending digits: 1→1, 4→4, 5→5, 6→6, 9→9, 0→0. The swapped pairs are (2 ↔ 8) and (3 ↔ 7)!"
            ),
            relatedTopicId = "squares_cubes"
        ),

        // 2. PYTHAGOREAN TRIPLETS
        RevisionItem(
            id = "pythagorean_triplets",
            title = "Pythagorean Triplets & Generation Rules",
            category = RevisionCategory.SQUARES_TRIPLETS,
            topicTag = RevisionTopicTag.TRIPLETS,
            summary = "Essential right-angled triangle side combinations for geometry, trigonometry & mensuration.",
            formulaOrRule = "a² + b² = c²   |   Odd n: (n, (n²-1)/2, (n²+1)/2)   |   Even n: (n, (n/2)²-1, (n/2)²+1)",
            explanation = "Pythagorean triplets represent integer side lengths of a right triangle. Any scaled multiple (ka, kb, kc) is also a Pythagorean triplet!",
            tableHeaders = listOf("Base Triplet", "Scaled Multiples (×2, ×3)", "Hypotenuse (c)"),
            tableData = listOf(
                ReferenceTableEntry("(3, 4, 5)", "(6, 8, 10), (9, 12, 15)", "5"),
                ReferenceTableEntry("(5, 12, 13)", "(10, 24, 26), (15, 36, 39)", "13"),
                ReferenceTableEntry("(7, 24, 25)", "(14, 48, 50), (21, 72, 75)", "25"),
                ReferenceTableEntry("(8, 15, 17)", "(16, 30, 34), (24, 45, 51)", "17"),
                ReferenceTableEntry("(9, 40, 41)", "(18, 80, 82)", "41"),
                ReferenceTableEntry("(11, 60, 61)", "(22, 120, 122)", "61"),
                ReferenceTableEntry("(12, 35, 37)", "(24, 70, 74)", "37"),
                ReferenceTableEntry("(20, 21, 29)", "(40, 42, 58)", "29")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Generate a triplet starting with odd number 9",
                    stepByStep = listOf(
                        "a = 9",
                        "b = (9² - 1) / 2 = (81 - 1) / 2 = 40",
                        "c = (9² + 1) / 2 = (81 + 1) / 2 = 41",
                        "Check: 9² + 40² = 81 + 1600 = 1681 = 41²"
                    ),
                    answer = "(9, 40, 41)"
                )
            ),
            tips = listOf(
                "Remember (3,4,5), (5,12,13), (7,24,25), (8,15,17), and (20,21,29). They cover 90%+ of exam questions!"
            ),
            relatedTopicId = "squares_cubes"
        ),

        // 3. MULTIPLICATION TABLES
        RevisionItem(
            id = "multiplication_tables_1_30",
            title = "Multiplication Tables Master Reference (11 to 25)",
            category = RevisionCategory.TABLES_LISTS,
            topicTag = RevisionTopicTag.TABLES,
            summary = "Quick recall table for tough multiplication tables 12, 13, 14, 17, 19, 23, 29.",
            formulaOrRule = "17 × n = (10 × n) + (7 × n)   |   19 × n = (20 × n) - n",
            explanation = "Mastering tables up to 30 dramatically speeds up division, ratios, and simplification.",
            tableHeaders = listOf("N", "× 6", "× 7", "× 8", "× 9"),
            tableData = listOf(
                ReferenceTableEntry("12", "72", "84", "96", "108"),
                ReferenceTableEntry("13", "78", "91", "104", "117"),
                ReferenceTableEntry("14", "84", "98", "112", "126"),
                ReferenceTableEntry("15", "90", "105", "120", "135"),
                ReferenceTableEntry("16", "96", "112", "128", "144"),
                ReferenceTableEntry("17", "102", "119", "136", "153"),
                ReferenceTableEntry("18", "108", "126", "144", "162"),
                ReferenceTableEntry("19", "114", "133", "152", "171"),
                ReferenceTableEntry("23", "138", "161", "184", "207"),
                ReferenceTableEntry("29", "174", "203", "232", "261")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Calculate 29 × 7 mentally",
                    stepByStep = listOf(
                        "Rewrite 29 as (30 - 1)",
                        "Multiply 30 × 7 = 210",
                        "Subtract 1 × 7 = 7",
                        "210 - 7 = 203"
                    ),
                    answer = "203"
                )
            ),
            tips = listOf(
                "Table of 19: Units digit decreases by 1 (9,8,7,6,5,4,3,2,1,0), Tens digits are consecutive odd numbers (1,3,5,7,9,11,13,15,17,19)!"
            ),
            relatedTopicId = "multiplication"
        ),

        // 4. FRACTIONS & DECIMALS
        RevisionItem(
            id = "fractions_percentages_table",
            title = "Fraction to Percentage Conversion Chart",
            category = RevisionCategory.TABLES_LISTS,
            topicTag = RevisionTopicTag.FRACTIONS,
            summary = "Crucial fraction-to-percentage equivalences for rapid arithmetic & data interpretation.",
            formulaOrRule = "Fraction × 100 = Percentage %",
            explanation = "Converting complex percentages into fractions turns heavy multiplication into simple division.",
            tableHeaders = listOf("Fraction", "Percentage", "Fraction", "Percentage"),
            tableData = listOf(
                ReferenceTableEntry("1/2", "50%", "1/9", "11.11% (11 1/9%)"),
                ReferenceTableEntry("1/3", "33.33% (33 1/3%)", "1/10", "10%"),
                ReferenceTableEntry("1/4", "25%", "1/11", "9.09% (9 1/11%)"),
                ReferenceTableEntry("1/5", "20%", "1/12", "8.33% (8 1/3%)"),
                ReferenceTableEntry("1/6", "16.67% (16 2/3%)", "1/15", "6.67% (6 2/3%)"),
                ReferenceTableEntry("1/7", "14.28% (14 2/7%)", "1/16", "6.25% (6 1/4%)"),
                ReferenceTableEntry("1/8", "12.5% (12 1/2%)", "1/20", "5%")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Calculate 37.5% of 640 mentally",
                    stepByStep = listOf(
                        "Recognize 37.5% = 3 × 12.5% = 3/8",
                        "Calculate (3/8) × 640",
                        "640 ÷ 8 = 80",
                        "80 × 3 = 240"
                    ),
                    answer = "240"
                )
            ),
            tips = listOf(
                "Multiples of 1/8: 1/8 = 12.5%, 3/8 = 37.5%, 5/8 = 62.5%, 7/8 = 87.5%!",
                "Multiples of 1/7: 1/7 = 14.28%, 2/7 = 28.56%, 3/7 = 42.85%, 4/7 = 57.14%!"
            ),
            relatedTopicId = "fractions"
        ),

        // 5. MENSURATION (2D & 3D)
        RevisionItem(
            id = "mensuration_2d_formulas",
            title = "2D Mensuration Formulas (Area & Perimeter)",
            category = RevisionCategory.MENSURATION_GEOMETRY,
            topicTag = RevisionTopicTag.MENSURATION,
            summary = "Area, perimeter, and diagonal formulas for triangles, quadrilaterals, and circles.",
            formulaOrRule = "Triangle: Area = ½bh = √(s(s-a)(s-b)(s-c))   |   Circle: Area = πr², C = 2πr",
            explanation = "2D mensuration deals with plane figures. Semi-perimeter s = (a + b + c) / 2 for scalene triangle Heron's formula.",
            tableHeaders = listOf("Shape", "Area", "Perimeter / Circumference", "Diagonal / Height"),
            tableData = listOf(
                ReferenceTableEntry("Equilateral Triangle", "√3/4 × a²", "3a", "h = √3/2 × a"),
                ReferenceTableEntry("Right Triangle", "½ × base × height", "a + b + c", "Hypotenuse = √(b² + h²)"),
                ReferenceTableEntry("Rectangle", "length × width", "2(l + w)", "d = √(l² + w²)"),
                ReferenceTableEntry("Square", "a² or ½ d²", "4a", "d = a√2"),
                ReferenceTableEntry("Circle", "πr²", "2πr", "Diameter = 2r"),
                ReferenceTableEntry("Rhombus", "½ × d1 × d2", "4a", "a = ½ √(d1² + d2²)"),
                ReferenceTableEntry("Trapezium", "½ × (a + b) × h", "Sum of 4 sides", "-")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Find area of equilateral triangle with side 6 cm",
                    stepByStep = listOf(
                        "Formula: Area = (√3 / 4) × a²",
                        "a = 6 → a² = 36",
                        "Area = (√3 / 4) × 36 = 9√3 cm²"
                    ),
                    answer = "9√3 cm² (≈ 15.58 cm²)"
                )
            ),
            tips = listOf(
                "If side of square or radius of circle increases by x%, Area increases by (2x + x²/100)%!"
            ),
            relatedTopicId = "geometry"
        ),

        RevisionItem(
            id = "mensuration_3d_formulas",
            title = "3D Mensuration Formulas (Volume & Surface Area)",
            category = RevisionCategory.MENSURATION_GEOMETRY,
            topicTag = RevisionTopicTag.MENSURATION,
            summary = "Volume, curved surface area (CSA), and total surface area (TSA) formulas.",
            formulaOrRule = "Cylinder V = πr²h   |   Cone V = ⅓πr²h   |   Sphere V = ⁴/₃πr³",
            explanation = "3D figures occupy space. Slant height of cone l = √(r² + h²).",
            tableHeaders = listOf("3D Solid", "Volume", "Curved Surface Area (CSA)", "Total Surface Area (TSA)"),
            tableData = listOf(
                ReferenceTableEntry("Cube", "a³", "4a² (Lateral)", "6a²"),
                ReferenceTableEntry("Cuboid", "length × width × height", "2h(l + w)", "2(lw + wh + hl)"),
                ReferenceTableEntry("Cylinder", "πr²h", "2πrh", "2πr(r + h)"),
                ReferenceTableEntry("Cone", "⅓ πr²h", "πrl (l = √(r²+h²))", "πr(r + l)"),
                ReferenceTableEntry("Sphere", "⁴/₃ πr³", "4πr²", "4πr²"),
                ReferenceTableEntry("Hemisphere", "⅔ πr³", "2πr²", "3πr²")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Calculate total surface area of hemisphere of radius 7 cm (Use π = 22/7)",
                    stepByStep = listOf(
                        "TSA of Hemisphere = 3πr²",
                        "TSA = 3 × (22/7) × 7 × 7",
                        "3 × 22 × 7 = 66 × 7 = 462 cm²"
                    ),
                    answer = "462 cm²"
                )
            ),
            tips = listOf(
                "Ratio of volumes of Cylinder : Cone : Sphere of same radius and height = 3 : 1 : 2!"
            ),
            relatedTopicId = "geometry"
        ),

        // 6. TRIGONOMETRY
        RevisionItem(
            id = "trigonometry_values_identities",
            title = "Trigonometry Standard Values & Core Identities",
            category = RevisionCategory.TRIGONOMETRY,
            topicTag = RevisionTopicTag.TRIGONOMETRY,
            summary = "Complete angle table (0°, 30°, 45°, 60°, 90°) & Pythagorean trigonometric identities.",
            formulaOrRule = "sin²θ + cos²θ = 1   |   1 + tan²θ = sec²θ   |   1 + cot²θ = cosec²θ",
            explanation = "Trigonometry relates triangle angles to side ratios. tan θ = sin θ / cos θ and cot θ = cos θ / sin θ.",
            tableHeaders = listOf("θ", "0°", "30°", "45°", "60°", "90°"),
            tableData = listOf(
                ReferenceTableEntry("sin θ", "0", "1/2", "1/√2", "√3/2", "1"),
                ReferenceTableEntry("cos θ", "1", "√3/2", "1/√2", "1/2", "0"),
                ReferenceTableEntry("tan θ", "0", "1/√3", "1", "√3", "Undefined"),
                ReferenceTableEntry("cot θ", "Undefined", "√3", "1", "1/√3", "0"),
                ReferenceTableEntry("sec θ", "1", "2/√3", "√2", "2", "Undefined"),
                ReferenceTableEntry("cosec θ", "Undefined", "2", "√2", "2/√3", "1")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Evaluate: sin² 30° + cos² 30° - tan 45°",
                    stepByStep = listOf(
                        "sin 30° = 1/2 → (1/2)² = 1/4",
                        "cos 30° = √3/2 → (√3/2)² = 3/4",
                        "tan 45° = 1",
                        "1/4 + 3/4 - 1 = 1 - 1 = 0"
                    ),
                    answer = "0"
                )
            ),
            tips = listOf(
                "Complementary Angles: sin(90° - θ) = cos θ, tan(90° - θ) = cot θ, sec(90° - θ) = cosec θ!",
                "Height & Distance trick: For 30°-60°-90° triangle, sides are in ratio 1 : √3 : 2!"
            ),
            relatedTopicId = "algebra"
        ),

        // 7. GEOMETRY
        RevisionItem(
            id = "geometry_theorems_rules",
            title = "Essential Geometry Theorems & Angle Rules",
            category = RevisionCategory.MENSURATION_GEOMETRY,
            topicTag = RevisionTopicTag.GEOMETRY,
            summary = "Triangle angle sum, exterior angles, parallel lines & circle theorems.",
            formulaOrRule = "Sum of interior angles of polygon = (n - 2) × 180°   |   Exterior angle = sum of opposite interior angles",
            explanation = "Geometry rules allow solving angle and length problems without lengthy calculations.",
            tableHeaders = listOf("Property / Theorem", "Rule / Formula", "Key Application"),
            tableData = listOf(
                ReferenceTableEntry("Triangle Angle Sum", "∠A + ∠B + ∠C = 180°", "Finding missing triangle angle"),
                ReferenceTableEntry("Polygon Interior Sum", "(n - 2) × 180°", "Hexagon (6 sides) = 720°"),
                ReferenceTableEntry("Polygon Exterior Sum", "Always 360° for any convex polygon", "Each exterior angle of regular polygon = 360°/n"),
                ReferenceTableEntry("Circle Center Angle", "Angle at center = 2 × Angle at circumference", "Inscribed angle theorem"),
                ReferenceTableEntry("Semicircle Angle", "Angle inscribed in semicircle = 90°", "Right triangle inside circle"),
                ReferenceTableEntry("Cyclic Quadrilateral", "Opposite angles sum to 180°", "∠A + ∠C = 180°, ∠B + ∠D = 180°")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Find each interior angle of a regular octagon (8 sides)",
                    stepByStep = listOf(
                        "Each exterior angle = 360° / 8 = 45°",
                        "Interior angle = 180° - 45° = 135°",
                        "Or formula: (8 - 2) × 180° / 8 = 1080° / 8 = 135°"
                    ),
                    answer = "135°"
                )
            ),
            tips = listOf(
                "Number of diagonals in a polygon of n sides = n(n - 3) / 2!"
            ),
            relatedTopicId = "geometry"
        ),

        // 8. ARITHMETIC (Profit, Interest)
        RevisionItem(
            id = "arithmetic_profit_interest",
            title = "Commercial Arithmetic: Profit, Discount, SI & CI",
            category = RevisionCategory.ARITHMETIC,
            topicTag = RevisionTopicTag.ARITHMETIC,
            summary = "Core formulas for Profit & Loss, Marked Price Discount, Simple Interest & Compound Interest.",
            formulaOrRule = "SI = (P × R × T) / 100   |   CI = P(1 + R/100)ᵀ - P   |   Effective 2-Yr CI Rate = 2R + R²/100",
            explanation = "Commercial math evaluates money transactions. Always calculate Profit% on Cost Price (CP) unless stated on Selling Price (SP). Discount is always given on Marked Price (MP).",
            tableHeaders = listOf("Concept", "Key Formula", "Notes / Shortcut"),
            tableData = listOf(
                ReferenceTableEntry("Profit %", "(Profit / CP) × 100", "Profit = SP - CP"),
                ReferenceTableEntry("Loss %", "(Loss / CP) × 100", "Loss = CP - SP"),
                ReferenceTableEntry("Discount %", "(Discount / MP) × 100", "Discount = MP - SP"),
                ReferenceTableEntry("Simple Interest", "SI = (P × R × T) / 100", "Interest remains CONSTANT each year"),
                ReferenceTableEntry("Compound Interest", "A = P(1 + R/100)ᵀ", "Interest compounds on updated principal"),
                ReferenceTableEntry("CI - SI Difference (2 Yrs)", "Diff = P × (R / 100)²", "Fast difference formula for 2 years")
            ),
            examples = listOf(
                RevisionExample(
                    problem = "Find CI on $10,000 at 10% per annum for 2 years",
                    stepByStep = listOf(
                        "Effective 2-year CI rate = 10 + 10 + (10 × 10)/100 = 21%",
                        "CI = 21% of 10,000",
                        "CI = 0.21 × 10,000 = $2,100"
                    ),
                    answer = "$2,100"
                )
            ),
            tips = listOf(
                "If CP of x items = SP of y items, Profit/Loss % = [(x - y) / y] × 100!"
            ),
            relatedTopicId = "percentage"
        ),

        // 9. RATIO & AVERAGE
        RevisionItem(
            id = "ratio_proportion_averages",
            title = "Ratio, Proportion & Weighted Averages",
            category = RevisionCategory.ARITHMETIC,
            topicTag = RevisionTopicTag.RATIO_AVERAGE,
            summary = "Properties of proportions, mean calculation, and weighted average mixture rules.",
            formulaOrRule = "Average = Sum / Count   |   Weighted Avg = (n1·A1 + n2·A2) / (n1 + n2)",
            explanation = "Ratio compares two quantities. In proportion a:b :: c:d, Product of Extremes (ad) = Product of Means (bc).",
            examples = listOf(
                RevisionExample(
                    problem = "Group A (20 people) avg = 80, Group B (30 people) avg = 90. Find combined average.",
                    stepByStep = listOf(
                        "Weighted Avg = (20 × 80 + 30 × 90) / (20 + 30)",
                        "Numerator = 1600 + 2700 = 4300",
                        "Denominator = 50",
                        "4300 ÷ 50 = 86"
                    ),
                    answer = "86"
                )
            ),
            tips = listOf(
                "In Alligation / Mixture: Ratio of quantities = (Higher Avg - Mixed Avg) : (Mixed Avg - Lower Avg)!"
            ),
            relatedTopicId = "averages"
        ),

        // 10. SPEED, TIME & WORK
        RevisionItem(
            id = "speed_distance_work",
            title = "Speed, Distance, Time & Time & Work",
            category = RevisionCategory.SPEED_WORK,
            topicTag = RevisionTopicTag.SPEED_WORK,
            summary = "Relative speed, unit conversions, combined work rates, and pipes & cisterns.",
            formulaOrRule = "Combined Work Time = (A × B) / (A + B)   |   1 km/h = 5/18 m/s",
            explanation = "If A does work in x days, A's 1-day rate = 1/x. Relative speed: opposite direction = S1 + S2, same direction = |S1 - S2|.",
            examples = listOf(
                RevisionExample(
                    problem = "Pipe A fills tank in 10 hrs, Pipe B empties in 15 hrs. Time to fill together?",
                    stepByStep = listOf(
                        "Net 1-hr rate = (1/10) - (1/15)",
                        "Common denominator 30: (3 - 2) / 30 = 1/30",
                        "Tank fills in 30 hours"
                    ),
                    answer = "30 hours"
                )
            ),
            tips = listOf(
                "Average Speed for equal distance d at speeds S1 and S2 = (2 × S1 × S2) / (S1 + S2)!"
            ),
            relatedTopicId = "division"
        ),

        // 11. ALGEBRA
        RevisionItem(
            id = "algebra_equations_identities",
            title = "Algebraic Identities & Quadratic Equations",
            category = RevisionCategory.FORMULAS,
            topicTag = RevisionTopicTag.ALGEBRA,
            summary = "Polynomial expansions, quadratic formula, and sum/product of roots.",
            formulaOrRule = "x = (-b ± √(b² - 4ac)) / (2a)   |   Sum of roots = -b/a   |   Product = c/a",
            explanation = "Algebraic identities simplify expressions and allow solving unknown variable equations.",
            examples = listOf(
                RevisionExample(
                    problem = "Find sum and product of roots for 2x² - 8x + 6 = 0",
                    stepByStep = listOf(
                        "a = 2, b = -8, c = 6",
                        "Sum of roots = -(-8) / 2 = 8 / 2 = 4",
                        "Product of roots = 6 / 2 = 3"
                    ),
                    answer = "Sum = 4, Product = 3"
                )
            ),
            tips = listOf(
                "If x + 1/x = k, then x² + 1/x² = k² - 2 and x³ + 1/x³ = k³ - 3k!"
            ),
            relatedTopicId = "algebra"
        ),

        // 12. ADDITION & SUBTRACTION
        RevisionItem(
            id = "addition_subtraction_shortcuts",
            title = "Mental Addition & Subtraction Shortcuts",
            category = RevisionCategory.CALCULATION_TRICKS,
            topicTag = RevisionTopicTag.ADDITION_SUBTRACTION,
            summary = "Left-to-right mental addition, compensation method, and subtraction complement rule.",
            formulaOrRule = "All from 9 and Last from 10 (Subtractions from 100, 1000, 10000)",
            explanation = "Mental math is much faster from left to right (highest place value to lowest).",
            examples = listOf(
                RevisionExample(
                    problem = "Add 68 + 57 mentally using compensation",
                    stepByStep = listOf(
                        "Round 68 to 70 (+2)",
                        "Add 70 + 57 = 127",
                        "Compensate by subtracting 2: 127 - 2 = 125"
                    ),
                    answer = "125"
                )
            ),
            tips = listOf(
                "To subtract from 1000: subtract first digits from 9, and the last digit from 10 (e.g., 1000 - 437 → 9-4=5, 9-3=6, 10-7=3 → 563)!"
            ),
            relatedTopicId = "addition"
        ),

        // 13. MULTIPLICATION & DIVISION
        RevisionItem(
            id = "multiplication_division_tricks",
            title = "Fast Multiplication & Division Shortcuts",
            category = RevisionCategory.CALCULATION_TRICKS,
            topicTag = RevisionTopicTag.MULTIPLICATION_DIVISION,
            summary = "Criss-cross 2-digit multiplication, doubling/halving, and division by 5, 25, 50.",
            formulaOrRule = "ab × cd = (a×c) | (a×d + b×c) | (b×d)   |   N ÷ 25 = (N × 4) / 100",
            explanation = "Vedic criss-cross (Urdhva Tiryagbhyam) multiplies any two 2-digit numbers in a single line.",
            examples = listOf(
                RevisionExample(
                    problem = "Multiply 32 × 24 using Criss-Cross method",
                    stepByStep = listOf(
                        "Step 1 (Vertical Right): 2 × 4 = 8",
                        "Step 2 (Cross): (3×4) + (2×2) = 12 + 4 = 16 (Write 6, carry 1)",
                        "Step 3 (Vertical Left): 3 × 2 = 6 + carry 1 = 7",
                        "Combine: 768"
                    ),
                    answer = "768"
                )
            ),
            tips = listOf(
                "To divide by 5: Double the number and shift decimal left 1 place! (e.g. 142 ÷ 5 → 284 → 28.4)",
                "To divide by 25: Multiply by 4 and shift decimal left 2 places!"
            ),
            relatedTopicId = "multiplication"
        ),

        // 14. PERCENTAGE
        RevisionItem(
            id = "percentage_tricks",
            title = "Percentage Calculation Shortcuts & A% of B = B% of A",
            category = RevisionCategory.CALCULATION_TRICKS,
            topicTag = RevisionTopicTag.PERCENTAGE,
            summary = "Reversibility rule of percentages and breaking percentages into 10%, 5%, 1%.",
            formulaOrRule = "A% of B = B% of A   |   Net Change = x + y + (xy/100)",
            explanation = "Switching percentages often turns a difficult calculation into an instant mental result.",
            examples = listOf(
                RevisionExample(
                    problem = "Calculate 84% of 50 mentally",
                    stepByStep = listOf(
                        "Apply rule: 84% of 50 = 50% of 84",
                        "50% means half of 84",
                        "84 ÷ 2 = 42"
                    ),
                    answer = "42"
                )
            ),
            tips = listOf(
                "If price increases by 25%, consumption must decrease by 20% to keep expenditure constant!"
            ),
            relatedTopicId = "percentage"
        ),

        // 15. POWERS & ROOTS
        RevisionItem(
            id = "powers_roots_surds",
            title = "Powers, Exponents, Surds & Square Root Trick",
            category = RevisionCategory.FORMULAS,
            topicTag = RevisionTopicTag.POWERS_ROOTS,
            summary = "Laws of indices, rationalization, and non-perfect square root approximation formula.",
            formulaOrRule = "xᵃ × xᵇ = xᵃ⁺ᵇ   |   √(A ± B) ≈ √A ± (B / (2√A))",
            explanation = "Exponents govern growth and root extraction. Non-perfect square roots can be approximated with high accuracy.",
            examples = listOf(
                RevisionExample(
                    problem = "Approximate √27 using formula √(A + B) ≈ √A + (B / 2√A)",
                    stepByStep = listOf(
                        "Split 27 into nearest square 25 + 2",
                        "A = 25 (√A = 5), B = 2",
                        "√27 ≈ 5 + (2 / (2 × 5)) = 5 + (2 / 10) = 5.2"
                    ),
                    answer = "≈ 5.2 (Exact = 5.196)"
                )
            ),
            tips = listOf(
                "Rationalize denominator: 1 / (√a - √b) = (√a + √b) / (a - b)!"
            ),
            relatedTopicId = "squares_cubes"
        ),

        // 16. SIMPLIFICATION & BODMAS
        RevisionItem(
            id = "simplification_bodmas",
            title = "Simplification Rules & BODMAS Priority",
            category = RevisionCategory.FORMULAS,
            topicTag = RevisionTopicTag.SIMPLIFICATION,
            summary = "Standard order of evaluation for complex algebraic and arithmetic expressions.",
            formulaOrRule = "B (Brackets: (), {}, []) → O (Orders/Of) → D/M (Left to Right) → A/S (Left to Right)",
            explanation = "Always evaluate 'Of' before division or multiplication. 'Of' implies multiplication with higher precedence than standard ÷/×.",
            examples = listOf(
                RevisionExample(
                    problem = "Evaluate: 40 ÷ 5 of 2 + (18 - 6) ÷ 4",
                    stepByStep = listOf(
                        "Brackets first: (18 - 6) = 12",
                        "'Of' before division: 5 of 2 = 5 × 2 = 10",
                        "Expression: 40 ÷ 10 + 12 ÷ 4",
                        "Division: 40 ÷ 10 = 4, and 12 ÷ 4 = 3",
                        "Addition: 4 + 3 = 7"
                    ),
                    answer = "7"
                )
            ),
            tips = listOf(
                "Nested brackets order: Round () → Curly {} → Square []!"
            ),
            relatedTopicId = "addition"
        ),

        // 17. EXAM TRICKS & DIGITAL ROOT
        RevisionItem(
            id = "exam_tricks_digital_root",
            title = "Digit Sum (Digital Root) Method for Multiple Choice Exams",
            category = RevisionCategory.CALCULATION_TRICKS,
            topicTag = RevisionTopicTag.EXAM_TRICKS,
            summary = "Verify large calculation options in multiple-choice exams in seconds without full computation.",
            formulaOrRule = "Digit Sum = Sum of digits reduced to single digit (Cast out 9s: 9 = 0)",
            explanation = "The digit sum of the left-hand side of any valid equation MUST equal the digit sum of the right-hand side. Treat all 9s or combinations summing to 9 as 0.",
            examples = listOf(
                RevisionExample(
                    problem = "Which option is correct for 4321 × 123? Options: A) 531,483  B) 531,473",
                    stepByStep = listOf(
                        "LHS Digit Sum: 4321 → 4+3+2+1 = 10 → 1. 123 → 1+2+3 = 6.",
                        "Product LHS = 1 × 6 = 6",
                        "Check Option A (531,483): 5+3+1+4+8+3 = 24 → 2+4 = 6 (MATCH!)",
                        "Check Option B (531,473): 5+3+1+4+7+3 = 23 → 2+3 = 5 (NO MATCH)"
                    ),
                    answer = "Option A (531,483)"
                )
            ),
            tips = listOf(
                "Digital root technique works for Addition, Subtraction, Multiplication, and Squares!",
                "Combine Digital Root with Last Digit (Units Digit) verification to guarantee 100% accurate option selection!"
            ),
            relatedTopicId = "multiplication"
        )
    )
}
