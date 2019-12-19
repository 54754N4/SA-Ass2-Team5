var baseURL = "http://localhost:5000/patient";
function getPatientDetailAPI () {
    var patient = sessionStorage.getItem("selectedPatient");
    patient = JSON.parse(patient);
    console.log(patient);
    $.ajax({
        type: "GET",
        url: baseURL + "/detail/?ID=" + patient.id,
        success: function(data) {
          console.log(data);
          plotPatientInfoData(data);
        }
      });
}

function plotPatientInfoData (data) {
    $("#firstName").val(data.lastName);
    $("#lastName").val(data.firstName);
    $("#patientID").val(data.patientID);
    $("#title").val(data.title);
    $("#gender").val();
    $("#birthday").val(data.birthday);
    $("#marryStatus").val();
    $("#email").val(data.email);
    if(data.homeAddress === null) {
        $("#address").val("N/A");
        $("#suburd").val("N/A");
        $("#postcode").val("N/A");
        $("#country").val("N/A");
    }else {
        $("#address").val("N/A");
        $("#suburd").val("N/A");
        $("#postcode").val("N/A");
        $("#country").val("N/A");
    }
    if (data.contactInfo === null) {
        $("#hphone").val("N/A");
        $("#ophone").val("N/A");
        $("#mphone").val("N/A");
        $("#fax").val("N/A");
    }else {
        $("#hphone").val("N/A");
        $("#ophone").val("N/A");
        $("#mphone").val("N/A");
        $("#fax").val("N/A");
    }
    if (data.nextToKin === null) {
        $("#kinName").val("N/A");
        $("#contactKin").val("N/A");
    }  else {
        $("#kinName").val("N/A");
        $("#contactKin").val("N/A");
    }
   
}