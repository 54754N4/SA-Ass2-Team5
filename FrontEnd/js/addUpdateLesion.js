function setUpLesionForm () {
    var action = sessionStorage.getItem("lesionAction");
    var visitID = sessionStorage.getItem("visitID");
    if (action === "add") {

    }else if (action === "update") {
        //var lesionRecord = getLesionList(visitID);
        //console.log(lesionRecord);
        fetchLesionRecord();
    }
}

var lesionBaseURL = "http://localhost:5001"
function fetchLesionRecord () {
  var visitID = sessionStorage.getItem("visitID");
  var requestVisitID = "HCM_" + visitID;
  var location = sessionStorage.getItem("lesionLocation");
  var requestURI = lesionBaseURL + "/lesion/location?visitID=" + requestVisitID + "&location=" +location;
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
    $("#doctor").val (visitData.doctor);
    $("#patient").val(visitData.patientName);
    $("#lesionid").val(lastestData.lesionID);
    $("#status").val(lastestData.status);
    $("#location").val (lastestData.location);
    //plotHistoryInfo(data);
}


var lesionBaseURL = "http://localhost:5001"
function getLesionList (visitID) {
  var visitID = sessionStorage.getItem("visitID");
  var requestVisitID = "HCM_" + visitID;
  $.ajax({
    type: "GET",
    url: lesionBaseURL + "/lesion/visit/" + requestVisitID,
    success: function(data) {
     // console.log("lesion data");
    //  console.log(data);
    getLesionLocationList(data.data);
    }
  });
}

function getLesionLocationList (data) {
    var location = sessionStorage.getItem("lesionLocation");
    lesionLocation = data.filter(x => x.location === location);
    console.log(location);
}

function setBtnEvent () {
    $("#cancelBtn").click(function () {
        console.log("cancel button");
        window.location.pathname = "/screen9.html";
    });
    $("#addOrUpdateBtn").click (function () {
        console.log("add or update");
        uploadImages();
        // var action = sessionStorage.getItem("lesionAction");
        // if (action === "update") {
        //     sendUpdateRequest ();
        // }else if (action === "add") {
        //     sendAddNewRequest ();
        // }
    })
}

function construcRequestBody () {
    var requestBody = {
        "timeTaken":"9:59",
        "date": "2019-12-12",
        "size": 99.9,
        "location": "Brain",
        "status": "Shoulder",
        "visitID": "HCM_2",
        "doctorNote": "Needs immediate attention, referred to mental hospital"
    };
    return requestBody;
}


function sendUpdateRequest () {
    var requestData = constuctRequestBody();
    $.ajax ({
      type: "PUT",
      url: baseURL + "lesion/update/" + lesionID,
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

function sendAddNewRequest () {
    var requestData = construcRequestBody();
    $.ajax ({
      type: "POST",
      url: lesionBaseURL + "/lesion/add",
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

function uploadImages ( ) {
    var fd = new FormData();
    var files = $('#image_uploads')[0].files[0];
    fd.append('file',files);
   
    $.ajax ({
        type: "POST",
        crossDomain: true,
        url: "http://18.136.207.121:9999/api/images",
        headers: {
            "Authorization": "Basic dXNlcjp1c2Vy",
        },
        data: fd,
        contentType: "application/x-www-form-urlencoded",
        processData: false,
        success: function (data) {
          console.log(data);
        },
        error: function (data) {
          console.log(data)
        }
      })
}