public class WordCount {
    public static void main(java.lang.String[] args) {
        WordCount.countWordsViaGUI();
    }

    // allow user to pick file to exam via GUI.
    // allow multiple picks
    public static void countWordsViaGUI() {
        WordCount.setLookAndFeel();
        try {
            java.util.Scanner key = new java.util.Scanner(java.lang.System.in);
            do {
                java.lang.System.out.println("Opening GUI to choose file.");
                java.util.Scanner fileScanner = new java.util.Scanner(WordCount.getFile());
                Stopwatch st = new Stopwatch();
                st.start();
                java.util.ArrayList<java.lang.String> words = WordCount.countWordsWithArrayList(fileScanner);
                st.stop();
                java.lang.System.out.println("time to count: " + st);
                java.lang.System.out.print("Enter number of words to display: ");
                int numWordsToShow = java.lang.Integer.parseInt(key.nextLine());
                WordCount.showWords(words, numWordsToShow);
                fileScanner.close();
                java.lang.System.out.print("Perform another count? ");
            } while (key.nextLine().toLowerCase().charAt(0) == 'y' );
            key.close();
        } catch (java.io.FileNotFoundException e) {
            java.lang.System.out.println("Problem reading the data file. Exiting the program." + e);
        }
    }

    // determine distinct words in a file using an array list
    private static java.util.ArrayList<java.lang.String> countWordsWithArrayList(java.util.Scanner fileScanner) {
        java.lang.System.out.println("Total number of words: " + numWords);
        java.lang.System.out.println("number of distincy words: " + result.size());
        return result;
    }

    // determine distinct words in a file and frequency of each word with a Map
    private static java.util.Map<java.lang.String, java.lang.Integer> countWordsWithMap(java.util.Scanner fileScanner) {
        java.lang.System.out.println("Total number of words: " + numWords);
        java.lang.System.out.println("number of distincy words: " + result.size());
        return result;
    }

    private static void showWords(java.util.ArrayList<java.lang.String> words, int numWordsToShow) {
        for (int i = 0; (i < words.size()) && (i < numWordsToShow); i++)
            java.lang.System.out.println(words.get(i));

    }

    private static void showWords(java.util.Map<java.lang.String, java.lang.Integer> words, int numWordsToShow) {
    }

    // perform a series of experiments on files. Determine average time to
    // count words in files of various sizes
    private static void performExp() {
        java.lang.String[] smallerWorks = new java.lang.String[]{ "smallWords.txt", "2BR02B.txt", "Alice.txt", "SherlockHolmes.txt" };
        java.lang.String[] bigFile = new java.lang.String[]{ "ciaFactBook2008.txt" };
        WordCount.timingExpWithArrayList(smallerWorks, 50);
        WordCount.timingExpWithArrayList(bigFile, 3);
        WordCount.timingExpWithMap(smallerWorks, 50);
        WordCount.timingExpWithMap(bigFile, 3);
    }

    // pre: titles != null, elements of titles refer to files in the
    // same path as this program, numExp >= 0
    // read words from files and print average time to cound words.
    private static void timingExpWithMap(java.lang.String[] titles, int numExp) {
        try {
            double[] times = new double[titles.length];
            final int NUM_EXP = 50;
            for (int i = 0; i < NUM_EXP; i++) {
                for (int j = 0; j < titles.length; j++) {
                    java.util.Scanner fileScanner = new java.util.Scanner(new java.io.File(titles[j]));
                    Stopwatch st = new Stopwatch();
                    st.start();
                    java.util.Map<java.lang.String, java.lang.Integer> words = WordCount.countWordsWithMap(fileScanner);
                    st.stop();
                    java.lang.System.out.println(words.size());
                    times[j] += st.time();
                    fileScanner.close();
                }
            }
            for (double a : times)
                java.lang.System.out.println(a / NUM_EXP);

        } catch (java.io.FileNotFoundException e) {
            java.lang.System.out.println("Problem reading the data file. Exiting the program." + e);
        }
    }

    // pre: titles != null, elements of titles refer to files in the
    // same path as this program, numExp >= 0
    // read words from files and print average time to cound words.
    private static void timingExpWithArrayList(java.lang.String[] titles, int numExp) {
        try {
            double[] times = new double[titles.length];
            for (int i = 0; i < numExp; i++) {
                for (int j = 0; j < titles.length; j++) {
                    java.util.Scanner fileScanner = new java.util.Scanner(new java.io.File(titles[j]));
                    Stopwatch st = new Stopwatch();
                    st.start();
                    java.util.ArrayList<java.lang.String> words = WordCount.countWordsWithArrayList(fileScanner);
                    st.stop();
                    times[j] += st.time();
                    fileScanner.close();
                }
            }
            for (int i = 0; i < titles.length; i++)
                java.lang.System.out.println((("Average time for " + titles[i]) + ": ") + (times[i] / numExp));

        } catch (java.io.FileNotFoundException e) {
            java.lang.System.out.println("Problem reading the data file. Exiting the program." + e);
        }
    }

    // try to set look and feel to same as system
    private static void setLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (java.lang.Exception e) {
            java.lang.System.out.println("Unable to set look at feel to local settings. " + "Continuing with default Java look and feel.");
        }
    }

    /**
     * Method to choose a file using a window.
     *
     * @return the file chosen by the user. Returns null if no file picked.
     */
    private static java.io.File getFile() {
        // create a GUI window to pick the text to evaluate
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser(".");
        chooser.setDialogTitle("Select File To Count Words:");
        int retval = chooser.showOpenDialog(null);
        java.io.File f = null;
        chooser.grabFocus();
        if (retval == javax.swing.JFileChooser.APPROVE_OPTION)
            f = chooser.getSelectedFile();

        return f;
    }
}