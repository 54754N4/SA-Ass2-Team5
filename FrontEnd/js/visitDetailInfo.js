var baseURL = "http://localhost:5000";
function getVisitDetailInfo () {
    var visitID = sessionStorage.getItem("visitID");
    plotBodyPicture();
    $.ajax({
        type: "GET",
        url: baseURL + "/visit/detail/" + visitID,
        success: function(data) {
            console.log(data);
            plotVisitDetailData(data);
        }
        
      });
}

function plotVisitDetailData (data) {
    $("#visitDate").html(data.date);
    $("#visitTime").html(data.time)
    $("#patientName").html(data.patientName);
    $("#patientID").html(data.patientID);
    $("#doctorName").html(data.doctor);
    $("#visitnote").val(data.doctorNote);
}

function plotBodyPicture () {
var canvas = document.getElementById("bodyPicture"),
ctx = canvas.getContext("2d");

canvas.height = 325;


var background = new Image();
background.src = "./images/picture.png";
background.onload = function(){
    ctx.drawImage(background,0,0);   
}

}