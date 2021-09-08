// 피드백 삭제 확인 함수
function f_DeleteConfirm(n) {
    if (confirm("이 피드백을 삭제하시겠습니까?")) {
        // href 넣기
        window.location.href = n + "/delete1";
    }
    else {
        return false;
    }
}

// 전송 질문 삭제 확인 함수
function Q_DeleteConfirm(n) {
    if (confirm("전송한 질문을 삭제하시겠습니까?")) {
        // href 넣기
        window.location.href = "delete/" + n;
    }
    else {
        return false;
    }
} 

// 리포트 삭제 확인 함수
function DeleteConfirm(n) {
    if (confirm("해당 리포트를 삭제하시겠습니까?")) {
        // href 넣기
        window.location.href = "delete/" + n;
    }
    else {
        return false;
    }
}