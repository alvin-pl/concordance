import java.io.*;
import java.util.*;

public class Concordance {

    // List allows me to have control over my elements than just a String
    static List<String> readFile() {
        List<String> words = new ArrayList<>();
        try {
            File file = new File("the_jungle_book.txt");
            Scanner myScanner = new Scanner(file);
            while (myScanner.hasNextLine()) {
                String[] lineWords = myScanner.nextLine().split(" ");
                Collections.addAll(words, lineWords);
            }
            myScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return words;
    }

    static void createFile() {
        try {
            File myFile = new File("jungle_book.txt_words.txt");
            if(myFile.createNewFile()){
                System.out.println("File created " + myFile.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void writeTextToFile(List<String> words) {
        // get size of ArrayList
        int wordNums = words.size();
        String displayedSortedWords = "";
        try {

            FileWriter myWriter = new FileWriter("jungle_book.txt_words.txt");
            // write the number of words in the file
            myWriter.write(wordNums + " " + "\n");
            // print the number of words contained in the file to the console
            //System.out.println(wordNums + "\n");
            for (int i = 0; i < words.size(); i++) { //.size()
                // write words in the ArrayList to the file
                myWriter.write(words.get(i) + " " + i + "\n");
                // display the words in the file to the console
                displayedSortedWords = words.get(i) + " " + i + "\n";
                //System.out.println(displayedSortedWords);
            }
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static int[] generateIndices(int length) {
        int[] indices = new int[length];
        for(int i = 0; i < length; i++) {
            indices[i] = i;
        }
        return indices;
    }

    static void sortWriteText() {
        try {
            File myFile1 = new File("jungle_book.txt_words.txt");
            Scanner myScanner1 = new Scanner(myFile1);
            List<String> words = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();

            while (myScanner1.hasNextLine()) {
                String data = myScanner1.nextLine();
                // separate the data at a space
                String[] splitData = data.split(" ");
                if(splitData.length > 1) {
                    words.add(splitData[0]);
                    // makes the string into an integer
                    indices.add(Integer.parseInt(splitData[1]));
                } else {
                    // if the length of the string in equal to 1
                    if (splitData.length == 1) {
                        words.add(splitData[0]);
                        // add a default index
                        indices.add(-1);
                    }
                }
            }

            // sort all the words and indices
            for (int i = 0; i < words.size() - 1; i++) {
                for (int j = i + 1; j < words.size(); j++) {
                    if (words.get(i).compareTo(words.get(j)) > 0) {
                        // get the words at a certain index and swap them
                        String temp = words.get(i);
                        words.set(i, words.get(j));
                        words.set(j, temp);

                        // get the indices and swap them
                        int tempIndex = indices.get(i);
                        indices.set(i, indices.get(j));
                        indices.set(j, tempIndex);
                    }
                }
            }
            myScanner1.close();
            // writer contains the text inside the jungle_book.txt_sorted.txt file
            PrintWriter writer = new PrintWriter(new FileWriter("jungle_book.txt_sorted.txt"));

            for (int i = 0; i < words.size(); i++) {
                writer.println(words.get(i) + " " + indices.get(i));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    // merge sort
    public static String[] mergeSort(String[] array) {
        // Base case: if the array has 1 element, then it's sorted by default
        if (array.length == 1)
            return array;

        // Split the array into two half arrays a and b
        int halfway = array.length / 2;
        String[] a = new String[halfway]; // a goes from 0..half
        String[] b = new String[array.length - halfway]; // b goes from half..end

        // Copy array[0...half] --> a[0...half]
        for (int i = 0; i < halfway; i++) {
            a[i] = array[i];
        }

        // Copy array[half..end] --> b[0..array.length-half]
        for (int i = halfway; i < array.length; i++) {
            // since i starts at halfway, subtract halfway when indexing b
            b[i - halfway] = array[i];
        }

        // Call mergeSort on a to sort a
        a = mergeSort(a);
        // Now call mergeSort on b to get a sorted b
        b = mergeSort(b);

        String[] r = merge(a, b);
        // r is sorted completely, so we're done
        return r;
    }

    // Function merge takes two sorted arrays, puts them together into one sorted array
    public static String[] merge(String[] a, String[] b) {
        // Return array has to be big enough to hold all of a and all of b
        String[] r = new String[a.length + b.length];

        // Pointers to the next elements in a and b to be compared
        int aptr = 0;
        int bptr = 0;

        // Pointer to the next place I'm going to put something in my return array
        int rptr = 0;

        // Run this until one of the arrays a or b is finished
        // aptr = a.length then done. bptr = b.length also done
        while (aptr < a.length && bptr < b.length) {
            // Compare
            // b is smaller?
            if (b[bptr].compareTo(a[aptr]) < 0) {
                // b is smaller, move it down from b to r
                r[rptr] = b[bptr];
                rptr = rptr + 1; // Advance r's pointer to the next empty space
                bptr = bptr + 1; // Move to the next element in b
            }
            // a is smaller, or they're the same, copy from a
            else {
                r[rptr] = a[aptr];
                rptr = rptr + 1;
                aptr = aptr + 1;
            }
        }

        // At this point, we need to copy the remainder of array a or array b to r
        // If a is finished, copy b
        while (aptr < a.length) {
            // Copy from a into r
            r[rptr] = a[aptr];
            rptr = rptr + 1;
            aptr = aptr + 1;
        }

        // If b is finished, copy a
        while (bptr < b.length) {
            // Copy from b into r
            r[rptr] = b[bptr];
            rptr = rptr + 1;
            bptr = bptr + 1;
        }

        return r;
    }

    static String[] removeDuplicateWords(String[] word) {
        String displayNonDuplicates = "";
        try {
            File inputFile = new File("jungle_book.txt_sorted.txt");
            Scanner scanner = new Scanner(inputFile);
            PrintWriter writer = new PrintWriter(new FileWriter("jungle_book.txt_index.txt"));

            String previousWord = null;
            List<Integer> indices = new ArrayList<>();

            // loops through each line in the input file
            while (scanner.hasNextLine()) {
                // starts off reading the first line in the input file
                // and stores that words into the currentline variable
                String currentline = scanner.nextLine();
                // splits the string currentline into an array of strings
                // stores the string in the stringArr variable
                String[] stringArr = currentline.split(" ");
                // take the first element in the array
                String currentWord = stringArr[0];
                // make the number that's a string into an index
                int index = Integer.parseInt(stringArr[1]);
                // check if the currentWord is different from the previous word
                // the goal is to find a unique word
                if (!currentWord.equals(previousWord)) {
                    // if it is not the first unique word, aka a duplicate,
                    // write it to the file
                    if (previousWord != null) {
                        // Manually build the string of indices
                        StringBuilder indexString = new StringBuilder();
                        for (int i = 0; i < indices.size(); i++) {
                            if (i > 0) {
                                indexString.append(" ");
                            }
                            // take the element at index i and
                            // append it to indexString
                            indexString.append(indices.get(i));
                        }
                        writer.println(previousWord + " " + indexString.toString());
                    }
                    previousWord = currentWord;
                    indices = new ArrayList<>();
                }
                indices.add(index);
            }

            if (previousWord != null) {
                // build a string of indices for the last word
                StringBuilder indexString1 = new StringBuilder();
                for (int i = 0; i < indices.size(); i++) {
                    if (i > 0) {
                        indexString1.append(" ");
                    }
                    indexString1.append(indices.get(i));
                }
                writer.println(previousWord + " " + indexString1.toString());

                // save non duplicates to var
                displayNonDuplicates = previousWord + " " + indexString1.toString();
            }
            scanner.close();
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new String[]{displayNonDuplicates};
    }

    static int binarySearch(String[] words, String query) {
        int left = 0;
        int right = words.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            // compare two strings, query and words[mid]
            int comparison = query.compareTo(words[mid]);

            // checks if query is to words[mid],
            // if found...
            if (comparison == 0) {
                return mid; // Found the word, return its index
            } else if (comparison < 0) {
                right = mid - 1; // Query is smaller, go left
            } else {
                left = mid + 1; // Query is larger, go right
            }
        }
        return -1; // Word not found in the array
    }

    static void queryAndSearch(List<String> originalWords) {
        Scanner myScanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a word: ");
            String userWord = myScanner.nextLine().trim().toLowerCase();

            if (userWord.isEmpty()) {
                // exit loop
                break;
            }

            int index = originalWords.indexOf(userWord);
            if (index != -1) {
                // this line checks if a word at a certain index
                // has two words before it and two words after it
                if (index >= 2 && index + 2 < originalWords.size()) {
                    // this line iterates over the indices from two
                    // places before the index of the word to two
                    // places after the index of the word
                    for (int i = index - 2; i <= index + 2; i++) {
                        System.out.print(originalWords.get(i) + " ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Can't find " + userWord + " in the text");
            }
        }
    }


    static List<String[]> displayWordsFromFile() {
        List<String[]> wordsAndIndices = new ArrayList<>();
        try {
            File indexFile = new File("the_jungle_book.txt");
            Scanner scanner = new Scanner(indexFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] wordAndIndex = line.split(" ");
                wordsAndIndices.add(wordAndIndex);
                for (String word : wordAndIndex) {
                    // print each word and a space after it
                    System.out.print(word + " ");
                }
                System.out.println();
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the index file.");
            e.printStackTrace();
        }
        return wordsAndIndices;
    }



    public static void main(String[] args) throws FileNotFoundException {
        // return the information in the method readFile() to a variable
        // so you can pass it to another method
        List<String> originalWords = readFile();
        createFile();
        //writeTextToFile(words);

        // sort the words into words variable
        //words = List.of(mergeSort(words.toArray(new String[0])));

        sortWriteText();
        String[] wordsArray = new String[0];
        removeDuplicateWords(wordsArray);
        // displays words from file
        //displayWordsFromFile();

        // turn ArrayList to array
        // wordsArray = originalWords.toArray(new String[0]);

        // request and search for words
        queryAndSearch(originalWords);

        // write words to file
        writeTextToFile(originalWords);
    }
}
