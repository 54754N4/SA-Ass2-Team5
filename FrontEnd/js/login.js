var advancedURL = "http://localhost:5000/user/validate";
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
	else 
		alert("proceed to login");
}

function cry(msg="") {
	var element = document.getElementById("erroMess");
	element.style.display = "block";
	if (msg != "" && typeof(msg) != "object")
		element.innerHTML = "<strong>"+msg+"</strong>";
}

function advancedValidate() {
	post(advancedURL, getCredentials(), validateRole, cry)
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