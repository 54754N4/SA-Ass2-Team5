var lesionHistory = [];
var lesionLocation;
function loadLeisonDetail () {
    fetchLesionRecord();

    var visitLesionID = sessionStorage.getItem("lesionID");
    var lesionData = JSON.parse(sessionStorage.getItem("visitLesionList"));
    var visitData = JSON.parse(sessionStorage.getItem("visitData"));
   // plotGeneralInfo (visitData, visitLesionID, lesionData);
    // console.log(visitLesionID);
    // console.log(lesionData);
    // console.log(visitData);
}


var lesionBaseURL = "http://localhost:5001"
function fetchLesionRecord () {
  var visitID = sessionStorage.getItem("visitID");
  var requestVisitID = "HCM_" + visitID;
  var location = sessionStorage.getItem("lesionLocation");
  console.log(location);
  var requestURI = lesionBaseURL + "/lesion/location?visitID=" + requestVisitID + "&location=" +location;
  console.log(requestURI);
  $.ajax({
    type: "GET",
    url: requestURI,
    success: function(data) {
    
    console.log(data);
    plotLatestInfo(data.data);
    }
  });
}

function plotLatestInfo (data) {
    var lastestData = data[0];
    console.log(lastestData);
    var visitData = JSON.parse(sessionStorage.getItem("visitData"));
    $("#doctor").html (visitData.doctor);
    $("#patient").html(visitData.patientName);
    $("#lesionid").html(lastestData.lesionID);
    $("#status").html(lastestData.status);
    $("#location").html (lastestData.location);
    plotHistoryInfo(data);
}

function plotHistoryInfo (data) {
    $("#lesionHistory").empty();
    for (var i = 1; i < data.length; i ++) {
        $("#lesionHistory").append(constructLesionHistoryRow(data[i]));
    }
}

function plotGeneralInfo (visitData, lesionID, lesiondata) {
    $("#doctor").html (visitData.doctor);
    $("#patient").html(visitData.patientName);
    var item = lesiondata.find(item => item.lesionID === parseInt(lesionID));
    $("#lesionid").html(item.lesionID);
    $("#status").html(item.status);
    $("#location").html (item.location);
    lesionLocation = item.location;
    plotHistoryInfo(item.location, lesiondata);
}

// function plotHistoryInfo (location, lesionData) {
//     $("#lesionHistory").empty();
//     lesionHistory = lesionData.filter(x => x.location === location);
//     console.log(history);
//     for (var i = 0; i < lesionHistory.length; i ++) {
//         $("#lesionHistory").append(constructLesionHistoryRow(lesionHistory[i]));
//     }
// }

function constructLesionHistoryRow (data) {
    console.log(data);
    var responseDate = moment(data.date).format('MM/DD/YYYY');

    var imageLink = "";
    var display = '<div class="row">' +
    '<div class="col-md-3 order-md-1">' +
            '<li class="list-group-item">' +
                '<label for="date" class="col-sm-12 col-form-label">'+ responseDate + '</label>' +
                '<label for="time" class="col-sm-12 col-form-label">' + data.timeTaken +'</label>' +
                '<label for="size" class="col-sm-12 col-form-label">' + data.size + 'cm in size</label>' +
            '</li>' +
    '</div>' +
    '<div class="col-md-2 order-md-1">' +
        '<img style="min-height: 140px; height: 140px;" src="'+ imageLink+'" class="rounded mx-auto d-block" alt="...">' +
    '</div>' + 
    '<div class="col-md-7 order-md-2">' +
        '<form class="form-inline">' +
            '<label for="status" class="mb-2">Note:</label>' +

            '<div class="form-group mx-sm-3 mb-2">' +
              '<textarea class="form-control" rows="5" style="width: 500px;" disabled>' + data.doctorNote +'</textarea>' +
            '</div>' +
          '</form>' +
        '<br class="mb-4">' +
        '<br>' +
    '</div>' +   
'</div>'
    return display;
}

function setUpEventBtn () {
    $("#updateRecord").click(function  () {
        console.log("update button");
       // sessionStorage.setItem("lesionLocation", lesionLocation)
     //   sessionStorage.setItem("lesionList", JSON.stringify(lesionHistory));
        sessionStorage.setItem("lesionAction", "update");
        window.location.pathname = "/screen10.html";
    });
    $("#addNewRecord").click (function () {
        console.log("add new button");
        sessionStorage.setItem("lesionAction", "add");
        window.location.pathname = "/screen10.html";
    })
}