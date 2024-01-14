package ml;

import linalg.Matrix;

import java.util.ArrayList;

public class Stack {
    private ArrayList<Matrix> stack;
    private boolean isActive = true;

    public Stack() {
        this(true);
    }

    public Stack(boolean isActive) {
        setActive(isActive);
        stack = new ArrayList<>();
    }

    public void save(Matrix input) {
        if (!isActive)
            return;
        stack.add(input);
    }

    public Matrix get() {
        if(!isActive)
            return null;
        return stack.remove(stack.size() - 1);
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void clear() {
        stack.clear();
    }

    public void print() {
        System.out.println("Stack:");
        for (int i = 0; i < stack.size(); i++) {
            System.out.println("===== " + (i + 1) + " =====");
            stack.get(i).print();
        }
    }

    public int getLength() {
        return stack.size();
    }
}
