package src.domain.classes;

import src.domain.utils.UtilsDomain.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Constraints Class.
 * @author Joaquim Gómez & Mireia Cano
 */
public class Constraints {

    // Structure to save the enabled constraints

    private static class ConstraintsSet {
        static boolean notSameClassroomAndSessionEnabled                                   = false;
        static boolean classOfSameSubgroupAndLevelNoTogetherEnabled                        = false;
        static boolean theorysOfSubjectsOfSameLevelNoTogetherEnabled                       = false;
        static boolean theoryOfSubjectFromDifferentClassesNoTogetherEnabled                = false;
        static boolean labsAndProblemsFromDifferentSubjectsOfSameGroupNoTogetherEnabled    = false;
        static boolean labsAndTheoryOfSameGroupAndSubjectNotTogetherEnabled                = false;


        private static void setContraints(boolean[] sc){
            notSameClassroomAndSessionEnabled = sc[0];
            classOfSameSubgroupAndLevelNoTogetherEnabled = sc[1];
            theorysOfSubjectsOfSameLevelNoTogetherEnabled = sc[2];
            theoryOfSubjectFromDifferentClassesNoTogetherEnabled = sc[3];
            labsAndProblemsFromDifferentSubjectsOfSameGroupNoTogetherEnabled = sc[4];
            labsAndTheoryOfSameGroupAndSubjectNotTogetherEnabled = sc[5];
        }
    }

    public void setContraints(boolean[] sc){
        ConstraintsSet.setContraints(sc);
    }


    // UNARY CONSTRAINTS

    /**
     * Unary Constraint: Size Classroom Constraint.
     * @param m MUS to try the constraint with the pair Classroom-Session.
     * @param cs Pair Classroo-Session to try the contraint with the MUS.
     * @return True if satisfied constraint.
     */
    public static boolean sizeClassroomUnaryConstraint(MUS m, Pair<Classroom, Session> cs) {
        /* Una clase debe tener un aula de tamaño mayor o igual que el número de alumnos de la clase. */
        return cs.first.getCapacity() >= m.getClassClass().getQuantityStudents();
    }

    /**
     * Unary Constraint: Type Classroom Constraint.
     * @param m MUS to try the constraint with the pair Classroom-Session.
     * @param cs Pair Classroo-Session to try the contraint with the MUS.
     * @return True if satisfied constraint.
     */
    public static boolean typeClassroomUnaryConstraint(MUS m, Pair<Classroom, Session> cs) {
        /* Una clase de una asignatura de un tipo solo puede ir a un aula de dicho tipo. */
        return (cs.first.getType() == m.getClassClass().getType() ||
                (m.getClassClass().getType().ordinal() == ClassType.PROBLEMS.ordinal()
                        && cs.first.getType().ordinal() == ClassType.LABORATORY.ordinal() ));
    }

    /**
     * Unary Constraint: Shift Class Constraint
     * @param m MUS to try the constraint with the pair Classroom-Session.
     * @param cs Pair Classroo-Session to try the contraint with the MUS.
     * @return True if satisfied constraint.
     */
    public static boolean shiftClassUnaryConstraint(MUS m, Pair<Classroom, Session> cs) {
        /* Las clases de asignaturas /del mismo nivel/ han de ser de mañanas o de tarde, pero no de ambas. */
        if (m.getClassClass().getShift() == typeShift.MORNING ) return cs.second.getHour() < 14;
        else if (m.getClassClass().getShift() == typeShift.AFTERNOON ) return cs.second.getHour() >= 14;
        return true;
    }



    //CONSTRAINTS CHECKERS

