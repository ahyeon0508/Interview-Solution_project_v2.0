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
}

function cancleForm(){
    if(confirm("이 페이지를 벗어나면 마지막 저장 후 수정된 내용은 저장되지 않습니다.")){
        // 메인 홈페이지 주소 입력하기
        window.location.href = "";
    }
    else {
        return false;
    }

}

function questionDeleteConfirm(n) {
    if (confirm("이 질문을 내 질문에서 삭제하시겠습니까?")) {
        window.location.href = "/website/myquestion/delete/" + n;
    } else {
        return false;
    }
}

function questionnonstar(n) {
    if (confirm("이 질문을 내 질문으로 등록하시겠습니까?")) {
        window.location.href = "/website/questionList/nonstar/" + n;
    } else {
        return false;
    }
}

function questionstar(n) {
    if (confirm("이 질문을 내 질문에서 취소하시겠습니까?")) {
        window.location.href = "/website/questionList/star/" + n;
    } else {
        return false;
    }
}

function internonstar(n) {
    if (confirm("이 질문을 내 질문으로 등록하시겠습니까?")) {
        window.location.href = "/website/student/interview/nonstar/" + n;
    } else {
        return false;
    }
}

function interstar(n) {
    if (confirm("이 질문을 내 질문에서 취소하시겠습니까?")) {
        window.location.href = "/website/student/interview/star/" + n;
    } else {
        return false;
    }
}