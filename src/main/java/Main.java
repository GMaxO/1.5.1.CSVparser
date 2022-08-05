import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String[] employee1 = "1,John,Smith,USA,25".split(",");
        String[] employee2 = "2,Inav,Petrov,RU,23".split(",");
        try (CSVWriter writer = new CSVWriter(new FileWriter("data.csv"))) {
            writer.writeNext(employee1);
            writer.writeNext(employee2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        List<Employee> employeeList = parseCSV(columnMapping, fileName);
        String json = listToJson(employeeList);
        writeString(json);

    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> personal = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            personal = csv.parse();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return personal;
    }

    private static String listToJson(List<Employee> employeeList) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(employeeList);
    }

    private static void writeString(String json) {
        File file = new File("C:\\Users\\Максим\\1.5.1.CSVparser\\data.json");
        try (FileWriter fileWriter = new FileWriter(file)){
            file.createNewFile();
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

