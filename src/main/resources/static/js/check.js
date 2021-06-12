function f_DeleteConfirm(n) {
    if (confirm("이 피드백을 삭제하시겠습니까?")) {
        // href 넣기
        window.location.href = n + "/delete1";
    }
    else {
        return false;
    }
}

function Q_DeleteConfirm(n) {
    if (confirm("전송한 질문을 삭제하시겠습니까?")) {
        // href 넣기
        window.location.href = "delete/" + n;
    }
    else {
        return false;
    }
} 

function DeleteConfirm(n) {
    if (confirm("해당 리포트를 삭제하시겠습니까?")) {
        // href 넣기
        window.location.href = "/website/myVideo/delete/" + n;
    }
    else {
        return false;
    }
}