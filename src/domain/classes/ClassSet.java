/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.domain.classes;

import src.domain.utils.UtilsDomain;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Vector;

/**
 * CLassSet represents a set of Classes of different types
 * @author Sergio Mazzariol
 */
public class ClassSet {
    
    // ATTRIBUTES -------------------------------------

    private HashMap<String, ClassClass> classSet;

    // CONSTRUCTOR -----------------------------------

    /**
    * Class constructor that initialize the set
    */    
    public ClassSet() {
        classSet = new HashMap<>();
    }

    /**
    * Class constructor specifying the member's values.
    * @param subjectsSet set of subjects that are needed to create the set of classes
    */
    public ClassSet( SubjectsSet subjectsSet ) {

        classSet = new HashMap<>();
        createSetOfClasses(subjectsSet);

    }

    /**
     * Class constructor for a given set of subjects in string format.
     * @param classS Vector of vector with one class at each row
     */
    public ClassSet( Vector< Vector<String> > classS ){
        classSet = new HashMap<>();
        for (Vector<String> classClass: classS) {
            ClassClass auxClass = ClassClass.fromStr(classClass);
            this.addClass(auxClass.getIdentifier(), auxClass);
        }
    }

    // PRIVATE METHODS -------------------------------------------



    // METHODS ---------------------------------------------------

    /**
     * Check if a given Class it's in the set
     * @param subjectName Name of the subject to check
     * @param subGroup Subgroup of the class to check
     * @return
     */
    public boolean existsClass( String subjectName, int subGroup ) {
        return classSet.containsKey(""+subjectName+subGroup );
    }

    /**
     * Gets a given class from the set
     * @param name Name of the subject to find
     * @param subGroup Group of the class to check
     * @return The class on success and null if can't find it
     */
    public ClassClass getClass( String name, int subGroup ){
        if (this.existsClass(name, subGroup)) {
            return classSet.get(""+name+subGroup);
        }
        return null;
    }

    /**
     * Add a new class to the set
     * @param identifier identifies the object.
     * @param newClass the new class that needs to be added.
     */
    public void addClass( String identifier, ClassClass newClass ){
        classSet.put( identifier, newClass);
    }

    /**
     * Creates a set of classes from a set of subjects
     * @param subjects Subject set for the classes set creation
     */
    private void createSetOfClasses( SubjectsSet subjects ){
        ArrayList<Subject> subjectsArray = subjects.unset();

        // for each subject
        for ( Subject subject : subjectsArray ){

            // [0] Number of Groups => [1] Number of subgroups
            int[] groups = subject.getNumberOfGroups();
            int subGroupCount;
            int subGroup;
            String identifier;
            int quantityStudents;
            int[] hoursOfClass = subject.getHoursClasses();
            UtilsDomain.typeShift shiftA;
            UtilsDomain.typeShift[] allShift = UtilsDomain.typeShift.values();
            ClassClass c;

            // For each group of the subject we create the respective theory classes, labs and problems classes.
            for (int i = 1; i <= groups[0]; i++){

                subGroupCount = 0;

                // Verify if the shift is morning - afternoon
                if ( subject.getTypeShift().ordinal() == 2 ){
                    if(i%2 != 0){
                        // if it's odd we assign morning shift
                        shiftA = allShift[0];
                    }else{
                        // if it's pair we assign afternoon shift
                        shiftA = allShift[1];
                    }
                } else if( subject.getTypeShift().ordinal() == 1 ){
                    // We assign morning shift
                    shiftA = allShift[1];
                } else {
                    // We assign afternoon shift
                    shiftA = allShift[0];
                }

                // If there is TheoryHours we create the class
                if ( hoursOfClass[0] != 0 ) {
                    subGroup = i*10+subGroupCount;
                    identifier = ""+subject.getName()+subGroup;
                    // identifier, subGroup, subject, group, quantityStudents, UtilsDomain.TimeZone shift)
                    quantityStudents = ((groups[0] != 0)?(int)Math.ceil(subject.getNumberStudents()/groups[0]): 0);
                    for(int k = 0; k < hoursOfClass[0]; ++k) {
                        c = new TheoryClass( identifier, subject, i*10, quantityStudents, shiftA , subGroup  );
                        this.addClass( identifier + String.valueOf(k), c);
                    }
                    subGroupCount++;
                }


                for ( int j = 0; j < groups[1]; j++) {
                    // If there is LaboratoryHours we create the class
                    if ( hoursOfClass[1] != 0 ) {
                        subGroup = i*10+subGroupCount;
                        subGroupCount++;
                        identifier = ""+subject.getName()+subGroup;
                        quantityStudents = ((groups[0] != 0)?(int)Math.ceil( (subject.getNumberStudents()/groups[0])/groups[1]): 0);
                        for(int k = 0 ; k < hoursOfClass[1]; ++k) {
                            c = new LaboratoryClass( identifier, subject, i*10, quantityStudents, shiftA , subGroup  );
                            this.addClass( identifier + String.valueOf(k), c);
                        }
                    }

                    // If there is ProblemsHours we create the class
                    if ( hoursOfClass[2] != 0 ) {
                        subGroup = i*10+subGroupCount;
                        subGroupCount++;
                        identifier = ""+subject.getName()+subGroup;
                        quantityStudents = ((groups[0] != 0)?(int)Math.ceil( (subject.getNumberStudents()/groups[0])/groups[1]): 0);
                        for(int k = 0; k < hoursOfClass[2]; ++k) {
                            c = new ProblemsClass( identifier, subject, i*10, quantityStudents, shiftA , subGroup  );
                            this.addClass( identifier + String.valueOf(k), c);
                        }
                    }
                }
            }
        }
    }

