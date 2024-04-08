package helpers;

import models.ContactListModel;
import models.ContactModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseReader {
   static String url = "jdbc:mysql://localhost:3306/phonebook";
   static String username = "root";
    static String password = "Vp12345$";


    public static ContactModel readContactFromDatabase(String id) throws SQLException, SQLException {


        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connection successful!");
        String query = "Select * From contacts  where id= ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        ContactModel contactModel=new ContactModel();
        if(resultSet.next()){

            contactModel.setId(resultSet.getString("id"));
            contactModel.setName(resultSet.getString("name"));
            contactModel.setLastName(resultSet.getString("lastname"));
            contactModel.setEmail(resultSet.getString("email"));
            contactModel.setPhone(resultSet.getString("phone"));
            contactModel.setAddress(resultSet.getString("address"));
            contactModel.setDescription(resultSet.getString("description"));

        }
        else {
            System.out.println("No contact found using id "+id);
        }
        preparedStatement.close();
        connection.close();
        return contactModel;
    }

    //**********************************************
    public static List<ContactModel> readAllContactsFromDatabase() throws SQLException {
       List<ContactModel> contacts  = new ArrayList<>();
       // ContactListModel contacts= ContactListModel.builder().build();
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connection successful!");
        String query = "Select * From contacts";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()){
            ContactModel contactModel = new ContactModel();

            contactModel.setId(resultSet.getString("id"));
            contactModel.setName(resultSet.getString("name"));
            contactModel.setLastName(resultSet.getString("lastname"));
            contactModel.setEmail(resultSet.getString("email"));
            contactModel.setPhone(resultSet.getString("phone"));
            contactModel.setAddress(resultSet.getString("address"));
            contactModel.setDescription(resultSet.getString("description"));
            contacts.add(contactModel);
        }

        statement.close();
        connection.close();
        return contacts;
    }

    public static void main(String[] args) throws SQLException {


       List<ContactModel> cont = readAllContactsFromDatabase();
        for (ContactModel contactModel : cont){
            System.out.println("Record: "+ contactModel.toString());
        }
//        ContactModel contact = readContactFromDatabase("");
//        System.out.println("Contact name: "+contact.getName());
//        System.out.println("An email: "+contact.getEmail());
    }


}
