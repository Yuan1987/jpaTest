package com.jpatest;

/**
 * 单向链表
 * 
 * @author yuan
 */
public class Queue {

    public Node head;

    public Node tail;

    public Node take() {
        
        Node t = head;
        if (t != null) {
            head = t.next;
        }
        return t;
    }

    public void add(Node node) {
        if(head == null) {
            head = node;
        }else {
            tail.next = node;
        }
        tail = node;
    }
    
    public Node pop() {
        
        return head;
    }
    
    public void display() {
        
        Node t = head;
        while(t != null) {
            System.out.println(t.data);
            t = t.next;
        }
    }
    
    public static void main(String [] a) {
        
        Queue q = new Queue();
        q.add(new Node(1));
        q.add(new Node(2));
        q.add(new Node(3));
        
        q.display();
        
        System.out.println("-------------");
        
        q.add(new Node(4));
        
        Node take = q.take();
        
        System.out.println( take == null ? "" : take.data);
        
        System.out.println("---------------------");
        
        q.display();
        
        q.take();
        
        System.out.println("-------------------");
        
        q.display();
        
        q.take();
        
        q.take();
        
        System.out.println("-------------------");
        
        q.display();
        
    }

}
