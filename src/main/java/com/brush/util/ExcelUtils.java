package com.brush.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @description excel操作工具类，读写excel工具
 * @author songhao
 * @date 2017年6月14日 上午10:59:16
 */
public class ExcelUtils {
	
	//定义对应的文件扩展名
	private static final String xls_suffix = "xls";
	private static final String xlsx_suffix = "xlsx";
	
	//默认从第0行开始读取
	private static final int readFromIndex = 0;
	
	/**
	 * 根据excel的路径得到整个excel文件的内容
	 * @param path 文件路径
	 * @return
	 * @throws Exception
	 */
	public static List<List<String>> getListByExcelPath(String path,int startIndex) throws Exception{
		List<List<String>> result = new ArrayList<List<String>>();
		if(path.endsWith(xls_suffix)){
			result = readXls(path,startIndex);
		}else if(path.endsWith(xlsx_suffix)){
			result = readXlsx(path,startIndex);
		}
		return result;
	}
	
	/**
	 * 根据excel获取所有的sheet的名称集合
	 * @param excelpath excel路径
	 * @return
	 */
	public static List<String> getSheetList(String excelpath) throws Exception{
		List<String> sheetList = new ArrayList<String>();
		if(excelpath.endsWith(xls_suffix)){
			sheetList = getSheetsreadXls(excelpath);
		}else if(excelpath.endsWith(xlsx_suffix)){
			sheetList = getSheetsreadXlsx(excelpath);
		}
		return sheetList;
	}
	
	/**
	 * 根据sheetName获取对应的内容信息
	 * @param sheetName
	 * @return
	 */
	public static List<List<String>> getListBySheetName(String excelpath,String sheetName) throws Exception{
		List<List<String>> contentList = new ArrayList<List<String>>();
		if(excelpath.endsWith(xls_suffix)){
			contentList = getContentXlsBySheetname(excelpath,sheetName,readFromIndex);
		}else if(excelpath.endsWith(xlsx_suffix)){
			contentList = getContentXlsxBySheetname(excelpath,sheetName,readFromIndex);
		}
		return contentList;
	}
	
	/**
	 * 根据excel路径和页名称获取集合信息
	 * @param excelpath excel的路径信息
	 * @param sheetName sheet页面的名称信息
	 * @param index 从第几行开始读取
	 * @return
	 */
	private static List<List<String>> getContentXlsxBySheetname(String excelpath, String sheetName,int index) throws Exception{
		List<List<String>> result = new ArrayList<List<String>>();
		InputStream is = new FileInputStream(excelpath);
		XSSFWorkbook xssfWorkbook = null;
		try{
			xssfWorkbook = new XSSFWorkbook(is);
			int numSheet = xssfWorkbook.getNumberOfSheets();
			if(numSheet == 0){
				return result;
			}
			//表示对应的标签页面是不是已经找到了
			boolean isAccepted = false;
			for(int sheetIndex = 0; sheetIndex < numSheet; sheetIndex++){
				if(isAccepted){
					break;
				}
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
				if(xssfSheet == null){
					continue;
				}
				//判断这个sheet页面是否是我们要寻找的目标sheet
				String tempSn = xssfSheet.getSheetName();
				if(tempSn != null && !tempSn.equals(sheetName)){
					continue;
				}
				//循环当前页，循环读取每一行
				for(int rowNum = index; rowNum <= xssfSheet.getLastRowNum(); rowNum++){
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if(xssfRow == null){
						continue;
					}
					int minColIx = xssfRow.getFirstCellNum();
					int maxColIx = xssfRow.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					//遍历改行,获取处理每个cell元素
					for(int colIx = minColIx;colIx < maxColIx; colIx++){
						XSSFCell cell = xssfRow.getCell(colIx);
						if(cell == null){
							continue;
						}
						rowList.add(cell.toString());
					}
					result.add(rowList);
				}
				isAccepted = true;
			}
		}finally{
			if(xssfWorkbook != null){
				xssfWorkbook.close();
			}
		}
		return result;
	}

