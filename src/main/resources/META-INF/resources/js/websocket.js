$(document).ready(function () {
    var url = window.location.href.replace("http", "ws") + "waddle/";
    var user;
    $.ajax({
        async: false,
        type: 'GET',
        url: '/api/user',
        success: function (data) {
            user = data.toString()
            url = url.concat(user);
        }
    });

    createFakeImgContainer();

    var ws = new WebSocket(url);

    ws.onopen = function () {
        console.log("ws client connected");
        $.ajax({
            type: 'GET',
            url: '/api/placements',
            success: function (data) {
                console.log(data);
                $.each(data, function (i, placement) {
                    $("#container").prepend($('<img class="imgContainer" src="img/penguin' + placement.penguin.option + '.png" heightalt="penguin" style="left:' + (placement.x * $(window).width()) + 'px; top:' + (placement.y * $(window).height()) + 'px;" >'));
                });
            }
        });
    }

    ws.onmessage = function (event) {
        console.log(event);
        var finalData = JSON.parse(event.data.replace(/\\/g, ""));
        switch (finalData.info) {
            case "connected":
                console.log(user + " connected");
                break;
            case "disconnected":
                console.log(user + " disconnected");
                break;
            case "spawn-pengu":
                var msgContent = finalData.placement;
                var xCoord = (msgContent.x * $(window).width()) - parseInt($(".imgContainer").css("width")) / 2;
                var yCoord = (msgContent.y * $(window).height()) - parseInt($(".imgContainer").css("height")) / 2;
                $("#container").prepend($('<img class="imgContainer" src="img/penguin' + msgContent.penguin.option + '.png" heightalt="penguin" style="left:' + xCoord + 'px; top:' + yCoord + 'px;" >'));
                break;
            case "remove-placements":
                $('.imgContainer').remove();
                createFakeImgContainer();
                break;
        }
    }

    ws.onclose = function () {
        console.log("ws client disconnected");
    }

    $("#container").click(function (event) {
        console.log(event);
        let penguId = $("#penguNameOpts").val();
        if (penguId != null) {
            let xPercent = event.clientX / $(window).width();
            let yPercent = event.clientY / $(window).height();
            let payload = { info: "spawn-pengu", placement: { x: xPercent, y: yPercent, penguin: {id: penguId} } };
            ws.send(JSON.stringify(payload));
        }
    });

    $('#clearScrBtn').click(function (e) {
        let payload = { info: "remove-placements"};
        ws.send(JSON.stringify(payload));
        e.stopPropagation();
    });
});

function createFakeImgContainer() {
    $('body').prepend($('<div style="display:none;" class="imgContainer"></div>'));
}