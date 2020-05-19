function sample6_execDaumPostcode() {
	new daum.Postcode({
		oncomplete : function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ""; // 주소 변수
			var extraAddr = ""; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === "R") {
				// 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else {
				// 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === "R") {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== "" && data.apartment === "Y") {
					extraAddr += extraAddr !== "" ? ", " + data.buildingName
							: data.buildingName;
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== "") {
					extraAddr = " (" + extraAddr + ")";
				}
				// 조합된 참고항목을 해당 필드에 넣는다.
				document.getElementById("addr2").value = extraAddr;
			} else {
				document.getElementById("addr2").value = "";
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById("postcode").value = data.zonecode;
			document.getElementById("addr1").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("addr-etc").focus();
		},
	}).open();
}

$(function() {
	$("#id-error").hide();
	$("#pwd-error1").hide();
	$("#pwd-error2").hide();
	$("#name-error").hide();
	$("#email-error").hide();
	$("#birth-error").hide();
	$("#phone-error").hide();
	$("#addr-error").hide();

	$("#btn-join").click(function() {
	    var id = $("#id").val();
	    if (id.length < 1) {
	      $("#id-error").show();
	      $("#id").focus();
	      return false;
	    } else {
	      $("#id-error").hide();
	    }

		var pwd1 = $("#pwd1").val();
		if (pwd1.length < 1) {
			$("#pwd-error1").show();
			$("#pwd1").focus();
			return false;
		} else {
			$("#pwd-error1").hide();
		}

		var pwd2 = $("#pwd2").val();
		if (pwd1 != pwd2) {
			$("#pwd-error2").show();
			$("#pwd2").focus();
			return false;
		} else {
			$("#pwd-error2").hide();
		}

		var name = $("#name").val();
		if (name.length < 1) {
			$("#name-error").show();
			$("#name").focus();
			return false;
		} else {
			$("#name-error").hide();
		}

		var email = $("#email").val();
		// if (!email.match("@")) {
		if(email.length < 1) {
			$("#email-error").show();
			$("#email").focus();
			return false;
		} else {
			$("#email-error").hide();
		}

		var year = $("#year").val();
		var mm = $("#mm").val();
		var dd = $("#dd").val();
		if (year == null) {
			$("#birth-error").show();
			$("#year").focus();
			return false;
		} else {
			$("#birth-error").hide();
		}
		if (mm == "월") {
			$("#birth-error").show();
			$("#mm").focus();
			return false;
		} else {
			$("#birth-error").hide();
		}
		if (dd == null) {
			$("#birth-error").show();
			$("#dd").focus();
			return false;
		} else {
			$("#birth-error").hide();
		}

		var phone = $("#phone").val();
		if (phone.length < 13 || phone.length > 17) {
			$("#phone-error").show();
			$("#phone").focus();
			return false;
		} else {
			$("#phone-error").hide();
		}

		var postcode = $("#postcode").val();
		var addr3 = $("#addr-etc").val();
		if (postcode.length < 1 || addr3.length < 1) {
			$("#addr-error").show();
			$("#addr-etc").focus();
			return false;
		} else {
			$("#addr-error").hide();
		}
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