    /**
     * Checks if m1 and m2 satisfy all the compulsory constraints
     * @param m1 First MUS to try the constraints.
     * @param m2 Second MUS to try the constraints.
     * @return returns true if m1 and m2 satisfy all constraints, false otherwise;
     */
    public static boolean satisfiesConstraints(MUS m1, MUS m2){
        if(!((!ConstraintsSet.notSameClassroomAndSessionEnabled || notSameClassroomAndSession(m1, m2)) &&
                (!ConstraintsSet.classOfSameSubgroupAndLevelNoTogetherEnabled || classOfSameSubgroupAndLevelNoTogether(m1, m2)) &&
                (!ConstraintsSet.theorysOfSubjectsOfSameLevelNoTogetherEnabled || theorysOfSubjectsOfSameLevelNoTogether(m1, m2)) &&
                (!ConstraintsSet.theoryOfSubjectFromDifferentClassesNoTogetherEnabled || theoryOfSubjectFromDifferentClassesNoTogether(m1, m2)) &&
                (!ConstraintsSet.labsAndProblemsFromDifferentSubjectsOfSameGroupNoTogetherEnabled || labsAndProblemsFromDifferentSubjectsOfSameGroupNoTogether(m1, m2)) &&
                (!ConstraintsSet.labsAndTheoryOfSameGroupAndSubjectNotTogetherEnabled || labsAndTheoryOfSameGroupAndSubjectNotTogether(m1, m2))))
            return true;
        return false;

        /*if(!(notSameClassroomAndSession(m1, m2) &&
                classOfSameSubgroupAndLevelNoTogether(m1, m2) &&
                theorysOfSubjectsOfSameLevelNoTogether(m1, m2) &&
                theoryOfSubjectFromDifferentClassesNoTogether(m1, m2) &&
                labsAndProblemsFromDifferentSubjectsOfSameGroupNoTogether(m1, m2) &&
                labsAndTheoryOfSameGroupAndSubjectNotTogether(m1, m2)))
            return false;
        return true;*/
    }

    /**
     * checks if two MUSes with the same identifier and that are paired with each other, satisfy the constraints
     * @param m1 First MUS to try the constraint.
     * @param m2 Second MUS to try the constraint.
     * @return true if MUSes are in the same Classroom and consecutive Sessions
     */
    public static boolean satisfiesSameClassNotPairedConditions(MUS m1, MUS m2) {
        if(!(m1.getClassroom().getName().equals(m2.getClassroom().getName()) &&
                m1.getSession().neighbor(m2.getSession())))
            return false;
        return true;
    }

    /**
     * Checks if two MUSes with the same identifier and that are paired with other MUSes, satisfy the constraints
     * @param m1 First MUS to try the constraint.
     * @param m2 Second MUS to try the constraint.
     * @return true if MUSes are not in the same Day
     */
    public static boolean satisfiesSameClassPairedConditions(MUS m1, MUS m2) {
        if((m1.getSession().getDay().ordinal() == m2.getSession().getDay().ordinal()))
            return false;
        return true;
    }



    // N-ARY CONSTRAINTS

    /**
     * N-ary Constraint: Not Same Classroom and Session.
     * @param m1 First MUS to try the constraint.
     * @param m2 Second MUS to try the constraint.
     * @return True if satisfied constraint.
     */
    public static boolean notSameClassroomAndSession(MUS m1, MUS m2) {
        /* Dos sesiones no pueden coincidir en la misma aula. */
        if((Session.compare(m1.getSession(), "==", m2.getSession())) &&
           (m1.getClassroom().getName().equals(m2.getClassroom().getName())))
            return false;
        return true;
    }

    /**
     * N-ary Constraint: Theory sessions can't collapse with laboratory/problems sessions of the same group.
     * @param m1 First MUS to try the constraint.
     * @param m2 Second MUS to try the constraint.
     * @return True if satisfied constraint.
     */
    public static boolean labsAndTheoryOfSameGroupAndSubjectNotTogether(MUS m1, MUS m2) {
        /* La teoría de los grupos de una asignatura no puede coincidir con sus laboratorios/problemas. */
        if(m1.getClassClass().getSubject().getName().equals(m2.getClassClass().getSubject().getName()) &&
                ((m1.getClassClass().getType() == ClassType.THEORY &&
                        (m2.getClassClass().getType() == ClassType.LABORATORY ||
                                m2.getClassClass().getType() == ClassType.PROBLEMS)) ||
                        (m2.getClassClass().getType() == ClassType.THEORY &&
                                (m1.getClassClass().getType() == ClassType.LABORATORY ||
                                        m1.getClassClass().getType() == ClassType.PROBLEMS))) &&
                m1.getClassClass().getGroup() == m2.getClassClass().getGroup() &&
                Session.compare(m1.getSession(), "==", m2.getSession()))
            return false;
        return true;
    }

