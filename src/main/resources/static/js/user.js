// 참고 : https://tonhnegod.tistory.com/7
function onlyNumber(event){
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
        return;
    else
        return false;
}

function removeChar(event) {
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
        return;
    else
        event.target.value = event.target.value.replace(/[^0-9]/g, "");
}

function fn_pw_lenCheck() {
    var pw = document.getElementById("password").value; //비밀번호

    if(pw.length < 8 || pw.length > 20) {
        alert("비밀번호를 다시 입력하여 주시기 바랍니다.");
        return false;
    }

    return true;
}

function fn_pw_check() {
    var pw = document.getElementById("password").value; //비밀번호
    var pw2 = document.getElementById("passwordChk").value; // 확인 비밀번호

    if(pw != pw2) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    return true;
<<<<<<< HEAD
=======
}
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
>>>>>>> 7035bc39ccf7b3bfb12348778704d65db0ed6994
}