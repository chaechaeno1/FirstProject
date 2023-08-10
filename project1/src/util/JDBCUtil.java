package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {
   /*
    * JDBC를 좀 더 쉽고 편하게 사용하기 위한 Utility 클래스
    * 
    * Map<String, Object> selectOne(String sql)
    * Map<String, Object> selectOne(String sql, List<Object> param)
    * List<Map<String, Object>> selectList(String sql)
    * List<Map<String, Object>> selectList(String sql, List<Object> param)
    * int update(String sql)
    * int update(String sql, List<Object> param)
    * */
   
   // 싱글톤 패턴 : 인스턴스의 생성을 제한하여 하나의 인스턴스만 사용하는 디자인 패턴
   
   // 인스턴스를 보관할 변수
   private static JDBCUtil instance = null;
   // JDBCUtil 객체를 만들 수 없게(인스턴스화 할 수 없게) private으로 제한함
   private JDBCUtil() {} 
   public static JDBCUtil getInstance() {
      if(instance == null) 
         instance = new JDBCUtil();
      return instance;
   }
   
   private String url = "jdbc:oracle:thin:@192.168.36.102:1521:xe";
   private String user = "busta";
   private String password = "java";
   
   private Connection conn = null;
   private ResultSet rs = null;
   private PreparedStatement ps = null;
   
   public List<Map<String, Object>> selectList(String sql, List<Object> param){
      // sql => "SELECT * FROM MEMBER WHERE MEM_ADD1 LIKE '%'||?||'%'"
      // sql => "SELECT * FROM JAVA_BOARD WHERE WRITER=?"
      // sql => "SELECT * FROM JAVA_BOARD WHERE BOARD_NUM > ?"
      List<Map<String, Object>> result = null;
      try {
         conn = DriverManager.getConnection(url, user, password);
         ps = conn.prepareStatement(sql);
         for(int i = 0; i < param.size(); i++) {
            ps.setObject(i + 1, param.get(i));
         }
         rs = ps.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         int columnCount = rsmd.getColumnCount();
         while(rs.next()) {
            if(result == null) result = new ArrayList<>();
            Map<String, Object> row = new HashMap<>();
            for(int i = 1; i <= columnCount; i++) {
               String key = rsmd.getColumnLabel(i);
               Object value = rs.getObject(i);
               row.put(key, value);
            }
            result.add(row);
         }
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         if(rs != null) try { rs.close(); } catch(Exception e) {}
         if(ps != null) try { ps.close(); } catch(Exception e) {}
         if(conn != null) try { conn.close(); } catch(Exception e) {}
      }
      return result;
   }
   public List<Map<String, Object>> selectList(String sql){
      // sql => "SELECT * FROM MEMBER"
      // sql => "SELECT * FROM JAVA_BOARD"
      // sql => "SELECT * FROM JAVA_BOARD WHERE BOARD_NUM > 10"
      List<Map<String, Object>> result = null;
      try {
            conn = DriverManager.getConnection(url, user, password);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs!=null) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(rs.next()) {
               if(result == null) result = new ArrayList<>();
               Map<String, Object> row = new HashMap<>();
               for(int i = 1; i <= columnCount; i++) {
                  String key = rsmd.getColumnLabel(i);
                  Object value = rs.getObject(i);
                  row.put(key, value);
               }
               result.add(row);
            }
         }
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         if(rs != null) try { rs.close(); } catch(Exception e) {}
         if(ps != null) try { ps.close(); } catch(Exception e) {}
         if(conn != null) try { conn.close(); } catch(Exception e) {}
      }
      return result;
   }
   
   
   public int update(String sql, List<Object> param) {
      // sql => "DELETE FROM JAVA_BOARD WHERE BOARD_NUMBER=?"
      // sql => "UPDATE JAVA_BOARD SET TITLE='하하' WHERE BOARD_NUMBER=?"
      // sql => "INSERT MY_MEMBER (MEM_ID, MEM_PASS, MEM_NAME) VALUES (?, ?, ?)"
      int result = 0;
      try {
         conn = DriverManager.getConnection(url, user, password);
         ps = conn.prepareStatement(sql); //동적쿼리
         // 1. DB 커넥션 객체 생성 2. 명령어 객체 생성(statement 객체 생성) 3. 실행
         for(int i = 0; i < param.size(); i++) { //ArrayList의 갯수 세기-> size()
            ps.setObject(i + 1, param.get(i)); 
            //set+타입
            //i+1 -> 물음표(?)의 순번, param.get(i) -> param의 i번째 정보 꺼내서 i번째 ?에 대응
            //param에 저장되어있는 타입이 Object
            //i+1은 Object의 문법 지배를 받음.(오라클은 1번째부터, 자바는 0번째부터)
            //
         }
         result = ps.executeUpdate();
         //select -> executeQuery
         //insert, update, delete -> executeUpdate => commit 필수
         //영향 받은 행의 수 출력
         if(result>0) conn.commit();
         
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         if(rs != null) try {  rs.close();  } catch (Exception e) { }
         if(ps != null) try {  ps.close();  } catch (Exception e) { }
         if(conn != null) try { conn.close(); } catch (Exception e) { }
      }
      return result;//dao로 반환
   }
   public int update(String sql) {
      // sql => "DELETE FROM JAVA_BOARD"
      // sql => "UPDATE JAVA_BOARD SET TITLE='하하'"
      // sql => "UPDATE JAVA_BOARD SET TITLE='하하' WHERE BOARD_NUMBER=1"
      // sql => "INSERT MY_MEMBER (MEM_ID, MEM_PASS, MEM_NAME) VALUES ('admin', '1234', '홍길동')"
      int result = 0;
      try {
         conn = DriverManager.getConnection(url, user, password);
         ps = conn.prepareStatement(sql);
         result = ps.executeUpdate();
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         if(rs != null) try {  rs.close();  } catch (Exception e) { }
         if(ps != null) try {  ps.close();  } catch (Exception e) { }
         if(conn != null) try { conn.close(); } catch (Exception e) { }
      }
      return result;
   }
   public Map<String, Object> selectOne(String sql, List<Object> param){
      // sql => "SELECT * FROM JAVA_BOARD WHERE BOARD_NUMBER=?"
      // param => [1]
      //
      // sql => "SELECT * FROM JAVA_BOARD WHERE WRITER=? AND TITLE=?"
      // param => ["홍길동", "안녕"]
      Map<String, Object> row = null;
      
      try {
         conn = DriverManager.getConnection(url, user, password);
         ps = conn.prepareStatement(sql);
         for(int i = 0; i < param.size(); i++) {
            ps.setObject(i + 1, param.get(i));
         }
         rs = ps.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         int columnCount = rsmd.getColumnCount();
         while(rs.next()) {
            row = new HashMap<>();
            for(int i = 1; i <= columnCount; i++) {
               String key = rsmd.getColumnLabel(i);
               Object value = rs.getObject(i);
               row.put(key,value);
            }
            // {DATETIME=2022-08-05 16:35:08.0, WRITER=홍길동, TITLE=안녕하세요, CONTENT=안녕안녕, BOARD_NUMBER=1}
         }
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         if(rs != null) try {  rs.close();  } catch (Exception e) { }
         if(ps != null) try {  ps.close();  } catch (Exception e) { }
         if(conn != null) try { conn.close(); } catch (Exception e) { }
      }
      
      return row;
   }
   public Map<String, Object> selectOne(String sql){
      // sql => "SELECT * FROM JAVA_BOARD 
      //         WHERE BOARD_NUMBER=(SELECT MAX(BOARD_NUMBER) FROM JAVA_BOARD)"
      // sql => "SELECT * FROM MEMBER MEM_ID='a001' AND MEM_PASS='123'"
      Map<String, Object> row = null;
      
      try {
         conn = DriverManager.getConnection(url, user, password);
         ps = conn.prepareStatement(sql);
         rs = ps.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         int columnCount = rsmd.getColumnCount();
         while(rs.next()) {
            row = new HashMap<>();
            for(int i = 1; i <= columnCount; i++) {
               String key = rsmd.getColumnLabel(i);
               // getColumnName vs getColumnLabel
               // getColumnName : 원본 컬럼명을 가져옴
               // getColumnLabel : as로 선언된 별명을 가져옴, 없으면 원본 컬럼명
               Object value = rs.getObject(i);
               row.put(key,value);
            }
         }
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         if(rs != null) try {  rs.close();  } catch (Exception e) { }
         if(ps != null) try {  ps.close();  } catch (Exception e) { }
         if(conn != null) try { conn.close(); } catch (Exception e) { }
      }
      
      return row;
   }
   
   
   public String selectValue(String sql){
      // sql => "SELECT * FROM JAVA_BOARD 
      //         WHERE BOARD_NUMBER=(SELECT MAX(BOARD_NUMBER) FROM JAVA_BOARD)"
      // sql => "SELECT * FROM MEMBER MEM_ID='a001' AND MEM_PASS='123'"
      String  result = null;
      
      try {
         conn = DriverManager.getConnection(url, user, password);
         ps = conn.prepareStatement(sql);
         rs = ps.executeQuery();
         
         while(rs.next()) {
            result = (String)rs.getObject(1); //게시글번호가 getObject로 변환하여 출력         
         }
         

         System.out.println(result);
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         if(rs != null) try {  rs.close();  } catch (Exception e) { }
         if(ps != null) try {  ps.close();  } catch (Exception e) { }
         if(conn != null) try { conn.close(); } catch (Exception e) { }
      }
      
      return result;
   }   
}