$(document).ready(function () {
    var url = "ws://localhost:8083/waddle/";
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
                    $("#container").prepend($('<img class="imgContainer" src="img/penguin' + placement.penguin.option + '.png" heightalt="penguin" style="left:' + placement.x + 'px; top:' + placement.y + 'px;" >'));
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
                var xCoord = msgContent.x - parseInt($(".imgContainer").css("width")) / 2;
                var yCoord = msgContent.y - parseInt($(".imgContainer").css("height")) / 2;
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
        let penguId = $("#penguNameOpts").val();
        if (penguId != null) {
            let payload = { info: "spawn-pengu", placement: { x: event.clientX, y: event.clientY, penguin: {id: penguId} } };
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