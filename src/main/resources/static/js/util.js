function questionnonstar(n) {
    if (confirm("이 질문을 내 질문으로 등록하시겠습니까?")) {
        window.location.href = "/questionList/check/" + n;
    } else {
        return false;
    }
}

function questionstar(n) {
    if (confirm("이 질문을 내 질문에서 취소하시겠습니까?")) {
        window.location.href = "/questionList/uncheck/" + n;
    } else {
        return false;
    }
}

function interstar(n) {
    if (confirm("이 질문을 내 질문에서 취소하시겠습니까?")) {
        window.location.href = "/myQuestionList/uncheck/" + n;
    } else {
        return false;
    }
}

function interviewQuestionCheck(n) {
    if (confirm("이 질문을 내 질문으로 등록하시겠습니까?")) {
        window.location.href = "/student/interview/check/" + n;
    } else {
        return false;
    }
}

function interviewQuestionUnCheck(n) {
    if (confirm("이 질문을 내 질문에서 취소하시겠습니까?")) {
        window.location.href = "/student/interview/uncheck/" + n;
    } else {
        return false;
    }
}

function confirmUser(){
    alert("로그인이 필요합니다.");
    window.location.href = "/signin";
}