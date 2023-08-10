package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class CustomerDAO {
   private static CustomerDAO instance = null;
   private CustomerDAO() {}
   public static CustomerDAO getInstance() {
      if(instance == null) instance = new CustomerDAO();
      return instance;
   }
   
   JDBCUtil jdbc = JDBCUtil.getInstance();
   
   public Map<String, Object> login(String id, String pass) {
      return jdbc.selectOne("SELECT * FROM CUSTOMER "
            + " WHERE CST_ID='"+id+"' AND CST_PW='"+pass+"' ");
   }
   public int signUp(List<Object> param) {
      return jdbc.update( "INSERT INTO CUSTOMER ( CST_ID, CST_PW, CST_NAME, CST_TEL, CST_AGE ) VALUES (?, ?, ?, ?, ?)", param);
   }
   
   public List<Map<String,Object>> selectAll(){
      return jdbc.selectList("SELECT * FROM CUSTOMER");
   }
}

