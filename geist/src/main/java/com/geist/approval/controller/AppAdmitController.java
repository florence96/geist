package com.geist.approval.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geist.approval.domain.ApprovalAgrDTO;
import com.geist.approval.domain.ApprovalAgrVO;
import com.geist.approval.domain.ApprovalVO;
import com.geist.approval.service.ApprovalService;
import com.geist.main.domain.Criteria;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/* *
 * 결재 페이지
 * 담당 : 김현선
 */

@RestController
@RequestMapping("/approvalAdmit/*")
@AllArgsConstructor
@Log4j
public class AppAdmitController {

	private ApprovalService service;
	
	// 결재 승인 조회	
	@GetMapping(value = "/{page}/{empNo}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity <ApprovalAgrDTO> agreeList(@PathVariable("page") int page, @PathVariable("empNo") Long emp_no) {
		Criteria cri = new Criteria(page, 10);
		return new ResponseEntity<ApprovalAgrDTO>(service.agreeListWithPaging(cri, emp_no), HttpStatus.OK);
	}

	@GetMapping(value = "/detail/{appNo}/{empNo}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity <ApprovalVO> agreeListDetail(@PathVariable("appNo") Long app_no, @PathVariable("empNo") Long emp_no) {
		return new ResponseEntity<ApprovalVO>(service.agreeListDetail(app_no, emp_no), HttpStatus.OK);
	}
	
	// 결재 승인, 반려
	@PostMapping(value = "/admit", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> admit(@RequestBody ApprovalAgrVO agrVo) {
		service.appAdmit(agrVo);
		service.finalState(agrVo.getApp_no());
		
		return new ResponseEntity<>("success", HttpStatus.OK);
	}	
}