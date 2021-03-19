package models;

import java.util.Random;

public class RandomInt {

    private int max = 0;
    private int min = 1;
    private Random generator = new Random();

    public RandomInt(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int generate() {
        return generator.nextInt(max - min) + min;
    }
}
