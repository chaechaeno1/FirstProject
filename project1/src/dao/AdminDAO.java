package dao;

import util.JDBCUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDAO {
      //싱글톤 패턴
      private static AdminDAO instance = null;
      private AdminDAO() {}
      public static AdminDAO getInstance() {
         if(instance == null) instance = new AdminDAO();
         return instance;
      }
      
      JDBCUtil jdbc = JDBCUtil.getInstance();
      

   public List<Map<String, Object>> route() {
      return jdbc.selectList("select rt_id, rt_name, rt_price, rt_pnum, rt_time from route order by rt_id");
   }
   
   public List<Map<String, Object>> buslist() {
      return jdbc.selectList("SELECT BUS_ID, BUS_NUM, BUS_GRADE FROM BUS ORDER BY BUS_ID");
   }
   
   public int insert(List<Object> param) {
      String sql="INSERT INTO SERVICE(RT_ID, SV_DATE, BUS_ID, SV_ID) ";
       sql=sql+" VALUES(?, ?, ?, ?) ";        
       return jdbc.update(sql, param);
       //public int update(String sql, List<Object> param) { 실행
   }
   
   

}









//   public String createBoardNumber(String dt){
//   String sql="SELECT fn_create_board_number('"+dt+"') FROM DUAL";
//   
//   return jdbc.selectValue(sql);
//   }
//
//   public int insert(List<Object> param) {
//   String sql="INSERT INTO BOARD(BOARD_NUMBER, TITLE, CONTENT, MEM_ID, DATETIME) ";
//    sql=sql+" VALUES(?, ?, ?, ?, ?) "; 
//    
//    return jdbc.update(sql, param);
//    //public int update(String sql, List<Object> param) { 실행
//   }
//}

