package ServerBackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import MysqlQueries.SqlQueries;

public class DataBaseManager {
    
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    public DataBaseManager() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notesmuscle", "root", "uglyday@14"); 
       
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery(SqlQueries.getAllUsers);

        while(resultset.next()){
            System.out.println(resultset.getString("first_name"));
        }
    
    }

}
