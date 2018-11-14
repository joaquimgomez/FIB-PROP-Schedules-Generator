package src.domain.drivers;

import src.domain.classes.Classroom;
import src.domain.classes.ClassroomSet;
import src.domain.classes.LabClassroom;
import src.domain.classes.TheoryClassroom;
import src.domain.utils.UtilsDomain;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class ClassroomSetDriver {

    private static ClassroomSet cSet;

    private static void writeClassroom(Classroom c){
        String s = c.getName() + " "
                + String.valueOf(c.getCapacity()) + " "
                + String.valueOf(c.getType().name()) + " ";
        if(c.isMultimedia()) s += "true ";
        else s += "false ";
        if(c.getType() == UtilsDomain.ClassType.LABORATORY) {
            LabClassroom l = (LabClassroom) c;
            s += String.valueOf(l.getNumComputers());
        }
        else s += "0";

        System.out.println(s);
    }

    public static void testConstructor(){
        ArrayList<Classroom> c = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int lab = sc.nextInt();
        int theo = sc.nextInt();
        for(int i = 0; i < lab; ++i) {
            String name = sc.next();
            int cap = sc.nextInt();
            boolean multimedia = sc.nextBoolean();
            int nComp = sc.nextInt();
            c.add(new LabClassroom(name, cap, multimedia, nComp));
        }
        for(int i = 0; i < theo; ++i) {
            String name = sc.next();
            int cap = sc.nextInt();
            boolean multimedia = sc.nextBoolean();
            c.add(new TheoryClassroom(name, cap, multimedia));
        }
        cSet = new ClassroomSet(c);
    }
    public static void testLabTheoryConstructor(){
        ArrayList<LabClassroom> labArray = new ArrayList<>();
        ArrayList<TheoryClassroom> theoArray = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int lab = sc.nextInt();
        int theo = sc.nextInt();
        for(int i = 0; i < lab; ++i) {
            String name = sc.next();
            int cap = sc.nextInt();
            boolean multimedia = sc.nextBoolean();
            int nComp = sc.nextInt();
            labArray.add(new LabClassroom(name, cap, multimedia, nComp));
        }
        for(int i = 0; i < theo; ++i) {
            String name = sc.next();
            int cap = sc.nextInt();
            boolean multimedia = sc.nextBoolean();
            theoArray.add(new TheoryClassroom(name, cap, multimedia));
        }
        cSet = new ClassroomSet(theoArray, labArray);
    }
    public static void testConstructorFromString(){
        Scanner sc = new Scanner(System.in);
        int lab = sc.nextInt();
        int theo = sc.nextInt();
        Vector<Vector<String>> vec = new Vector< Vector<String> >((lab+theo));
        for(int i = 0; i < lab; ++i) {
            Vector<String> v = new Vector<>(5);
            v.add(sc.next()); //name
            v.add(sc.next()); //capacity
            v.add("Laboratory"); //type
            v.add(sc.next()); //multimedia
            v.add(sc.next()); //nComp

            vec.add(v);
        }

        for(int i = 0; i < theo; ++i) {
            Vector<String> v = new Vector<>(5);
            v.add(sc.next()); //name
            v.add(sc.next()); //capacity
            v.add("Theory"); //type
            v.add(sc.next()); //multimedia
            v.add("0"); //nComp

            vec.add(v);
        }
        cSet = new ClassroomSet(vec);
    }
    public static void testGetClassroomValues(){
        ArrayList<Classroom> c = cSet.getClassroomValues();
        for (Classroom aC : c) writeClassroom(aC);
    }
    public static void testGetLabClassroomSet(){
        ArrayList<LabClassroom> lab = cSet.getLabClassroomSet();
        for(int i = 0; i < lab.size(); ++i) {
            Classroom c = lab.get(i);
            writeClassroom(c);
        }
    }
    public static void testGetTheoryClassroomSet(){
        ArrayList<TheoryClassroom> theo = cSet.getTheoryClassroomSet();
        for(int i = 0; i < theo.size(); ++i) {
            Classroom c = theo.get(i);
            writeClassroom(c);
        }
    }
    public static void testGetNumClassrooms(){
        System.out.println(cSet.getNumClassrooms());
    }
    public static void testExists(){
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        s = (String)(cSet.exists(s)?"true":"false");
        System.out.println(s);
    }
    public static void testGetClassroom(){
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        if(cSet.getClassroom(s).queryTest) {
            Classroom c = (Classroom)  cSet.getClassroom(s).result;
            writeClassroom(c);
        }
        else System.out.println("non existing classroom");
    }
    public static void testAddClassroomSet(){
        ArrayList<Classroom> c = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int lab = sc.nextInt();
        int theo = sc.nextInt();
        for(int i = 0; i < lab; ++i) {
            String name = sc.next();
            int cap = sc.nextInt();
            boolean multimedia = sc.nextBoolean();
            int nComp = sc.nextInt();
            c.add(new LabClassroom(name, cap, multimedia, nComp));
        }
        for(int i = 0; i < theo; ++i) {
            String name = sc.next();
            int cap = sc.nextInt();
            boolean multimedia = sc.nextBoolean();
            c.add(new TheoryClassroom(name, cap, multimedia));
        }
        cSet.addClassroomSet(c);
    }
    public static void testToStr(){
        Vector< Vector <String> > v = cSet.toStr();
        for(int i = 0; i < v.size(); ++i) {
            System.out.println(
                    v.get(i).get(0) + " " +
                    v.get(i).get(1) + " " +
                    v.get(i).get(2) + " " +
                    v.get(i).get(3) + " " +
                    v.get(i).get(4)
            );
        }
    }

    public static void  main(String args[]) {
        cSet = new ClassroomSet();
        Scanner sc = new Scanner(System.in);
        write();
        do{

            int i = sc.nextInt();
            switch(i) {
                case 0:
                    testConstructor();
                    break;
                case 1:
                    testLabTheoryConstructor();
                    break;
                case 2:
                    testConstructorFromString();
                    break;
                case 3:
                    testGetClassroomValues();
                    break;
                case 4:
                    testGetLabClassroomSet();
                    break;
                case 5:
                    testGetTheoryClassroomSet();
                    break;
                case 6:
                    testGetNumClassrooms();
                    break;
                case 7:
                    testExists();
                    break;
                case 8:
                    testGetClassroom();
                    break;
                case 9:
                    testAddClassroomSet();
                    break;
                case 10:
                    testToStr();
                    break;
                default:
                    System.out.println("\tCtr+D to EXIT");
            }
            write();
        }while(sc.hasNextInt());

    }

    //TODO: treure això pels jocs de proves
    public static void write() {
        System.out.println("------------------------------------------");
        System.out.println("\nWrite the number of the function you want to test:");
        System.out.println("\t0 -> Basic constructor");
        System.out.println("\t1 -> Lab & Theory constructor");
        System.out.println("\t2 -> Constructor from String");
        System.out.println("\t3 -> Get values of classrooms");
        System.out.println("\t4 -> Laboratory list getter");
        System.out.println("\t5 -> Theory list getter");
        System.out.println("\t6 -> Get number of classrooms");
        System.out.println("\t7 -> Classroom exists?");
        System.out.println("\t8 -> Specific classroom getter");
        System.out.println("\t9 -> Add classroom");
        System.out.println("\t10 -> Convert Object to String");

    }
}
