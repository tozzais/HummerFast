package com.tozzais.baselibrary.util;


import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    public static List<String> getData(int size){
        List<String> data = new ArrayList<>();
        for (int i=0;i<size;i++){
            data.add("");
        }
        return data;
    }






}
