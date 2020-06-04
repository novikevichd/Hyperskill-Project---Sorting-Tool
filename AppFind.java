package sorting;

public class AppFind {
    public AppFind(String[] mode) {
        String sortingType = "";
        String dataType = "";
        String inFilePath = "";
        String outFilePath = "";
        for (int i = 0; i < mode.length; i++) {
            if (mode[i].equals("-sortingType")) {
                if (i == mode.length - 1) {
                    System.out.println("No sorting type defined!");
                    break;
                }
                else sortingType = mode[i + 1];
            }
            if (mode[i].equals("-dataType")) {
                if (i == mode.length - 1) {
                    System.out.println("No data type defined!");
                    break;
                }
                else dataType = mode[i + 1];
            }
            if (mode[i].equals("-inputFile")) inFilePath = mode[i + 1];
            if (mode[i].equals("-outputFile")) outFilePath = mode[i + 1];
        }

        if (dataType.equals("long")) new IntegersSort(sortingType, inFilePath, outFilePath);
        if (dataType.equals("word")) new WordFinder(sortingType, inFilePath, outFilePath);
        if (dataType.equals("line") || "".equals(dataType)) new LinesFinder(sortingType, inFilePath, outFilePath);

    }
}
