function LoadingBarStart() {
    var backHeight = $(document).height();
    var backWidth = window.document.body.clientWidth;
    var backGroundCover = "<div id='back'></div>";
    var loadingBarImage = '';

    loadingBarImage += "<div id='loadingBar'>";
    loadingBarImage += " <img src='../img/loading150.gif'/>";
    loadingBarImage += "</div>";

    $('body').append(backGroundCover).append(loadingBarImage);
    $('#back').css({ 'width': backWidth, 'height': backHeight, 'opacity': '0.3' });
    $('#back').show();
    $('#loadingBar').show();
}
function LoadingBarEnd() {
    $('#back, #loadingBar').hide();
    $('#back, #loadingBar').remove();
}
