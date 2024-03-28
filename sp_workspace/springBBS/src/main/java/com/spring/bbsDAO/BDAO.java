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

import com.spring.bbsVO.BVO;

public class BDAO {
	
	DataSource dataSource;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public BDAO() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/"+ "jdbc/dbconn");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//DB에서 글 목록 가져오는 메서드
	public ArrayList<BVO> list() {
		ArrayList<BVO> bVOs = new ArrayList<BVO>();
		
		try {
			conn = dataSource.getConnection();
			System.out.println("=======list - DB 연동 성공=======");
			
			//bGroup : 해당 글의 현재 번호, bStep : 먼저 쓴 글이 위로 올라오게(댓글 처리시)
			String sql = "select bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_bbs "
					+ " order by bGroup desc, bStep asc";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bNo = rs.getInt("bNo");
				String bName = rs.getString("bName");
				String bSubject = rs.getString("bSubject");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				BVO bvo = new BVO(bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent);
				bVOs.add(bvo);
			}//while()
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
//				System.out.println("=======list - DB연동 해제=======");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bVOs;
	}//list()

	//글쓰기 등록을 위한 메서드
	public void write(String bName, String bSubject, String bContent) {

		String sql = "alter table mvc_bbs auto_increment = 1";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			//bGroup currval(오라클) 썼는데 용도를 모르겠어서 일단 0
			//답글이 있을 때 그 해당 글과 묶여서 정렬되는 용도
			sql = "insert into mvc_bbs(bName, bSubject, bContent, bHit, bStep, bIndent)"
					+ "values(?,?,?,0,0,0)";
			
			conn = dataSource.getConnection();
			System.out.println("=======write - DB 연동 성공=======");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,bName);
			pstmt.setString(2,bSubject);
			pstmt.setString(3,bContent);
			
			int n = pstmt.executeUpdate();
			System.out.println("-------write() 수행 완료-------");
			
		} catch (SQLException e) {
			System.out.println("write() 예외 발생 : "+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("write() close()호출 예외 : " + e2.getMessage());
				e2.printStackTrace();
			}
		}
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
		BVO bVo	= null;
		
		try {
			String sql = "select * from mvc_bbs where bNo = ?";
			
			conn = dataSource.getConnection();
			System.out.println("=======contentView - DB 연동 성공=======");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(bbsNo));
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
				
				bVo = new BVO(bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
		} catch (SQLException e) {
			System.out.println("contentView() 예외 발생 : "+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("contentView() close()호출 예외 : " + e2.getMessage());
				e2.printStackTrace();
			}
		}
		addHit(bbsNo); //조회 수 증가
		return bVo;
	}//contentView()
	
	//글 수정하기
	public void modify(String bNo, String bName, String bSubject, String bContent) {

		try {
			String sql = "update mvc_bbs set bName = ?, bSubject = ?, bContent = ? where bNo = ?";
			
			conn = dataSource.getConnection();
			System.out.println("=======modify - DB 연동 성공=======");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bSubject);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bNo));
			
			//업데이트 하면 리턴값이 숫자(정수)형으로 날아옴. 0보다 크면 제대로 업데이트 된 것
			int n = pstmt.executeUpdate();
			System.out.println("-------modify() 수행 완료-------");
			
		} catch (SQLException e) {
			System.out.println("modify() 예외 발생 : "+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("modify() close()호출 예외 : " + e2.getMessage());
				e2.printStackTrace();
			}
		}
	}//modify()
	
	//글 삭제하기
	public void delete(String bNo) {
		try {
			String sql = "delete from mvc_bbs where bNo = ?";
			
			conn = dataSource.getConnection();
			System.out.println("=======delete - DB 연동 성공=======");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(bNo));
			
			//업데이트 하면 리턴값이 숫자(정수)형으로 날아옴. 0보다 크면 제대로 업데이트 된 것
			int n = pstmt.executeUpdate();
			System.out.println("-------delete() 수행 완료-------");
			
		} catch (SQLException e) {
			System.out.println("delete() 예외 발생 : "+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("delete() close()호출 예외 : " + e2.getMessage());
				e2.printStackTrace();
			}
		}
	}//delete()
	
	//답글 보기(bbsNo 중간에 bNO와 중복 방지를 위해)
	public BVO replyForm(String bbsNo) {
		BVO bVO = null;
		
		try {
			String sql = "select * from mvc_bbs where bNo = ?";
			
			conn = dataSource.getConnection();
			System.out.println("=======replyForm - DB 연동 성공=======");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(bbsNo));
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
				
				bVO = new BVO(bNo, bName, bSubject, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}//if()
		} catch (SQLException e) {
			System.out.println("replyForm() 예외 발생 : "+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("replyForm() close()호출 예외 : " + e2.getMessage());
				e2.printStackTrace();
			}
		}
		return bVO;
	}//replyForm()
	
	//답변 등록
	public void replyOk(String bNo, String bName, String bSubject, String bContent, 
			String bGroup, String bStep, String bIndent) {
		
		replySet(bGroup, bStep);
		
		String sql = "alter table mvc_bbs auto_increment = 1";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			//자동 증가 있어서 sql에는 bNo 뺌
			
			sql = "insert into mvc_bbs (bName, bSubject, bContent, bGroup, bStep, bIndent) "
					+ " values(?,?,?,?,?,?)";
			
			conn = dataSource.getConnection();
			System.out.println("=======replyOk - DB 연동 성공=======");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bSubject);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep)+1);
			pstmt.setInt(6, Integer.parseInt(bIndent)+1);
			
			int n = pstmt.executeUpdate();
			System.out.println("-------replyOk() 수행 완료-------");
			
		} catch (SQLException e) {
			System.out.println("replyOk() 예외 발생 : "+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("replyOk() close()호출 예외 : " + e2.getMessage());
				e2.printStackTrace();
			}
		}
		
	}//replyOk

	//그룹 스텝 증가?
	private void replySet(String group, String step) {
		
		try {
			String sql = "update mvc_bbs set bStep = bStep+1 where bGroup = ? and bStep > ?";
			
			conn = dataSource.getConnection();
			System.out.println("=======replySet - DB 연동 성공=======");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(group));
			pstmt.setInt(2, Integer.parseInt(step));
			
			int n = pstmt.executeUpdate();
			System.out.println("-------replySet() 수행 완료-------");
			
		} catch (SQLException e) {
			System.out.println("replySet() 예외 발생 : "+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("replySet() close()호출 예외 : " + e2.getMessage());
				e2.printStackTrace();
			}
		}
	}//replySet()

	//조회 수 증가 메서드
	private void addHit(String bNo) {
		
		try {
			String sql = "update mvc_bbs set bHit = bHit + 1 where bNo = ?"; 
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bNo);
			
			int n = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("addHit() 예외 발생 : "+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("addHit() close()호출 예외 : " + e2.getMessage());
				e2.printStackTrace();
			}
		}
	}//addHit()

	

}
