var baseURL = "http://localhost:5000/patient";
var currentAction;
var visitMaxPage, detectPageVisit;
var currentPatientID;
var currentPatientData;
// get the patient data and fill in the form
function getPatientDetailAPI(newPatientID) {
    var id;
    console.log(newPatientID);
    if (newPatientID === undefined) {
        var patient = sessionStorage.getItem("selectedPatient");
        patient = JSON.parse(patient);
        console.log(patient);
        id = patient.id;
    }else {
        id = newPatientID;
    }
  $.ajax({
    type: "GET",
    url: baseURL + "/detail/?ID=" + id,
    success: function(data) {
      plotPatientInfoData(data);
      currentPatientData = data;
      currentPatientID = data.patientID;
      loadVisitEntries(data.patientID);
    }
  });
}

// plot data to the input text and disable them for avoiding user to to edit
function plotPatientInfoData(data) {
  $("#firstName")
    .val(data.lastName)
    .prop("disabled", true);
  $("#lastName")
    .val(data.firstName)
    .prop("disabled", true);
  $("#patientID")
    .val(data.patientID)
    .prop("disabled", true);
  $("#title")
    .val(data.title)
    .prop("disabled", true);
  var responseDate = moment(data.birthday).format("MM/DD/YYYY"); // parse the date
  $("#birthday")
    .val(responseDate)
    .prop("disabled", true);
  $("#email")
    .val(data.email)
    .prop("disabled", true);
  $("#occupation")
    .val(data.occupation)
    .prop("disabled", true);
  // if the home address object is null, fill all the field of home address to N/A
  // otherwise field with there values
  if (data.homeAddress === null) {
    $("#address")
      .val("N/A")
      .prop("disabled", true);
    $("#suburd")
      .val("N/A")
      .prop("disabled", true);
    $("#postcode")
      .val("N/A")
      .prop("disabled", true);
    $("#locality-dropdown")
      .val("N/A")
      .prop("disabled", true);
  } else {
    $("#address")
      .val(data.homeAddress.streetAddress)
      .prop("disabled", true);
    $("#suburd")
      .val(data.homeAddress.suburd)
      .prop("disabled", true);
    $("#postcode")
      .val(data.homeAddress.postCode)
      .prop("disabled", true);
    $("#locality-dropdown")
      .val(data.homeAddress.country)
      .prop("disabled", true);
  }

  // if the contact info object is null, fill all the field of contact info to N/A
  // otherwise field with there values
  if (data.contactInfo === null) {
    $("#hphone")
      .val("N/A")
      .prop("disabled", true);
    $("#ophone")
      .val("N/A")
      .prop("disabled", true);
    $("#mphone")
      .val("N/A")
      .prop("disabled", true);
    $("#faxNum")
      .val("N/A")
      .prop("disabled", true);
  } else {
    $("#hphone")
      .val(data.contactInfo.homePhone)
      .prop("disabled", true);
    $("#ophone")
      .val(data.contactInfo.officePhone)
      .prop("disabled", true);
    $("#mphone")
      .val(data.contactInfo.mobilePhone)
      .prop("disabled", true);
    $("#faxNum")
      .val(data.contactInfo.faxNumber)
      .prop("disabled", true);
  }

  // if the next to kin object is null, fill all the field of next to kin to N/A
  // otherwise field with there values
  if (data.nextToKin === null) {
    $("#kinName")
      .val("N/A")
      .prop("disabled", true);
    $("#contactKin")
      .val("N/A")
      .prop("disabled", true);
  } else {
    $("#kinName")
      .val(data.nextToKin.name)
      .prop("disabled", true);
    $("#contactKin")
      .val(data.nextToKin.contactNum)
      .prop("disabled", true);
  }
  // set value for gender and martial status as they are select box
  setGenderAndMS(data.gender, data.married);
}

function setGenderAndMS(gender, marryStatus) {
  // set gender value
  $("#gender")
    .val(gender)
    .change();
  $("#gender").prop("disabled", true);
  // set martial status values
  if (marryStatus === true) marryStatus = "married";
  else marryStatus = "single";
  $("#marryStatus")
    .val(marryStatus)
    .change();
  $("#marryStatus").prop("disabled", true);
}

function loadVisitEntries (patientID) {
    var page = $("currentPage").val();
        if (page === undefined) page = 1;
        $.ajax({
            type: "GET",
            url: "http://localhost:5000" + "/visit/summary/" + patientID + "?page=" + page,
            success: function(data) {
                console.log(data);
                plotVisitData(data);
            }        
    });
}

