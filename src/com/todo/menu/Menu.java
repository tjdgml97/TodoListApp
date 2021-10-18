package com.todo.menu;

import com.todo.service.TodoUtil;

public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList 관리 명령어 사용법>");
        System.out.println("1.add >> 항목추가");
        System.out.println("2.del >> 항목삭제");
        System.out.println("3.edit >> 항목수정 ");
        System.out.println("4.ls >> 전체목록 ");
        System.out.println("5.ls_name >> 제목순으로 정렬 ");
        System.out.println("6.ls_name_desc >> 제목역순으로 정렬 ");
        System.out.println("7.ls_date >> 날짜순으로 정렬 ");
        System.out.println("8.ls_date_desc >> 날짜순으로 정렬 ");
        System.out.println("9.find >> 키워드 찾기 ");
        System.out.println("10.find_cate >> 키워드로 카테고리  찾기 ");
        System.out.println("11.ls_cate >> 카테고리 출력  ");
        System.out.println("12.show_D-day >> D-7 일정 확인  ");
       // System.out.println("13.list_D-day >> D-day 항목 출력  ");
        System.out.println("13.comp>> 항목 완료체크  ");
        System.out.println("14.ls_comp>>완료한 항목 정렬 ");



       // System.out.println("10.ls_cate >> 카테고 찾기 ");

        System.out.println("14.exit >> 종료 ");
      
    }
    public static void prompt()
    {
    	System.out.print("\n명령어를 입력하세요 > ");
    }
}