	/**
	 * 根据excel路径和页名称获取集合信息
	 * @param excelpath excel的路径信息
	 * @param sheetName sheet页面的名称信息
	 * @param index 从第几行开始读取
	 * @return
	 */
	private static List<List<String>> getContentXlsBySheetname(String excelpath, String sheetName,int index) throws Exception{
		List<List<String>> result = new ArrayList<List<String>>();
		InputStream is = new FileInputStream(excelpath);
		HSSFWorkbook hssfWorkbook = null;
		try{
			hssfWorkbook = new HSSFWorkbook(is);
			int numSheet = hssfWorkbook.getNumberOfSheets();
			//表示对应的标签页面是不是已经找到了
			boolean isAccepted = false;
			//循环每一页,并处理当前循环页
			for(int sheetIndex = 0; sheetIndex < numSheet; sheetIndex++){
				if(isAccepted){
					break;
				}
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetIndex);
				if(hssfSheet == null){
					continue;
				}
				//判断这个sheet页面是否是我们要寻找的目标sheet
				String tempSn = hssfSheet.getSheetName();
				if(tempSn != null && !tempSn.equals(sheetName)){
					continue;
				}
				//处理当前页，循环读取每一行
				for(int rowNum = index; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if(hssfRow == null){
						continue;
					}
					int minColIx = hssfRow.getFirstCellNum();
					int maxColIx = hssfRow.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					if(minColIx != 0){
						continue;
					}
					//如果一整行的数据都为空代表这一行有误
					int nullIndex = 0;
					//遍历改行,获取处理每个cell元素
					for(int colIx = minColIx; colIx < maxColIx; colIx++){
						HSSFCell cell = hssfRow.getCell(colIx);
						if(cell == null){
							nullIndex++; 
							continue;
						}
						String value = getStringVal(cell);
						if(value == null || "".equals(value.trim())){
							nullIndex++; 
						}
						rowList.add(value);
					}
					if(nullIndex != maxColIx){
						result.add(rowList);
					}
				}
				isAccepted = true;
			}
		}finally{
			if(hssfWorkbook != null){
				hssfWorkbook.close();
			}
		}
		return result;
	}

	/**
	 * 读取扩展名为xls的excel文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private static List<List<String>> readXls(String path,int index) throws Exception{
		List<List<String>> result = new ArrayList<List<String>>();
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = null;
		try{
			hssfWorkbook = new HSSFWorkbook(is);
			int numSheet = hssfWorkbook.getNumberOfSheets();
			//循环每一页,并处理当前循环页
			for(int sheetIndex = 0; sheetIndex < numSheet; sheetIndex++){
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetIndex);
				if(hssfSheet == null){
					continue;
				}
				//处理当前页，循环读取每一行
				for(int rowNum = index; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if(hssfRow == null){
						continue;
					}
					int minColIx = hssfRow.getFirstCellNum();
					int maxColIx = hssfRow.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					if(minColIx != 0){
						continue;
					}
					//如果一整行的数据都为空代表这一行有误
					int nullIndex = 0;
					//遍历改行,获取处理每个cell元素
					for(int colIx = minColIx; colIx < maxColIx; colIx++){
						HSSFCell cell = hssfRow.getCell(colIx);
						if(cell == null){
							nullIndex++; 
							continue;
						}
						String value = getStringVal(cell);
						if(value == null || "".equals(value.trim())){
							nullIndex++; 
						}
						rowList.add(value);
					}
					if(nullIndex != maxColIx){
						result.add(rowList);
					}
				}
			}
		}finally{
			if(hssfWorkbook != null){
				hssfWorkbook.close();
			}
		}
		return result;
	}
	
	/**
	 * 读取扩展名xlsx的excel文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private static List<List<String>> readXlsx(String path,int index) throws Exception{
		List<List<String>> result = new ArrayList<List<String>>();
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = null;
		try{
			xssfWorkbook = new XSSFWorkbook(is);
			int numSheet = xssfWorkbook.getNumberOfSheets();
			if(numSheet == 0){
				return result;
			}
			for(int sheetIndex = 0; sheetIndex < numSheet; sheetIndex++){
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
				if(xssfSheet == null){
					continue;
				}
				//循环当前页，循环读取每一行
				for(int rowNum = index; rowNum <= xssfSheet.getLastRowNum(); rowNum++){
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if(xssfRow == null){
						continue;
					}
					int minColIx = xssfRow.getFirstCellNum();
					int maxColIx = xssfRow.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					//遍历改行,获取处理每个cell元素
					for(int colIx = minColIx;colIx < maxColIx; colIx++){
						XSSFCell cell = xssfRow.getCell(colIx);
						if(cell == null){
							continue;
						}
						rowList.add(cell.toString());
					}
					result.add(rowList);
				}
			}
		}finally{
			if(xssfWorkbook != null){
				xssfWorkbook.close();
			}
		}
		return result;
	}
	
	/**
	 * 读取扩展名为xlsx的excel文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private static List<String> getSheetsreadXlsx(String excelpath) throws Exception{
		List<String> sheetList = new ArrayList<String>();
		InputStream is = new FileInputStream(excelpath);
		XSSFWorkbook xssfWorkbook = null;
		try{
			xssfWorkbook = new XSSFWorkbook(is);
			int numSheet = xssfWorkbook.getNumberOfSheets();
			if(numSheet == 0){
				return sheetList;
			}
			for(int sheetIndex = 0; sheetIndex < numSheet; sheetIndex++){
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
				if(xssfSheet != null){
					sheetList.add(xssfSheet.getSheetName());
				}
			}
		}finally{
			if(xssfWorkbook != null){
				xssfWorkbook.close();
			}
		}
		return sheetList;
	}


	/**
	 * 读取扩展名为xls的excel文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private static List<String> getSheetsreadXls(String excelpath) throws Exception {
		List<String> sheetList = new ArrayList<String>();
		InputStream is = new FileInputStream(excelpath);
		HSSFWorkbook hssfWorkbook = null;
		try{
			hssfWorkbook = new HSSFWorkbook(is);
			int numSheet = hssfWorkbook.getNumberOfSheets();
			//循环每一页,并处理当前循环页
			for(int sheetIndex = 0; sheetIndex < numSheet; sheetIndex++){
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetIndex);
				if(hssfSheet != null){
					sheetList.add(hssfSheet.getSheetName());
				}
			}
		}finally{
			if(hssfWorkbook != null){
				hssfWorkbook.close();
			}
		}
		return sheetList;
	}


	/**
	 * 获取单元格内的值
	 * @param cell
	 * @return
	 */
	private static String getStringVal(HSSFCell cell){
		switch(cell.getCellType()){
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return "";
		}
	}
	
	public static void main(String[] args) throws Exception{
		/*String path = "C:\\test.xlsx";
		List<List<String>> result = getListByExcelPath(path,0);
		
		System.out.println("获取到集合的大小为：" + result.size());
		
		for(List<String> r : result){
			for(String s : r){
				System.out.print(s + "\t");
			}
			System.out.println();
		}*/
		String path = "C:\\test.xlsx";
		int index = 0;
		
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = null;
		try{
			xssfWorkbook = new XSSFWorkbook(is);
			int numSheet = xssfWorkbook.getNumberOfSheets();
			if(numSheet == 0){
				return;
			}
			
			for(int sheetIndex = 0; sheetIndex < numSheet; sheetIndex++){
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
				if(xssfSheet == null){
					continue;
				}
				String tablename = xssfSheet.getSheetName();
				int game_id = 1;//游戏id
				StringBuffer tableBuffer = new StringBuffer("insert into t_table(`table_name`,`game_id`,`table_label`,`hdfspath`) values('" + tablename + "',1,");
				//循环当前页，循环读取每一行
				for(int rowNum = index; rowNum <= xssfSheet.getLastRowNum(); rowNum++){
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if(xssfRow == null){
						continue;
					}
					int minColIx = xssfRow.getFirstCellNum();
					int maxColIx = xssfRow.getLastCellNum();
					
					int table_id = 1;//表格id
					int status = 1;//是否是选择字段
					int filterstatus = 0;//是否是过滤字段
					StringBuffer columnBuffer = new StringBuffer("insert into t_column(`table_id`,`status`,`filterstatus`,`column_name`,"
							+ "`column_label`,`column_type`) values(" + table_id + "," + status + "," + filterstatus + ",");
					
					//遍历改行,获取处理每个cell元素
					for(int colIx = minColIx;colIx < maxColIx; colIx++){
						XSSFCell cell = xssfRow.getCell(colIx);
						if(cell == null){
							continue;
						}
						if(rowNum == 0){
							tableBuffer.append("'" + cell.toString() + "',");
						}else if(rowNum >= 3){
							columnBuffer.append("'" + cell.toString() + "',");
						}
					};
					if(rowNum >= 3){
						if(columnBuffer.length() > 0){
							columnBuffer.delete(columnBuffer.length() - 1, columnBuffer.length());
						}
						columnBuffer.append(")");
						System.out.println("------" + columnBuffer.toString());
					}
				}
				if(tableBuffer.length() > 0){
					tableBuffer.delete(tableBuffer.length() - 1, tableBuffer.length());
				}
				tableBuffer.append(")");
				System.out.println("===" + tableBuffer.toString());
			}
		}finally{
			if(xssfWorkbook != null){
				xssfWorkbook.close();
			}
		}
	}
}