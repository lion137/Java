package part02;

import java.util.ArrayList;

/**
 * Created by lion on 26/05/17.
 */
public class UnrolledStack {
    private Node first;
    int length = 0;
    int max_elements;

    public UnrolledStack(int max){
        max_elements = max;
    }


    private class Node{
        ArrayList<Integer> data = new ArrayList<Integer>();
        Node next;
        int non_zero = max_elements;
    }



    public void push(int item){
        if (first != null) {
            if (first.non_zero > 0){
                first.data.add(item);
                first.non_zero -= 1;
            }
            else {
                Node old = first;
                first = new Node();

                first.data.add(item);
                first.non_zero -= 1;
                first.next = old;
            }
        }
        else{
            Node old = first;
            first = new Node();
            first.data.add(item);
            first.non_zero -= 1;
            first.next = old;
            
        }
        length += 1;
    }

    public Object pop(){
        return this.popI();
    }

    private Object popI(){
        if (this.isEmpty()) {return null;}
        else {
            if (first.data.isEmpty()) {
                first = first.next;
                if (first == null) {return null;}
                length -= 1;
                return first.data.remove(first.data.size() - 1);
            } else {
                length -= 1;
                return first.data.remove(first.data.size() - 1);
            }
        }

    }

    public boolean isEmpty(){
        return first == null;
    }

    public int getLength(){
        return length;
    }

    public void printUnrolledStack(){

            Node current;
            current = first;
            while (current != null) {
                System.out.print(current.data);
                current = current.next;
            }
    }

}
