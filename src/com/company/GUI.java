package com.company;

import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI {
    float[] values;
    int N;
    int plays;
    int repeats;
    float epsilon;
    float temp;
    float alpha;
    String algorithm;
    float[][] allRewards;
    float[][] allOptPercents;
    float [] averagePercent;
    float[] AVG;

    public GUI(){
        JFrame frame =  new JFrame();
        frame.setLayout(new GridLayout(7,1));
//        JPanel mainPanel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setTitle("Multi-Armed-Bandit");
        frame.setLocation(500, 500);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l1 = new JLabel("N = ");
        panel1.add(l1);
        final JTextField tf1 = new JTextField(10);
        panel1.add(tf1);
        frame.add(panel1);

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


        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l3 = new JLabel("Plays = ");
        final JTextField tf2 = new JTextField(10);
        panel3.add(l3);
        panel3.add(tf2);
        frame.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l4 = new JLabel("Repeats = ");
        final JTextField tf3 = new JTextField(10);
        panel4.add(l4);
        panel4.add(tf3);
        frame.add(panel4);

        JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l5 = new JLabel("Select Algorithm: ");
        final String [] algorithms = {"Greedy","Epsilon-Greedy","SoftMax","Linear-Automata(Reward-Inaction)","Linear-Automata(Reward-Penalty)","Reinforcement-comparison"};
        final JComboBox<String> combo1 = new JComboBox<String>(algorithms);
        final JLabel l6 = new JLabel("Epsilon: ");
        final JTextField tf4 = new JTextField(3);
        tf4.setEnabled(false);
        panel5.add(l5);
        panel5.add(combo1);
        panel5.add(l6);
        panel5.add(tf4);
        frame.add(panel5);

        JPanel panel6 = new JPanel();
        final JButton submitBtn = new JButton("AverageReward");
        final JButton optBtn = new JButton("OptimalActPercent");
        panel6.add(submitBtn);
        panel6.add(optBtn);

        frame.add(panel6);
        frame.setVisible(true);

        //button action listeners
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tf1.getText().isEmpty()) {
                    int size = Integer.parseInt(tf1.getText());
                    values = new float[size];
                    manualValue(size, values);

//                for (int i=0;i<values.length;i++){
//                    System.out.println(values[i]);
//                }
                }
            }
        });

        check1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO call function fo uniform
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

        combo1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (combo1.getSelectedItem().toString().equals("Epsilon-Greedy")){
                    tf4.setEnabled(true);
                    l6.setText("Epsilon:");

                }
                else if (combo1.getSelectedItem().toString().equals("SoftMax")){
                    tf4.setEnabled(true);
                    l6.setText("Temp:");
                }else if (combo1.getSelectedItem().toString().equals("Linear-Automata(Reward-Inaction)")){
                    tf4.setEnabled(true);
                    l6.setText("Alpha:");
                }else if (combo1.getSelectedItem().toString().equals("Linear-Automata(Reward-Penalty)")){
                    tf4.setEnabled(true);
                    l6.setText("Alpha:");
                }else if (combo1.getSelectedItem().toString().equals("Reinforcement-comparison")){
                    tf4.setEnabled(true);
                    l6.setText("Alpha:");
                }
                else
                {
                    tf4.setEnabled(false);
                    epsilon = 0;

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
                algorithm = combo1.getSelectedItem().toString();
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("Epsilon-Greedy"))
                    epsilon = Float.parseFloat(tf4.getText());
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("SoftMax"))
                    temp = Float.parseFloat(tf4.getText());
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("Linear-Automata(Reward-Inaction)"))
                    alpha = Float.parseFloat(tf4.getText());
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("Linear-Automata(Reward-Penalty)"))
                    alpha = Float.parseFloat(tf4.getText());
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("Reinforcement-comparison"))
                    alpha = Float.parseFloat(tf4.getText());


                allRewards = new float[plays][repeats];
                allOptPercents = new float[plays][repeats];
                averagePercent = new float[plays];
                AVG = new float[plays];


