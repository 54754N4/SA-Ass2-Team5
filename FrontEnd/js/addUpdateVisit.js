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
        sendAddRequest();
    }else {
        sendUpdateRequest();
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

function sendAddRequest () {
  var requestData = constuctRequestBody();
  $.ajax ({
    type: "POST",
    url: baseURL + "visit/add",
    data : JSON.stringify(requestData), 
    contentType: "application/json",
    success: function (data) {
      console.log(data);
    },
    error: function (data) {
      console.log(data)
    }
  })
}

function sendUpdateRequest () {
  var requestData = constuctRequestBody();
  $.ajax ({
    type: "PUT",
    url: baseURL + "visit/update",
    data: JSON.stringify(requestData),
    contentType: "application/json",
    success: function (data) {
      console.log(data);
    },
    error: function (data) {
      console.log(data)
    }
  }) 
}