function plotVisitData (response) {
    var visitData = response.data;
    if (visitData.length > 0) {
        $('#visitEntries').empty();
        visitmaxPage = response.totalPage;
        $("#totalPage").html("of " + visitmaxPage);
        $("#currentPage").val(response.currentPage);
        var startIndex;
        if (response.currentPage === 1) {
            startIndex = response.currentPage;
        }else {
            startIndex = response.currentPage * 5 - 4;
        }
        for (var i = 0; i <  visitData.length; i ++) {
            $('#visitEntries').append(constructVisitList(visitData[i], startIndex));
            startIndex = startIndex + 1;
        }
    }else {
        $("#totalPage").html("N/A");
        $("#currentPage").val("").prop("disabled", true);

    }
}
var visitDataArr = [];
function constructVisitList (data, i) {
    console.log(data.date);
    visitDataArr.push(data.id);
    var responseDate = moment(data.date).format("MM/DD/YYYY"); // parse the date
    var visitInfo = '<div class="col-sm-12">' +
    '<div class="row">' +
        '<h3 for="visit" class="col-sm-4 col-form-label">' + stringifyNumber(i) + ' visit</h3>'+
        '<label for="date" class="col-sm-8 col-form-label">' +responseDate+ '</label>'+
    '</div>'+
    '<div class="row">'+
        '<label for="doctor" class="col-sm-4 col-form-label">Doctor</label>'+
        '<label for="doctorname" class="col-sm-8 col-form-label">' + data.doctor +'</label>'+
    '</div>'+
    '<div class="row">'+
        '<label for="clinic" class="col-sm-4 col-form-label">Clinic</label>'+
        '<label for="clinicname" class="col-sm-8 col-form-label">Ho Chi Minh Hospital</label>'+
    '</div>'+
    '<div class="row">'+
        '<label for="clinic" class="col-sm-9 col-form-label"></label>'+
        '<button class="btn btn-primary stretched-link col-sm-2" onclick="showEditVisit(' + data.id + ')">Edit</button>'+
    '</div>'+
    '<hr>'+
    '</div>'
    return visitInfo;
}


function showEditVisit (id) {
    sessionStorage.setItem("visitID", id);
    window.location.pathname = "/screen7.html";
}

function getVisitListByPage () {
    var currentPage = $("#currentPage").val();
    if (currentPage <= 0) $("#currentPage").val(1);
    else 
    {
        if (currentPage > visitmaxPage) {
            $("#currentPage").val(visitmaxPage);
        }
        if (currentPage !== detectPageVisit) {
            $.ajax({
                type: "GET",
                url: "http://localhost:5000" + "/visit/summary/" + currentPatientID + "?page=" + currentPage,
                success: function(data) {
                    plotVisitData(data);
                }
              });
            detectPageVisit = currentPage;
        }
    }
}

// set the event for all the button have on the screen
// each button will have different functionality
function setUpBtnEvent() {
  // update button (click this btn to create or update the patient record)
  $("#updatePCBtn").click(function() {
    if (currentAction === "update") {
      sendUpdatePCRequest();
    } else if (currentAction === "add") {
      sendAddNewPCRequest();
    }
  });
  // cancel button (click this btn to return to screen 1)
  $("#cancelPCBtn").click(function() {
    if (currentAction === "update" || currentAction === "add") {
      getPatientDetailAPI();
      currentAction = "";
      $("#createNewPC").prop("disabled", false);
      $("#createNewVisit").prop("disabled", false);
      $("#editPC").prop("disabled", false);
    } else {
      window.location.pathname = "/screen1.html";
    }
  });
  // create new visit button (click this btn will go to screen 8? I guess? I will edit later)
  $("#createNewVisit").click(function() {
    sessionStorage.removeItem("updateVisit");
    var patientName = currentPatientData.title + " " + currentPatientData.firstName + " " + currentPatientData.lastName;
    var visitPatient = {
        patientID : currentPatientData.patientID,
        patientName : patientName
    }
    sessionStorage.setItem("visitPatient", JSON.stringify(visitPatient));
    window.location.pathname = "/screen8.html";
  });

  // edit patient record btn, when click this all the field will be unlock for editing
  // however, both create new patient record and create new visit will be
  // disable when this button is clicked
  $("#editPC").click(function() {
    console.log("edit PC");
    $("#updatePCBtn").prop("disabled", false);
    currentAction = "update";
    setUpEditForm();
  });

  // create patient record btn, when click this all the field will be unlock for creating
  // all the field will be blank
  // however, both edit new patient record and create new visit will be
  // disable when this button is clicked
  $("#createNewPC").click(function() {
    $("#updatePCBtn").prop("disabled", false);
    currentAction = "add";
    setUpCreateForm();
  });
}

