var baseURL = "http://localhost:5000";
var detectPagePatient;
var patientmaxPage;
var detectPageVisit;
var visitmaxPage;
var selectedPatient; 
function queryPatientList() {
    var page = $('#currentPage').val();
    detectPagePatient = page;
    $.ajax({
        type: "GET",
        url: baseURL + "/patient/all?page=" + page,
        success: function(data) {
          console.log(data);
          plotPatientData(data);
        }
      });
}


function plotPatientData (response) {
    $("#patientList tbody tr").remove();
    $("#pageTotal").html("of " + response.totalPage);
    $("#currentPage").val(response.currentPage);
    var patientData = response.data;
    patientmaxPage = response.totalPage;
    for (var i = 0; i <  patientData.length; i ++) {
        $('#patientList tbody').append(constructPatientRow(patientData[i]));
    }

}

function getPatientValidPage () {
    var currentPage = $("#currentPage").val();
    if (currentPage <= 0) $("#currentPage").val(1);
    else 
    {
        if (currentPage > patientmaxPage) {
            $("#currentPage").val(patientmaxPage);
        }
        if (currentPage !== detectPagePatient) {
            queryPatientList();
            detectPagePatient = currentPage;
        }
    }
}

function constructPatientRow (data) {
    var patientInfo = '<tr class="clickable-row" onclick="getPatientVisitID(' + data.id + ')">' +
    '<td>' + data.name + '</td>' +
    '<td>' + data.id + '</td>' +
    '<td>' + data.gender + '</td>' +
    '<td>' + data.dob + '</td>' +
    '<td>' + data.address + '</td>' +
    '<td>' + data.mobileNum + '</td>' +
  '</tr>';
  $('#patientList').on('click', '.clickable-row', function(event) {
    $(this).addClass('active').siblings().removeClass('active');
  });
  return patientInfo;

}

function getPatientVisitID(id) {
    if (selectedPatient !== id ){
        var page = $("currentVisitPage").val();
        if (page === undefined) page = 1;
        $.ajax({
            type: "GET",
            url: baseURL + "/visit/summary/" + id + "?page=" + page,
            success: function(data) {
                plotVisitData(data);
            }
            
          });
          selectedPatient = id;
    }
}

function getVisitListByPage () {
    var currentPage = $("#currentVisitPage").val();
    if (currentPage <= 0) $("#currentVisitPage").val(1);
    else 
    {
        if (currentPage > visitmaxPage) {
            $("#currentPage").val(visitmaxPage);
        }
        if (currentPage !== detectPageVisit) {
            $.ajax({
                type: "GET",
                url: baseURL + "/visit/summary/" + selectedPatient + "?page=" + currentPage,
                success: function(data) {
                    plotVisitData(data);
                }
              });
            detectPageVisit = currentPage;
        }
    }
}
var visitDataArr = [];
function plotVisitData (response) {
    $("#visitList tr").remove();
    $("#patientName").html (response.patientName);
    $("#visitDateRange").html(response.visitDateRange);
    $("#infoVisitText").css("display", "none");
    $("#visitArea").css("display", "block");
    var visitData = response.data;
    if (visitData.length > 0) {
        visitmaxPage = response.totalPage;
        $("#pageVisitTotal").html("of " + visitmaxPage);
        $("#currentVisitPage").val(response.currentPage);
        for (var i = 0; i <  visitData.length; i ++) {
            $('#visitList tbody').append(constructVisitList(visitData[i]));
        }
    }else {
        $("#infoVisitText").css("display", "block");
        $("#infoVisitText").html("This patient does not have any visit record");
        $("#visitArea").css("display", "none");
    }
   
}

function constructVisitList (data) {
    visitDataArr.push(data.id);
    var visitInfo = '<tr>' +
    '<th scope="col-8">' +
       '<p id="visitIndex">' + stringifyNumber(data.id) + " visit" + '</p>' +
        '<p id="date">' + data.date  + '</p>' +
        '<p id="time">' + data.time + '</p>' +
        '<p id="reason">' + data.reason +'</p>' +
    '</th>' +
    '<th scope="col-4">' +
        '<p id="doctorName">' + data.doctor + '</p>' +
        '<p id="clinic">Ho Chi Minh Hospital</p>' +
        '<button class="btn btn-info" onclick="showVisitDetail(' + data.id + ')">View Detail</button>' +
    '</th> </tr>';
    return visitInfo;
}

function showVisitDetail (id) {
    sessionStorage.setItem("visitID", id);
    console.log(sessionStorage.getItem("visitID"));
    console.log(sessionStorage.setItem("visitList",JSON.stringify(visitDataArr)));
    window.location.pathname = "screen7.html";
}


// ref : https://stackoverflow.com/questions/928849/setting-table-column-width
var special = ['zeroth','first', 'second', 'third', 'fourth', 'fifth', 'sixth', 'seventh', 'eighth', 'ninth', 'tenth', 'eleventh', 'twelfth', 'thirteenth', 'fourteenth', 'fifteenth', 'sixteenth', 'seventeenth', 'eighteenth', 'nineteenth'];
var deca = ['twent', 'thirt', 'fort', 'fift', 'sixt', 'sevent', 'eight', 'ninet'];

function stringifyNumber(n) {
  if (n < 20) return special[n];
  if (n%10 === 0) return deca[Math.floor(n/10)-2] + 'ieth';
  return deca[Math.floor(n/10)-2] + 'y-' + special[n%10];
}