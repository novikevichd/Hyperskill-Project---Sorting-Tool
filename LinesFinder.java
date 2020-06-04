package sorting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LinesFinder {
    public LinesFinder(String mode, String inFilePath, String outFilePath) {
        this.addData(inFilePath);
        if ("byCount".equals(mode)) {
            this.sortIntersByCount(outFilePath);
        } else {
            this.sortIntersNatury(outFilePath);
        }
    }

    ArrayList<String> list = new ArrayList<>() {
        public String toString() {
            StringBuilder str = new StringBuilder();
            for (String s : list) {
                str.append(s + "\n");
            }
            return str.toString();
        }
    };



    private void addData(String inFilePath) {
        if ("".equals(inFilePath)) {
            Scanner in = new Scanner(System.in);
            while (in.hasNext()) {
                String s = in.nextLine();
                list.add(s);
            }
        } else {
            try (Scanner in = new Scanner(new FileReader(inFilePath))) {
                while (in.hasNext()) {
                    String s = in.nextLine();
                    list.add(s);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(list);
    }

    public void sortIntersNatury(String outFilePath) {

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


    public void sortIntersByCount(String outPutPathFile) {

        Map<String, Integer> dataEntryToCounts = new HashMap<>();
        for (String s : list) {
            if (!dataEntryToCounts.containsKey(s)) {
                dataEntryToCounts.put(s, 1);
            } else dataEntryToCounts.put(s, dataEntryToCounts.get(s) + 1);
        }

        ArrayList<Integer> sortedCounts = new ArrayList<>();
        for (Integer i : dataEntryToCounts.values()) {
            if (!sortedCounts.contains(i)) sortedCounts.add(i);
        }
        Collections.sort(sortedCounts);

        Map<Integer, Set<String>> countsToDataEntries = new TreeMap<>();

        for (Map.Entry<String, Integer> pair : dataEntryToCounts.entrySet()) {
            for (Integer i : sortedCounts) {
                if (pair.getValue() == i) {
                    if (!countsToDataEntries.containsKey(i)) {
                        Set<String> values = new TreeSet<>();
                        values.add(pair.getKey());
                        countsToDataEntries.put(i, values);
                    } else {
                        countsToDataEntries.get(i).add(pair.getKey());
                    }
                }
            }
        }
        int size = list.size();
        if ("".equals(outPutPathFile)) {
            System.out.println("Total number: " + size);
            for (Integer key : countsToDataEntries.keySet()) {
                for (String i : countsToDataEntries.get(key)) {
                    System.out.println(i + ": " + key + " time(s), " + ((100 / size) * key) + "%");
                }
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(outPutPathFile)) {
                fileWriter.write("Total number: " + size + "\n");
                for (Integer key : countsToDataEntries.keySet()) {
                    for (String i : countsToDataEntries.get(key)) {
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
