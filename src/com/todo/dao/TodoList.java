package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
//import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	
	private List<TodoItem> list;
	//다른곳에서 db사용
	Connection conn;
	
	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}
	
	

		
		

	
	//+
	public void importData(String filename) {
	
		try {
			BufferedReader br = new BufferedReader(new FileReader (filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)"
					+ "values (?,?,?,?,?);";
			
			int records = 0;
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String category = st.nextToken();
				String title= st.nextToken();
				String description= st.nextToken();
				String due_date= st.nextToken();
				String current_date= st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			
			System.out.println(records + "records read!!");
			br.close();
		
		}catch (Exception e) {
			e.printStackTrace();


				
			}
		}
	
	//+
	public int addItem(TodoItem t ) {
		String sql= "insert into list(title, memo, category, current_date, due_date)"
				+("values (?,?,?,?,?);");
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			count  = pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//+
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt ;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category,title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);

			}
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	//+
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt ;
		
		keyword = "%"+keyword+"%";
		
		try {
			
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			pstmt.setString(2,keyword);
			
			// 카테고리에 - ls_cate. find_cate ->참고하기 
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category,title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);

			}
			pstmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	//+
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement() ;
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	//+
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?,memo=?,category=?,current_date=?,due_date=?"
				+"where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt=  conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			pstmt.setInt(6,t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count ;
	}
	public int deleteItem(int index) {
		String sql = "delete from list where id?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql=  "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			
			//todoitem을담는게 아니라서 - 다른 위치에서 정의해줘도될듯 
			while (rs.next()) {
				//int id = rs.getInt("id");
			//	String[] category = rs.getString("category");
				String category = rs.getString("category");
				//TodoItem t = new TodoItem(category);
				list.add(category);
			}
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//중복 체크 
	
	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem>list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			ResultSet rs = pstmt.executeQuery();
			

			while (rs.next()) {
				//int id = rs.getInt("id");
			//	String[] category = rs.getString("category");
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category,title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);

			}
			pstmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
		
		
		}
	
		public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
			ArrayList<TodoItem>list = new ArrayList<TodoItem>();
			Statement stmt;
			try {
				stmt= conn.createStatement();
				String sql= "SELECT * FROM list ORDER BY "+orderby;
				if(ordering==0)
					sql += " desc";
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category,title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
				}
				stmt.close();

				}catch(SQLException e) {
				e.printStackTrace();
				}
				return list;
			
		}
		
		/*
		
		public boolean isDuplicate(String title){
			
			/*
			public int getCount() {
				Statement stmt;
				int count = 0;
				try {
					stmt = conn.createStatement() ;
					String sql = "SELECT count(id) FROM list;";
					ResultSet rs = stmt.executeQuery(sql);
					rs.next();
					count = rs.getInt("count(id)");
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return count;
			}
			
			
			
			
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			
		*/
			
			
		
				
			//title = "%"+title+"%";

			
			// 카테고리에 - ls_cate. find_cate ->참고하기 
		
				
				
		
					
					
				/*
			
			PreparedStatement pstmt;
			keyword = "%"+keyword+"%";

			int count = 0;
			try {
			pstmt = conn.createStatement() ;
			String sql = "SELECT count(*) FROM LIST WHERE title like ?";
			pstmt.setString(1,keyword);
			pstmt.setString(2,keyword);
			ResultSet rs = pstmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(title)");
			stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (count>=1) return true;
			else return false;
			
		
		}
		
		*/
		

}


/*
public class TodoList {
	private List<TodoItem> list;


	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
		
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}
	
	//util 에 대신 구현 
	/*
	list 에 있는 item 다 show 
	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + myitem.getDesc());
		}
	}
	*/
	/*
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	//list: todoitem 을 arraylist로 만든
	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			//중복된 제목 방지  
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
}

*/