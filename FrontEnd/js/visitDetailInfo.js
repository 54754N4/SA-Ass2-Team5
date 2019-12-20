var baseURL = "http://localhost:5000";
var visitData;
var listOfVisitID;
var allVisitLesion;
// use for screen 7
function getVisitDetailInfo() {
  var visitID = sessionStorage.getItem("visitID");
  console.log(visitID);
  if (visitID !== null) {
    $.ajax({
        type: "GET",
        url: baseURL + "/visit/detail/" + visitID,
        success: function(data) {
          plotVisitDetailData(data);
          visitData = data;
          getPatientVisitList(data.patientID);
          getLesionList();
        }
      });
  }else {
    window.location.pathname = "/screen2.html";
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
  console.log(locationPoint);
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

var lesionBaseURL = "http://localhost:5001"
function getLesionList () {
  var visitID = sessionStorage.getItem("visitID");
  var requestVisitID = "HCM_" + visitID;
  $.ajax({
    type: "GET",
    url: lesionBaseURL + "/lesion/visit/" + requestVisitID,
    success: function(data) {
     // console.log("lesion data");
    //  console.log(data);
      allVisitLesion = data.data;
      checkLesionInfo(data.data);
    }
  });
}

var locationTrack = [];
function checkLesionInfo (lesionData) {
  $("#visitNoteAndLesion").empty();
    for (var i = 0; i < lesionData.length; i ++) {
      var singleLesionData = lesionData[i];
      var currentLocation = singleLesionData.location;
      if (!locationTrack.includes(currentLocation)) {
        console.log(currentLocation);
        locationTrack.push(currentLocation);
        $("#visitNoteAndLesion").append(setUpLesionDisplay(singleLesionData));

      }
    }
}

function setUpLesionDisplay (data) {
  var imageLink = "http://18.136.207.121:9999/thumb/" + data.lesionID;
  var lesionDisplay = '<div class="row" >' +
  '<div class="col-sm-3">' +
      '<div class="row">' +
          '<h3 for="date" class=" col-form-label" onclick="displayLesionDetail(' + "'" + data.location + "'" + ')" >Lesion ' + data.lesionID +'</h3>' +
      '</div>' +
      '<div class="row">' + 
          '<label for="date" class="col-form-label">Taken: ' + data.timeTaken + '</label>' +
      '</div>' +
  '</div>' +
  
  '<div class="col-sm-3">' +
      '<img style="min-height: 100px; height: 100px;" src="' + imageLink+'" class="rounded mx-auto d-block" alt="...">' +
  '</div>' +       
'</div>'
return lesionDisplay;
}

function displayLesionDetail (location) {
  sessionStorage.setItem("lesionLocation", location);
  sessionStorage.setItem("visitLesionList", JSON.stringify(allVisitLesion));
  sessionStorage.setItem("visitData", JSON.stringify(visitData));
  window.location.pathname = "/screen9.html";
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