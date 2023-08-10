package util;

import java.util.Scanner;

public class ScanUtil {
   // 스캐너를 손쉽게 사용할 수 있는 static 메서드를 가지고있음
   static Scanner sc = new Scanner(System.in);
   public static String nextLine() { 
      return sc.nextLine();
   }
   public static int nextInt() {
      while(true) { //무한루프
         try {
            int result = Integer.parseInt(sc.nextLine());
            //문자열로 받은 입력값을 정수형으로 변환
            return result;
         }catch (NumberFormatException e) {
            //nextLine의 값이 숫자로 변형되어질 수 없는 값이 들어오면 잘못입력!!을 출력하는 예외처리
            System.out.println("잘못 입력!!");
         }
      }
   }
}