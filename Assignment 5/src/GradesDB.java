import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

public class GradesDB {

    int numStudents = 0;
    int numAssignments = 0;
    HashSet<Student> students = new HashSet<>();
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFSheet sheet2;

    public GradesDB(String loc) throws IOException
    {
        initStudent1(loc);
    }

    public void initStudent1(String loc) throws IOException
    {
        int rows = 1; //i
        int columns = 0; //j
        boolean hasNext = true;
        String cell1;
        String cell2;
        double dcell2;
        int ncell2;
        double dcell3;
        int ncell3;
        workbook = importFile(loc);
        sheet = workbook.getSheetAt(0);
        sheet2 = workbook.getSheetAt(2);
        XSSFRow row1;
        XSSFRow row2;

        do {
            try
            {
                //System.out.println(rows);
                row1 = sheet.getRow(rows);
                row2 = sheet2.getRow(rows);
                cell1 = row1.getCell(columns).toString();
                dcell2 = row1.getCell(1).getNumericCellValue();
                ncell2 = (int)dcell2;
                cell2 = Integer.toString(ncell2);
                dcell3 = row2.getCell(1).getNumericCellValue();
                ncell3 = (int)dcell3;
                //System.out.println(cell1 + " " + cell2 + " " + ncell3);
                students.add(new Student(cell1, cell2, ncell3));
                rows+= 1;
                numStudents++;
            }
            catch(Exception e)
            {
                hasNext = false;
            }
        }
        while(hasNext);
    }

    public int getNumStudents() {
        return numStudents;
    }

    public HashSet<Student> getStudents() {
        return students;
    }


    public int getNumAssignments() {
        sheet = workbook.getSheetAt(3);
        int rows = 0; //i
        int columns = 0; //j
        int assNum = 0;
        XSSFRow row1 = sheet.getRow(rows);
        do {
            columns++;
            assNum++;
        }
        while(row1.getCell(columns+1) != null);
        return assNum;
    }

    public int getNumProjects() {
        sheet = workbook.getSheetAt(4);
        int pro = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 1; j < 4; j++) {
                XSSFRow row = sheet.getRow(i);
                String cell = row.getCell(j).toString();
                //System.out.print(cell + " ");
                pro ++;
            }
        }
        // System.out.println(pro); //test return statement
        return pro;
    }

    public Student getStudentByName(String str)
    {
        boolean found = false;
        for (Student s : students) {
            if (s.getName().compareTo(str) == 0) {
                found = true;
                return s;
            }
        }
        return null;
    }

    public Student getStudentByID(String str)
    {
        boolean found = false;
        for (Student s : students) {
            if (s.getId().compareTo(str) == 0) {
                found = true;
                return s;
            }
        }
        return null;
    }



    public static XSSFWorkbook importFile(String gradesDb) throws FileNotFoundException, IOException {
        File f = new File(gradesDb);
        FileInputStream fis = new FileInputStream(f);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        return workbook;
    }


    /*public static void main(String[] args) {
        String GRADES_DB = "GradesDatabase.xlsx";
        try {
            //getStudents(GRADES_DB);
            //getNumAssignments(GRADES_DB);
            //getNumProjects(GRADES_DB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}

