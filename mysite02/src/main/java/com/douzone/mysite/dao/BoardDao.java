package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;


public class BoardDao {
	
	public List<BoardVo> findAll(int page,int rows,String kwd) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
				"  select a.no,a.title,a.content,a.hit,a.reg_date,a.g_no,a.o_no,a.depth,a.user_no,b.name "
				+ "from board a "
				+ "inner join user b on a.user_no=b.no "
				+ "where a.title like ? or a.content like ? or b.name like ? "
				+ "order by g_no desc,o_no "
				+ "limit ?,? ";
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setString(2, "%"+kwd+"%");
			pstmt.setString(3, "%"+kwd+"%");
			pstmt.setInt(4, (10*(page-1))-1==-1? 0:(10*(page-1)));
			pstmt.setInt(5, rows);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setHit(rs.getInt(4));
				vo.setRegDate(rs.getString(5));
				vo.setGroupNo(rs.getInt(6));
				vo.setOrderNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getLong(9));
				vo.setUserName(rs.getString(10));
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error : "+e);
		} finally {
			try {
				if(rs != null ) {
					rs.close();
				}
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int findPaging(String kwd) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql ="select count(*) as count "
					+ "from board a "
					+ "inner join user b on a.user_no=b.no "
					+ "where a.title like ? or a.content like ? or b.name like ?";
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setString(2, "%"+kwd+"%");
			pstmt.setString(3, "%"+kwd+"%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error : "+e);
		} finally {
			try {
				if(rs != null ) {
					rs.close();
				}
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void Insert(BoardVo vo) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement selectpstmt = null;
		PreparedStatement updatepstmt = null;
		ResultSet rs = null;
		String sql=null;
		String select_sql=null;
		String update_sql = null;
		int g_no =0;
		int o_no =0;
		int depth =0;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			
			//새글일 경우 
			if(vo.getNo() == 0) {
				
				//insert
				sql ="insert board(title,content,hit,reg_date,g_no,o_no,depth,user_no) "
					+"select ?,?,0,now(),ifnull(max(g_no),0)+1,1,0,? "
					+"from board";
				pstmt = conn.prepareStatement(sql);
					
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getUserNo());
				pstmt.executeQuery();
				
			//답글의 경우
			}else {
				// 부모 글의 g_no, o_no, depth 찾기
				select_sql = "select g_no, o_no,depth from board where no =?";
				selectpstmt = conn.prepareStatement(select_sql);
				
				selectpstmt.setLong(1, vo.getNo());
				rs = selectpstmt.executeQuery();
			
				if(rs.next()) {
					g_no =rs.getInt(1);
					o_no =rs.getInt(2)+1;
					depth =rs.getInt(3)+1;
				}
				
				//update
				update_sql="update board set o_no = o_no+1 where g_no =? and o_no >= ?";
				updatepstmt = conn.prepareStatement(update_sql);
				
				updatepstmt.setInt(1, g_no);
				updatepstmt.setInt(2, o_no);
				updatepstmt.executeQuery();
				
				//insert
				sql ="insert into board(title,content,hit,reg_date,g_no,o_no,depth,user_no) "
					+"values(?,?,0,now(),?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
					
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setInt(3, g_no);
				pstmt.setInt(4, o_no);
				pstmt.setInt(5, depth);
				pstmt.setLong(6, vo.getUserNo());
				pstmt.executeQuery();
				
			}
			
		} catch (SQLException e) {
			conn.rollback();
			System.out.println("error : "+e);
		} finally {
			try {
				if(rs != null ) {
					rs.close();
				}
				if(pstmt != null ) {
					pstmt.close();
				}
				if(selectpstmt != null ) {
					selectpstmt.close();
				}
				if(updatepstmt != null ) {
					updatepstmt.close();
				}
				if(conn != null ) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void Modify(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql ="update board "
					    +"set title=?, content=? "
					    +"where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
	
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error : "+e);
		} finally {
			try {
				
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void Delete(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql ="delete from board where no =?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1,no);
	
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error : "+e);
		} finally {
			try {
				
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	public BoardVo View(Long boardNo) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
				"  select a.no,a.title,a.content,a.hit,a.reg_date,a.g_no,a.o_no,a.depth,a.user_no,b.name "
				+ "from board a "
				+ "inner join user b on a.user_no=b.no "
				+ "where a.no=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setHit(rs.getInt(4));
				vo.setRegDate(rs.getString(5));
				vo.setGroupNo(rs.getInt(6));
				vo.setOrderNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getLong(9));
				vo.setUserName(rs.getString(10));
				result=vo;
			}
			
		} catch (SQLException e) {
			System.out.println("error : "+e);
		} finally {
			try {
				if(rs != null ) {
					rs.close();
				}
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void ViewCount(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql ="update board set hit=hit+1 where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1,no);
	
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error : "+e);
		} finally {
			try {
				
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://192.168.10.102:3307/webdb?charset=utf8";
			
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 :" +e);
		}
	
		return conn;
	}

	


	

	
	
}
