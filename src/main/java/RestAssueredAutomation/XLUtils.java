package RestAssueredAutomation;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtils {
    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;
    public static XSSFCell cell;
    public static XSSFRow row;

    public static  int getRowCount(String xlFile,String xlSheet) throws IOException {
        fi=new FileInputStream(xlFile);
        workbook=new XSSFWorkbook(fi);
        sheet=workbook.getSheet(xlSheet);
        int rowcount=sheet.getLastRowNum();
        workbook.close();
        fi.close();

    return rowcount;
    }
    public static int getColumnCount(String xlFile,String xlSheet,int rowcnt) throws IOException {
        fi=new FileInputStream(xlFile);
        workbook=new XSSFWorkbook(fi);
        sheet=workbook.getSheet(xlSheet);
        row=sheet.getRow(rowcnt);
        int cellcount=row.getLastCellNum();
        workbook.close();
        fi.close();
        return cellcount;
    }
    public static String getcellData(String xlFile,String xlSheet,int rowcnt,int cellcnt) throws IOException {
        fi=new FileInputStream(xlFile);
        workbook=new XSSFWorkbook(fi);
        sheet=workbook.getSheet(xlSheet);
        row=sheet.getRow(rowcnt);
        cell=row.getCell(cellcnt);
       String data;
        try {

            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return  cellData;
        }
        catch (Exception e)
        {
         data="";
        }

        workbook.close();
        fi.close();
        return data;
    }
    public static void setcellData(String xlFile,String xlSheet,int rowcnt,int cellcnt,String data) throws IOException {
        fi=new FileInputStream(xlFile);
        workbook=new XSSFWorkbook(fi);
        sheet=workbook.getSheet(xlSheet);
        row=sheet.getRow(rowcnt);
        cell=row.createCell(cellcnt);
        cell.setCellValue(data);
        fo=new FileOutputStream(xlFile);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();

    }
}
