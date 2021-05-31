package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        saveFile(allStudents());
    }

    public static LinkedHashMap<String,Float> inpuData(){
        LinkedHashMap<String, Float> studentData = new LinkedHashMap<>();
        Scanner sc = new Scanner(System.in);
        String name;
        float grade;
        String gradeErrorMessage = "The grade must be a NUMBER between 0 to 10";
        System.out.println("Type the student's name");
        name = sc.nextLine();
        System.out.println("Type the grade for " + name);
        while (!sc.hasNextFloat()) {
            System.out.println(gradeErrorMessage);
            sc.next();
        }
        grade = sc.nextFloat();
        while(!validateGrade(grade)){
            System.out.println(gradeErrorMessage);
            grade = sc.nextFloat();
        }
        studentData.put(name,grade);
        return studentData;
    }

    public static LinkedHashMap<String, Float> allStudents(){
        LinkedHashMap <String,Float> grades = new LinkedHashMap<>();
        for (int i = 0; i < 3; i++){
            grades.putAll(inpuData());
        }
        grades.putAll(stats(grades));
        return grades;
    }

    public static boolean validateGrade(Float grade){
        return grade >= 0.0 && grade <= 10.0;
    }
    
    public static LinkedHashMap<String,Float> stats(LinkedHashMap<String, Float> studentData){
        LinkedHashMap<String,Float> stats = new LinkedHashMap<>();

        float sum = 0;
        float average;
        Collection<Float> values = studentData.values();
        List<Float> vals = new ArrayList(values);
        int len = vals.size();
        float min = Collections.min(studentData.values());
        float max = Collections.max(studentData.values());
        for(float val: studentData.values()){
            sum += val;
        }
        average = sum / studentData.size();
        stats.put("Minimo", min);
        stats.put("Maximo", max);
        stats.put("Promedio", average);
        stats.put("Mas repetido",mostRepeated(vals.toArray(new Float[0]), len));
        stats.put("Menos repetido", leastFrequent(vals.toArray(new Float[0]), len));
        return stats;
    }

    public static float mostRepeated(Float[] vals, int len){
        LinkedHashMap<Float, Integer> mostMap = new LinkedHashMap<>();
        for(int i = 0; i < len; i++){
            Float key = vals[i];
            if(mostMap.containsKey(key)){
                int freq = mostMap.get(key);
                freq++;
                mostMap.put(key, freq);
            }
            else{
                mostMap.put(key, 1);
            }
        }
        int max_count = 0;
        float res = -1;
        for(Map.Entry<Float, Integer> val : mostMap.entrySet()){
            if (max_count < val.getValue()){
                res = val.getKey();
                max_count = val.getValue();
            }
        }
        return res;
    }

    static float leastFrequent(Float[] arr, int n){

        Map<Float,Integer> count = new HashMap<>();

        for(int i = 0; i < n; i++){
            float key = arr[i];
            if(count.containsKey(key)){
                int freq = count.get(key);
                freq++;
                count.put(key,freq);
            }
            else
                count.put(key,1);
        }

        int min_count = n+1;
        float res = -1;
        for(Map.Entry<Float,Integer> val : count.entrySet()){
            if (min_count >= val.getValue()){
                res = val.getKey();
                min_count = val.getValue();
            }
        }

        return res;
    }


    public static void saveFile(LinkedHashMap<String, Float> studentData){
        File file = new File("notas.txt");
        BufferedWriter bf = null;
        try {
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, Float> entry : studentData.entrySet()) {
                bf.write(entry.getKey() + ":" + entry.getValue());
                bf.newLine();
            }
            bf.newLine();
            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert bf != null;
                bf.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(studentData);
    }
}
