package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class ManageDAO {
   
   private static ManageDAO instance = null;
   private ManageDAO() {}
   public static ManageDAO getInstance() {
      if(instance == null) 
         instance = new ManageDAO();
      return instance;
   }
   

   JDBCUtil jdbc = JDBCUtil.getInstance();
   
   public Map<String, Object> Manager(String id, String pw){
      String sql = "SELECT * FROM ADMIN WHERE AD_ID = ? AND AD_PW = ?";
      List<Object> param = new ArrayList<Object>();
      param.add(id);
      param.add(pw);
      
      return jdbc.selectOne(sql, param);
   }
}