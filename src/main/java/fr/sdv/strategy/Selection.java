package fr.sdv.strategy;

public class Selection  implements IStrategy {

    @Override
    public void trier(Integer[] int_array) {
        for (int i = 0; i < int_array.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < int_array.length; j++) {
                if (int_array[j] < int_array[index]) {
                    index = j;//searching for lowest index
                }
            }
            int smallerNumber = int_array[index];
            int_array[index] = int_array[i];
            int_array[i] = smallerNumber;
        }
    }
    
}
