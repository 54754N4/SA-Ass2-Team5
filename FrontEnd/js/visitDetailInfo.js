var baseURL = "http://localhost:5000";
var visitData;
var listOfVisitID;

// use for screen 7
function getVisitDetailInfo() {
  var visitID = sessionStorage.getItem("visitID");
  if (visitID !== null) {
    $.ajax({
        type: "GET",
        url: baseURL + "/visit/detail/" + visitID,
        success: function(data) {
          plotVisitDetailData(data);
          visitData = data;
          getPatientVisitList(data.patientID);
        }
      });
  }
  
}

function plotVisitDetailData(data) {
  $("#visitDate").html(data.date);
  $("#visitTime").html(data.time);
  $("#patientName").html(data.patientName);
  $("#patientID").html(data.patientID);
  $("#doctorName").html(data.doctor);
  $("#visitnote").val(data.doctorNote);
  plotBodyPicture(data.locationPoints);
}

function plotBodyPicture(locationPoint) {
  var canvas = document.getElementById("bodyPicture");
  var context = canvas.getContext("2d");
  canvas.height = 325;
  var background = new Image();
  background.src = "./images/picture.png";
  background.onload = function() {
    context.drawImage(background, 0, 0);
  };
  context.clearRect(0, 0, canvas.width, canvas.height);
  context.save();
  // Why setTimeout - Because it takes time to load FontAwesome Fonts file.
  setTimeout(function() {
    context.fillStyle = "#ff0000";
    context.font = "20px FontAwesome"; // You can use any font size of your size.
    for (var i = 0; i < locationPoint.length; i++) {
      var singlePoint = locationPoint[i];
      context.fillText("\uf05c", singlePoint.xCor, singlePoint.yCor);
    }
  }, 500);
}

function setUpBtnEvent () {
    $("#updateBtn").click (function () {
        sessionStorage.setItem("updateVisit", JSON.stringify(visitData));
        window.location.pathname = "screen8.html";
    });
    $("#addBtn").click(function () {
        sessionStorage.removeItem("updateVisit");
        window.location.pathname = "screen8.html";
    });
    
}

function getPatientVisitList (patientID) {
  $.ajax({
    type: "GET",
    url: baseURL + "/visit/all/" + patientID,
    success: function(data) {
      listOfVisitID = data.idList;
      if (listOfVisitID.length == 1) {
        $("#iterateBtn").css("display", "none");
    }
    var visitID = sessionStorage.getItem("visitID");
    $("#iterateBtn").click(function () {
       var currentIndex = listOfVisitID.indexOf(parseInt(visitID));
       if (listOfVisitID[currentIndex + 1] === undefined) {
        $("#iterateBtn").css("display", "none");
       }else {
        visitID = listOfVisitID[currentIndex + 1];
        sessionStorage.setItem("visitID", visitID);
        getVisitDetailInfo();
       }
    });
    }
  });
}