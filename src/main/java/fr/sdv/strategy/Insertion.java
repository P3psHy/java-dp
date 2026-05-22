package fr.sdv.strategy;

public class Insertion implements IStrategy {

    @Override
    public void trier(Integer[] int_array) {
        for (int k = 1; k < int_array.length - 1; k++) {
            int temp = int_array[k];
            int j = k - 1;
            while (j >= 0 && temp <= int_array[j]) {
                int_array[j + 1] = int_array[j];
                j = j - 1;
            }
            int_array[j + 1] = temp;
        }
    }
    
}
