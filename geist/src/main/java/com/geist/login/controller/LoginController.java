package com.geist.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geist.login.domain.LoginVO;
import com.geist.login.service.LoginService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/* *
 * 로그인 페이지
 * 담당 : 김호영
 */

@RestController
@RequestMapping("/login/*")
@AllArgsConstructor
@Log4j
public class LoginController {
	private LoginService service;
	
	//로그인 정보를 전송시켜주는 컨트롤러
	@PostMapping(value = "/login", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> login(@RequestBody LoginVO vo, HttpServletRequest req){
		//로그인 정보를 session에 담아 유지시킴
		HttpSession session = req.getSession();
		LoginVO login = service.login(vo);
		String result = "";
		String sys = "";
		//String position = "";

		if(login == null) {
			session.setAttribute("member", null);
			result = "fail";
		}else {
			//사원번호를 통해 직급을 확인
			//position = login.getEmp_position();
			
			//관리자(100)일 경우 관리자 계정으로 접속
			if(login.getEmp_no() == 100) {
				sys = "sys";
				result = "success";
				session.setAttribute("sys", sys);
				session.setAttribute("empPosition", login.getEmp_position());
				session.setAttribute("member", login);
			//관리자가 아닐 경우
			}else {
				result = "success";				
				session.setAttribute("empPosition", login.getEmp_position());
				session.setAttribute("member", login);
			}
		}
		log.info("session : " + session);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/idSearch", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> idSearch(@RequestBody LoginVO vo){
		log.info("vo : " + vo);
		LoginVO id = service.idSearch(vo);
		String result = "";
		
		if(id == null) {
			result = "fail";
		}
		else {
			result = id.getEmp_id();
		}
		log.info("result : " + result);
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/pwSearch", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> pwSearch(@RequestBody LoginVO vo){
		log.info("vo : " + vo);
		LoginVO pw = service.pwSearch(vo);
		String result = "";
		
		if(pw == null) {
			result = "fail";
		}
		else {
			result = pw.getEmp_pw();
		}
		log.info("result : " + result);
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
