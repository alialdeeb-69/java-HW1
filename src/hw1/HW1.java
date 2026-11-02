package hw1;

import java.util.Random;

public class HW1 {

    static final int N = 100000;

   

    // عشوائية تماماً
    static int[] random(Random r) {
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = r.nextInt(1000000);
        }
        return a;
    }

   
    static int[] sorted(Random r) {
        int[] a = new int[N];
        a[0] = r.nextInt(10);
        for (int i = 1; i < N; i++) {
            a[i] = a[i - 1] + r.nextInt(10);
        }
        return a;
    }

  
    static int[] reversed(Random r) {
        int[] a = sorted(r);
        for (int i = 0; i < N / 2; i++) {
            int t = a[i];
            a[i] = a[N - 1 - i];
            a[N - 1 - i] = t;
        }
        return a;
    }

   
    static int[] partial(Random r) {
        int[] a = sorted(r);
        int max = a[N - 1] + 1;
        for (int i = 0; i < N / 5; i++) {
            a[r.nextInt(N)] = r.nextInt(max);
        }
        return a;
    }

  

    static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            int t = a[i];
            a[i] = a[min];
            a[min] = t;
        }
    }

    static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j;
            for (j = i - 1; j >= 0 && a[j] > key; j--) {
                a[j + 1] = a[j];
            }
            a[j + 1] = key;
        }
    }

    static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    static void mergeSort(int[] a, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(a, temp, left, mid);
        mergeSort(a, temp, mid + 1, right);

        
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                temp[k] = a[j++];
            } else if (j > right) {
                temp[k] = a[i++];
            } else if (a[i] <= a[j]) {
                temp[k] = a[i++];
            } else {
                temp[k] = a[j++];
            }
        }
        for (int k = left; k <= right; k++) {
            a[k] = temp[k];
        }
    }

    static void quickSort(int[] a, int low, int high) {
        if (low >= high) {
            return;
        }
       
        int mid = (low + high) / 2;
        int t = a[mid];
        a[mid] = a[high];
        a[high] = t;
        int pivot = a[high];

        int p = low;
        for (int i = low; i < high; i++) {
            if (a[i] < pivot) {
                t = a[i];
                a[i] = a[p];
                a[p] = t;
                p++;
            }
        }
        t = a[p];
        a[p] = a[high];
        a[high] = t;

        quickSort(a, low, p - 1);
        quickSort(a, p + 1, high);
    }

   

   
    static long toMicro(long nano) {
        return nano / 1000;
    }

    static long measure(int algo, int[] original) {
        int[] a = original.clone(); 

        long start = System.nanoTime();
        if (algo == 0) {
            selectionSort(a);
        } else if (algo == 1) {
            insertionSort(a);
        } else if (algo == 2) {
            bubbleSort(a);
        } else if (algo == 3) {
            mergeSort(a, new int[N], 0, N - 1);
        } else {
            quickSort(a, 0, N - 1);
        }
        long end = System.nanoTime();

        return toMicro(end - start);
    }

    public static void main(String[] args) {
        Random r = new Random();
        String[] names = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Quick Sort"};

      
        int[][] data = {random(r), reversed(r), partial(r), sorted(r)};

        long[][] result = new long[5][4];
        for (int algo = 0; algo < 5; algo++) {
            for (int c = 0; c < 4; c++) {
                result[algo][c] = measure(algo, data[c]);
            }
        }

       
        System.out.printf("%-16s%12s%12s%12s%12s%n", "Algorithm", "Random", "Reversed", "Partial", "Sorted");
        for (int algo = 0; algo < 5; algo++) {
            System.out.printf("%-16s%12d%12d%12d%12d%n", names[algo],
                    result[algo][0], result[algo][1], result[algo][2], result[algo][3]);
        }
        System.out.println("(time in microseconds)");
    }
}