//                for (int i=0;i<values.length;i++)
//                    System.out.println(values[i]);
//                System.out.println("information:\n N:"+N+"values\n"+values+"plays\n"+plays+algorithm+"\n"+epsilon);
//                mab.start();
//                System.out.println("##########this is average rewards:##############");
//                for (int i=0;i<mab.averageRewards.length;i++){
//                    System.out.println("["+i+"]"+mab.averageRewards[i]);
//
//                }
//                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//                for (int i=0;i<N;i++){
//                    System.out.println(i+"= "+mab.actionCount[i]);
//                }
//
                for (int i=0;i<repeats;i++){
                    MultiArmed mab = new MultiArmed(N,values,plays,algorithm,epsilon,temp,alpha);
                    mab.start();
                    for (int j=0;j<mab.averageRewards.length;j++){
                        allRewards[j][i] = mab.averageRewards[j];
                    }
                    for (int k=0;k<mab.selectedActionPercent.length;k++){
                        allOptPercents[k][i] = mab.selectedActionPercent[k];
                    }

                }
                for (int i=0;i<plays;i++){
                    AVG[i] = average(allRewards[i]);
                    averagePercent[i]=average(allOptPercents[i]);
                }

                XYLineChart_AWT chart = new XYLineChart_AWT("Multi armed bandit learning",
                        "Average reward of each play ",AVG,algorithm,"Average Reward","Play");
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
                algorithm = combo1.getSelectedItem().toString();
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("Epsilon-Greedy"))
                    epsilon = Float.parseFloat(tf4.getText());
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("SoftMax"))
                    temp = Float.parseFloat(tf4.getText());
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("Linear-Automata(Reward-Inaction)"))
                    alpha = Float.parseFloat(tf4.getText());
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("Linear-Automata(Reward-Penalty)"))
                    alpha = Float.parseFloat(tf4.getText());
                if (!tf4.getText().isEmpty() && combo1.getSelectedItem().toString().equals("Reinforcement-comparison"))
                    alpha = Float.parseFloat(tf4.getText());


                allRewards = new float[plays][repeats];
                allOptPercents = new float[plays][repeats];
                averagePercent = new float[plays];
                AVG = new float[plays];


//                for (int i=0;i<values.length;i++)
//                    System.out.println(values[i]);
//                System.out.println("information:\n N:"+N+"values\n"+values+"plays\n"+plays+algorithm+"\n"+epsilon);
//                mab.start();
//                System.out.println("##########this is average rewards:##############");
//                for (int i=0;i<mab.averageRewards.length;i++){
//                    System.out.println("["+i+"]"+mab.averageRewards[i]);
//
//                }
//                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//                for (int i=0;i<N;i++){
//                    System.out.println(i+"= "+mab.actionCount[i]);
//                }
//
                for (int i=0;i<repeats;i++){
                    MultiArmed mab = new MultiArmed(N,values,plays,algorithm,epsilon,temp,alpha);
                    mab.start();
                    for (int j=0;j<mab.averageRewards.length;j++){
                        allRewards[j][i] = mab.averageRewards[j];
                    }
                    for (int k=0;k<mab.selectedActionPercent.length;k++){
                        allOptPercents[k][i] = mab.selectedActionPercent[k];
                    }

                }
                for (int i=0;i<plays;i++){
                    AVG[i] = average(allRewards[i]);
                    averagePercent[i]=average(allOptPercents[i]);
                }

                XYLineChart_AWT chart = new XYLineChart_AWT("Multi armed bandit learning",
                        "Optimal arm selection percentage ",averagePercent,algorithm,"Optimal Percent%","Play");
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
//        final float[] values = new float[n];
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
//                        System.out.println("this is "+Float.parseFloat(textFields[i].getText()));
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
//        return values;
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
            System.out.println("fact = "+fact);
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
