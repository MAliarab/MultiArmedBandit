package com.company;

import java.util.Random;

public class MultiArmed {
    float[] probList;
    float[] rewardBar;
    int n;
    float[] values;
    int plays;
    String algorithm;
    int action;
    float[] rewards;
    float epsilon;
    float temp;
    float alpha;
    int[] actionCount;
    float[] actionValues;
    float[] averageRewards;
    int c0=0,c1=0,c2=0;
    float maxVal=0;
    int [] selectedAction;
    float [] selectedActionPercent;
    int maxIndex;
//    float[] softValues;

    public MultiArmed(){

    }
    public MultiArmed(int n,float[] values,int plays,String algorithm,float epsilon,float temp,float alpha){
        this.n=n;
        this.values = values;
        this.plays = plays;

        this.algorithm = algorithm;


        this.actionCount = new int[n];
        this.actionValues = new float[n];
        this.rewardBar = new float[n];
        this.probList = new float[n];
        this.selectedActionPercent = new float[plays];

        for (int i=0;i<n;i++){
            this.actionValues[i]=0.001f;
            this.actionCount[i]=0;
            this.probList[i]=0;
            this.rewardBar[i]=0;
        }
        this.epsilon = epsilon;
        this.temp = temp;
        this.alpha = alpha;
        this.averageRewards = new float[plays];
        this.selectedAction = new int[plays];
        for (int i=0;i<plays;i++) {
            averageRewards[i] = 0;
            selectedAction[i]=0;
            selectedActionPercent[i]=0;
        }
        maxVal = max(values);
        maxIndex= argmax(values);
//        softValues = new float[actionValues.length];
//        System.out.println("max index="+maxIndex);
    }

    public float[] start(){

        for (int i=0;i<plays;i++) {

            rewards = generateRewards(values);

            if (algorithm.equals("Greedy")) {
                action = greedy();
                update(i);
            }
            else if (algorithm.equals("Epsilon-Greedy")){
                action = epsilonGreedy(epsilon);
                update(i);
            }
            else if (algorithm.equals("SoftMax")){
                action = softMax(temp);
                update(i);
            }
            else if (algorithm.equals("Linear-Automata(Reward-Inaction)")){
                action = reward_Inaction(alpha);
                update(i);
            }
            else if (algorithm.equals("Linear-Automata(Reward-Penalty)")){
                action = reward_Penalty(alpha);
                update(i);
            }
            else if (algorithm.equals("Reinforcement-comparison")){
                action = RIComparison(alpha);
                update(i);
//                System.out.println("this is alpha="+alpha);
            }


            selectedAction[i]=action;
//            System.out.println("action: "+action);
        }
        calOptPercent();
//        for (int k=0;k<selectedActionPercent.length;k++)
//            System.out.println("percent: "+k+"="+selectedActionPercent[k]);
        return averageRewards;
    }

    private void calOptPercent() {
        int optCount = 0;
        for (int i=0;i<selectedAction.length;i++){
            if (selectedAction[i]==maxIndex)
                optCount++;
            selectedActionPercent[i]=(float)optCount/(float)(i+1);
//            System.out.println("this is percent"+selectedActionPercent[i]);
        }
    }

    private int greedy() {
        int act = argmax(actionValues);
        Random r = new Random();
        if (act== -1)
            return r.nextInt(n);
        else
            return act;
    }

    private int epsilonGreedy(float epsilon){
        Random r = new Random();
        float random = r.nextFloat();
        if (random<=epsilon){
            int i =0;
            i = r.nextInt(values.length);
            while (i==argmax(actionValues)){
                i = r.nextInt(values.length);
            }
            return i;
        }
        else {
            return argmax(actionValues);
        }
    }

    public int softMax(float temp){
        float[] softValues = new float[actionValues.length];
        for (int i=0;i<actionValues.length;i++){
            float sum=0;
            float a = (float) Math.exp(actionValues[i]/temp);
            for (int j=0;j<actionValues.length;j++){
                if (j!=i)
                    sum += (float) Math.exp(actionValues[j]/temp);
            }
            softValues[i] = a/sum;
//            System.out.println(i+" soft = "+softValues[i]);
        }
        return weightedRand(softValues);
    }

    public int reward_Inaction(float alpha){

        int act = weightedRand(actionValues);
//        System.out.println("selected act="+act);
        System.out.println("actValPrev:"+act+"="+actionValues[act]);
        int result = interact(act);
        if (result==1){
            actionValues[act]=actionValues[act]+alpha*(1-actionValues[act]);
            for (int i=0;i<actionValues.length;i++){
                if (i != act)
                    actionValues[i] = (1-alpha)*actionValues[i];
            }
        }
        System.out.println("acValAft:"+act+"="+actionValues[act]);
        return act;
    }

    public int reward_Penalty(float alpha){

        int act = weightedRand(actionValues);
//        System.out.println("selected act="+act);
        int result = interact(act);
        if (result==1){
            actionValues[act]=actionValues[act]+alpha*(1-actionValues[act]);
            for (int i=0;i<actionValues.length;i++){
                if (i != act)
                    actionValues[i] = (1-alpha)*actionValues[i];
            }
        }
        else {
            actionValues[act]=(1-alpha)*actionValues[act];
        }
        return act;
    }

    public int RIComparison(float alpha){
        int act = weightedRand(actionValues);
//        System.out.println("act="+act);
        probList[act]=probList[act]+(rewards[act]-rewardBar[act]);
        rewardBar[act]=rewardBar[act]+alpha*(rewards[act]-rewardBar[act]);
        float sum=0;
        float a = (float) Math.exp(probList[act]);
        for (int j=0;j<probList.length;j++){
            if (j!=act)
                sum += (float) Math.exp(probList[j]);
        }
        actionValues[act] = a/sum;
//        System.out.println("actVal="+actionValues[act]);
        return act;
    }

    private int interact(int act) {
        Random r = new Random();
        float probReward = rewards[act];
        float randNum = r.nextFloat()*maxVal;
//        System.out.println("ranNum="+randNum+" probRew="+probReward);
        if (randNum<=probReward){
            return 1;
        }
        else
            return 0;
    }

    public int argmax(float[] array){

        Random r = new Random();
        int best = -1;
        float best_confidence = 0.0f;

        for(int i = 0;i < array.length;i++){
            float value = array[i];

            if (value > best_confidence){

                best_confidence = value;
                best = i;
            }
        }
        if (best==-1) {
            best = r.nextInt(array.length);
            return best;
        }
        else {
            return best;
        }
    }
    public void update(int play){
//        System.out.println("action:"+action+"play: "+play);
        averageRewards[play] = rewards[action];

        actionCount[action]+=1;
        float n = actionCount[action];
        float oldValue = actionValues[action];
        actionValues[action]=(((n-1)/n)*oldValue)+ ((1/n)*rewards[action]);
    }
    public float[] generateRewards(float[] values){
        float[] rewards = new float[values.length];
        Random r = new Random();
        for (int i=0;i<values.length;i++){

            rewards[i] = r.nextFloat()*values[i];
        }

        return rewards;
    }

    public static int weightedRand(float []items){
        double totalWeight = 0.0d;
        Random r = new Random();
        for (float i : items)
        {
            totalWeight += i;
        }
        int randomIndex = r.nextInt(items.length);
        double random = Math.random() * totalWeight;
        for (int i = 0; i < items.length; ++i)
        {
            random -= items[i];
            if (random <= 0.0d)
            {
                randomIndex = i;
                break;
            }
        }

        return randomIndex;
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
