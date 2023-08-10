package service;

import java.util.Map;

import controller.Controller;
import dao.ManageDAO;
import util.ScanUtil;
import util.View;

public class ManageService {

   private static ManageService instance = null;
   private ManageService() {}
   public static ManageService getInstance() {
      if(instance == null) 
         instance = new ManageService();
      return instance;
   }
   
   public static int loginCount = 0;
   
   ManageDAO dao = ManageDAO.getInstance();
   int pageNo = 0;
   
   public int manager(){
      System.out.print("아이디 입력>>> ");
      String id = ScanUtil.nextLine();
      System.out.print("비밀번호 입력>>> ");
      String pw = ScanUtil.nextLine();
      
      Map<String, Object> result = dao.Manager(id,pw);
      
      if(result != null && result.get("AD_ID").equals(id)){
         Controller.sessionStorage.put("login", true);
         Controller.sessionStorage.put("loginInfo", result.get("AD_ID"));
         System.out.println("관리자가 로그인 되었습니다");
         pageNo = View.HOME;
      }else{
         System.out.println("다시 로그인해주세요!");
         pageNo = View.HOME;
      }
      return pageNo;
   }
}