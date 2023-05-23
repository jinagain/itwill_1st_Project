package com.itwillbs.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 모든 Action페이지의 형태를 고정
public interface Action {
	// 상수, 추상메서드
	/**
	 * 
	 * 		execute() 실행할 때 반드시 request, response가 필요하다.
	 * 		execute()는 실행결과로 ActionForward(이동티켓) 생성
	 */
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
