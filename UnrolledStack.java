package part02;

/**
 * Created by lion on 28/05/17.
 */
public class UnrolledStackArray {
    private Node first;
    int length = 0;
    int max_elements;
    int top = 0;
    boolean cnt = false;

    public UnrolledStackArray(int max) {
        max_elements = max;
    }


    private class Node {
        Object[] data = new Object[max_elements];
        Node next;

    }

    public void push(int item) {
        if (first != null) {
            if (top == max_elements) {
                Node old = first;
                first = new Node();
                top = 0;
                first.data[top] = item;
                first.next = old;
                top++;
            } else {
                first.data[top] = item;
                top++;
            }
        } else {
            Node old = first;
            first = new Node();
            top = 0;
            first.data[top] = item;
            first.next = old;
            top++;
        }
        length += 1;
        cnt = false;
    }

    public Object pop() {
        return this.popI();
    }

    private Object popI() {
        if (this.isEmpty()) {
            return null;
        } else {
            if (top >= max_elements) {
                if (first == null) {
                    return null;
                }
                length -= 1;
                top = max_elements - 1;
                Object tmp = first.data[top];
                first.data[top] = null;
                top--;
                return tmp;
            } else {
                if (top == -1) {
                    first = first.next;
                    if (first == null) {
                        return null;
                    }
                    length -= 1;
                    top = max_elements - 1;
                    Object tmp = first.data[top];
                    first.data[top] = null;
                    top--;
                    return tmp;
                }
                if (first.data[top] == null) {
                    top--;
                }
                length -= 1;
                Object tmp = first.data[top];
                first.data[top] = null;
                top--;
                return tmp;

            }
        }

    }

    public boolean isEmpty() {
        return length == 0;
    }

    public int getLength() {
        return length;
    }
}
