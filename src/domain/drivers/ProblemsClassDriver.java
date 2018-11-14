package src.domain.drivers;

import src.domain.classes.ClassClass;
import src.domain.classes.Subject;
import src.domain.classes.ProblemsClass;
import src.domain.utils.UtilsDomain;
import src.domain.utils.inout;

import java.util.Vector;

public class ProblemsClassDriver {

    public static ClassClass tc;
    public static inout i = new inout();

    private static void testConstructor() throws Exception {
        String identifier;
        int group;
        int subGroup;
        UtilsDomain.ClassType typeG;
        UtilsDomain.typeShift shift;
        int quantityStudents;


        i.write("ID: ");
        identifier = i.readword();
        i.write("Group: ");
        group = i.readint();
        i.write("subGroup: ");
        subGroup = i.readint();
        //i.write("Type of group 0.Theory, 1.Lab, 2.Problems: ");
        //typeG = UtilsDomain.ClassType.values()[0];
        i.write("Shift of group 0.Morning, 1.Afternoon: ");
        shift = UtilsDomain.typeShift.values()[i.readint()];
        i.write("Quantity students: ");
        quantityStudents = i.readint();

        tc = new ProblemsClass(identifier, readSubject(), group, quantityStudents, shift, subGroup);
        i.write("-", 20);
        i.writeln("Theory Class successfully created");
    }

    public static void testConstructorFromStr () throws Exception {
        Vector<String> myVector = new Vector<>();

        i.write("ID: ");
        myVector.add(i.readword());
        i.write("Group: ");
        myVector.add(i.readword());
        i.write("subGroup: ");
        myVector.add(i.readword());
        //i.write("Type of group 0.Theory, 1.Lab, 2.Problems: ");
        myVector.add("PROBLEMS");
        i.write("Shift of group 0.Morning, 1.Afternoon: ");
        myVector.add(i.readword());
        i.write("Quantity students: ");
        myVector.add(i.readword());

        i.write("Subject Name: ");
        myVector.add(i.readword());
        i.write("Num_students: ");
        myVector.add(i.readword());
        i.write("Level: ");
        myVector.add(i.readword());
        i.write("Theory_hours: ");
        myVector.add(i.readword());
        i.write("Laboratory_hours: ");
        myVector.add(i.readword());
        i.write("Problems_hours: ");
        myVector.add(i.readword());
        i.write("Number of groups: ");
        myVector.add(i.readword());
        i.write("Number of Subgroups: ");
        myVector.add(i.readword());
        i.write("Shift 0.Morning, 1.Afternoon, 2.Both ");
        myVector.add(i.readword());

        tc = new ProblemsClass(myVector);
        i.write("-", 20);
        i.writeln("Theory Class successfully created");
    }

    public static Subject readSubject() throws Exception{

        String subjectName;
        int num_students;
        int level;
        int[] hoursClasses = new int[3];
        int[] numberOfGroups = new int[2];
        UtilsDomain.typeShift shiftSubj;

        i.write("Subject Name: ");
        subjectName = i.readword();
        i.write("Num_students: ");
        num_students = i.readint();
        i.write("Level: ");
        level = i.readint();
        i.write("Theory_hours: ");
        hoursClasses[0] = i.readint();
        i.write("Laboratory_hours: ");
        hoursClasses[1] = i.readint();
        i.write("Problems_hours: ");
        hoursClasses[2] = i.readint();
        i.write("Number of groups: ");
        numberOfGroups[0] = i.readint();
        i.write("Number of Subgroups: ");
        numberOfGroups[1] = i.readint();
        i.write("Shift 0.Morning, 1.Afternoon, 2.Both ");
        shiftSubj = UtilsDomain.typeShift.values()[i.readint()];

        Subject subject = new Subject(subjectName, num_students, level, hoursClasses, numberOfGroups, shiftSubj);
        return subject;

    }

    public static void testGetGroup() throws Exception {i.writeln( tc.getGroup() );}
    public static void testGetIdentifier() throws Exception {i.writeln( tc.getIdentifier() );}
    public static void testGetSubject() throws Exception {
        Subject subject = tc.getSubject();
        i.writeln( subject.getName() );
        i.writeln( subject.getLevel() );
        i.writeln( subject.getNumberStudents() );
        i.writeln( subject.getTypeShift().toString() );

    }
    public static void testGetType() throws Exception {i.writeln( tc.getType().toString() );}
    public static void testGetShift() throws Exception {i.writeln( tc.getShift().toString() );}
    public static void testGetQuantityStudents() throws Exception {i.writeln( tc.getQuantityStudents() );}
    public static void testGetSubGroup() throws Exception {i.writeln(tc.getSubGroup()); }


