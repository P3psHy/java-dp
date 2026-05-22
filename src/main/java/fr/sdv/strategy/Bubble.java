package fr.sdv.strategy;

public class Bubble implements IStrategy {

    @Override
    public void trier(Integer[] int_array) {
        int n = int_array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (int_array[j] > int_array[j + 1]) {
                    int temp = int_array[j];
                    int_array[j] = int_array[j + 1];
                    int_array[j + 1] = temp;
                }
            }
        }
    }
    
}
