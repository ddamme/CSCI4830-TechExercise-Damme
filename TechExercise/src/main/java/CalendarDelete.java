import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CalendarDelete")
public class CalendarDelete extends HttpServlet 
{
   private static final long serialVersionUID = 1L;

   public CalendarDelete() 
   {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {
      String name = request.getParameter("name");
      String date = request.getParameter("date");
      String time = request.getParameter("time");
      String location = request.getParameter("location");
      search(name, date, time, location, response);
   }

   void search(String name, String date, String time, String location, HttpServletResponse response) throws IOException 
   {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Events Deleted";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try 
      {
         DBConnectionDamme.getDBConnection(getServletContext());
         connection = DBConnectionDamme.connection;
         
         ArrayList<String> input = new ArrayList<String>();
         String selectSQL = "";
         if (!name.isEmpty())
         {
        	 selectSQL = selectSQL.concat(" AND WHERE NAME = ?");
        	 input.add(name);
         }
         if (!date.isEmpty())
         {
        	 selectSQL = selectSQL.concat(" AND WHERE DATE = ?");
        	 input.add(date);
         }
         if (!time.isEmpty())
         {
        	 selectSQL = selectSQL.concat(" AND WHERE TIME = ?");
        	 input.add(time);
         }
         if (!location.isEmpty())
         {
        	 selectSQL = selectSQL.concat(" AND WHERE LOCATION = ?");
        	 input.add(location);
         }
         System.out.println(selectSQL);
         if (selectSQL.length() > 0)
         {
        	 selectSQL = selectSQL.substring(4);
         }
         selectSQL = "DELETE FROM CalendarTable".concat(selectSQL);
         System.out.println(selectSQL);
         preparedStatement = connection.prepareStatement(selectSQL);
         for (int i = 0; i < input.size(); i++)
         {
        	 preparedStatement.setString(i + 1, input.get(i));
         }
         preparedStatement.execute();
         
         out.println("<a href=/TechExercise/calendarDelete.html>Back</a> <br>");
         out.println("</body></html>");
         preparedStatement.close();
         connection.close();
      }
      catch (SQLException se) 
      {
         se.printStackTrace();
      } 
      catch (Exception e) 
      {
         e.printStackTrace();
      } 
      finally 
      {
         try 
         {
            if (preparedStatement != null)
               preparedStatement.close();
         } 
         catch (SQLException se2) 
         {
         }
         try 
         {
            if (connection != null)
               connection.close();
         } 
         catch (SQLException se) 
         {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {
      doGet(request, response);
   }

}