    public static void testSetGroup() throws Exception{ tc.setGroup( i.readint()); }
    public static void testSetIdentifier() throws Exception{ tc.setIdentifier( i.readword() ); }
    public static void testSetSubject() throws Exception{ tc.setSubject( readSubject() ); }
    public static void testSetShift() throws Exception{ tc.setShift( UtilsDomain.typeShift.values()[i.readint()]); }
    public static void testSetQuantityStudents() throws Exception{ tc.setQuantityStudents(i.readint()); }
    public static void testSetSubGroup() throws Exception{ tc.setSubGroup(i.readint()); }


    public static void testToStr() throws Exception {
        Vector<String> myVector = tc.toStr();

        for (int ii = 0; ii < myVector.size(); ii++) {
            i.writeln( myVector.get(ii) );
        }
    }

    public static void testFromStr() throws Exception{
        Vector<String> myVector = new Vector<>();
        i.write("ID: ");
        myVector.add(i.readword());
        i.write("Group: ");
        myVector.add(i.readword());
        i.write("subGroup: ");
        myVector.add(i.readword());
        //i.write("Type of group 0.Theory, 1.Lab, 2.Problems: ");
        myVector.add("PROBLEMS");
        i.write("Type of group 0.Morning, 1.Afternoon: ");
        myVector.add(i.readword());
        i.write("Quantity students: ");
        myVector.add(i.readword());

        i.write("Subject Name: ");
        myVector.add(i.readword());
        i.write("Num_students: ");
        myVector.add(i.readword());
        i.write("Level: ");
        myVector.add(i.readword());
        i.write("Theory_hours: ");
        myVector.add(i.readword());
        i.write("Laboratory_hours: ");
        myVector.add(i.readword());
        i.write("Problems_hours: ");
        myVector.add(i.readword());
        i.write("Number of groups: ");
        myVector.add(i.readword());
        i.write("Number of Subgroups: ");
        myVector.add(i.readword());
        i.write("Shift 0.Morning, 1.Afternoon, 2.Both ");
        myVector.add(i.readword());

        tc = ClassClass.fromStr(myVector);
    }

    public static void main (String [] args) throws Exception {

        int option = 0;

        while( option != 17) {

            i.write("------------------------------------------ \n");
            i.write("------------------MENU-------------------- \n");
            i.write("------------------------------------------ \n");
            i.write("Test Constructor 1: \n");
            i.write("Test Constructor from String Vector 2: \n");
            i.write("Test GetGroup 3: \n");
            i.write("Test GetIdentifier 4: \n");
            i.write("Test GetSubject 5: \n");
            i.write("Test GetType 6: \n");
            i.write("Test GetShift 7: \n");
            i.write("Test GetQuantityStudents 8: \n");
            i.write("Test GetSubGroup 9: \n");
            i.write("Test ToStr 10:  \n");
            i.write("Test SetGroup 11:  \n");
            i.write("Test SetIdentifier 12:  \n");
            i.write("Test SetSubject 13:  \n");
            i.write("Test SetShift 14:  \n");
            i.write("Test SetQuantityStudents 15:  \n");
            i.write("Test SetSubGroup 16:  \n");
            i.write("Exit 17:  \n");

            option = i.readint();


            switch (option) {
                case 1:
                    testConstructor();
                    System.in.read();
                    break;
                case 2:
                    testConstructorFromStr();
                    System.in.read();
                    break;
                case 3:
                    testGetGroup();
                    System.in.read();
                    break;
                case 4:
                    testGetIdentifier();
                    System.in.read();
                    break;
                case 5:
                    testGetSubject();
                    System.in.read();
                    break;
                case 6:
                    testGetType();
                    System.in.read();
                    break;
                case 7:
                    testGetShift();
                    System.in.read();
                    break;
                case 8:
                    testGetQuantityStudents();
                    System.in.read();
                    break;
                case 9:
                    testGetSubGroup();
                    System.in.read();
                    break;
                case 10:
                    testToStr();
                    System.in.read();
                    break;
                case 11:
                    testSetGroup();
                    System.in.read();
                    break;
                case 12:
                    testSetIdentifier();
                    System.in.read();
                    break;
                case 13:
                    testSetSubject();
                    System.in.read();
                    break;
                case 14:
                    testSetShift();
                    System.in.read();
                    break;
                case 15:
                    testSetQuantityStudents();
                    System.in.read();
                    break;
                case 16:
                    testSetSubGroup();
                    System.in.read();
                    break;
            }
        }
    }
}
