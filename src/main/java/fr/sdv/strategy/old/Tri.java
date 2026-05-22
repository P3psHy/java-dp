package fr.sdv.strategy.old;

public class Tri {

    public void exec(int typeTri, Integer[] arr) {

        // Bubble sort algorithm
        if (typeTri == 1) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }
        // insertion sort algorithm
        else if (typeTri == 2) {
            for (int k = 1; k < arr.length - 1; k++) {
                int temp = arr[k];
                int j = k - 1;
                while (j >= 0 && temp <= arr[j]) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                }
                arr[j + 1] = temp;
            }
        } else if (typeTri == 3) {
            for (int i = 0; i < arr.length - 1; i++) {
                int index = i;
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[j] < arr[index]) {
                        index = j;//searching for lowest index
                    }
                }
                int smallerNumber = arr[index];
                arr[index] = arr[i];
                arr[i] = smallerNumber;
            }
        }
    }
}
