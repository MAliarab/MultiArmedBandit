package com.company;

//created by Mahmoud Aliarab
// this class is for getting inputs with GUI and show results;
// you can choose Multi armed algorithms and enter other inputs and see the result in a pretty graph:)
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class GUI {
    float[] values;
    int N;
    int plays;
    int repeats;
    float epsilon;
    float temp;
    float alpha;
    float alpha1;
    float alpha2;
    float alpha3;
    float [] alphaList;

    String algorithm;
    String[] algos;
    float[][] allRewards;
    float[][] allOptPercents;
    float [] averagePercent;
    float[] AVG;
    float[][] AVGList;
    ArrayList<String> algoList;

    public GUI(){
        //init
        alphaList = new float[3];
        algoList= new ArrayList<String>();
        //-----------------

        // Constructing input GUI
        JFrame frame =  new JFrame();
        frame.setLayout(new GridLayout(7,1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 400);
        frame.setTitle("Multi-Armed-Bandit");
        frame.setLocation(500, 500);

        //panel1 for getting number of arms
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l1 = new JLabel("N = ");
        panel1.add(l1);
        final JTextField tf1 = new JTextField(10);
        panel1.add(tf1);
        frame.add(panel1);

        //panel2 for setting real action values manually or with distribution types
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l2 = new JLabel("Action value: ");
        JButton btn1  = new JButton("Manually");
        JButton check1 = new JButton("Uniform");
        JButton check2 = new JButton("Normal");
        JButton check3 = new JButton("Poisson");
        panel2.add(l2);
        panel2.add(btn1);
        panel2.add(check1);
        panel2.add(check2);
        panel2.add(check3);
        frame.add(panel2);

        //panel3 for setting num of plays
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l3 = new JLabel("Plays = ");
        final JTextField tf2 = new JTextField(10);
        panel3.add(l3);
        panel3.add(tf2);
        frame.add(panel3);

        //panel4 for setting repeat #
        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l4 = new JLabel("Repeats = ");
        final JTextField tf3 = new JTextField(10);
        panel4.add(l4);
        panel4.add(tf3);
        frame.add(panel4);

        //panel5 is for choosing algorithm(s)
        JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l5 = new JLabel("Select Algorithm: ");
        final JCheckBox greedyBox = new JCheckBox("Greedy");
        final JCheckBox e_greedyBox = new JCheckBox("E-Greedy");
        final JCheckBox softMaxBox = new JCheckBox("SoftMax");
        final JCheckBox r_iBox = new JCheckBox("R-I");
        final JCheckBox r_pBox = new JCheckBox("R-P");
        final JCheckBox r_cBox = new JCheckBox("RI_C");
        final JTextField epsilonField = new JTextField(3);
        epsilonField.setEnabled(false);
        final JTextField tempField = new JTextField(3);
        tempField.setEnabled(false);
        final JTextField alphaField = new JTextField(3);
        alphaField.setEnabled(false);
        final JTextField alpha2Field = new JTextField(3);
        alpha2Field.setEnabled(false);
        final JTextField alpha3Field = new JTextField(3);
        alpha3Field.setEnabled(false);

        panel5.add(l5);
        panel5.add(greedyBox);
        panel5.add(e_greedyBox);
        panel5.add(epsilonField);
        panel5.add(softMaxBox);
        panel5.add(tempField);
        panel5.add(r_iBox);
        panel5.add(alphaField);
        panel5.add(r_pBox);
        panel5.add(alpha2Field);
        panel5.add(r_cBox);
        panel5.add(alpha3Field);
        frame.add(panel5);
        //panel6 contain button for average reward or optimal action statistics
        JPanel panel6 = new JPanel();
        final JButton submitBtn = new JButton("AverageReward");
        final JButton optBtn = new JButton("OptimalActPercent");
        panel6.add(submitBtn);
        panel6.add(optBtn);

        frame.add(panel6);
        frame.setVisible(true);

        //button action listeners
        e_greedyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e_greedyBox.isSelected())
                    epsilonField.setEnabled(true);
                else
                    epsilonField.setEnabled(false);

            }
        });
        softMaxBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (softMaxBox.isSelected())
                    tempField.setEnabled(true);
                else
                    tempField.setEnabled(false);
            }
        });
        r_iBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (r_iBox.isSelected())
                    alphaField.setEnabled(true);
                else
                    alphaField.setEnabled(false);

            }
        });
        r_pBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (r_pBox.isSelected())
                    alpha2Field.setEnabled(true);
                else
                    alpha2Field.setEnabled(false);

            }
        });
        r_cBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (r_cBox.isSelected())
                    alpha3Field.setEnabled(true);
                else
                    alpha3Field.setEnabled(false);

            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tf1.getText().isEmpty()) {
                    int size = Integer.parseInt(tf1.getText());
                    values = new float[size];
                    manualValue(size, values);
                }
            }
        });

        check1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tf1.getText().isEmpty()) {
                    int size = Integer.parseInt(tf1.getText());
                    values = new float[size];
                    uniformValue(values);
                    System.out.println("max value="+max(values));
                }
            }
        });

        check2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tf1.getText().isEmpty()) {
                    int size = Integer.parseInt(tf1.getText());
                    values = new float[size];
                    normalValue(values);
                    System.out.println("max value="+max(values));

                }
            }
        });

        check3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO call poissonValue() func after completing
                if (!tf1.getText().isEmpty()) {
                    int size = Integer.parseInt(tf1.getText());
                    values = new float[size];
                    poissonValue(values);
                }
            }
        });


        submitBtn.addActionListener(new ActionListener() {

            //TODO add other variables
            @Override
            public void actionPerformed(ActionEvent e) {
                N = Integer.parseInt(tf1.getText());
                plays = Integer.parseInt(tf2.getText());
                repeats = Integer.parseInt(tf3.getText());
//                algorithm = combo1.getSelectedItem().toString();
                if (greedyBox.isSelected()){
                    algoList.add("Greedy");
                }
                if (!epsilonField.getText().isEmpty() && e_greedyBox.isSelected()){
                    epsilon = Float.parseFloat(epsilonField.getText());
                    algoList.add("Epsilon-Greedy");
                }
                if (!tempField.getText().isEmpty() && softMaxBox.isSelected()){
                    temp = Float.parseFloat(tempField.getText());
                    algoList.add("SoftMax");
                }
                if (!alphaField.getText().isEmpty() && r_iBox.isSelected()){
                    alpha1 = Float.parseFloat(alphaField.getText());
                    algoList.add("Linear-Automata(Reward-Inaction)");
                }
                if (!alpha2Field.getText().isEmpty() && r_pBox.isSelected()){
                    alpha2 = Float.parseFloat(alpha2Field.getText());
                    algoList.add("Linear-Automata(Reward-Penalty)");
                }
                if (!alpha3Field.getText().isEmpty() && r_cBox.isSelected()){
                    alpha3 = Float.parseFloat(alpha3Field.getText());
                    algoList.add("Reinforcement-comparison");
                }
                allRewards = new float[plays][repeats];
                allOptPercents = new float[plays][repeats];
                averagePercent = new float[plays];
                AVG = new float[plays];
                AVGList = new float[algoList.size()][plays];
                algos = new String[algoList.size()];
                ListIterator<String> lt = algoList.listIterator();
                int count = 0;
                String alg;
                while (lt.hasNext()) {
                    alg = lt.next();
                    for (int i = 0; i < repeats; i++) {
                        MultiArmed mab = new MultiArmed(N, values, plays,alg, epsilon, temp, alpha1,alpha2,alpha3);
                        mab.start();
                        for (int j = 0; j < mab.averageRewards.length; j++) {
                            allRewards[j][i] = mab.averageRewards[j];
                        }
                        for (int k = 0; k < mab.selectedActionPercent.length; k++) {
                            allOptPercents[k][i] = mab.selectedActionPercent[k];
                        }

                    }
                    for (int i = 0; i < plays; i++) {
                        AVG[i] = average(allRewards[i]);
                        averagePercent[i] = average(allOptPercents[i]);
                    }

                    for (int i=0;i<plays;i++){
                        AVGList[count][i] = AVG[i];
                    }
                    algos[count] = alg;
                    count++;
                }

                XYLineChart_AWT chart = new XYLineChart_AWT("Multi armed bandit learning",
                        "Average reward of each play ",AVGList,algos,"Average Reward","Play");
                chart.pack( );
                RefineryUtilities.centerFrameOnScreen(chart);
                chart.setVisible( true );


            }


        });

        optBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                N = Integer.parseInt(tf1.getText());
                plays = Integer.parseInt(tf2.getText());
                repeats = Integer.parseInt(tf3.getText());
