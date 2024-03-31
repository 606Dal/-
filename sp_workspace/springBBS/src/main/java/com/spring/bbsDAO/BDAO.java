
  package com.spring.bbsDAO;
  
  import java.sql.Connection; 
  import java.sql.PreparedStatement; 
  import java.sql.ResultSet; 
  import java.sql.SQLException; 
  import java.sql.Timestamp;
  import java.util.ArrayList;
  
  import javax.naming.Context; 
  import javax.naming.InitialContext; 
  import javax.sql.DataSource;
  
  import org.springframework.jdbc.core.BeanPropertyRowMapper; 
  import org.springframework.jdbc.core.JdbcTemplate; 
  import org.springframework.jdbc.core.PreparedStatementSetter;
  
  import com.spring.bbsVO.BVO; 
  import com.spring.template.StaticTemplate;
  
  public class BDAO {
  
  JdbcTemplate template;
  
  DataSource dataSource; 
  private Connection conn = null; 
  private PreparedStatement pstmt = null;
  private ResultSet rs = null;
  
  public BDAO() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/dbconn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 위의 문장이 아래 한 문장으로 대체(되는데 updatebGroup때문에 못 지움)
		this.template = StaticTemplate.template;
	}
  
  //DB에서 글 목록 가져오는 메서드
  public ArrayList<BVO> list() {
  
  ArrayList<BVO> bVOs = null;
  String sql = "select bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_bbs "
  + " order by bGroup desc, bStep asc";
  
  bVOs = (ArrayList<BVO>) template.query(sql, new BeanPropertyRowMapper<BVO>(BVO.class));
  
  return bVOs; }//list()
  
  //글쓰기 등록을 위한 메서드
  //현재는 final 없어도 오류 표시는 안 나옴.
  public void write(final String bName, final String bSubject, final String bContent) {
  
	  this.template.execute("alter table mvc_bbs auto_increment = 1");
  
	  //PreparedStatement 객체를 생성하기 위한 인터페이스
//	  this.template.update(new PreparedStatementCreator() {
//  
//	  @Override
//	  public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//  
//		  String sql =  "insert into mvc_bbs(bName, bSubject, bContent, bHit, bStep, bIndent) "
//		  + " values(?,?,?,0,0,0)";
//  
//	  PreparedStatement pstmt = con.prepareStatement(sql);
//	  pstmt.setString(1,bName);
//	  pstmt.setString(2,bSubject);
//	  pstmt.setString(3,bContent);
//  
//	  return pstmt; } });
	  //위랑 같은 기능
	  String sql =
	  "insert into mvc_bbs(bName, bSubject, bContent, bHit, bStep, bIndent)" +
	  "values(?,?,?,0,0,0)";
  
	  this.template.update(sql, new PreparedStatementSetter() {
  
	  @Override
	  public void setValues(PreparedStatement pstmt) throws SQLException {
		  pstmt.setString(1, bName); 
		  pstmt.setString(2, bSubject); 
		  pstmt.setString(3, bContent); }
	  });
	  
  }//write()
  
  
  //bGroup을 bNo랑 같은 숫자로
	public void updatebGroup(String bbNo) {
	BVO bvo = null;
	//테이블의 제일 마지막 글 가져 옴
	String sql = "select * from mvc_bbs order by bDate desc limit 1;";

	try {
		conn = dataSource.getConnection();
		System.out.println("=======replyForm - DB 연동 성공=======");
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			int bNo = rs.getInt("bNo");
			String bName = rs.getString("bName");
			String bSubject = rs.getString("bSubject");
			String bContent = rs.getString("bContent");
			Timestamp bDate = rs.getTimestamp("bDate");
			
			int bHit = rs.getInt("bHit");
			int bGroup = rs.getInt("bGroup");
			int bStep = rs.getInt("bStep");
			int bIndent = rs.getInt("bIndent");
			
			bvo = new BVO(bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent);
		}
		sql = "update mvc_bbs set bGroup = bNo where bNo = ?";
		
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, bvo.getbNo());
		System.out.println("bvo.getbNo() : "+bvo.getbNo());
		
		int n = pstmt.executeUpdate();
		
	} catch (SQLException e) {
		System.out.println("bGbNsame() 예외 발생 : "+e.getMessage());
		e.printStackTrace();
	} finally {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		} catch (Exception e2) {
			System.out.println("bGbNsame() close()호출 예외 : " + e2.getMessage());
			e2.printStackTrace();
		}
	}
}//updatebGroup()

  
  //글 상세보기
  public BVO contentView(String bbsNo) {
	  addHit(bbsNo); //조회 수 증가
  
	  // String sql = "select * from mvc_bbs where bNo = ? ";
	  String sql = "select * from mvc_bbs where bNo = "+bbsNo;
  
	  return this.template.queryForObject(sql, new BeanPropertyRowMapper<BVO>(BVO.class));
  }//contentView()
  
  //글 수정하기 //final 빼 봄
  public void modify(String bNo, String bName, String bSubject, String bContent) {
	  //sql을 먼저 세팅하고 update 쓰는 방식
	  String sql = "update mvc_bbs set bName = ?, bSubject = ?, bContent = ? where bNo = ?";
  
	  this.template.update(sql, new PreparedStatementSetter() {
  
		  @Override
		  public void setValues(PreparedStatement pstmt) throws SQLException{
			  pstmt.setString(1, bName);
			  pstmt.setString(2, bSubject);
			  pstmt.setString(3, bContent);
			  pstmt.setInt(4, Integer.parseInt(bNo)); }
		  });
  }//modify()
  
  //글 삭제하기
  public void delete(String bNo) {
  
	  String sql = "delete from mvc_bbs where bNo = ?";
  
	  this.template.update(sql, new PreparedStatementSetter() {
  
		  	@Override
		  	public void setValues(PreparedStatement ps) throws SQLException {
		  		ps.setString(1, bNo); //? setInt 아니어도 잘 지워짐
		  		}
		  	});
	  		//pstmt.setInt(1, Integer.parseInt(bNo));
  }//delete()
  
  //답글 보기(bbsNo 중간에 bNO와 중복 방지를 위해)
  public BVO replyForm(String bbsNo) {
  
	  String sql = "select * from mvc_bbs where bNo = "+bbsNo;
	  
	  return this.template.queryForObject(sql, new BeanPropertyRowMapper<BVO>(BVO.class));
  }//replyForm()
	  
  //답변 등록
  public void replyOk(String bNo, String bName, String bSubject, String bContent, String bGroup, String bStep, String bIndent) {
  
	  replySet(bGroup, bStep);
	  
	  template.execute("alter table mvc_bbs auto_increment = 1");
	  
	  String sql = "insert into mvc_bbs (bName, bSubject, bContent, bGroup, bStep, bIndent) "
	  + " values(?,?,?,?,?,?)";
	  
	  this.template.update(sql, new PreparedStatementSetter() {
	  
		  @Override
		  public void setValues(PreparedStatement ps) throws SQLException {
		  ps.setString(1, bName);
		  ps.setString(2, bSubject);
		  ps.setString(3, bContent);
		  ps.setInt(4, Integer.parseInt(bGroup));
		  ps.setInt(5, Integer.parseInt(bStep)+1);
		  ps.setInt(6, Integer.parseInt(bIndent)+1); 
		  }
	  });
  }//replyOk
  
  //그룹 스텝 증가?
  private void replySet(String group, String step) {
  
	  String sql = "update mvc_bbs set bStep = bStep+1 where bGroup = ? and bStep > ?";
	  
	  this.template.update(sql, new PreparedStatementSetter() {
	  
		  @Override
		  public void setValues(PreparedStatement ps) throws SQLException {
		  
		  ps.setString(1, group);
		  ps.setString(2, step);
		  }
	  });
//		  pstmt.setInt(1, Integer.parseInt(group)); pstmt.setInt(2,
//		  Integer.parseInt(step));
  }//replySet()
  
  //조회 수 증가 메서드
  private void addHit(String bNo) {
	  String sql = "update mvc_bbs set bHit = bHit + 1 where bNo = ?";
  
	  this.template.update(sql, new PreparedStatementSetter() {
  
		  @Override
		  public void setValues(PreparedStatement ps) throws SQLException {
		  
		  ps.setString(1, bNo); }
		  });
  }//addHit()
  
  
}
 
