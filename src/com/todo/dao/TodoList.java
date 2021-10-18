package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

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
			String sql = "insert into list (title, memo, category, current_date, due_date,priority,com)"
					+ "values (?,?,?,?,?,?,?);";
			
			int records = 0;
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String category = st.nextToken();
				String title= st.nextToken();
				String description= st.nextToken();
				String due_date= st.nextToken();
				String current_date= st.nextToken();
				//String priority= st.nextToken();
				//String with= st.nextToken();
				//String current_date= st.nextToken();

				
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
		String sql= "insert into list(title, memo, category, current_date, due_date,priority,with)"
				+("values (?,?,?,?,?,?,?);");
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getPriority());
			pstmt.setString(7, t.getWith() );
			

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
				String priority = rs.getString("priority");
				String with = rs.getString("with");

				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				

				TodoItem t = new TodoItem(priority, with,  category, title, description, due_date,current_date);
				
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
	
	*/
	
	
	
	
	
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
				String dday  = rs.getString("dday");
				String with = rs.getString("with");
				String priority = rs.getString("priority");

				    
				

				TodoItem t = new TodoItem(priority,with,dday,category,title, description, due_date);

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
		String sql = "update list set title=?,memo=?,category=?,current_date=?,due_date=?,priority=?,with=?"
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
			pstmt.setString(7,t.getPriority());
			pstmt.setString(8,t.getWith());

			count = pstmt.executeUpdate();
			pstmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count ;
	}
	public int deleteItem(int index) {
		String sql = "delete from list where id = ?;";
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
				
				String priority = rs.getString("priority");
				String with= rs.getString("With");
				//String dday = rs.getString("dday");

				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
/////추가해야함 
				TodoItem t = new TodoItem(priority,with,category,title,description, due_date,current_date);
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
				String priority = rs.getString("priority");
				String with = rs.getString("with");
				// dday = rs.getString("dday");

				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				
				
				
				
				TodoItem t = new TodoItem(priority,with,category, title, desc, due_date, current_date);
						//TodoItem(category,title, description, due_date);
				t.setId(id);
				//t.setCurrent_date(current_date);
				list.add(t);
				}
				stmt.close();

				}catch(SQLException e) {
				e.printStackTrace();
				}
				return list;
			
		}
		
		
		
		public Boolean isDuplicate(String title) {

			
			PreparedStatement pstmt;
			title = "%"+title+"%"	;			

			int count = 0;
			try {
				//
				String sql = "select title from list where title like ?";
						//"select count(title) from list where title = title ;";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,title);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					count++;
				}

				//ResultSet rs = pstmt.executeQuery();
		//		rs.next();
		//		count = rs.getInt("count(title)");
				pstmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(count > 0) 
			 return true;
			 return false;
		}
		
		public int add_dday (int index)
		{    
			/*
			 *month - 그 달 
			 *day- 일수 
			 *month_day 별 일수넣어줌  - month - 1이면 month_day 
			 *id 입력받음
			 *id의 내용 select - 조회해옴
			 *그중 마감 날짜 가져옴
			* 마감날짜중 / 빼줌
			* 마감날짜 - 7 해줌
			*vbut 7<=num 
			* month -1 의 day를가져옴 -> month 별 day 알기 함수로 이동 //over = true
			* if over == true / day - 7 -> ?/?/?  -> month-1  / day-7
			* over== false = day-7 
			* insert - > 같은 내용. 함수 - string 넣어주고  날짜만 바꿔서 넣어줌 
			*내용  + [7일전입니다] - 이렇게 / 마감일자  -수정
			*
			 */
			
			int month =0;
			int m_day=0; // m 별 일수 
			int day =0; //일수
			int year=0 ;
			//String due_date;
			String ddate; 
			//int count = 0;
			String line = null;
			//조회 
			
			PreparedStatement pstmt;
			
			try {
			String sql = "select * from list where id = ?";
			//Statement stat1 = conn.createStatement();
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1,index);
			ResultSet rs1 = pstmt.executeQuery();
			while(rs1.next()) {
				//String id = rs1.getString("id");
				//String name = rs1.getString("name");
				String due_date = rs1.getString("due_date");
				line = due_date;
			}
			
			
			 //stat1.close();
			
			//for(int i=0; i<3; i++) {
			StringTokenizer st = new StringTokenizer(line,"/");
			
			year =Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken());
			day = Integer.parseInt(st.nextToken());
			System.out.println(year+"/"+month+day);
			

			//
			if(day <=7)
			{
				if(month == 2) 
				{
					if(  ((year%4==0)&&(year%10!=0))||(year%400==0))
					{
						m_day = 29;
					}
					else 
						m_day = 28;
				}
				else if((month == 4)||(month == 6)||(month == 9)||(month == 11))
				{
					m_day= 30;
				}
				else 
				{
					m_day = 31;
				}
				
			}
			
			day = m_day + day - 7 ;
			
			//month = month-1;
			
			//다시 입력해줌 
			//insert - d-day 에!!
			
			pstmt.close();
			
			}catch (Exception e)
			{
				e.printStackTrace();
			} 
			

			String new_year = Integer.toString(year);
			String new_month = Integer.toString(month);
			String new_day = Integer.toString(day);

			
			
			ddate =	new_year+"/"+new_month+"/"+new_day;
			System.out.println(index+"번의 마감일 7일전은 "+ddate+" 입니다.");
			
			//int count =d_update(ddate,index );
			int count  = 0;
			return count ;
			//데이터 update (d-day 추가 )
			
		}
			
			
			public  int d_update (String s,int index ) {
			
			String sql = "update list set dday=D-7 : ?"+" where id = ?;";
			PreparedStatement pstmt;
			int count1 = 0;
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,s);
				pstmt.setInt(2,index);
				count1 = pstmt.executeUpdate();
				pstmt.close();
			} catch(SQLException e)
			{
				e.printStackTrace();
			}
		
			return count1;
		}
		

			
		//d_day 조회- 넘겨주
		
		public ArrayList<TodoItem> d_getList() {
			ArrayList<TodoItem> list = new ArrayList<TodoItem>();
			PreparedStatement pstmt ;
			
			//D-7 이라는 거를 - d-day할때 디폴트로 입력을 할것!! 
			// 기준으로 가져와서  -> 새로운 리스트를 만듦  -> 그 것을 화면에 출력 !!!! -
			
			String keyword = "%"+"D-7";
			
			try {
				
				String sql = "SELECT * FROM list WHERE dday like ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,keyword);
				
				// 카테고리에 - ls_cate. find_cate ->참고하기 
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("id");
					String category = rs.getString("category");
					String title = rs.getString("title");
					String description = rs.getString("memo");
					String due_date = rs.getString("due_date");
					String current_date = rs.getString("current_date");
					String priority = rs.getString("priority");
					String with= rs.getString("with");
					String dday = rs.getString("dday");

					TodoItem t = new TodoItem(priority,with,dday,category,title, description, due_date);
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
		
	public int completeItem(int num)
	{
		
		String sql = "UPDATE list set is_complete=1 " + "where id=?;";
		PreparedStatement pstmt;

		
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,1);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return count;
				
	}
	
	
	public ArrayList<TodoItem> getList(int num) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list WHERE is_completed like 1";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = 1;//rs.getInt("is_completed");
				String priority = rs.getString("priority");
				String with = rs.getString("with");

				TodoItem t = new TodoItem(is_completed,priority,with,category,title, description, due_date,current_date);

				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
			
			//String날짜를 =>br.buffered reader 이용해서 - > 아예  / 기준으로 자른 후 
			
			
			
			
}
		

		
		