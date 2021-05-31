package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //input Data
        //save as map
        //calculate stats
        //save in file
        saveFile(allStudents());
    }

    //Input de Datos
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

    //Guardar en Hashmap
    public static LinkedHashMap<String, Float> allStudents(){
        LinkedHashMap <String,Float> grades = new LinkedHashMap<>();
        for (int i = 0; i < 3; i++){
            grades.putAll(inpuData());
        }
        grades.putAll(stats(grades));
        return grades;
    }

    //validar notas
    public static boolean validateGrade(Float grade){
        return grade >= 0.0 && grade <= 10.0;
    }

    //calculo de stadisticas
    public static LinkedHashMap<String,Float> stats(LinkedHashMap<String, Float> studentData){
        DecimalFormat df = new DecimalFormat("0.00");
        LinkedHashMap<String,Float> stats = new LinkedHashMap<>();
        float sum = 0;
        float average;
        Collection<Float> values = studentData.values();
        List<Float> vals = new ArrayList<>(values);
        float min = Collections.min(studentData.values());
        float max = Collections.max(studentData.values());
        for(float val: studentData.values()){
            sum += val;
        }
        average = sum / studentData.size();
        stats.put("Minimo", min);
        stats.put("Maximo", max);
        stats.put("Promedio", Float.valueOf(df.format(average)));
        stats.put("Mas repetido",mostRepeated(vals.toArray(new Float[0])));
        stats.put("Menos repetido", leastFrequent(vals.toArray(new Float[0])));
        return stats;
    }

    //valor mas repetido
    public static float mostRepeated(Float[] vals){
        LinkedHashMap<Float, Integer> mostMap = new LinkedHashMap<>();

        for (float keyVal : vals){
            if(mostMap.containsKey(keyVal)){
                int freq = mostMap.get(keyVal);
                freq++;
                mostMap.put(keyVal, freq);
            }
            else{
                mostMap.put(keyVal, 1);
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

    //valor menos repetidos
    static float leastFrequent(Float[] arr){
        Map<Float,Integer> count = new HashMap<>();
        for(float keyVal : arr){
            if(count.containsKey(keyVal)){
                int freq = count.get(keyVal);
                freq++;
                count.put(keyVal,freq);
            }
            else
                count.put(keyVal,1);
        }
        int min_count = arr.length + 1;
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
