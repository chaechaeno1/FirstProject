package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.CustomerDAO;
import util.ScanUtil;
import util.View;

public class CustomerService {
   private static CustomerService instance = null;
   private CustomerService() {}
   public static CustomerService getInstance() {
      if(instance == null) instance = new CustomerService();
      return instance;
   }
   
   CustomerDAO dao = CustomerDAO.getInstance();
   
   public int login() {
      if((boolean) Controller.sessionStorage.get("login")) {
         System.out.println("이미 로그인되어있습니다.");
         return View.HOME;
      }
      System.out.println("-- 로그인 --");
      System.out.print("아이디 >> ");
      String id = ScanUtil.nextLine();
      System.out.print("비밀번호 >> ");
      String pass = ScanUtil.nextLine();
      Map<String, Object> row = dao.login(id, pass);
      if(row == null) {
         System.out.println("아이디가 없습니다.");
      }else {
         Controller.sessionStorage.put("login", true);
         System.out.println(row.get("CST_NAME") + "님 환영합니다!");
      }
      return View.HOME;
   }
   public int signUp() {
      System.out.println("-- 회원가입 --");
      System.out.print("아이디 >> ");
      String id = ScanUtil.nextLine();
      
      System.out.print("비밀번호 >> ");
      String pw = ScanUtil.nextLine();
      
      System.out.print("이름 >> ");
      String name = ScanUtil.nextLine();
      
      System.out.print("전화번호 >> ");
      String tel = ScanUtil.nextLine();
      
      System.out.print("나이 >> ");
      String age = ScanUtil.nextLine();
      
      
      List<Object> param = new ArrayList<>();
      param.add(id);
      param.add(pw);
      param.add(name);
      param.add(tel);
      param.add(age);
      
      int result = dao.signUp(param);
      if (result>0) {
         System.out.println("회원가입이 완료되었습니다.");
      }else {
         System.out.println("회원가입 실패!");
      }
      return View.HOME;
   }
}



