function f_DeleteConfirm(n) {
    if (confirm("이 피드백을 삭제하시겠습니까?")) {
        // href 넣기
        window.location.href = "";
    }
    else {
        return false;
    }
}

function Q_DeleteConfirm(n) {
    if (confirm("전송한 질문을 삭제하시겠습니까?")) {
        // href 넣기
        window.location.href = "";
    }
    else {
        return false;
    }
} 