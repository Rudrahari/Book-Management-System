package BookRegistrationApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import Utility.DbUtils;

public class BookMain {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		final String VIEW_QUERY="select * from bookdetails";
		final String INSERT_QUERY=
				 "INSERT INTO bookdetails"
				+ "(book_name,book_price,author_name) VALUES"
				+"(?,?,?)";
		final String UPDATE_QUERY="UPDATE bookdetails "
				+"SET book_name=?,book_price=?,author_name=?"
				+"WHERE id= ?";
		final String DELETE_QUERY="DELETE FROM bookdetails WHERE id=?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int choice;
		while(true)
		{
			System.out.println("Select an Option:");
			System.out.println("\n1.Add a Boook");
			System.out.println("2.Update a Book");
			System.out.println("3.Delete a Book");
			System.out.println("4.View Books");
			System.out.println("5.Exit\n");
			System.out.print("Your Option: ");
			choice=sc.nextInt();
			if(choice==1)
			{ 
				try(   
						Connection con=DriverManager.getConnection(DbUtils.BOOK_URL,DbUtils.USERNAME,DbUtils.PASSWORD);
				PreparedStatement stmt=con.prepareStatement(INSERT_QUERY);
				 )
				{
					String authorName;
					String bookName;
					double bookPrice;
					System.out.println("Enter book Name: ");
					sc.nextLine();
					bookName=sc.nextLine();
					System.out.println("Enter book Price: ");
					bookPrice=sc.nextDouble();
					System.out.print("Enter book author: ");
					sc.nextLine();
					authorName=sc.nextLine();
					
					stmt.setString(1, bookName);
					stmt.setDouble(2, bookPrice);
					stmt.setString(3, authorName);
					 
				
					 int result=stmt.executeUpdate();
					 
					 if(result==1)
					 {
						 System.out.println("Book Added Sucessfully");
					 }
					 else
					 {
						 System.out.println("Data can't be Inserted");
					 }
				}
				catch(SQLException E)
				{
					E.printStackTrace();
				}
			}
			else if(choice==2)
			{
				int bookId;
				System.out.print("Enter book Id to Update: ");
				bookId=sc.nextInt();
				try(Connection con=DriverManager.getConnection(DbUtils.BOOK_URL,DbUtils.USERNAME,DbUtils.PASSWORD);
				
				PreparedStatement stmt=con.prepareStatement(UPDATE_QUERY);)
				{
					String authorName;
					String bookName;
					double bookPrice;
					System.out.println("Enter new book Name: ");
					sc.nextLine();
					bookName=sc.nextLine();
					System.out.println("Enter new book Price: ");
					bookPrice=sc.nextDouble();
					System.out.print("Enter new book author: ");
					sc.nextLine();
					authorName=sc.nextLine();
					
					stmt.setString(1, bookName);
					stmt.setDouble(2, bookPrice);
					stmt.setString(3, authorName);
					stmt.setInt(4, bookId);
					
					int result=stmt.executeUpdate();
					 
					 if(result==1)
					 {
						 System.out.println("Book Updated Sucessfully ");
					 }
					 else
					 {
						 System.out.println("Data can't be Updated");
					 }
					
					
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			else if(choice==3)
			{
				try(Connection con=DriverManager.getConnection(DbUtils.BOOK_URL,DbUtils.USERNAME,DbUtils.PASSWORD);
						PreparedStatement stmt=con.prepareStatement(DELETE_QUERY);
						 )
				{
					int id;
					System.out.println("Enter book ID: ");
					id=sc.nextInt();
					stmt.setInt(1, id);
					
					int result=stmt.executeUpdate();
					if(result==1)
					{
						System.out.println("Book Deleted Successully");
					}
					else
					{
						System.out.println("Can't be");
					}
					
				}
				catch(SQLException E) {
					E.printStackTrace();
				}
			}
			else if(choice==4)
			{
				try(Connection con=DriverManager.getConnection(DbUtils.BOOK_URL,
						DbUtils.USERNAME,DbUtils.PASSWORD);
						  Statement pstmt=con.createStatement();)
				{
					ResultSet rSet=pstmt.executeQuery(VIEW_QUERY);
					ArrayList<Book> bookDetails=new ArrayList<Book>();
					while(rSet.next())
					{
						int id=rSet.getInt("id");
						String bookName=rSet.getString("book_name");
						double bookPrice=rSet.getDouble("book_price");
						String authorName=rSet.getString("author_name");
						
						Book books=new Book();
						books.setId(id);
						books.setAuthorName(authorName);
						books.setBookName(bookName);
						books.setBookPrice(bookPrice);
						bookDetails.add(books);
					}
					System.out.println(bookDetails);
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				
			}
			else if(choice==5)
			{
				System.out.println("Thanks for visiting,come soon!!");
				break;
			}
			else
			{
				System.out.println("Invalid Choice, Please Refer the Menu");
				continue;
			}
		}
	}

}
