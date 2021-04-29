$(document).ready(function () {
    $('#showmeet').on('click', function() {

        $data = $("#showmeet").data('id');
        $.ajax({
            type: "GET",
            url: filterpath+'?id='+$data,
            dataType: "json",
            success: function (data) {
                if (data.status === "ERROR") {

                } else {
                    $("#datetext").empty().append(moment(data.data.disponibilite.datedeb).format("MMM. D, YYYY [at] h:mm A z"));
                    $("#studentIdtext").empty().append(data.data.idStudent.email);
                    $("#specialiteIdtext").empty().append(data.data.specialite);

                    $("#showMeet").modal("show");

                }
            },
            error: function (data) {
                console.log("error");
                console.log(data.responseText);
            },
        });
    });

});