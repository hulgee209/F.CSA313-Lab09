package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScholarshipCalculatorTest {

    private final ScholarshipCalculator calc = new ScholarshipCalculator();

    // ===== Part A: happy path =====

    @Test
    void letterGrade_95_isA() {
        assertEquals("A", calc.letterGrade(95));
    }

    @Test
    void letterGrade_85_isB() {
        assertEquals("B", calc.letterGrade(85));
    }

    @Test
    void gpa_95_85_is3_5() {
        assertEquals(3.5, calc.gpa(new double[]{95, 85}), 1e-9);
    }

    @Test
    void scholarship_full_100() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
    }

    @Test
    void scholarship_partial_70() {
        assertEquals(70, calc.scholarship(3.5, 12, false));
    }

    // ===== Part B: branch 100% =====

    // --- letterGrade ---
    @Test
    void letterGrade_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
    }

    @Test
    void letterGrade_above100_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
    }

    @Test
    void letterGrade_75_isC() {
        assertEquals("C", calc.letterGrade(75));
    }

    @Test
    void letterGrade_65_isD() {
        assertEquals("D", calc.letterGrade(65));
    }

    @Test
    void letterGrade_50_isF() {
        assertEquals("F", calc.letterGrade(50));
    }

    // --- gpa ---
    @Test
    void gpa_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
    }

    @Test
    void gpa_empty_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{}));
    }

    @Test
    void gpa_allGrades_isTwo() {
        // A=4, B=3, C=2, D=1, F=0 (switch default) -> 10/5 = 2.0
        assertEquals(2.0, calc.gpa(new double[]{95, 85, 75, 65, 50}), 1e-9);
    }

    // --- scholarship ---
    @Test
    void scholarship_negativeGpa_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(-0.1, 15, false));
    }

    @Test
    void scholarship_gpaAbove4_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.1, 15, false));
    }

    @Test
    void scholarship_negativeCredits_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.5, -1, false));
    }

    @Test
    void scholarship_violation_0() {
        assertEquals(0, calc.scholarship(3.9, 16, true));
    }

    @Test
    void scholarship_highGpaFewCredits_70() {
        // gpa >= 3.8 true, credits >= 15 false -> falls to 70 rule
        assertEquals(70, calc.scholarship(3.9, 14, false));
    }

    @Test
    void scholarship_goodGpaFewCredits_50() {
        // gpa >= 3.4 true, credits >= 12 false -> falls to 50 rule
        assertEquals(50, calc.scholarship(3.5, 10, false));
    }

    @Test
    void scholarship_gpa3_2_50() {
        // gpa >= 3.4 false, gpa >= 3.0 true
        assertEquals(50, calc.scholarship(3.2, 16, false));
    }

    @Test
    void scholarship_lowGpa_0() {
        // gpa >= 3.0 false
        assertEquals(0, calc.scholarship(2.5, 16, false));
    }

    // ===== Part C: loop boundary tests for gpa() (0 / 1 / 2+ iterations) =====

    @Test
    void gpaLoop_zeroIterations_emptyArray_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[0]));
    }

    @Test
    void gpaLoop_zeroIterations_nullInput_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
    }

    @Test
    void gpaLoop_oneIteration_singleScore_is4() {
        assertEquals(4.0, calc.gpa(new double[]{95}), 1e-9);
    }

    @Test
    void gpaLoop_manyIterations_fourScores_is2_5() {
        // A(4) + B(3) + C(2) + D(1) = 10 / 4 = 2.5
        assertEquals(2.5, calc.gpa(new double[]{95, 85, 75, 65}), 1e-9);
    }

    // ===== Part D: boundary value tests for letterGrade() =====

    @Test
    void letterGrade_boundary_60_isD() {
        assertEquals("D", calc.letterGrade(60));
    }

    @Test
    void letterGrade_boundary_59_99_isF() {
        assertEquals("F", calc.letterGrade(59.99));
    }

    @Test
    void letterGrade_boundary_69_99_isD() {
        assertEquals("D", calc.letterGrade(69.99));
    }

    @Test
    void letterGrade_boundary_0_isF() {
        assertEquals("F", calc.letterGrade(0));
    }

    @Test
    void letterGrade_boundary_100_isA() {
        assertEquals("A", calc.letterGrade(100));
    }

    @Test
    void letterGrade_boundary_89_99_isB() {
        assertEquals("B", calc.letterGrade(89.99));
    }

    @Test
    void letterGrade_boundary_90_isA() {
        assertEquals("A", calc.letterGrade(90));
    }
}
