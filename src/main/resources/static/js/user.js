// 참고 : https://tonhnegod.tistory.com/7
// 숫자만 입력받는 함수
function onlyNumber(event){
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
        return;
    else
        return false;
}

// 문자 입력 받지 못 하게 하는 함수
function removeChar(event) {
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
        return;
    else
        event.target.value = event.target.value.replace(/[^0-9]/g, "");
}

// 패스워드 길이 체크 함수
function fn_pw_lenCheck() {
    var pw = document.getElementById("password").value; //비밀번호

    if(pw.length < 8 || pw.length > 20) {
        alert("비밀번호를 다시 입력하여 주시기 바랍니다.");
        return false;
    }

    return true;
}

// 비밀번호, 확인 비밀번호 체크 함수
function fn_pw_check() {
    var pw = document.getElementById("password").value; //비밀번호
    var pw2 = document.getElementById("passwordChk").value; // 확인 비밀번호

    if(pw != pw2) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    return true;
}

// 탈퇴 확인 여부 함수
function secedeForm(){
    if(confirm("정말로 탈퇴하시겠습니까?")){
        // 탈퇴 주소 입력하기
        window.location.href = "/secede";
    } else {
        return false;
    }
}

// 회원가입 폼 함수
function checkSignupForm(){
    var username = document.getElementById("username").value;
    var userID = document.getElementById("userID").value;
    var pw = document.getElementById("password").value;

    if(username==""){
        alert("이름을 입력해주세요.");
        return false;
    }
    if(userID==""){
        alert("아이디 입력해주세요.");
        return false;
    }
    if(pw==""){
        alert("비밀번호 입력해주세요.");
        return false;
    }
}