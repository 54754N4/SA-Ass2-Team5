function setUpLesionForm () {
    var action = sessionStorage.getItem("lesionAction");
    var visitID = sessionStorage.getItem("visitID");
    if (action === "add") {

    }else if (action === "update") {
        var lesionRecord = getLesionList(visitID);
        console.log(lesionRecord);
    }
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