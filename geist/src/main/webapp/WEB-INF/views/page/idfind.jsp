<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="/resources/css/idfind.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <title>Geist</title>
</head>
<body>
<div class="wrap">
    <div id="header">
        <div class="top">
            <h1>
                <a href="#" class="logo"><span class="logo">GEIST</span></a>
            </h1>
        </div>
        <div class="menu">
            <ul class="menu_type">
                <li><a href="javascript:void(0)" onclick="location.href='/idSearch'" class="idclick"><span>아이디 찾기</span></a></li>
                <span class="menu-bar">&nbsp;|&nbsp;</span>
                <li><a href="javascript:void(0)" onclick="location.href='/pwSearch'" class="pwdclick"><span>비밀번호 찾기</span></a></li>
            </ul>
        </div>
    </div>
    <div id="conteiner">
        <div class="content">
            <div class="content_header">
                <h3>아이디 찾기</h3>
            </div>
            <hr>
            <p>각 정보들을 입력해 주세요</p>
            <form id="idfind">
                <span class="ps_box">
                    <input type="text" id="name" name="emp_name" class="int" maxlength="20" placeholder="이름을 입력하세요"> 
                </span>
                <span class="ps_box">
                    <input type="text" id="phone" name="emp_phone" class="int" maxlength="13" placeholder="휴대폰 번호를 입력하세요" oninput="noKorean(this);" onkeyup="inputPhoneNumber(this);"> 
                </span>
                <span class="ps_box">
                    <input type="text" id="email" name="emp_email" class="int" maxlength="20" placeholder="이메일을 입력하세요"> 
                </span>
                <div class="btn-row">
                    <input type="button"id="btn-find" value="찾 기">
                </div>
            </form>
        </div>
        
    </div>
</div>

<script type="text/javascript">
	var idSearchService = (function() {
		function idSearch(param, callback, error) {
			$.ajax({
				type : 'post',
				url : 'login/idSearch',
				data : JSON.stringify(param),
				contentType : "application/json; charset=utf-8",
				success : function(result, status, xhr) {
					if(callback) {
						callback(result);
					}
				},
				error : function(xhr, status, err) {
					if(error) {
						error(err);
					}
				}
			});
		}
		return {
			idSearch : idSearch
		}
	})();
	
	$(document).ready(function() {
		var searchForm = $("#idfind");
		var idSearchBtn = $("#btn-find");
		
		idSearchBtn.on("click", function() {
			if(searchForm.find("input[name='emp_name']").val() === null) {
				alert("이름을 입력하세요.");
				return false;
			}
			if(searchForm.find("input[name='emp_phone']").val() === null) {
				alert("휴대폰 번호를 입력하세요.");
				return false;
			}
			if(searchForm.find("input[name='emp_email']").val() === null) {
				alert("이메일을 입력하세요.");
				return false;
			}
			
			var emp_name = $("input[name='emp_name']").val();
			var emp_phone = $("input[name='emp_phone']").val();
			var emp_email = $("input[name='emp_emanil']").val();
			
			idSearchService.idSearch({
				emp_name : emp_name,
				emp_phone : emp_phone,
				emp_email : emp_email				
			}, function(result) {
				if(result == 'fail') {
					alert("회원 정보가 없습니다.");
				} else {
					alret("회원님의 아이디는 " + result + "입니다.");
				}
			});
		});
		
	});
	
	
	// input#input-number : 한글 입력 불가능
	function noKorean(obj) {
		obj.value = obj.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	}
	
	// input#input-number : 자동 "-" 삽입
	function inputPhoneNumber(obj) {
		var number = obj.value.replace(/[^0-9]/g, "");
		var phone = "";

		if (number.length < 4) {
			return number;
		} else if (number.length < 7) {
			phone += number.substr(0, 3);
			phone += "-";
			phone += number.substr(3);
		} else if (number.length < 11) {
			phone += number.substr(0, 3);
			phone += "-";
			phone += number.substr(3, 3);
			phone += "-";
			phone += number.substr(6);
		} else {
			phone += number.substr(0, 3);
			phone += "-";
			phone += number.substr(3, 4);
			phone += "-";
			phone += number.substr(7);
		}
		obj.value = phone;
	}


</script>




</body>
</html>