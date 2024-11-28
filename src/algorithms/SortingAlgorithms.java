package algorithms;

import java.util.Random;

public class SortingAlgorithms {
    private static Random rand = new Random();

    // Insertion Sort
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // Selection Sort
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIdx];
            arr[minIdx] = temp;
        }
    }

    // Bubble Sort
    public static void bubbleSort(int[] arr) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);
    }

    // Quick Sort
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // Merge Sort
    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) return;
        int mid = arr.length / 2;

        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];

        System.arraycopy(arr, 0, left, 0, mid);
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        while (i < left.length) {
            arr[k++] = left[i++];
        }

        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }

    // Bogo Sort
    public static void bogoSort(int[] arr) {
        while (!isSorted(arr)) {
            for (int i = 0; i < arr.length; i++) {
                int randomIndex = rand.nextInt(arr.length);
                int temp = arr[i];
                arr[i] = arr[randomIndex];
                arr[randomIndex] = temp;
            }
        }
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) return false;
        }
        return true;
    }

    // Testing utilities
    private static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10000);
        }
        return arr;
    }

    private static void testSort(String sortName, int size, int trials, SortingFunction sortFunc) {
        double totalTime = 0;
        
        System.out.printf("Testing %s with array size %d (%d trials)%n", sortName, size, trials);
        
        for (int t = 0; t < trials; t++) {
            int[] arr = generateRandomArray(size);
            long startTime = System.nanoTime();
            sortFunc.sort(arr);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime) / 1e6; // Convert to milliseconds
            
            if (!isSorted(arr)) {
                System.out.println("Error: Array not sorted correctly!");
                return;
            }
        }
        
        System.out.printf("Average time: %.2f ms%n%n", totalTime / trials);
    }

    @FunctionalInterface
    interface SortingFunction {
        void sort(int[] arr);
    }

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000};  // CHANGE THIS FOR TEST CASES
        int trials = 5;

        for (int size : sizes) {
            testSort("Insertion Sort", size, trials, SortingAlgorithms::insertionSort);
            testSort("Selection Sort", size, trials, SortingAlgorithms::selectionSort);
            testSort("Bubble Sort", size, trials, SortingAlgorithms::bubbleSort);
            testSort("Quick Sort", size, trials, SortingAlgorithms::quickSort);
            testSort("Merge Sort", size, trials, SortingAlgorithms::mergeSort);
            
            // Only test Bogo Sort with very small arrays
            if (size <= 1000) {
                testSort("Bogo Sort", 10, 1, SortingAlgorithms::bogoSort);
            }
            
            System.out.println("-".repeat(80));
        }
    }
}