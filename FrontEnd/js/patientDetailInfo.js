var baseURL = "http://localhost:5000/patient";
var currentAction;
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
    $("#firstName").val(data.lastName).prop("disabled", true);
    $("#lastName").val(data.firstName).prop("disabled", true);
    $("#patientID").val(data.patientID).prop("disabled", true);
    $("#title").val(data.title).prop("disabled", true);
    var responseDate = moment(data.birthday).format('MM/DD/YYYY');
    $("#birthday").val(responseDate).prop("disabled", true);
    $("#email").val(data.email).prop("disabled", true);
    $("#occupation").val(data.occupation).prop("disabled", true);
    if(data.homeAddress === null) {
        $("#address").val("N/A").prop("disabled", true);
        $("#suburd").val("N/A").prop("disabled", true);
        $("#postcode").val("N/A").prop("disabled", true);
        $("#locality-dropdown").val("N/A").prop("disabled", true);
    }else {
        $("#address").val(data.homeAddress.streetAddress).prop("disabled", true);
        $("#suburd").val(data.homeAddress.suburd).prop("disabled", true);
        $("#postcode").val(data.homeAddress.postCode).prop("disabled", true);
        $("#locality-dropdown").val(data.homeAddress.country).prop("disabled", true);
    }
    if (data.contactInfo === null) {
        $("#hphone").val("N/A").prop("disabled", true);
        $("#ophone").val("N/A").prop("disabled", true);
        $("#mphone").val("N/A").prop("disabled", true);
        $("#faxNum").val("N/A").prop("disabled", true);
    }else {
        $("#hphone").val(data.contactInfo.homePhone).prop("disabled", true);
        $("#ophone").val(data.contactInfo.officePhone).prop("disabled", true);
        $("#mphone").val(data.contactInfo.mobilePhone).prop("disabled", true);
        $("#faxNum").val(data.contactInfo.faxNumber).prop("disabled", true);
    }
    if (data.nextToKin === null) {
        $("#kinName").val("N/A").prop("disabled", true);
        $("#contactKin").val("N/A").prop("disabled", true);
    }  else {
        $("#kinName").val(data.nextToKin.name).prop("disabled", true);
        $("#contactKin").val(data.nextToKin.contactNum).prop("disabled", true);
    }
    $("#gender").val();
    $("#marryStatus").val();

    setGenderAndMS(data.gender, data.married);
}

function setGenderAndMS (gender, marryStatus) { 
    $("#gender").val(gender).change();
    $("#gender").prop("disabled", true);

    if (marryStatus === true) marryStatus = "married";
    else marryStatus = "single"
    $("#marryStatus").val(marryStatus).change();
    $("#marryStatus").prop("disabled", true)
}

function setUpBtnEvent () {
    $("#updatePCBtn").click(function () {
        if (currentAction === "update") {
            sendUpdatePCRequest();
        }else if (currentAction === "add") {
            sendAddNewPCRequest();
        }
    });

    $("#cancelPCBtn").click(function () {
        window.location.pathname = "/screen1.html";
    })

    $("#createNewVisit").click (function () {
        console.log("create new visit");
    })

    $("#editPC").click (function () {
        console.log("edit PC");
        $("#updatePCBtn").prop("disabled", false);
        currentAction = "update";
        setUpEditForm();
    })

    $("#createNewPC").click (function () {
        $("#updatePCBtn").prop("disabled", false);
        currentAction = "add";
        setUpCreateForm();
    })

}


function constuctPCRequestBody () {
    var country = $("#locality-dropdown").val();
    console.log(country);
    var PCRequest =
    {
        "lastName": "Hoang",
        "firstName": "Tran",
        "occupation": "retired",
        "email": "honag@gmail.com",
        "title": "Mr", 
        "birthDay": "2019-09-24",
        "gender": "M", 
        "married": false,
        "streetAddress" : "702 Nguyen Van Linh",
        "suburd": "District 1", 
        "country": "Vietnam", 
        "homePhone": "09019029103",
        "officePhone": "09019029103",
        "mobilePhone": "09019029103",
        "faxNumber": "09019029103",
        "ntkName": "Dinh Minh",
        "ntkContactInfo": "0906486787"
    }
    return PCRequest;
}

function sendUpdatePCRequest () {
    var requestData = constuctPCRequestBody();
    // $.ajax ({
    //   type: "PUT",
    //   url: baseURL + "/update",
    //   data: JSON.stringify(requestData),
    //   contentType: "application/json",
    //   success: function (data) {
    //     console.log(data);
    //   },
    //   error: function (data) {
    //     console.log(data)
    //   }
    // }) 
}

function sendAddNewPCRequest () {
    var requestData = constuctPCRequestBody();
//   $.ajax ({
//     type: "POST",
//     url: baseURL + "visit/add",
//     data : JSON.stringify(requestData), 
//     contentType: "application/json",
//     success: function (data) {
//       console.log(data);
//     },
//     error: function (data) {
//       console.log(data)
//     }
//   })
}


function setUpCreateForm () {
    $("#firstName").val("").prop("disabled", false);
        $("#lastName").val("").prop("disabled", false);
        $("#patientID").val("System will automatically create for you ^^").prop("disabled", true);
        $("#title").val("").prop("disabled", false);
        $("#birthday").val(new Date()).prop("disabled", false);
        $("#email").val("").prop("disabled", false);
        $("#occupation").val("").prop("disabled", false);
        // contry info
        $("#address").val("").prop("disabled", false);
        $("#suburd").val("").prop("disabled", false);
        $("#postcode").val("").prop("disabled", false);
        $("#locality-dropdown").val("").prop("disabled", false);
        // contact info
        $("#hphone").val("").prop("disabled", false);
        $("#ophone").val("").prop("disabled", false);
        $("#mphone").val("").prop("disabled", false);
        $("#faxNum").val("").prop("disabled", false);
        $("#editPC").prop("disabled", true);
        $("#createNewVisit").prop("disabled", true);
        // next to kin
        $("#kinName").val("").prop("disabled", false);
        $("#contactKin").val("").prop("disabled", false);
}

function setUpEditForm () {
    $("#firstName").prop("disabled", false);
    $("#lastName").prop("disabled", false);
    $("#patientID").prop("disabled", true);
    $("#title").prop("disabled", false);
    $("#birthday").prop("disabled", false);
    $("#email").prop("disabled", false);
    $("#occupation").prop("disabled", false);
    // address info
    $("#address").prop("disabled", false);
    $("#suburd").prop("disabled", false);
    $("#postcode").prop("disabled", false);
    $("#locality-dropdown").prop("disabled", false);
    // contact info
    $("#hphone").prop("disabled", false);
    $("#ophone").prop("disabled", false);
    $("#mphone").prop("disabled", false);
    $("#faxNum").prop("disabled", false);
    $("#kinName").prop("disabled", false);
    $("#contactKin").prop("disabled", false);
    $("#createNewPC").prop("disabled", true);
    $("#createNewVisit").prop("disabled", true);

}