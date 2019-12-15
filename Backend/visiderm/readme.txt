This file contains the information for Postman test API and Database Configuration

1. Get all the patient information
API: http://localhost:5000/patient/all?page=1
Method: GET
Request parameter: page (optional). If you dont specify, the api will always return page 1
Each page contains 10 records

2. Get the patient list by patient Name.
API: http://localhost:5000/patient/name?patientName=Hoang Tran
Method: GET
Request parameter:
    page (optional). If you dont specify, the api will always return page 1
                     Each page contains 10 records
    patientName: this one is compulsory. You can specify the last name or first name or full name of patient

3. Get the patient detail
API: http://localhost:5000/patient/detail/{patientID}
Method: GET
Request parameter:
    patientID: this param is required. if the system does not found the patient with that ID, the system will
    return error

4. Add new patient
API: http://localhost:5000/patient/add
Method: POST
Request Body:
The API will require the information of lastname, first name, occupation, email, title,
birthday, gender, married, street address, suburb, country. Other fields such as home phone,
office phone, fax number next to kin name and contact info will be optional
{
	"lastName": "Hoang",
	"firstName": "Tran",
	"occupation": "student",
	"email": "admin@gmail.com",
	"title": "Mr",
	"birthDay": "2019-09-24",
	"gender": "M",
	"married": false,
	"streetAddress" : "702 Nguyen Van Linh",
	"suburd": "District 10",
	"country": "Vietnam",
	"homePhone": "",
	"officePhone": "",
	"mobilePhone": "",
	"faxNumber": "",
	"ntkName": "Dinh Minh",
	"ntkContactInfo": "0906486787"
}
or
{
    "lastName": "Hoang",
	"firstName": "Tran",
	"occupation": "student",
	"email": "admin@gmail.com",
	"title": "Mr",
	"birthDay": "2019-09-24",
	"gender": "M",
	"married": false,
	"streetAddress" : "702 Nguyen Van Linh",
	"suburd": "District 10",
	"country": "Vietnam",
}

5. Update new patient
API: http://localhost:5000/patient/add
Method: PUT
Request Body:
The API will require the information of lastname, first name, occupation, email, title,
birthday, gender, married, street address, suburb, country. Other fields such as home phone,
office phone, fax number next to kin name and contact info will be optional
{
	"lastName": "Hoang",
	"firstName": "Tran",
	"occupation": "student",
	"email": "hi",
	"title": "Mr",
	"birthDay": "2019-09-24",
	"gender": "M",
	"married": false,
	"streetAddress" : "702 Nguyen Van Linh",
	"suburd": "District 10",
	"country": "Vietnam",
	"homePhone": "",
	"officePhone": "",
	"mobilePhone": "",
	"faxNumber": "",
	"ntkName": "Dinh Minh",
	"ntkContactInfo": "0906486787"
}
or
{
    "lastName": "Hoang",
	"firstName": "Tran",
	"occupation": "student",
	"email": "admin@gmail.com",
	"title": "Mr",
	"birthDay": "2019-09-24",
	"gender": "M",
	"married": false,
	"streetAddress" : "702 Nguyen Van Linh",
	"suburd": "District 10",
	"country": "Vietnam",
}

==================================================================
Visit information
1. Add new visit
API: http://localhost:5000/visit/add
Method: POST
Request body:
{
	"patientID" : 1,
	"startTime": "09:00",
	"endTime": "11:00",
	"visitDate": "2019-12-13",
	"doctorName": "Minh Dinh",
	"clinic": "RMIT University",
	"visitReason": "Stress due to this course",
	"visitNote": "Should be HD",
	"lesionDate": "",
	"lesionTime": "",
	"lesionSize": "",
	"lesionLocation": "",
	"lesionNote": ""
}

2. Get visit information of patient
API: http://localhost:5000/visit/summary/{PatientID}
example : http://localhost:5000/visit/summary/1
Method: GET

3. Get visit detail of patient
API: http://localhost:5000/visit/detail/{visitID}
example: http://localhost:5000/visit/detail/1
Method: GET

4. Update visit information
API: http://localhost:5000/visit/update/{visitID}
Method: PUT
Request body:
{
	"patientID" : 1,
	"startTime": "09:00",
	"endTime": "11:00",
	"visitDate": "2019-12-13",
	"doctorName": "Minh Dinh",
	"clinic": "RMIT University",
	"visitReason": "Stress due to this course",
	"visitNote": "Should be HD",
	"lesionDate": "",
	"lesionTime": "",
	"lesionSize": "",
	"lesionLocation": "",
	"lesionNote": ""
}
