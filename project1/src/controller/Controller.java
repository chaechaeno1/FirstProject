
package controller;

import java.util.HashMap;
import java.util.Map;

import service.AdminService;
import service.CustomerService;
import service.LoginService;
import service.ManageService;
import util.ScanUtil;
import util.View;

public class Controller {
   public static Map<String, Object> sessionStorage = new HashMap<>();
   
   static AdminService adminService = AdminService.getInstance();
//   CustomerService customerService = CustomerService.getInstance();
//   LoginService loginService = LoginService.getInstance();
//   ManageService manageService = ManageService.getInstance();

   public static void main(String[] args) {
              adminService.submitsv();
   }
}
//      new Controller().start();
//   }
//
//   private void start() {
//      sessionStorage.put("login", false);
//      sessionStorage.put("loginInfo", null);
//      int view = View.HOME;
//      while (true) {
//         switch (view) {
//         case View.HOME:
//            view = home();
//            break;
//         case View.CUSTOMER_LOGIN:
//            view = loginService.login();
//            break;
//         case View.CUSTOMER_SIGNUP:
//            view = customerService.signUp();
//            break;
//         case View.MANAGE_LOGIN:
//            view = manageService.manager();
//            break;
//
//         }
//      }
//   }
//
//   private int home() {
//      System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
//      System.out.println("=*=*=*=*=*=*<<버스타요터미널시스템>>*=*=*=*=*=*=");
//      System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
//      System.out.println();
//      System.out.println("1.로그인     2.회원가입     5.관리자용");
//      System.out.println();
//      System.out.println("-------------------------------------------");
//      System.out.print("번호 입력 >> ");
//      switch (ScanUtil.nextInt()) {
//      case 1:
//         return View.CUSTOMER_LOGIN;
//      case 2:
//         return View.CUSTOMER_SIGNUP;
//      case 5:
//         return View.MANAGE_LOGIN;
//
//      default:
//         return View.HOME;
//      }
//   }
//}