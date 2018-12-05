package com.agilean.kata;

public class StaticMethodClass {
    private static StaticMethodClass instance;

    public static StaticMethodClass getInstance(){
        if(instance == null){
            instance = new StaticMethodClass();
        }
        throw new RuntimeException("getinstance exception");
    }

    public static String getMessage(){
        return "real method message";
    }

    public void doSth(){
        throw new RuntimeException("doSth");
    }

    public String returnSth(){
        throw new RuntimeException("returnSth");
    }
}
