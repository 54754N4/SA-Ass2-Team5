// use for screen 8
var locationTrack = [];
var oldPointsArr = [];
var context;
function setUpForm() {
  var updateItem = sessionStorage.getItem("updateVisit");
  if (updateItem !== null) {
    data = JSON.parse(updateItem);
    console.log(data);
    var responseDate = moment(data.date).format('MM/DD/YYYY');
    console.log(responseDate);
    $("#visitDate").val(responseDate);
    $("#staticVisitTime").val(data.time).prop("disabled", true);
    $("#newVisitTimeRow").css("display", "none");
    $("#visitReason").val(data.reason).prop("disabled", true);
    $("#patientName").val(data.patientName);
    $("#patientID").val(data.patientID);
    $("#doctorName").val(data.doctor);
    $("#visitNote").val(data.doctorNote);
    plotBodyPicture(data.locationPoints);
    oldPointsArr = data.locationPoints;
  }else {
    $("#staticVisitTimeRow").css("display", "none");
    var updatePatient = sessionStorage.getItem("visitPatient");
    data = JSON.parse(updatePatient);
    $("#patientID").val(data.patientID).prop("disabled", true);
    $("#patientName").val(data.patientName).prop("disabled", true);
  }
}

function setUpBodyPicture() {
  var canvas = document.getElementById("newBodyPicture");
  context = canvas.getContext("2d");
  canvas.height = 325;
  var background = new Image();
  background.src = "./images/picture.png";
  background.onload = function() {
    context.drawImage(background, 0, 0);
  };
  context.clearRect(0, 0, canvas.width, canvas.height);

  canvas.onclick = onCanvasClicked;
  function onCanvasClicked(event) {
    var rect = event.target.getBoundingClientRect();
    var x = event.clientX - rect.left - 9; //x position within the element.
    var y = event.clientY - rect.top + 9; //y position within the element.
    checkForCoordinate(x, y);
  }
}

// this function is used to check the distance between to point
// this function is used to avoid duplication
function checkForCoordinate(newX, newY) {
  var tooAdd = true;
  if (locationTrack.length > 0) {
    for (var i = 0; i < locationTrack.length; i++) {
      oldPoint = locationTrack[i];
      c = calcualteDistance(oldPoint, newX, newY);
      if (c <= 7) {
        tooAdd = false;
        break;
      }
    }
    var oldPointCheck = checkWithOldPoint(newX, newY);
    if (tooAdd && oldPointCheck) saveAndPlot(newX, newY);
  } else {
    var oldPointCheck = checkWithOldPoint(newX, newY);
    if (oldPointCheck) saveAndPlot(newX, newY);
  }
}

function calcualteDistance(oldPoint, newX, newY) {
  var oldX = oldPoint.xCor;
  var oldY = oldPoint.yCor;
  // calculate the distance between points
  var a = oldX - newX;
  var b = oldY - newY;
  var c = Math.sqrt(a * a + b * b);
  return c;
}

// save and plot into the body picture
function saveAndPlot(x, y) {
  cordinate = { xCor: x, yCor: y };
  locationTrack.push(cordinate);
  console.log("location track length: " + locationTrack.length);
  setTimeout(function() {
    context.fillStyle = "#ff0000";
    context.font = "20px FontAwesome"; // You can use any font size of your size.
    context.fillText("\uf05c", x, y);
  }, 100);
}

// this function is used to plot the old lesion on body picture so that the user can classify
function plotBodyPicture(locationPoint) {
  setTimeout(function() {
    context.fillStyle = "#ffAA1D";
    context.font = "20px FontAwesome"; // You can use any font size of your size.
    for (var i = 0; i < locationPoint.length; i++) {
      var singlePoint = locationPoint[i];
      context.fillText("\uf05c", singlePoint.xCor, singlePoint.yCor);
    }
  }, 500);
}

function checkWithOldPoint(newX, newY) {
  if (oldPointsArr.length > 0) {
    for (var i = 0; i < oldPointsArr.length; i++) {
      oldPoint = oldPointsArr[i];
      c = calcualteDistance(oldPoint, newX, newY);
      console.log(c);
      if (c <= 7) {
        return false;
      }
    }
    return true;
  } else return true;
}

function setUpBtnEvent () {
    $("#updateVisitBtn").click (function () {
        addOrUpdate();
        console.log("clicked")
    });
    $("#cancelBtn").click(function () {
       //sessionStorage.removeItem("updateVisit");
        window.location.pathname = "screen7.html";
    })
}

