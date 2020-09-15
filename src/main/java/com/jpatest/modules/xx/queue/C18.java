package com.jpatest.modules.xx.queue;

public class C18 extends Thread {
    
    public synchronized void run() {
        
        System.out.println("run()1");
        try {
            Thread.sleep(5000);
        } catch (Exception a) {
            System.out.println("cwu");
        }
        System.out.println("run()2");
    }

    public synchronized void A() {
        
        System.out.println("A");
        try {
            Thread.sleep(1000);
        } catch (Exception a4) {
            System.out.println("cwu");
        }
        System.out.println("A");
    }

    public static void main(String[] args) {
        C18 a = new C18();
        a.start();
        a.A();
    }

}