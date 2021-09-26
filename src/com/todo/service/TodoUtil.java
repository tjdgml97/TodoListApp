package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
//직접실행
public class TodoUtil {
	
	@SuppressWarnings("resource")
	public static void createItem(TodoList list) {
		
		String title, desc,category,due_date;
		Scanner sc = new Scanner(System.in);
		
		/*System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the title\n");
		*/
		System.out.print("[항목을 추가합니다]\n카테고리 > ");
		//System.out.print("제목 > ");
		category = sc.next();  
		System.out.print("제목 > ");
		title = sc.next(); // 문장이 아니라서 가
		sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		}
		System.out.print("내용 > ");
		desc = sc.nextLine();  
		//sc.nextLine();
		System.out.print("마감일자 > ");
		due_date = sc.nextLine().trim();
		TodoItem t = new TodoItem(category,title, desc, due_date);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	@SuppressWarnings("resource")
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목을 삭제합니다]\n삭제할 번호를 입력하세요 > ");

		//System.out.print("[항목을 삭제합니다]\n삭제할 제목을 입력하세요 > ");
		Integer num = sc.nextInt();
		Integer count = 0;
		
		for (TodoItem item : l.getList()) {
			count++;
			if (num.equals(count)) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}


	@SuppressWarnings("resource")
	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		//System.out.print("[항목을 수정합니다]\n수정할 제목을 입력하세요 > ");
		System.out.print("[항목을 수정합니다]\n수정할 일련번호를 입력하세요 > ");

	//Int 로 수정
		Integer num = sc.nextInt();
		 
		
		/*if (!l.isDuplicate(title)) {
			System.out.println("입력하신 제목이 없습니다.");
			return;
		}
		*/
		
		System.out.print("새로운 카테고리를 입력하세요 > ");
		String new_category = sc.next();
		

		System.out.print("새로운 제목을 입력하세요 > ");
		String new_title = sc.next().trim();
		
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다. ");
			return;
		}
		
		System.out.print("새로운 내용을 입력하세요 > ");
		String new_description = sc.next().trim();
		
		sc.nextLine();   
		

		
		System.out.print("새로운 마감일를 입력하세요 > ");
		String new_due_date = sc.next().trim();
		
		


	
		Integer count= 0; 
		for (TodoItem item : l.getList()) {
			count++;
			if(count.equals(num)) {
			// (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_category,new_title, new_description,new_due_date);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		
	int count = 0;
		System.out.println("[전체목록]");
		
		for (TodoItem item : l.getList()) {
			count++;
			System.out.print(count+". ");
			System.out.println(item.toString());
		}
		System.out.println("전체목록은 총 "+count+"입니다.");
	}
			
			
			
			
			
			
			
			
			
			
			
			
			
	
	public static void saveList(TodoList l, String filename) {
		//이미 todolist l 안에 내용이다 있음 - 이것을쓰면되는ㄴ것!
		//FileWriter 위해서.........
		//tosaveString() -> 문장을 저장 
		try {
			Writer w = new FileWriter(filename);
			// list l-> 반복문 써서 - item 한개로 -> 그 아이템을 tosave 로 하면-save 형식됨
			// 그 형식을- 저장하기 
			
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			System.out.println("정보 저장이 완료되었습니다.");
			w.close();
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		//"todolist.txt"
		//BufferedReader
		//FileReader
		//StringTokenizer 사용 
		
		System.out.println("\n[ 저장된 파일 내용]");

		BufferedReader b;
		try {
			b = new BufferedReader(new FileReader(filename));
			
			String line;
			while((line = b.readLine())!= null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String load_category = st.nextToken();
				String load_title = st.nextToken();
				String load_desc= st.nextToken();
				String load_due_date = st.nextToken();
				String load_date = st.nextToken();
				
				TodoItem t = new TodoItem(load_category,load_title,load_desc,load_due_date,load_date );
				
				// 파일->todolist 의 list 에 넣어줘야함 . 
				l.addItem(t);
				
				System.out.println( l.indexOf(t)+1+". "+ t.toString());
			}
			b.close();
			System.out.println("[ 로딩 완료 되었습니다.] ");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}



	public static void findList (TodoList l, String s) {
	
		//Scanner sc = new Scanner(System.in);
		//System.out.println("키워드를 입력 ");
		int count =0; 

		for(TodoItem item : l.getList()) {
		
			//System.out.println("'"+item.getTitle()+" '");
			if(item.getTitle().contains(s)||item.getDesc().contains(s) )
			{
				System.out.print(l.indexOf(item)+1+". ");
				System.out.println(item.toString());
				count++;
			}
		}
		
		System.out.println("총 "+count+"개의 항목을 찾았습니다.");
		//////   
		
        //index = myList.indexOf(d);
	}
	
	public static void findcate (TodoList l, String s) {

		int count =0; 
		
		for(TodoItem item : l.getList()) {
			if(item.getCategory().contains(s))
			{
				System.out.print(l.indexOf(item)+1+". ");
				System.out.println(item.toString());
			
				count++;
			}
		}
		
		System.out.println("총 "+count+"개의 항목을 찾았습니다.");
		//////   
		
        //index = myList.indexOf(d);
	}
	
	/*
	public static void ls_cate (TodoList l) {
		String[] x = new String[1];
		
		int count = 1; 
		
		
		
		for(TodoItem item : l.getList() ) {
			
			if(l.indexOf(item)==0) x[0] =item.getCategory();
				

			for(int i=0; i<count ; i++) {
			if(x[i].equals(item.getCategory()))
			{
				count = count+0;
			}
			else 
			{
				x[count] = item.getCategory();
				count++;
			}
			}
		}
		
		 
		
		
		for(int i=0; i<x.length; i++) {
			System.out.print(x[i]);
			if(count!=(i-1)) System.out.print("/");

		}
	}
	
	*/
	
}
