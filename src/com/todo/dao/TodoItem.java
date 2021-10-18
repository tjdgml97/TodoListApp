package com.todo.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.todo.service.DbConnect;

public class TodoItem {
	//Connection conn;
	
	/*
	public TodoItem() {
		this.conn = DbConnect.getConnection();
	}
	*/
	
    private String title;
    private String desc;
    //1
    private Date today = new Date();
    private String current_date;
    private SimpleDateFormat x ;
    private String  category;
    private String due_date;
    private int Id;
    //private String D;
    private String dday;
    private String priority; //우선순위 
    private int is_completed=0; //com; //완료  
    private String with;
    
   //private Date current_date;
    
    public String toSaveString() {
    	return category+"##"+title+"##"+desc+"##"+due_date+"##"+current_date+"##"+priority+"##"+with+"\n";
    }


    ///add 만 필요  // current 뺴고 - 6
    public TodoItem(String priority, String with, String category, String title, String desc, String due_date ){
        this.title=title;
        this.desc=desc;
        //2
        x = new SimpleDateFormat("YYYY/MM/dd H시m분ss초 ");
        this.current_date =x.format(today) ;
        this.category = category;
        this.due_date = due_date;
        this.with = with;
        this.priority = priority;
        
        //this.current_date=new Date();
    }
    
    
    
    //update 등  - current 지정 필요 x - 7 ( 그냥들고는 옴 ) 
    public TodoItem(String priority,String with, String category, String title, String desc,String due_date,String current_date){
        this.category = category;
    	this.title=title;
        this.desc=desc;
        
  
        //this.current_date = current_date;
        this.due_date = due_date;
       // this.dday = dday;
        this.priority = priority ;
        this.with = with;
        this.current_date = current_date;
        //this.current_date=new Date();
    }
    

			
    //list 를 위한것 - dday 포함 // 8 개 다 포함 
    

    @Overloading
    public TodoItem(int is_completed,String priority,String with,String category, String title, String desc,String due_date,String current_date){
        this.category = category;
    	this.title=title;
        this.desc=desc;
        this.is_completed = is_completed;
  
        //this.current_date = current_date;
        this.due_date = due_date;
       // this.dday = dday;
        this.priority = priority ;
        this.with = with;
        this.current_date = current_date;
        //this.current_date=new Date();
    }
    
    @Overloading
    /*
    public TodoItem(String priority,String with, String dday ,String category, String title, String desc,String due_date,String current_date){
        this.category = category;
    	this.title=title;
        this.desc=desc;
        
  
        //this.current_date = current_date;
        this.due_date = due_date;
        this.dday = dday;
        this.priority = priority ;
        this.with = with;
        this.current_date = current_date;
        //this.current_date=new Date();
    }
    
    
  
    */
    
    public TodoItem(String category){
    	this.category = category;
    }



	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
    	//3
    	
        return current_date;
    }

    public void setCurrent_date(String current_date) {
    	//4
        this.current_date = current_date;
    }

    
    	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getDue_date() {
		return due_date;
	}


	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	@Override
	public String toString() {
		if(is_completed==0) return Id+"["+category+"] "+title+" - " +desc +" - "+"마감:"+due_date+" - "+current_date+" - "+"*:"+priority+" - "+with;
		else  return Id+" "+"["+category+"] "+title+" - " +desc +"[v]"+" - "+"마감:"+due_date+" - "+current_date+" - "+"*:"+priority+" - "+with;

	}
	
	public String totoString() {
		return Id+"< "+dday+" >"+" "+"["+category+"] "+title+" - " +desc +" - "+"마감:"+due_date+" - "+current_date;
	}

	
	

	


	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}


	public String getDday() {
		return dday;
	}


	public void setDday(String dday) {
		this.dday = dday;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public int getIs_completed() {
		return is_completed;
	}


	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}


	public String getWith() {
		return with;
	}


	public void setWith(String with) {
		this.with = with;
	}


	

	
	
	


	
    
}
