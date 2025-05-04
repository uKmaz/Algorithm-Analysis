import java.io.*;
import java.sql.Array;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Sorting {
    static Random rnd = new Random();
    static String[] test = {"insertion","merge","heap","quick"};
    static String[] size = {"1.000","10.000","100.000"};
    static String[] type = {"Random","Increasing","Decreasing"};
    static int combination = test.length*size.length*type.length;
    static private File _1K_random_input = new File("inputs/1K_random_input.txt");
    static private File _10K_random_input = new File("inputs/10K_random_input.txt");
    static private File _100K_random_input = new File("inputs/100K_random_input.txt");


    // <editor-fold desc = "SORTS">
    static public int[] insertion_sort(int[]A){

        // WORST CASE TIME COMPLEXITY = O(n^2)

        for(int i=0;i<A.length;i++){
            int j = i;
            while(j>0 && A[j-1]>A[j]){
                swap(A,j,j-1);
                j=j-1;
            }
        }
        return A;
    }
    // <editor-fold desc="Merge Sort">

    // WORST CASE TIME COMPLEXITY = O(n log n)
    static public int[] merge(int[] A, int[] B) {
        int[] C = new int[A.length + B.length];

        int a = 0, b = 0, c = 0;

        while (a < A.length && b < B.length) {
            if (A[a] < B[b]) {
                C[c++] = A[a++];
            } else {
                C[c++] = B[b++];
            }
        }

        while (a < A.length) {
            C[c++] = A[a++];
        }

        while (b < B.length) {
            C[c++] = B[b++];
        }

        return C;
    }

    static public int[] merge_sort(int[] A) {
        int length = A.length;

        if (length <= 1) {
            return A;
        }

        int mid = length / 2;

        int[] arrayOne = new int[mid];
        int[] arrayTwo = new int[length - mid];

        for (int i = 0; i < mid; i++) {
            arrayOne[i] = A[i];
        }

        for (int i = 0; i < length - mid; i++) {
            arrayTwo[i] = A[mid + i];
        }

        arrayOne = merge_sort(arrayOne);
        arrayTwo = merge_sort(arrayTwo);

        return merge(arrayOne, arrayTwo);
    }


    // </editor-fold>
    // <editor-fold desc = "Heap Sort">

    // WORST CASE TIME COMPLEXITY = O(n log n)
    public static int[] heapSort(int[] A) {
        buildMaxHeap(A);
        int n = A.length;

        for (int i = n - 1; i > 0; i--) {
            swap(A, 0, i);       // Swap root with the last element
            heapify(A, 0, i);    // Heapify the reduced heap
        }

        return A;
    }

    public static void buildMaxHeap(int[] A) {
        int n = A.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(A, i, n);
        }
    }

    public static void heapify(int[] A, int i, int n) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int max = i;

        if (left < n && A[left] > A[max]) {
            max = left;
        }
        if (right < n && A[right] > A[max]) {
            max = right;
        }

        if (max != i) {
            swap(A, i, max);
            heapify(A, max, n);
        }
    }
    // </editor-fold>
    // <editor-fold desc = "Quick Sort">


    // THE PROGRAM GIVES StackOverflowError WHEN QUICK SORT IS USED WITH THE 100.000 SIZED FILE AND INCREASING OR DECREASING TYPE
    // Exactly after 15.633 data
    // I HAVE SOLVED THIS ISSUE WITH RANDOMIZED PIVOT CHOOSING.

    // WORST CASE TIME COMPLEXITY = O(n^2)

    public static int randomizedPartition(int[] A, int low, int high) {
        int pivotIndex = rnd.nextInt(high - low + 1) + low;
        swap(A, low, pivotIndex); //
        return partition(A, low, high);
    }

    public static int partition(int[] A, int low, int high) {
        int pivot = A[low];
        int leftwall = low;

        for (int i = low + 1; i <= high; i++) {
            if (A[i] < pivot) {
                leftwall++;
                swap(A, i, leftwall);
            }
        }

        swap(A, low, leftwall);
        return leftwall;
    }

    public static int[] quick_sort(int[] A, int low, int high) {

        if (low < high) {
            int pivotIndex = randomizedPartition(A, low, high);
            quick_sort(A, low, pivotIndex - 1);
            quick_sort(A, pivotIndex + 1, high);
        }
        return A;
    }
