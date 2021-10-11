package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList(); //todolist class 의 객체 생성//자동 - list 객체만들어짐 
		l.importData("todolist.txt");
		
		boolean isList = false;
		boolean quit = false;
		
		Menu.displaymenu();//메뉴를 화면에 뿌려줌 
	//	TodoUtil.loadList( l, "todolist.txt");
		
		do {
			//Menu.displaymenu();
			
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			
			case "ls_name":
				System.out.println("제목순으로 정렬되었습니다.");
				TodoUtil.listAll(l,"title",1);
				break;

			case "ls_name_desc":
			//todolist class -불려 
				System.out.println("제목역순으로 정렬되었습니다.");	
				TodoUtil.listAll(l,"title",0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬되었습니다.");
				TodoUtil.listAll(l,"due_date",1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬되었습니다.");
				TodoUtil.listAll(l,"due_date",0);
				break;
				
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "find":
				//sc.nextLine();
				String key = sc.nextLine().trim();
		
				TodoUtil.findList(l,key);
				break;
			case "find_cate" :
				
				String s = sc.nextLine().trim();
				TodoUtil.findCateList(l,s);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			
			case "exit":
				quit = true;
				break;

			default:
				System.out.println("잘못입력하셨습니다. 정확한 명령어를 입력해주세요. (도움말이 필요할시 help 입력)");
				break;
			}
			
			if(isList) TodoUtil.listAll(l); //한번에 3개 처
			//	l.listAll(); //after sort -> show  
		} while (!quit);  //false가 되기전까지 
		
		//TodoUtil.saveList( l, "todolist.txt");

	}
}
