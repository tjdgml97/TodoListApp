package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    //1
    private Date today = new Date();
    
    private String current_date;
    private SimpleDateFormat x ;
    
   //private Date current_date;
    
    public String toSaveString() {
    	return title+"##"+desc+"##"+current_date+"\n";
    }


    public TodoItem(String title, String desc){
        this.title=title;
        this.desc=desc;
        //2
        x = new SimpleDateFormat("YYYY/MM/dd H시m분ss초 ");
        this.current_date =x.format(today) ;
        
        //this.current_date=new Date();
    }
    
    public TodoItem(String title, String desc,String current_date){
        this.title=title;
        this.desc=desc;
        //2
        
        this.current_date = current_date;
        
        //this.current_date=new Date();
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

    	//수정고민. 
	@Override
	public String toString() {
		//5
		return "[" +title+ "] "+ desc + "- [시간]" + current_date;
	}
    
    
}
