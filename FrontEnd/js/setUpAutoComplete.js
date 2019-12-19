var baseURL = "http://localhost:5000";
function setUpACForVisitView () {
    $(function () {
        $("#patientName").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url:  baseURL + "/patient",
                    dataType: "json",
                    data: "",
                    success: function (data) {
                        console.log (data);
                        response($.map(data, function (item) {
                            console.log(item);
                            return {
                                label : item.patientName,
                                value: item.patientName,
                                data: item.pateitnName
                            };
                        }));
                    }
                });
            },
            appendTo: "",
            minLength: 1,
            select: function (event, ui) {
                
            }
        })
    })
}