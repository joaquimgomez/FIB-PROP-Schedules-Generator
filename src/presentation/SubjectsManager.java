package src.presentation;

import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class SubjectsManager extends JDialog implements ListSelectionListener {
    private CtrlPresenter ctrlPresenter;

    private ArrayList<Vector<String>> subjects;

    private JPanel rootPanel;
    private JPanel atributtesPanel;

    DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> list;

    private JLabel Name;
    private JTextArea nameArea;
    private JTextArea numStudentsArea;
    private JTextArea theoryHoursArea;
    private JTextArea laboratoryHoursArea;
    private JTextArea problemsHoursArea;
    private JTextArea numberOfGroupsArea;
    private JTextArea numberOfSubgrupsArea;
    private JTextArea shiftArea;
    private JPanel listPanel;
    private JTextArea levelArea;
    private JButton saveSetOfSubjectsButton;
    private JButton deleteButton;
    private JButton addButton;

    private static final String nameAreaString = "Name";
    private static final String levelAreaString = "Level";
    private static final String numStudentsAreaString = "Number of Students";
    private static final String numberOfTheoryHoursAreaString = "Number of Theory Hours";
    private static final String numberOfLaboratoryHoursAreaString = "Number of Laboratory Hours";
    private static final String numberOfProblemsHoursAreaString = "Number of Problems Hours";
    private static final String numberOfGroupsAreaString = "Number of Groups";
    private static final String numberOfSubgroupsAreaString = "Number of Subgroups per Group";
    private static final String shiftAreaString = "MORNING/AFTERNOON/BOTH";


    public SubjectsManager(CtrlPresenter ctrlPresenter, Vector<Vector<String>> subjects) {
        this.ctrlPresenter = ctrlPresenter;

        this.subjects = new ArrayList<>(subjects);

        initList(this.subjects);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);

        initComponents();

        setSize(600,500);
        setTitle("SUBJECTS MANAGER");

        add(rootPanel);

        rootPanel.setEnabled(true);
        rootPanel.setVisible(true);

    }

    public void initList(ArrayList<Vector<String>> subjects) {
        for(Vector<String> v : subjects) {
            model.addElement(v.get(0));
            list.setModel(model);
        }
    }


    public void initComponents() {
        saveSetOfSubjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JDialog d = new JDialog();
                    if (valid()){
                        ctrlPresenter.saveSubjectSet(new Vector<>(subjects));
                    } else {
                        JOptionPane.showMessageDialog(d, "Error.");
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }

            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();


                Vector<String> v = new Vector<>(9);
                subjects.add(index, v);
                for (int i = 0; i < 9; i++) {
                    subjects.get(index).add(i, "");
                }

                model.add(index,"SUBJECT");
                list.setSelectedIndex(index);

                nameArea.setText(nameAreaString);
                levelArea.setText(levelAreaString);
                numStudentsArea.setText(numStudentsAreaString);
                theoryHoursArea.setText(numberOfTheoryHoursAreaString);
                laboratoryHoursArea.setText(numberOfLaboratoryHoursAreaString);
                problemsHoursArea.setText(numberOfProblemsHoursAreaString);
                numberOfGroupsArea.setText(numberOfGroupsAreaString);
                numberOfSubgrupsArea.setText(numberOfSubgroupsAreaString);
                shiftArea.setText(shiftAreaString);


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();
                list.setSelectedIndex(index+1);
                subjects.remove(index);
                model.remove(index);
            }
        });

        nameArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!nameArea.getText().equals(nameAreaString)) {
                    subjects.get(list.getSelectedIndex()).set(0, nameArea.getText());
                    model.set(list.getSelectedIndex(), nameArea.getText());
                }
            }
        });

        levelArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!levelArea.getText().equals(levelAreaString)) {
                    subjects.get(list.getSelectedIndex()).set(1, levelArea.getText());
                }
            }
        });

        numStudentsArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!numStudentsArea.getText().equals(numStudentsAreaString)) {
                    subjects.get(list.getSelectedIndex()).set(2, numStudentsArea.getText());
                }
            }
        });

        theoryHoursArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!theoryHoursArea.getText().equals(numberOfTheoryHoursAreaString)) {
                    subjects.get(list.getSelectedIndex()).set(3, theoryHoursArea.getText());
                }
            }
        });

        laboratoryHoursArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!laboratoryHoursArea.getText().equals(numberOfLaboratoryHoursAreaString)) {
                    subjects.get(list.getSelectedIndex()).set(4, laboratoryHoursArea.getText());
                }
            }
        });

        problemsHoursArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!problemsHoursArea.getText().equals(numberOfProblemsHoursAreaString)) {
                    subjects.get(list.getSelectedIndex()).set(5, problemsHoursArea.getText());
                }
            }
        });

        numberOfGroupsArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!numberOfGroupsArea.getText().equals(numberOfGroupsAreaString)) {
                    subjects.get(list.getSelectedIndex()).set(5, numberOfGroupsArea.getText());
                }
            }
        });

        numberOfSubgrupsArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!numberOfSubgrupsArea.getText().equals(numberOfSubgroupsAreaString)) {
                    subjects.get(list.getSelectedIndex()).set(6, numberOfSubgrupsArea.getText());
                }
            }
        });

        shiftArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!shiftArea.getText().equals(shiftAreaString)) {
                    subjects.get(list.getSelectedIndex()).set(6, shiftArea.getText());

                }
            }
        });

    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            int index = list.getSelectedIndex();

            if (model.get(index).equals("SUBJECT")){
                nameArea.setText(nameAreaString);
                levelArea.setText(levelAreaString);
                numStudentsArea.setText(numStudentsAreaString);
                theoryHoursArea.setText(numberOfTheoryHoursAreaString);
                laboratoryHoursArea.setText(numberOfLaboratoryHoursAreaString);
                problemsHoursArea.setText(numberOfProblemsHoursAreaString);
                numberOfGroupsArea.setText(numberOfGroupsAreaString);
                numberOfSubgrupsArea.setText(numberOfSubgroupsAreaString);
                shiftArea.setText(shiftAreaString);
            } else {
                nameArea.setText(subjects.get(index).get(0));
                levelArea.setText(subjects.get(index).get(1));
                numStudentsArea.setText(subjects.get(index).get(2));
                theoryHoursArea.setText(subjects.get(index).get(3));
                laboratoryHoursArea.setText(subjects.get(index).get(4));
                problemsHoursArea.setText(subjects.get(index).get(5));
                numberOfGroupsArea.setText(subjects.get(index).get(6));
                numberOfSubgrupsArea.setText(subjects.get(index).get(7));
                shiftArea.setText(subjects.get(index).get(8));
            }
        }
    }

    public boolean valid(){
        for(Vector<String> v : subjects) {
            if (v.get(0).equals("") ||
                    v.get(1).equals("") ||
                    v.get(2).equals("") ||
                    v.get(3).equals("") ||
                    v.get(4).equals("") ||
                    v.get(5).equals("") ||
                    v.get(6).equals("") ||
                    v.get(7).equals("") ||
                    v.get(8).equals(""))
                return false;
        }
        return true;
    }
}