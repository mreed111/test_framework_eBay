package testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Sheet;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ReadWriteExcelData {
	
	public static void showData(String fileName, String sheetName)
	{
		Object[][] arrayObject = getExcelData(fileName, sheetName, true);
		int rows = arrayObject.length;
		int cols = arrayObject[0].length;
		
		System.out.println(String.valueOf(rows) + " rows, " + String.valueOf(cols)+ " cols");
		
		for (int i=0; i<rows; i++) {
			//String line = "";
			String format = "%-40s";
			for (int j=0; j<cols; j++) {
				//line = line + arrayObject[i][j];
				if (j==cols-1) {
					format = "%-40s%n";
				}
				System.out.format(format,arrayObject[i][j]);
//				String format = "%-40s%s%n";
//				System.out.format(format, "...", arrayObject[i][j]);
				//System.out.println(" " + arrayObject[i][j]);
			}
		}
	}
	
	/**
	 * read data values from a *.xls file.
	 * @param fileName:  *.xls file name
	 * @param sheetName: sheet name to be read.
	 * @return array[][]:  a two dimensional array of string values.
	 */
	public static String[][] getExcelData(String fileName, String sheetName, boolean includeColumnHeaders) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			int totalCols = sh.getColumns();
			System.out.println("getExcelData totalCols = " + totalCols);
			int totalRows = (includeColumnHeaders) ? sh.getRows() : sh.getRows() - 1;
			
			arrayExcelData = new String[totalRows][totalCols];
			
			for (int i=0 ; i < totalRows; i++) {
				for (int j=0; j < totalCols; j++) {
					if (includeColumnHeaders) {
						arrayExcelData[i][j] = sh.getCell(j, i).getContents();
					} else {
						arrayExcelData[i][j] = sh.getCell(j, (i+1)).getContents();
					}
				}
			}
			fs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}
	// default:  includeColumnHeaders = false
	public static String[][] getExcelData(String fileName, String sheetName)
	{
		return getExcelData(fileName, sheetName, false);
	}
	
	/**
	 * write data values to a *.xls file.
	 * @param inputArray:  a two dimensional array of string values.
	 * @param outputFile:  path to *.xls output file.
	 * @param sheet:	   sheet name to be created in the new *.xls file.
	 * @return none.
	 */
	public static void writeExcelData ( Object[][] inputArray, String outputFile, String sheet ) 
	{
		try {
			WritableWorkbook wb = Workbook.createWorkbook(new File(outputFile));
			WritableSheet sh = wb.createSheet(sheet,0);
			
			//System.out.println("Write data to: " + outputFile + "  Sheet: " + sheet);
			
			int rows = inputArray.length;
			int cols = inputArray[0].length;
			
			for (int r=0; r<rows; r++) {
				for (int c=0; c<cols; c++) {
					//System.out.println(" " + inputArray[r][c]);
					// add data to Excel sheet
					String newData = (String) inputArray[r][c];
		            Label label = new Label(c, r, newData);
		            sh.addCell(label);
				}
			}
			wb.write();
			wb.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