//                algorithm = combo1.getSelectedItem().toString();
                if (greedyBox.isSelected()){
                    algoList.add("Greedy");
                }
                if (!epsilonField.getText().isEmpty() && e_greedyBox.isSelected()){
                    epsilon = Float.parseFloat(epsilonField.getText());
                    algoList.add("Epsilon-Greedy");
                }
                if (!tempField.getText().isEmpty() && softMaxBox.isSelected()){
                    temp = Float.parseFloat(tempField.getText());
                    algoList.add("SoftMax");
                }
                if (!alphaField.getText().isEmpty() && r_iBox.isSelected()){
                    alpha1 = Float.parseFloat(alphaField.getText());
                    algoList.add("Linear-Automata(Reward-Inaction)");
                }
                if (!alpha2Field.getText().isEmpty() && r_pBox.isSelected()){
                    alpha2 = Float.parseFloat(alpha2Field.getText());
                    algoList.add("Linear-Automata(Reward-Penalty)");
                }
                if (!alpha3Field.getText().isEmpty() && r_cBox.isSelected()){
                    alpha3 = Float.parseFloat(alpha3Field.getText());
                    algoList.add("Reinforcement-comparison");
                }

                allRewards = new float[plays][repeats];
                allOptPercents = new float[plays][repeats];
                averagePercent = new float[plays];
                AVG = new float[plays];
                AVGList = new float[algoList.size()][plays];
                algos = new String[algoList.size()];
                ListIterator<String> lt = algoList.listIterator();
                int count = 0;
                String alg;
                while (lt.hasNext()) {
                    alg = lt.next();
                    for (int i = 0; i < repeats; i++) {
                        MultiArmed mab = new MultiArmed(N, values, plays,alg, epsilon, temp, alpha1,alpha2,alpha3);
                        mab.start();
                        for (int j = 0; j < mab.averageRewards.length; j++) {
                            allRewards[j][i] = mab.averageRewards[j];
                        }
                        for (int k = 0; k < mab.selectedActionPercent.length; k++) {
                            allOptPercents[k][i] = mab.selectedActionPercent[k];
                        }

                    }
                    for (int i = 0; i < plays; i++) {
                        AVG[i] = average(allRewards[i]);
                        averagePercent[i] = average(allOptPercents[i]);
                    }

                    for (int i=0;i<plays;i++){
                        AVGList[count][i] = averagePercent[i];
                    }
                    algos[count] = alg;
                    count++;
                }

                XYLineChart_AWT chart = new XYLineChart_AWT("Multi armed bandit learning",
                        "Optimal action % of each play ",AVGList,algos,"Optimal action %","Play");
                chart.pack( );
                RefineryUtilities.centerFrameOnScreen(chart);
                chart.setVisible( true );
            }
        });


    }

    public void print(){
        for (float value : values) {
            System.out.println("this value is:");
            System.out.println(value);
        }
        System.out.println("------------------------");

    }

    public void manualValue(final int n, final float[] values){
        JPanel[] panels = new JPanel[n];
        JLabel[] labels = new JLabel[n];
        final JTextField[] textFields = new JTextField[n];
        final JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(n,1));

        for (int i=0;i<n;i++){
            panels[i] = new JPanel();
            panels[i].setLayout(new FlowLayout(FlowLayout.LEFT));
            labels[i] = new JLabel("value of "+Integer.toString(i+1)+" = ");
            textFields[i]= new JTextField(5);
            panels[i].add(labels[i]);
            panels[i].add(textFields[i]);
            frame.add(panels[i]);

        }
        JButton btn = new JButton("submit");
        JPanel btnPanel = new JPanel();
        btnPanel.add(btn);

        frame.add(btnPanel);
        frame.setSize(400, n * 40);
        frame.setLocation(600,500);
        frame.setVisible(true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i=0;i<n;i++){
                    if (!textFields[i].getText().isEmpty()) {
                        values[i] = Float.parseFloat(textFields[i].getText());
                    }
                    else {
                        values[i] = 0;
                    }
                }
                frame.dispose();
                print();
                System.out.println("max value="+max(values));

            }
        });
    }

    public void uniformValue(float[] values){
        Random r = new Random();
        for (int i=0;i<values.length;i++){
            values[i] = r.nextFloat();
        }
        print();
    }

    public void normalValue(float[] values){
        Random r = new Random();
        for(int i=0; i<values.length;i++){
            values[i] = (float) (r.nextGaussian()*(.5/3)+.5);
        }
        print();
    }

    public void poissonValue(float[] values){
        //TODO complete poisson function

        float lambda = values.length/2;
        for (int i=0;i<values.length;i++){
            int fact = 1;
            for (int j=0;j<i+1;j++)
                fact *= (j+1);
            values[i] = (float)(( Math.exp(-lambda)*Math.pow(lambda,i))/fact);
        }
        print();
    }

    public float average(float[] list){
        float sum =0;
        float avg = 0;
        for (int i=0;i<list.length;i++){
            sum+=list[i];
        }
        avg = sum/list.length;
        return avg;
    }

    public float max(float[] values){
        float max = 0;
        for (int i=0;i<values.length;i++){
            if (values[i]>max)
                max = values[i];
        }
        return max;
    }
}
