package a.studentdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        Class.forName(derbyClientDriver);
        String url = "jdbc:derby://localhost:1527/student";
        String user = "app";
        String passwd = "app";

        Connection con = DriverManager.getConnection(url, user, passwd);
        //create statement
       Statement stmt = con.createStatement();
       Student st1 = new Student(1, "John", 3.2);
       Student st2 = new Student(2, "Marry", 4.0);
       insertStudent(stmt, st1);
       insertStudent(stmt, st2);
        stmt.close();
        con.close();
    }
    public static void printAllStudent(ArrayList<Student> StudentList) {
        for(Student st : StudentList) {
           System.out.print(st.getId() + " ");
           System.out.print(st.getName() + " ");
           System.out.println(st.getGPA() + " ");
       }
    }
    
    public static ArrayList<Student> getAllStudent (Connection con) throws SQLException {
        String sql = "select * from student order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> StudentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           Student student = new Student();
           student.setId(rs.getInt("id"));
           student.setName(rs.getString("name"));
           student.setGPA(rs.getDouble("salary"));
           StudentList.add(student);
       }
       rs.close();
       return StudentList;
       
    }
    
   public static Student getStudentById(Statement stmt, int id) throws SQLException {
       Student st = null;
       String sql = "select * from student where id = " + id;
       ResultSet rs = stmt.executeQuery(sql);
       if (rs.next()) {
           st = new Student();
           st.setId(rs.getInt("id"));
           st.setName(rs.getString("name"));
           st.setGPA(rs.getDouble("gpa"));
       }
       return st;
   } 
   public static void insertStudent(Statement stmt, Student st) throws SQLException {
       /*String sql = "insert into employee (id, name, salary)" +
                     " values (5, 'Mark', 12345)";*/
        String sql = "insert into student (id, name, gpa)" +
                     " values (" + st.getId() + "," + "'" + st.getName() + "'" + "," + st.getGPA() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
   } 
   public static void deleteStudent(Statement stmt, Student st) throws SQLException {
       String sql = "delete from student where id = " + st.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("delete " + result + " row");
   }
   public static void updateStudentSalary(Statement stmt, Student st) throws SQLException {
       String sql = "update student set salary  = " + st.getGPA() + 
               " where id = " + st.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateStudentName(Statement stmt, Student st) throws SQLException {
       String sql = "update student set name  = '" + st.getName() + "'" + 
               " where id = " + st.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   
   public static void insertStudentPreparedStatement(Connection con, Student st) throws SQLException {
       String sql = "insert into student (id, name, gpa)" + 
               " values (?,?,?)";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, st.getId());
       ps.setString(2, st.getName());
       ps.setDouble(3, st.getGPA());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Insert " + result + " row");
   }
   public static void deleteStudentPreparedStatement(Connection con, Student st) throws SQLException {
       String sql ="delete from student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, st.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Delete " + result + " row");
   }
   public static void updateStudentSalaryPreparedStatement(Connection con, Student st) throws SQLException {
       String sql = "update student set gpa  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setDouble(1, st.getGPA());
       ps.setInt(2, st.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateStudentNamePreparedStatement(Connection con, Student st) throws SQLException {
       String sql = "update student set name  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setString(1, st.getName());
       ps.setInt(2, st.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static Student getStudentByIdPreparedStatement(Connection con, int id) throws SQLException {
       Student st = null;
       String sql = "select * from student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, id);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
           st = new Student();
           st.setId(rs.getInt("id"));
           st.setName(rs.getString("name"));
           st.setGPA(rs.getDouble("gpa"));
       }
       return st;
   }
}