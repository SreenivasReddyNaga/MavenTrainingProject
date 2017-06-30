package org.epam.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.epam.annotations.DataSourceSheet;

public class ExcelDataUtil {

	private static final Logger LOGGER = LogManager.getRootLogger();

	public Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException {
		Workbook workbook = null;

		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	public  Object[][] getExcelData(String excelFilePath, String sheetName) {

		List<Map<String, String>> iterations = new ArrayList<Map<String, String>>();
		String value = null;
		try {
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
			Workbook workbook = getWorkbook(inputStream, excelFilePath);
			Sheet sheet = workbook.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);
			if (headerRow == null) {
				LOGGER.error("Unable to find a header row in the spreadsheet");
				inputStream.close();
				workbook.close();
				return null;
			}

			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row currentRow = sheet.getRow(i);
				Map<String, String> data = new TreeMap<String, String>();

				for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {
					Cell currentCell = currentRow.getCell(j);

					switch (currentCell.getCellType()) {
					case Cell.CELL_TYPE_STRING:

						value = currentCell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:

						long longVal = (long) currentCell.getNumericCellValue();
						value = Long.valueOf(longVal).toString();

						break;
					}
					data.put(headerRow.getCell(j).getStringCellValue(), value);

				}
				if (data.size() > 0) {
					LOGGER.debug("[Row " + i + "] " + data);
					iterations.add(data);
				} else {
					LOGGER.debug("[Row " + i + "] Data Not Found");
				}
			}

			inputStream.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertToArray(iterations);

	}

	public Object[][] convertToArray(List<Map<String, String>> dataMaps) {
		LOGGER.debug("Converting results to an Array for TestNG");
		Object testData[][] = new Object[dataMaps.size()][1];
		for (int index = 0; index < dataMaps.size(); index++) {
			testData[index][0] = dataMaps.get(index);
		}
		LOGGER.debug("Finished converting results to an Array for TestNG");
		return testData;
	}
	
	
	 /**
     * Searches the test method and test class for a {@link DataSourceSheet} annotation.
     */
    public String findDataSourceSheet(Method m) {

            LOGGER.debug("Method name: " + m.getName());
        LOGGER.debug ("Method's class: " + m.getDeclaringClass().getName());

        if (m.isAnnotationPresent(DataSourceSheet.class)) {
            DataSourceSheet dataSourceSheet = m.getAnnotation(DataSourceSheet.class);
         LOGGER.debug ("Found the DataSourceSheet on the method: ${dataSourceSheet.value()}");
            return dataSourceSheet.value();
        } else if (m.getDeclaringClass().isAnnotationPresent(DataSourceSheet.class)) {
            DataSourceSheet dataSourceSheet = m.getDeclaringClass().getAnnotation(DataSourceSheet.class);
            LOGGER.debug ("Found the DataSourceSheet on the class: ${dataSourceSheet.value()}");
            return dataSourceSheet.value();
        }
        LOGGER.debug ("Unable to find the wizard we are testing - Using the default value");
        return m.getName(); // Default!
    }

}
