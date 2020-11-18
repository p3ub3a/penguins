$( document ).ready(function() {
    var url="ws://localhost:8083/waddle/";
    var user;
    $.ajax({
        async: false,
        type: 'GET',
        url: '/user',
        success: function(data) {
            user = data.toString()
            url = url.concat(user);
        }
    });

    var ws = new WebSocket(url);

    ws.onopen = function(){
        console.log("ws online");
    }

    ws.onmessage = function(event){
        console.log(event);
        var finalData = JSON.parse(event.data.replace(/\\/g, ""));
        console.log(finalData.content);
        switch(finalData.info){
            case "connected":
                console.log(user + " connected");
                break;
            case "disconnected":
                console.log(user + " disconnected");
                break;
            case "spawnPengu":
                msgContent = finalData.messageContent;
                $("#container").prepend($('<img class="imgContainer" src="img/penguin' + msgContent.option + '.png" heightalt="penguin" style="left:' + msgContent.x + 'px; top:'+ msgContent.y + 'px;" >'));
        }
    }

    ws.onclose = function(){
        console.log("ws offline");
    }

    $( "#container" ).click(function(event) {
        let option = $("#penguinOpts").val();
        if(option != null){
            var payload = {option : option, x: event.clientX, y: event.clientY};
            console.log(JSON.stringify(payload));
            ws.send(JSON.stringify(payload));
            // $("#container").prepend($('<img class="imgContainer" src="img/penguin' + option + '.png" heightalt="penguin" style="left:' + event.clientX + 'px; top:'+ event.clientY + 'px;" >'));
        }
    }); 
    
    $('#penguinOpts').click(function(e){
        e.stopPropagation();
    });
    
    $('#clearBtn').click(function(e){
        $('.imgContainer').remove();
        e.stopPropagation();
    });
});



$.get('/api',  // url
    function (data, textStatus, jqXHR) {  // success callback
        $.each(data, function(i, item){
            $('#penguinOpts').append('<option value=' + item.id + '>' + item.name + '</option>');
        });
});