<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <%--    <link rel="stylesheet" href="/resources/demos/style.css">--%>
    <script
            src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

    <script>
        $(function() {
            var year

            $("#pickdate").datepicker({
                firstDay: 1 ,
                showWeek: true,

                // onSelect: function (dateText, inst) {
                //     alert($.datepicker.iso8601Week(new Date(dateText)))
                //     return $.datepicker.iso8601Week(new Date(dateText))
                // }


                onSelect: function(dateText, inst) {
                    var date = new Date(dateText)
                    $(this).val($.datepicker.iso8601Week(date));
                    // year = new Date(dateText).getFullYear()
                    // document.getElementById("year").innerHTML = year.toString();
                    // var date = $(this).datepicker("getDate");
                    // $("#placeholder").text(date.getFullYear());
                    document.getElementById('year').value = date.getFullYear();


                }
            })






        });

    </script>
</head>
<body>

<form:form name="rota" modelAttribute="rota" action="view/" method="POST" >

    <form:label path="weekNum">Select the rota you would like to view  </form:label>
    <form:input path="weekNum" id="pickdate" cssClass="form-control" ></form:input>

    <form:label path="year" >for the year</form:label>
    <form:input path="year" id="year" readonly="true"></form:input>

    <input type="submit" value="Submit" />
</form:form>


</body>
</html>