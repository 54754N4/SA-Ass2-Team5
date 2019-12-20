function loadLeisonDetail () {
    var visitLesionID = sessionStorage.getItem("lesionID");
    var lesionData = JSON.parse(sessionStorage.getItem("visitLesionList"));
    var visitData = JSON.parse(sessionStorage.getItem("visitData"));
    plotGeneralInfo (visitData, visitLesionID, lesionData);
    // console.log(visitLesionID);
     console.log(lesionData);
    // console.log(visitData);
}

function plotGeneralInfo (visitData, lesionID, lesiondata) {
    $("#doctor").html (visitData.doctor);
    $("#patient").html(visitData.patientName);
    var item = lesiondata.find(item => item.lesionID === parseInt(lesionID));
    console.log(item);
    $("#lesionid").html(item.lesionID);
    $("#status").html(item.status);
    $("#location").html (item.location);
    plotHistoryInfo(item.location, lesiondata);
}

function plotHistoryInfo (location, lesionData) {
    $("#lesionHistory").empty();
    var history = lesionData.filter(x => x.location === location);
    console.log(history);
    for (var i = 0; i < history.length; i ++) {
        $("#lesionHistory").append(constructLesionHistoryRow(history[i]));
    }
}

function constructLesionHistoryRow (data) {
    var imageLink = "";
    var display = '<div class="row">' +
    '<div class="col-md-3 order-md-1">' +
            '<li class="list-group-item">' +
                '<label for="date" class="col-sm-12 col-form-label">25/05/1995</label>' +
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