function addOrUpdate () {
    var updateItem = sessionStorage.getItem("updateVisit");
    if (updateItem === null) {
      console.log("add new item");
      sendNewVisitRequest();
    }else {
      sendUpdateVisitRequest();
    }
}
var baseURL = "http://localhost:5000/";
function constuctRequestBody () {
  var visitDate = $("#visitDate").val();
  console.log(visitDate);
  var responseDate = moment(new Date(visitDate)).format('YYYY-MM-DD');

  var visitTime = $("#visitTime").val();
  var patientName = $("#patientName").val();
  var patientID = $("#patientID").val();
  var doctorName = $("#doctorName").val();
  console.log(doctorName);
  var visitNote = $("#visitNote").val();
  var sendData = {
    "patientID" : patientID,
	"startTime": "09:00",
	"endTime": "11:00",
	"visitDate": responseDate,
	"doctorName": doctorName,
	"clinic": "Ho Chi Minh City",
	"visitReason": "as",
	"visitNote": visitNote,
	"lesionDate": "",
	"lesionTime": "",
	"lesionSize": "",
	"lesionLocation": "",
	"lesionNote": "",
	"lesionCoordinates": locationTrack
  }
  console.log(sendData);
  return sendData;
}

function sendAddRequest (callback) {
  var requestData = constuctRequestBody();
  $.ajax ({
    type: "POST",
    url: baseURL + "visit/add",
    data : JSON.stringify(requestData), 
    contentType: "application/json",
    success: function (data) {
      console.log(data);
      console.log(data.visitID);
      sessionStorage.setItem("visitID", data.visitID);
      callback();
    },
    error: function (data) {
      console.log(data)
    }
  })
}

function sendUpdateRequest (callback) {
  var requestData = constuctRequestBody();
  var updateItem = JSON.parse(sessionStorage.getItem("updateVisit"));
  var visitID = updateItem.patientID;
  sessionStorage.setItem("visitID", visitID);
  $.ajax ({
    type: "PUT",
    url: baseURL + "visit/update/" + visitID,
    data: JSON.stringify(requestData),
    contentType: "application/json",
    success: function (data) {
      console.log(data);
      callback();
    },
    error: function (data) {
      console.log(data)
    }
  }) 
}

var lesionBaseURL = "http://localhost:5001";
function getLesionRequestData () {
  var visitID = sessionStorage.getItem("visitID");
  console.log(visitID);
  var requestVisitID = "HCM_" + visitID;
  var lesionDate = $("#lesionDate").val();
  var responseDate = moment(new Date(lesionDate)).format('YYYY-MM-DD');
  var lesionTime = $("#lesionTime").val();
  var lesionSize = $("#lesionSize").val();
  var lesionLocation = $("#lesionLocation").val();
  var lesionNote = $("#lesionNote").val();
  var lesionData = {
    "timeTaken":lesionTime,
    "date": responseDate,
    "size": lesionSize,
    "location": lesionLocation,
    "status": lesionLocation,
    "visitID": requestVisitID,
    "doctorNote": lesionNote
  }
  return lesionData;
}

function sendNewVisitRequest () {
  var validateLesionInput = checkForLesionRecord ();
  if (validateLesionInput === "LESION_ADD") {
    sendAddRequest(function () {
      var requestData = getLesionRequestData();
      $.ajax ({
        type: "POST",
        url: lesionBaseURL + "/lesion/add",
        data : JSON.stringify(requestData), 
        contentType: "application/json",
        success: function (data) {
          console.log(data);
         window.location.pathname = "/screen7.html";
        },
        error: function (data) {
          console.log(data)
        }
      })
    })
  }else if (validateLesionInput === "LESION_NO") {
    sendAddRequest(function() {
      window.location.pathname = "/screen7.html";
    })
  }else {
    alert("INVALID ERROR FOR VALID INPUT");
  }
}

function sendUpdateVisitRequest () {
  var validateLesionInput = checkForLesionRecord ();
  if (validateLesionInput === "LESION_ADD") {
    sendUpdateRequest(function () {
      var requestData = getLesionRequestData();
      $.ajax ({
        type: "POST",
        url: lesionBaseURL + "/lesion/add",
        data : JSON.stringify(requestData), 
        contentType: "application/json",
        success: function (data) {
          console.log(data);
         window.location.pathname = "/screen7.html";
        },
        error: function (data) {
          console.log(data)
          alert("can't create the lesion record");
          window.location.pathname = "/screen7.html";
        }
      })
    })
  }else if (validateLesionInput === "LESION_NO") {
    sendUpdateRequest(function() {
      window.location.pathname = "/screen7.html";
    })
  }else {
    alert("INVALID ERROR FOR VALID INPUT");
  }
}


function checkForLesionRecord () {
  var lesionTime = $("#lesionTime").val().trim();
  var lesionSize = $("#lesionSize").val().trim();
  var lesionLocation = $("#lesionLocation").val().trim();
  var lesionNote = $("#lesionNote").val().trim();
  if (lesionTime !== "" && lesionSize !== "" && lesionLocation !== "" && lesionNote !== "") {
    return "LESION_ADD"; // if all field have values return true
  }else if (lesionTime === "" && lesionSize === "" && lesionLocation === "" && lesionNote === "") {
    return "LESION_NO"; // if all field does not have value return true
  } else {
    return "ERR";
  }
}