    /**
     * N-ary Constraint: Classes of the same subgroup and lever can't take place at the same time.
     * @param m1 First MUS to try the constraint.
     * @param m2 Second MUS to try the constraint.
     * @return True if satisfied constraint.
     */
    public static boolean classOfSameSubgroupAndLevelNoTogether(MUS m1, MUS m2) {
        /* Clases del mismo subgrupo y mismo nivel no pueden coincidir. */
        if (m1.getClassClass().getSubGroup() == m2.getClassClass().getSubGroup() &&
                m1.getClassClass().getSubject().getLevel() == m2.getClassClass().getSubject().getLevel() &&
                Session.compare(m1.getSession(), "==", m2.getSession()))
            return false;
        return true;
    }

    /**
     * N-ary Constraint: Theory Classes of Same Subject no Together.
     * @param m1 First MUS to try the constraint.
     * @param m2 Second MUS to try the constraint.
     * @return True if satisfied constraint.
     */
    public static boolean theoryOfSubjectFromDifferentClassesNoTogether(MUS m1, MUS m2) {
        /* Teorías de una misma asignatura no pueden coincidir. */
        if (m1.getClassClass().getType() == ClassType.THEORY &&
                m2.getClassClass().getType() == ClassType.THEORY &&
                m1.getClassClass().getSubject().getName().equals(m2.getClassClass().getSubject().getName()) &&
                Session.compare(m1.getSession(), "==", m2.getSession()))
            return false;
        return true;
    }

   // -------------------------------------------------



    /**
     * N-ary Constraint: Theorys of Subjects Of Same Level no Together.
     * @param m1 First MUS to try the constraint.
     * @param m2 Second MUS to try the constraint.
     * @return True if satisfied constraint.
     */
    public static boolean theorysOfSubjectsOfSameLevelNoTogether(MUS m1, MUS m2) {
        /* Los grupos de teoría de asignaturas diferentes de un mismo nivel no pueden coincidir. */
        if (m1.getClassClass().getType() == ClassType.THEORY &&
                m2.getClassClass().getType() == ClassType.THEORY &&
                !m1.getClassClass().getSubject().getName().equals(m2.getClassClass().getSubject().getName()) &&
                m1.getClassClass().getSubject().getLevel() == m2.getClassClass().getSubject().getLevel() &&
                m1.getClassClass().getGroup() == m2.getClassClass().getGroup() &&
                Session.compare(m1.getSession(), "==", m2.getSession()))
            return false;
        return true;
    }



    /**
     * N-ary Constraint: Labs and Problems From Different Subjects of Same Class no Together
     * @param m1 First MUS to try the constraint.
     * @param m2 Second MUS to try the constraint.
     * @return True if satisfied constraint.
     */
    public static boolean labsAndProblemsFromDifferentSubjectsOfSameGroupNoTogether(MUS m1, MUS m2) {
        if ((m1.getClassClass().getType() == ClassType.LABORATORY ||
                m1.getClassClass().getType() == ClassType.PROBLEMS) &&
                (m2.getClassClass().getType() == ClassType.LABORATORY ||
                m2.getClassClass().getType() == ClassType.PROBLEMS) &&
                !m1.getClassClass().getSubject().getName().equals(m2.getClassClass().getSubject().getName()) &&
                m1.getClassClass().getGroup() == m2.getClassClass().getGroup() &&
                m1.getClassClass().getSubject().getLevel() == m2.getClassClass().getSubject().getLevel() &&
                Session.compare(m1.getSession(), "==", m2.getSession()))
            return false;
        return true;
    }



}
