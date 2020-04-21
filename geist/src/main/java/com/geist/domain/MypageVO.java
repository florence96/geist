package com.geist.domain;

import java.util.Date;

import lombok.Data;

/* *
 * 마이페이지
 * 담당 : 김현선
 * 
 * 사원번호, 생년월일, 아이디, 비밀번호, 비밀번호 확인, 개인 연락처, 사내 연락처, 주소
 */

@Data
public class MypageVO {

	private Long emp_no;
	private Date emp_birth;
	private String emp_id;
	private String emp_pw;
	private String emp_phone;		//개인 연락처
	private String emp_tel;			//사내 연락처
	private String emp_address;	
	
}
