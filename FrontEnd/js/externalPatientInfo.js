var baseURL = "http://localhost:5000";
var detectPagePatient;
var patientmaxPage;
var detectPageVisit;
var visitmaxPage;
var selectedPatient; 
var selectedPatientName;
function queryPatientList() {
    var page = $('#currentPage').val();
    detectPagePatient = page;
    var requestURI;
    if (searchKeyWord === "") {
        requestURI = baseURL + "/patient/all?page=" + page
    }else {
        var searchKeyWord = $("#searchKeyWord").val().trim();
        requestURI = baseURL + "/patient/name?patientName=" + searchKeyWord  +"&page=" + page;
    }
    $.ajax({
        type: "GET",
        url: requestURI,
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
    $("#currentPage").prop("disabled", false);
    if (response.totalPage === 0) {
        $("#currentPage").val(1).prop("disabled", true);
    }
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
    var patientInfo = '<tr class="clickable-row" onclick="getPatientVisitID(' + data.id + ',' + "'" +data.name+ "'"+ ')">' +
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

// ref : https://stackoverflow.com/questions/928849/setting-table-column-width
var special = ['zeroth','first', 'second', 'third', 'fourth', 'fifth', 'sixth', 'seventh', 'eighth', 'ninth', 'tenth', 'eleventh', 'twelfth', 'thirteenth', 'fourteenth', 'fifteenth', 'sixteenth', 'seventeenth', 'eighteenth', 'nineteenth'];
var deca = ['twent', 'thirt', 'fort', 'fift', 'sixt', 'sevent', 'eight', 'ninet'];

function stringifyNumber(n) {
  if (n < 20) return special[n];
  if (n%10 === 0) return deca[Math.floor(n/10)-2] + 'ieth';
  return deca[Math.floor(n/10)-2] + 'y-' + special[n%10];
}

function setUpEventBtn () {
    $("#editBtn").click(function () {
        var patientData = {
            id : selectedPatient,
            name : selectedPatientName
        }
        sessionStorage.setItem("selectedPatient", JSON.stringify(patientData));
        window.location.pathname = "/screen2.html";
    })
    $("#searchBtn").click(function () {
        console.log("search btn");
        var searchKeyWord = $("#searchKeyWord").val().trim();
        console.log(searchKeyWord);
        if(searchKeyWord === "") {
            queryPatientList();
        }else {
            var selectOption = $("#searchOption").val();
            if (selectOption === "name") {
                console.log("name");
                searchForName(searchKeyWord);
            }else if (selectOption == "id") {
                console.log("id");
                searchForID(searchKeyWord);
            }
        }
    })
}

function searchForName (name) {
    var page = $('#currentPage').val();
    detectPagePatient = page;
    var uri = baseURL + "/patient/name?patientName=" + name  +"&page=" + page;
    $.ajax({
        type: "GET",
        url: uri,
        success: function(data) {
          console.log(data);
          plotPatientData(data);
        }
      });
}

function searchForID (ID) {
    $('#currentPage').val("").prop("disabled", true);
    $("#patientList tbody tr").remove();
    $("#pageTotal").html("");
    var patientID = parseInt(ID);
    console.log(patientID);
    if (isNaN(patientID)) alert("Error: Invalid format for ID number");
    else {
        $.ajax({
            type: "GET",
            url: baseURL + "/patient/single?ID=" + patientID,
            success: function(data) {
              console.log(data);
              $('#patientList tbody').append(constructPatientRow(data));
            }
          }); 
    }
}