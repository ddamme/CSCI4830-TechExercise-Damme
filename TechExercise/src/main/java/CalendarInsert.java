
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CalendarInsert")
public class CalendarInsert extends HttpServlet 
{
   private static final long serialVersionUID = 1L;

   public CalendarInsert() 
   {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {
      String name = request.getParameter("name");
      String date = request.getParameter("date");
      String time = request.getParameter("time");
      String location = request.getParameter("location");

      if (name.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty())
      {
    	// Set response content type
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          String title = "You must enter a valid value into all of the input boxes!";
          String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
          out.println(docType + //
                "<html>\n" + //
                "<head><title>" + title + "</title></head>\n" + //
                "<body bgcolor=\"#f0f0f0\">\n" + //
                "<h2 align=\"center\">" + title + "</h2>\n" + //
                "<ul>\n" + //
                "</ul>\n");

          out.println("<a href=/TechExercise/calendarInsert.html>Back</a> <br>");
          out.println("</body></html>");
      }
      else
      {
    	  Connection connection = null;
          String insertSql = " INSERT INTO CalendarTable (id, NAME, DATE, TIME, LOCATION) values (default, ?, ?, ?, ?)";

          try 
          {
             DBConnectionDamme.getDBConnection(getServletContext());
             connection = DBConnectionDamme.connection;
             PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
             preparedStmt.setString(1, name);
             preparedStmt.setString(2, date);
             preparedStmt.setString(3, time);
             preparedStmt.setString(4, location);
             preparedStmt.execute();
             connection.close();
          } 
          catch (Exception e) 
          {
             e.printStackTrace();
          }

          // Set response content type
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          String title = "Insert Data to DB table";
          String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
          out.println(docType + //
                "<html>\n" + //
                "<head><title>" + title + "</title></head>\n" + //
                "<body bgcolor=\"#f0f0f0\">\n" + //
                "<h2 align=\"center\">" + title + "</h2>\n" + //
                "<ul>\n" + //

                "  <li><b>User Name</b>: " + name + "\n" + //
                "  <li><b>Email</b>: " + date + "\n" + //
                "  <li><b>Phone</b>: " + time + "\n" + //
                "  <li><b>Location</b>: " + location + "\n" + //

                "</ul>\n");

          out.println("<a href=/TechExercise/calendarInsert.html>Back</a> <br>");
          out.println("</body></html>");
      }
      
     
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {
      doGet(request, response);
   }

}