var advancedURL = "http://localhost:5000/user/validate";
var generalURL = "http://localhost:5000/user/validate";
var limitedURL = "http://localhost:5002/user/validate";
var validRole;

function getUser() {
	return document.getElementById("username").value;
}

function getPass() {
	return document.getElementById("password").value;
}

function getCredentials() {
	requiredRole = document.getElementById("server").value.toLowerCase();
	return {
		username: getUser(),
		password: getPass()
	};
}

function validateRole(roles) {
	roles.forEach(function(role, index) {
		if (role.name == requiredRole) 
			validRole = true;
	});
	if (!validRole)
		cry("Account doesn't have role to use this server");
	else {
		var previousPage = sessionStorage.getItem("currentPage");
		sessionStorage.setItem("currentRole",requiredRole);
		window.location.pathname = previousPage;
	}
		
}

function cry(msg="") {
	var element = document.getElementById("erroMess");
	element.style.display = "block";
	if (msg != "" && typeof(msg) != "object")
		element.innerHTML = "<strong>"+msg+"</strong>";
}

function externalValidate () {
	var server = sessionStorage.getItem("locationName");
	if (server === "aus") limitedURL = "http://localhost:5002/user/validate";
	else if (server === "vn")limitedURL = "http://localhost:5002/user/validate";
	else if (server === "us") limitedURL = "http://localhost:5002/user/validate";
	var input = {
		username: getUser(),
		password: getPass()
	};
	$.ajax ({
		type: "POST",
		url: limitedURL,
		data: JSON.stringify(input),
		contentType: "application/json",
		success: function(data) {
			sessionStorage.setItem("currentRole", "external");
			console.log(sessionStorage.getItem("externalNext"));
			var nextScreen = sessionStorage.getItem("externalNext");
			window.location.pathname = nextScreen;
		},
		error: function(data) {
			$("#erroMess").css("display", "block");
		}
	})
}



function advancedValidate() {
	post(advancedURL, getCredentials(), validateRole, cry)
}

function generalValidate () {
	var input = {
		username: getUser(),
		password: getPass()
	};
	$.ajax ({
		type: "POST",
		url: generalURL,
		data: JSON.stringify(input),
		contentType: "application/json",
		success: function(data) {
			sessionStorage.setItem("currentRole", "general");
			window.location.pathname = "/screen1.html";
		},
		error: function(data) {
			$("#erroMess").css("display", "block");
		}
	})
}

function post(server, input, onSuccess, onError) {
	$.ajax ({
		type: "POST",
		url: server,
		data: JSON.stringify(input),
		contentType: "application/json",
		success: function(data) {
			onSuccess(data);
		},
		error: onError
	})
}

function checkForExternal () {
	sessionStorage.setItem("externalNext", "/screen15.html");
	window.location.pathname = "/screen12.html";
}

function checkForSerach () {
	sessionStorage.setItem("externalNext", "/screen16.html");
}