    /**
     * Size of the set
     * @return Size of the set
     */
    public int size(){
        return this.size();
    }

    /**
     * Unset the set of classes.
     * @return An Array with the classes of the set.
     */
    public ArrayList<ClassClass> unset() {
        ArrayList<ClassClass> tempSet = new ArrayList<>(classSet.values());
        //classSort(tempSet);
        return tempSet;
    }

    /**
     * Implements the different comparisons between two classes,
     * considering the group and the name of these.
     * @param s1 First class to compare.
     * @param op Operator of the comparison.
     * @param s2 Second class to compare.
     * @return Result of the comparison.
     */
    public static boolean compare(ClassClass s1, String op, ClassClass s2){
        if (op.equals("<"))     return s1.getGroup() < s2.getGroup() && s1.getSubject().getName().compareTo(s2.getSubject().getName()) > 0;
        if (op.equals(">"))     return s1.getGroup() > s2.getGroup() && s1.getSubject().getName().compareTo(s2.getSubject().getName()) < 0;
        if (op.equals("<="))    return s1.getGroup() <= s2.getGroup() && (s1.getSubject().getName().compareTo(s2.getSubject().getName()) > 0 || s1.getSubject().getName().contentEquals(s2.getSubject().getName()));
        if (op.equals(">="))    return s1.getGroup() >= s2.getGroup() && (s1.getSubject().getName().compareTo(s2.getSubject().getName()) < 0 || s1.getSubject().getName().contentEquals(s2.getSubject().getName()));
        if (op.equals("!="))    return s1.getGroup() != s2.getGroup() && !s1.getSubject().getName().contentEquals(s2.getSubject().getName());
        if (op.equals("=="))    return s1.getGroup() == s2.getGroup() && !s1.getSubject().getName().contentEquals(s2.getSubject().getName());

        return false;
    }


    /**
     * It returns the set as a vector of vectors (of strings) with the members of the elements of the set.
     * @return Vector of vectors (of strings) with the members of the elements of the set.
     */
    public Vector< Vector<String> > toStr() {
        ArrayList<ClassClass> cs = this.unset();
        Vector< Vector<String> > set = new Vector<>(cs.size());

        for (int i = 0; i < cs.size(); i++) set.add(i, cs.get(i).toStr());

        return set;
    }


    /* NOT IMPLEMENTED FOR THE MOMENT----------------------------------------------
    /**
     * Recursively implementation of the mergesort algorithm.
     * @param set that must be ordered.
     * @param start Start point of the sort.
     * @param end End point of the sort.

    private static void rClassSort(ArrayList<ClassClass> set, int start, int end) {
        if (start < end){
            int mid = start + (end - start) / 2;

            rClassSort(set, start, mid);
            rClassSort(set,mid + 1, end);

            merge(set, start, mid, end);
        }
    }
    /**
     * Sort of the classes considering the group and the name of these.
     * @param set Set that must be ordered.

    public static void classSort(ArrayList<ClassClass> set) {
        // Mergesort Implementation
        // Worst-case complexity: O(n log n) ; Worst-case space complexity: O(n)
        rClassSort(set, 0, set.size());
    }


    /**
     * Implementation of merge for the mergesort algorithm.
     * @param set Set that must be ordered.
     * @param start Start point of the sort.
     * @param mid Middle point of the sort.
     * @param end End point of the sort.

    private static void merge(ArrayList<ClassClass> set, int start, int mid, int end) {
        ArrayList<ClassClass> aux = new ArrayList<>();

        for (int i = start; i <= end; i++)  aux.add(i, set.get(i));

        int i = start;
        int j = mid + 1;
        int k = start;

        while (i <= mid && j <= end) {
            if (compare(aux.get(i), "<=", aux.get(j)))   set.set(k++, aux.get(i++));
            else    set.set(k++, aux.get(j++));
        }

        while (i <= mid)
            set.set(k++, aux.get(i++));
    }
    */


}
