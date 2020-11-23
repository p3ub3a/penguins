$(document).ready(function () {
    $('#formExpanderBtn').click(function (e) {
        $('#addPenguForm').slideToggle();
        e.stopPropagation();
    });

    $('#penguNameOpts').click(function (e) {
        if(e.target.className == "select"){
            $.ajax({
                type: 'GET',
                url: '/api/penguins',
                success: function (data) {
                    $('.penguNameOpt').remove();
                    $.each(data, function (i, item) {
                        $('#penguNameOpts').append('<option class="penguNameOpt" value=' + item.id + '>' + item.name + '</option>');
                    });
                }
            });
        }
        e.stopPropagation();
    });

    $('#penguInput').click(function(e){
        e.stopPropagation();
    });

    $("#addBtn").click(function(e){
        if($('input[name=penguRadio]:checked', '#addPenguForm').val()){
            $("#penguOpts").css("border", "");
            
            if($('#penguInput').is(':valid') && $('#penguInput').val() != ""){
                $("#penguInput").css("border", "");

                $.ajax({
                    type: "POST",
                    url: '/api/pengu',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    data: JSON.stringify({ 
                        "name": $('#penguInput').val(), 
                        "option": $('input[name=penguRadio]:checked', '#addPenguForm').val() 
                    }),
                    success: function (data) {
                        console.log(data.toString());
                    },
                    dataType: "json"
                });
    
                $('#penguInput').val("");
                unselectPengus();
            }else{
                $("#penguInput").css("border", "2px dashed crimson");
            }
        }else{
            $("#penguOpts").css("border", "2px dashed crimson");
        }
        
        e.stopPropagation();
    });
});

function selectPengu(event) {
    unselectPengus();
    let selection;
    if (event.target.className == "penguImgOpt") {
        selection = event.target.attributes.heightalt.value;
    } else {
        selection = event.target.firstChild.attributes.heightalt.value;
    }

    selection = selection.slice(-1);
    $("#hiddenPenguRadio" + selection).attr("checked", true);
    $("#penguinLi" + selection).css("background-color", "black");
    event.stopPropagation();
}

function unselectPengus(){
    $(".hiddenPenguRadio").removeAttr("checked");
    $(".penguLiOpt").css("background-color", "white");
}