function constuctPCRequestBody() {
  // patient general information:
  var firstName = $("#firstName").val();
  var lastName = $("#lastName").val();
  var title = $("#title").val();
  var birthday = $("#birthday").val();
  var responseDate = moment(new Date(birthday)).format("YYYY-MM-DD");
  var gender = $("#gender").val();
  var married = $("#marryStatus").val();
  var ms;
  if (married === "single") ms = false;
  else ms = true;
  var occupation = $("#occupation").val();
  var email = $("#email").val();
  // address information
  var streetAddress = $("#address").val();
  var country = $("#locality-dropdown").val();
  var suburd = $("#suburd").val();
  var postCode = $("#postcode").val();
  // contact inforamtion
  var homePhone = $("#hphone").val();
  var officePhone = $("#ophone").val();
  var mobilePhone = $("#mphone").val();
  var faxNumber = $("#faxNum").val();
  // next to kin information
  var kinName = $("#kinName").val();
  var contactKin = $("#contactKin").val();

  // $("#kinName").val("N/A").prop("disabled", true);
  // $("#contactKin").val("N/A").prop("disabled", true);

  // $("#firstName").val(data.lastName).prop("disabled", true);
  // $("#lastName").val(data.firstName).prop("disabled", true);
  // $("#patientID").val(data.patientID).prop("disabled", true);
  // $("#title").val(data.title).prop("disabled", true);
  // $("#address").val("N/A").prop("disabled", true);
  // $("#suburd").val("N/A").prop("disabled", true);
  // $("#postcode").val("N/A").prop("disabled", true);
  // $("#locality-dropdown").val("N/A").prop("disabled", true);

  // $("#hphone").val("N/A").prop("disabled", true);
  // $("#ophone").val("N/A").prop("disabled", true);
  // $("#mphone").val("N/A").prop("disabled", true);
  // $("#faxNum").val("N/A").prop("disabled", true);

  var PCRequest = {
    lastName: lastName,
    firstName: firstName,
    occupation: occupation,
    email: email,
    title: title,
    birthDay: responseDate,
    gender: gender,
    married: ms,
    streetAddress: streetAddress,
    suburd: suburd,
    country: country,
    postalCode: postCode,
    homePhone: homePhone,
    officePhone: officePhone,
    mobilePhone: mobilePhone,
    faxNumber: faxNumber,
    ntkName: kinName,
    ntkContactInfo: contactKin
  };
  console.log(PCRequest);

  return PCRequest;
}

function sendUpdatePCRequest() {
  var requestData = constuctPCRequestBody();
  var patientID = $("#patientID").val();

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

function sendAddNewPCRequest() {
  var requestData = constuctPCRequestBody();
  $.ajax({
    type: "POST",
    url: baseURL + "/add",
    data: JSON.stringify(requestData),
    contentType: "application/json",
    success: function(data) {
      console.log(data.patientID);
      getPatientDetailAPI(data.patientID);
      currentAction = "";
      $("#createNewPC").prop("disabled", false);
      $("#createNewVisit").prop("disabled", false);
      $("#editPC").prop("disabled", false);
    },
    error: function(data) {
      console.log(data);
    }
  });
}

function setUpCreateForm() {
  $("#firstName")
    .val("")
    .prop("disabled", false);
  $("#lastName")
    .val("")
    .prop("disabled", false);
  $("#patientID")
    .val("System will automatically create for you ^^")
    .prop("disabled", true);
  $("#title")
    .val("")
    .prop("disabled", false);
  $("#birthday")
    .val(new Date())
    .prop("disabled", false);
  $("#email")
    .val("")
    .prop("disabled", false);
  $("#occupation")
    .val("")
    .prop("disabled", false);
  $("#marryStatus").prop("disabled", false);
  $("#gender").prop("disabled", false);
  // contry info
  $("#address")
    .val("")
    .prop("disabled", false);
  $("#suburd")
    .val("")
    .prop("disabled", false);
  $("#postcode")
    .val("")
    .prop("disabled", false);
  $("#locality-dropdown")
    .val("")
    .prop("disabled", false);
  // contact info
  $("#hphone")
    .val("")
    .prop("disabled", false);
  $("#ophone")
    .val("")
    .prop("disabled", false);
  $("#mphone")
    .val("")
    .prop("disabled", false);
  $("#faxNum")
    .val("")
    .prop("disabled", false);
  $("#editPC").prop("disabled", true);
  $("#createNewVisit").prop("disabled", true);
  // next to kin
  $("#kinName")
    .val("")
    .prop("disabled", false);
  $("#contactKin")
    .val("")
    .prop("disabled", false);
}

function setUpEditForm() {
  $("#firstName").prop("disabled", false);
  $("#lastName").prop("disabled", false);
  $("#patientID").prop("disabled", true);
  $("#title").prop("disabled", false);
  $("#birthday").prop("disabled", false);
  $("#email").prop("disabled", false);
  $("#occupation").prop("disabled", false);
  $("#marryStatus").prop("disabled", false);
  $("#gender").prop("disabled", false);
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
}