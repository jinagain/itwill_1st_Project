package com.itwillbs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ModelDAO {
	
	private Connection con = null;  	
	private PreparedStatement pstmt = null;
	private ResultSet rs = null; 			
	private String sql ="";				

	//디비 연결 메서드
	private Connection getCon() throws Exception{
		// Context 객체생성
		Context initCTX = new InitialContext();
		// 디비연결정보 불러와서 사용
		DataSource ds 
		   = (DataSource) initCTX.lookup("java:comp/env/jdbc/c7d2301t1");
		// 디비연결
		con = ds.getConnection();
		System.out.println(" DAO : 디비연결 성공! "+con);
		
		return con;
	}//디비 연결 메서드
	
	// 자원해제
	public void closeDB() {
		try {
			if(rs != null)  rs.close();
			if(pstmt != null)  pstmt.close();
			if(con != null)  con.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//자원해제
	
	public JSONArray getModelList() {
		JSONArray modelList= null;
		try {
			modelList = new JSONArray();
			con = getCon();
			sql = "select * from model";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JSONObject dto = new JSONObject();
				dto.put("model_id", rs.getInt(1));
				dto.put("model", rs.getString(2));
				dto.put("color", rs.getString(3));
				modelList.add(dto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return modelList;
	} 
	
	public JSONArray getColorList() {
		JSONArray colorList= null;
		try {
			colorList = new JSONArray();
			con = getCon();
			sql = "select * from color";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				JSONObject dto = new JSONObject();
				dto.put("color_id", rs.getInt(1));
				dto.put("color", rs.getString(2));
				colorList.add(dto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return colorList;
	} 
}
