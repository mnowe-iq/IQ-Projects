import java.io.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class FileUtility extends ReDoReport {
	 public static final String OUTPUT_FILE = "Hours_" + getTimeStamp() +
				".xlsx";
	
	 public static void findFile() {
		JFileChooser fileChooser = new JFileChooser(".");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File","xlsx");
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		//filter that will only show Excel files
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("Select Input File");
		
		int status = fileChooser.showDialog(fileChooser, "Open");
		try { //options option to read file and executes if yes
			if(status == JFileChooser.APPROVE_OPTION) {
				 fileChooser.getSelectedFile();
				
				int n = JOptionPane.showConfirmDialog(fileChooser, "Read File?", "File Reader", 
						JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.OK_OPTION) { 
					readFile(fileChooser);
				}else if (n == JOptionPane.NO_OPTION) {
					findFile();
				}
		}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "File not found.");
		}
	}
	public static void readFile(JFileChooser fileChooser) throws IOException{
		ArrayList<Object> input = new ArrayList<>();
		File selectedFile = fileChooser.getSelectedFile();
		FileInputStream fInput = new FileInputStream(selectedFile);
		XSSFWorkbook wb = new XSSFWorkbook(fInput);
		XSSFSheet sheet0 = wb.getSheetAt(1); // <-----change to 0 when finished with tool
		Iterator<Row> rowIt = sheet0.rowIterator();
		
		while(rowIt.hasNext()) { // new Comment
			XSSFRow row = (XSSFRow)rowIt.next();
			Iterator<Cell> cellIt = row.cellIterator();
		
		while(cellIt.hasNext()) {
			XSSFCell cell =(XSSFCell) cellIt.next();
			input.add(cell);
		}
	}
		System.out.println(input);
		outputFile(input);
		wb.close();
}
	public static void outputFile(ArrayList<Object> input) throws IOException{
		String[] header = {"Month", "Customer", "Project", "Hours", "Cost", 
				"Invoiced", "Profitability"};
		String[] dateHeader = {" ","Jan","Feb","Mar","Apr", "May","Jun","Jul",
				"Aug","Sep","Oct","Nov","Dec"};
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet0 = wb.createSheet("Profitability");
		XSSFSheet sheet1 = wb.createSheet("Utilization");
		XSSFSheet sheet2 = wb.createSheet("Original File");
		CellStyle headCellStyle = wb.createCellStyle();
		Row pHeadRow = sheet0.createRow(0);
		Row uHeadRow = sheet1.createRow(0);
		Row initHeadRow = sheet2.createRow(0);
		
		for (int i = 0; i < header.length; i++) {
			Cell cell = pHeadRow.createCell(i);
			cell.setCellValue(header[i]);
			cell.setCellStyle(headCellStyle);
		}
		for (int i = 1; i < dateHeader.length; i++) {
			Cell cell = uHeadRow.createCell(i);
			cell.setCellValue(dateHeader[i] + "-" + year);
			cell.setCellStyle(headCellStyle);
		}
		try {
			FileOutputStream out = new FileOutputStream(OUTPUT_FILE);
			wb.write(out);
			wb.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	// test comment S
	public static String getTimeStamp() {
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd HHmmss");
			Date now = new Date();
			String strDate = df.format(now);
			return strDate;
		}
	} // new Comment
