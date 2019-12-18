// hard code the location and name of hospital
var hcmHospital = {lat: 10.8231, lng: 106.6297};
var texasHospital = {lat: 29.3838, lng:  -94.9027};
var ausHospital = {lat: -37.8047, lng: 144.9580};
var hcmHospitalName = '<h3>Ho Chi Minh Hospital</h3>';
var ausHospitalName = '<h3>RMIT Hospital</h3>';
var texasHospialName = '<h3>Texas Hospital</h3>';
var detailMarker;

// set the marker and their listeners when click on the world map
function setMarker (map, detailMap) { 
    var hcmHospitalMaker = new google.maps.Marker({position: hcmHospital, map: map});
    var texasHospitalMarker = new google.maps.Marker({position: texasHospital, map: map});
    var ausHospitalMarker = new google.maps.Marker({position: ausHospital, map: map});
    if (detailMarker === undefined) {
        detailMarker = new google.maps.Marker({ map: detailMap});
    }
    // hcm city marker world map click will open the focus view on country map
    // the same principle is applied for texas and australias hospitals
    hcmHospitalMaker.addListener('click', function() {
        detailMarker.setPosition(hcmHospital);
        detailMap.setCenter(detailMarker.getPosition());
        getCountryBound("Vietnam", detailMap);
        addInfoWindow(detailMarker, detailMap, hcmHospitalName);

    });
    texasHospitalMarker.addListener('click', function() {
        detailMarker.setPosition(texasHospital);
        detailMap.setCenter(detailMarker.getPosition());
        getCountryBound("US", detailMap);
        addInfoWindow(detailMarker, detailMap, texasHospialName);

    });
    ausHospitalMarker.addListener('click', function() {
        detailMarker.setPosition(ausHospital);
        detailMap.setCenter(detailMarker.getPosition());
        getCountryBound("Australia", detailMap);
        addInfoWindow(detailMarker, detailMap, ausHospitalName);
    });
}

// choose the country to view the hospital
function onCountryChoose () {
    var countryValue = $("#countryHospitalSelect").val();
    console.log(detailMarker);
    if (detailMarker === undefined) {
        detailMarker = new google.maps.Marker({ map: detailMap});
    }
    if (countryValue === "vn") {
       // detailMarker = new google.maps.Marker({position: hcmHospital, map: detailMap});
        detailMarker.setPosition(hcmHospital);
        detailMap.setCenter(detailMarker.getPosition());
        getCountryBound("Vietnam", detailMap);
        addInfoWindow(detailMarker, detailMap, hcmHospitalName);
    }else if (countryValue === "us"){ 
     //   detailMarker = new google.maps.Marker({position: texasHospital, map: detailMap});
        detailMarker.setPosition(texasHospital);

        detailMap.setCenter(detailMarker.getPosition());
        getCountryBound("US", detailMap);
        addInfoWindow(detailMarker, detailMap, texasHospialName);
    }else if (countryValue === "aus") {
     //   detailMarker = new google.maps.Marker({position: ausHospital, map: detailMap});
        detailMarker.setPosition(ausHospital);

        detailMap.setCenter(detailMarker.getPosition());
        getCountryBound("Australia", detailMap);
        addInfoWindow(detailMarker, detailMap, ausHospitalName);
    }
}

// create infon view for each hospital
function addInfoWindow (hosMarker, detailMap, name) {
    var contentString = '<div id="content">'+ name +
      '<div class="btn btn-danger">Access</div>'
      '</div>'; 
    var infowindow = new google.maps.InfoWindow({
        content: contentString
      });

      hosMarker.addListener('click', function() {
        infowindow.open(detailMap, hosMarker);
      });
} 

// when the selector for hospital change, the country selector will be changed
function onChangeHospital () {
    var hospitalValue = $("#hospitalSelect").val();
    var countryValue = $("#countrySelect").val();
    if (hospitalValue === "hcmHospital") {
        if (countryValue !== "vn") $("#countrySelect").val("vn").change();
    }else if (hospitalValue === "texasHospital"){ 
        if (countryValue !== "us") $("#countrySelect").val("us").change();
    }else if (hospitalValue === "rmitHospital") {
        if (countryValue !== "aus") $("#countrySelect").val("aus").change();
    }
}

// when the selector for country change, the hospital selector will be changed
function onCountryChange () {
    var countryValue = $("#countrySelect").val();
    var hospitalValue = $("#hospitalSelect").val();
    if (countryValue === "vn") {
        if (hospitalValue !== "hcmHospital") $("#hospitalSelect").val("hcmHospital").change();
    }else if (countryValue === "us"){ 
        if (hospitalValue !== "texasHospital") $("#hospitalSelect").val("texasHospital").change();
    }else if (countryValue === "aus") {
        if (hospitalValue !== "rmitHospital") $("#hospitalSelect").val("rmitHospital").change();
    }
}

// get the focus view for country level
function getCountryBound (address, detailMap) {
    geocoder = new google.maps.Geocoder();
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == 'OK') {
        detailMap.setCenter(results[0].geometry.location);
        detailMap.fitBounds(results[0].geometry.viewport);
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    });
}