/*  NORMAL PIVOT CHOOSING
    public static int[] quick_sort(int[] A, int low, int high) {

        if (low < high) {
            int pivotIndex = partition(A, low, high);
            quick_sort(A, low, pivotIndex - 1);
            quick_sort(A, pivotIndex + 1, high);
        }
        return A;
    }

    public static int partition(int[] A, int low, int high) {
        int pivot = A[low];
        int leftwall = low;

        for (int i = low + 1; i <= high; i++) {
            if (A[i] < pivot) {
                leftwall++;
                swap(A, i, leftwall);
            }
        }

        swap(A, low, leftwall);
        return leftwall;
    }
*/



    // </editor-fold>


    // </editor-fold>

    // <editor-fold desc = "ANALYSIS">
    static public float test_manual(int sort, int type) {
        // Sabit array ile testArray oluşturuluyor
        int[] testArray = { 5, 2, 8, 1, 3, 7, 4, 6, 9 }; // Burada istediğiniz array'i belirleyebilirsiniz

        // File Type (random / inc / dec) işlemi
        switch (type) {
            case 1:
                // Default, array zaten verilmiş
                break;
            case 2:
                // Artan sırayla sıralama
                Arrays.sort(testArray);
                break;
            case 3:
                // Azalan sırayla sıralama
                Arrays.sort(testArray);
                int[] tempArray = new int[testArray.length];
                int j = 0;
                for (int i = testArray.length - 1; i >= 0; i--) {
                    tempArray[j] = testArray[i];
                    j++;
                }
                testArray = tempArray;
                break;
            default:
                System.out.println("Type error");
                break;
        }

        // Sort Type (Insertion / Merge / Heap / Quick)
        long startTime = System.currentTimeMillis();
        switch (sort) {
            case 1:
                testArray = insertion_sort(testArray);
                break;
            case 2:
                testArray = merge_sort(testArray);
                break;
            case 3:
                testArray = heapSort(testArray);
                break;
            case 4:
                testArray = quick_sort(testArray, 0, testArray.length - 1);
                break;
            default:
                System.out.println("Invalid choice of test");
                break;
        }
        long endTime = System.currentTimeMillis();

        return (float) (endTime - startTime) / 1000;  // saniye cinsinden dönüş
    }

    static public float testTime(int sort, int type ,int size  ){
        File file = null;
        int fileSize = 0;


        // File Size ( 1.000 / 10.000 / 100.000 )
        switch (size){
            case 1:
                file = _1K_random_input;
                fileSize = 1000;
                break;
            case 2:
                file = _10K_random_input;
                fileSize = 10000;
                break;
            case 3:
                file = _100K_random_input;
                fileSize = 100000;
                break;
            default:
                 System.out.println("File Size Error");
                 break;

        }
        int[] testArray = fileRead(file);

        // File Type ( random / inc / dec )
        switch (type){
            case 1:
                // Default
                break;
            case 2:
                Arrays.sort(testArray);
                break;
            case 3:
                Arrays.sort(testArray);
                int[] tempArray = new int[testArray.length];
                int j =0;
                for (int i = testArray.length - 1; i >= 0; i--) {
                    tempArray[j] = testArray[i];
                    j++;
                }
                testArray = tempArray;
                break;
            default:
                System.out.println("Type error");
                break;
        }

        // Sort Type ( Insertion / Merge / Heap / Quick )

        long startTime = System.currentTimeMillis();
        switch (sort){
            case 1:
                testArray = insertion_sort(testArray);
                break;
            case 2:
                testArray = merge_sort(testArray);
                break;
            case 3:
                testArray = heapSort(testArray);
                break;
            case 4:
                testArray = quick_sort(testArray,0,testArray.length-1);
                break;
            default:
                System.out.println("Invalid choice of test");
                break;
        }
        long endTime = System.currentTimeMillis();

        return (float)(endTime - startTime) / 1000;
    }
    static public void allPossibilities() {

        //  Tests               Size            Type
        //  Insertion : 1       1.000 : 1       Random : 1
        //  Merge : 2           10.000 : 2      Increasing : 2
        //  Heap : 3            100.000 : 3     Decreasing : 3
        //  Quick : 4

        for( int i = 1;i<=4;i++){
            for( int j = 1;j<=3;j++){
                for( int k = 1;k<=3;k++){
                    System.out.println(testTime(i,j,k));
                }
            }
        }
    }
    static public int[] testForHW(int sort, int type ,int size){
        File file = null;
        int fileSize = 0;


        // File Size ( 1.000 / 10.000 / 100.000 )
        switch (size){
            case 1:
                file = _1K_random_input;
                fileSize = 1000;
                break;
            case 2:
                file = _10K_random_input;
                fileSize = 10000;
                break;
            case 3:
                file = _100K_random_input;
                fileSize = 100000;
                break;
            default:
                System.out.println("File Size Error");
                break;

        }
        int[] testArray = fileRead(file);

        // File Type ( random / inc / dec )
        switch (type){
            case 1:
                // Default
                break;
            case 2:
                Arrays.sort(testArray);
                break;
            case 3:
                Arrays.sort(testArray);
                int[] tempArray = new int[testArray.length];
                int j =0;
                for (int i = testArray.length - 1; i >= 0; i--) {
                    tempArray[j] = testArray[i];
                    j++;
                }
                testArray = tempArray;
                break;
            default:
                System.out.println("Type error");
                break;
        }

        // Sort Type ( Insertion / Merge / Heap / Quick )

        switch (sort){
            case 1:
                testArray = insertion_sort(testArray);
                break;
            case 2:
                testArray = merge_sort(testArray);
                break;
            case 3:
                testArray = heapSort(testArray);
                break;
            case 4:
                testArray = quick_sort(testArray,0,testArray.length-1);
                break;
            default:
                System.out.println("Invalid choice of test");
                break;
        }

        return testArray;
    }
    static public void outputWanted(){
        try {
            File directory = new File("outputs");
            directory.mkdirs();
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 3; j++) {
                    for (int k = 1; k <= 3; k++) {
                        int[] sortedArray = testForHW(i,j,k);
                        FileWriter txtWriter = new FileWriter("outputs/"+test[i-1]+"_" +size[k-1]+"_" +type[j-1]+"output" +".txt");
                        for(int r = 0;r<sortedArray.length;r++){
                            txtWriter.append(sortedArray[r]+"\n");
                        }
                        txtWriter.flush();
                        txtWriter.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static public void outputPrecise (){
        int combination = 4*3*3;
        float[] ms = new float[combination];
        int count = 0;
        int overall_from = 20;
        for(int z=0;z<overall_from;z++){
            count=0;
            for( int i = 0;i<=3;i++){
                for( int j = 0;j<=2;j++){
                    for( int k = 0;k<=2;k++){
                        ms[count] += testTime(i+1,j+1,k+1);
                        count++;
                    }
                }
            }
        }

        for( int i = 0;i<combination;i++) {
            ms[i] /= overall_from;
        }
        count=0;
        try {
            FileWriter csvWriter = new FileWriter("sorting_results_precise.csv");
            csvWriter.append("Algorithm,Size(*1000),Type,Time (ms)\n");

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        csvWriter.append(test[i]).append(",")
                                .append(size[j]).append(",")
                                .append(type[k]).append(",")
                                .append(String.format("%.6f", ms[count])).append("\n");
                        count++;
                    }
                }
            }

            csvWriter.flush();
            csvWriter.close();
            System.out.println("CSV dosyası başarıyla oluşturuldu: sorting_results.csv");

        } catch (IOException e) {
            System.out.println("CSV dosyası yazma hatası:");
            e.printStackTrace();
        }
    }
    // </editor-fold>

    static public void swap( int[] arr,int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static public int[] fileRead(File file) {
        List<Integer> dataList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                int number = Integer.parseInt(line.trim());
                dataList.add(number);
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        int[] list = new int[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            list[i] = dataList.get(i);
        }

        return list;
    }

    public static void main(String[] args) {
        //  Sort                Size            Type
        //  Insertion : 1       1.000 : 1       Random : 1
        //  Merge : 2           10.000 : 2      Increasing : 2
        //  Heap : 3            100.000 : 3     Decreasing : 3
        //  Quick : 4


        //  test(x,y,z)         to try any combination
        //allPossibilities(); //to get all 36 combination
        //outputPrecise();    //to get a .csv file with all the data out of 20(as much as wanted "overall_from") repetition
        outputWanted();
        //test_manual(4,1);


    }
}