package sorting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IntegersSort {
    public IntegersSort(String mode, String inFilePath, String outFilePath) {
        this.addData(inFilePath);
        if ("byCount".equals(mode)) {
            this.sortIntersByCounts(outFilePath);
        } else {
            this.sortIntersNaturaly(outFilePath);
        }
    }

    ArrayList<Integer> list = new ArrayList<>() {
        public String toString() {
            StringBuilder str = new StringBuilder();
            for (Integer i : list) {
                str.append(i + " ");
            }
            return str.toString();
        }
    };

    public void addData(String infilePath) {
        if ("".equals(infilePath)) {
            Scanner in = new Scanner(System.in);
            while (in.hasNext()) {
                int n = in.nextInt();
                list.add(n);
            }
        } else {
            try (Scanner in = new Scanner(new FileReader(infilePath))) {
                while (in.hasNext()) {
                    int n = in.nextInt();
                    list.add(n);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(list);
    }

    public void sortIntersNaturaly(String outFilePath) {
        if ("".equals(outFilePath)) {
            System.out.printf("Total numbers: %d.\n", list.size());
            System.out.println("Sorted data: " + list.toString());
        } else {
            try(FileWriter fileWriter = new FileWriter(outFilePath)) {
                fileWriter.write("Total numbers: " + list.size() + "\n");
                fileWriter.write("Sorted data: " + list.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        list.clear();
    }

    public void sortIntersByCounts(String outPutPathFile) {

        Map<Integer, Integer> dataEntryToCount = new HashMap<>();
        for (Integer i : list) {
            if (!dataEntryToCount.containsKey(i)) {
                dataEntryToCount.put(i, 1);
            } else dataEntryToCount.put(i, dataEntryToCount.get(i) + 1);
        }

        ArrayList<Integer> sortedCounts = new ArrayList<>();
        for (Integer i : dataEntryToCount.values()) {
            if (!sortedCounts.contains(i)) sortedCounts.add(i);
        }
        Collections.sort(sortedCounts);

        Map<Integer, Set<Integer>> countsToEntry = new TreeMap<>();

        for (Map.Entry<Integer, Integer> pair : dataEntryToCount.entrySet()) {
            for (Integer j : sortedCounts) {
                if (pair.getValue() == j) {
                    if (!countsToEntry.containsKey(j)) {
                        Set<Integer> set = new TreeSet<>();
                        set.add(pair.getKey());
                        countsToEntry.put(j, set);
                    } else {
                        countsToEntry.get(j).add(pair.getKey());
                    }
                }
            }
        }


        int size = list.size();
        if ("".equals(outPutPathFile)) {
            System.out.println("Total number: " + size);
            for (Integer key : countsToEntry.keySet()) {
                for (Integer i : countsToEntry.get(key)) {
                    System.out.println(i + ": " + key + " time(s), " + ((100 / size) * key) + "%");
                }
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(outPutPathFile)) {
                fileWriter.write("Total number: " + size + "\n");
                for (Integer key : countsToEntry.keySet()) {
                    for (Integer i : countsToEntry.get(key)) {
                        fileWriter.write(i + ": " + key + " time(s), " + ((100 / size) * key) + "%\n");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        list.clear();
    }


}
