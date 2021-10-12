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
    
   //private Date current_date;
    
    public String toSaveString() {
    	return category+"##"+title+"##"+desc+"##"+due_date+"##"+current_date+"\n";
    }


    public TodoItem( String category, String title, String desc, String due_date ){
        this.title=title;
        this.desc=desc;
        //2
        x = new SimpleDateFormat("YYYY/MM/dd H시m분ss초 ");
        this.current_date =x.format(today) ;
        this.category = category;
        this.due_date = due_date;
        
        //this.current_date=new Date();
    }
    
    public TodoItem(String category, String title, String desc,String due_date, String current_date){
        this.category = category;
    	this.title=title;
        this.desc=desc;
        
  
        this.current_date = current_date;
        this.due_date = due_date;
        //this.current_date=new Date();
    }
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
		return Id+" "+"["+category+"] "+title+" - " +desc +" - "+"마감:"+due_date+" - "+current_date;
	}


	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}



	
    
}
