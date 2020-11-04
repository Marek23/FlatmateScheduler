function getRoles() {
    var req = new XMLHttpRequest();
    req.open("GET", "../roles");
    req.send();
    
    req.onreadystatechange = (e) => {
        var resp = req.responseText;

        $(".roles")[0].innerHTML = "<b>Role: </b>" + resp;
    }
}

var addPayments = function (response, id) {
    var $row = $('<tr/>');
    var $td  = $('<td/>');
    $td.attr('colspan',6);
    $row.attr('id', 'payments-' + id);

    var $table = $('<table/>');
    var $thead = $('<thead/>');
    var $tr    = $('<td>Kto</td><td>Ile</td><td>Kiedy</td><td>Status</td>');

    $table.addClass("table table-bordered table-striped");

    $thead.append($tr);
    $table.append($thead);

    var $tbody = $('<tbody/>')
    for(var i=0; i<response.length; i++)
    {
        var p = response[i];

        $tr = $('<tr/>');
        $tr.append( '<td>' + p.resident.email + '</td>' );
        $tr.append( '<td>' + p.amount         + '</td>' );
        $tr.append( '<td>' + p.date           + '</td>' );
        $tr.append( '<td>' + p.status         + '</td>' );

        $tbody.append($tr);
    }

    $table.append($tbody);
    $td.append($table);

    $row.append($td);

    $("tr#" + "tr-" + id).after($row);
}

function showPayments(span) {
    var id = $(span).attr("id");

    var req = new XMLHttpRequest();

    req.responseType = 'json';
    req.open("GET", "/payments/settlement/" + id);
    req.send();

    req.onreadystatechange = (e) => {
        if (req.readyState == 4 && req.status == 200)
        {
            var json = req.response;
            addPayments(json, id);
        }
        $("span#" + id).replaceWith(
            '<span class="fas-stack fa-lg" onclick="hidePayments(this)" id="' + id + '"><i class="fas fa-arrow-up"></i></span>'
        );
    }
}

function hidePayments(span) {
    var id = $(span).attr("id");

    $("span#" + id).replaceWith(
        '<span class="fas-stack fa-lg" onclick="showPayments(this)" id="' + id + '"><i class="fas fa-arrow-down"></i></span>'
    );

    $("tr#"+ "payments-" + id).remove();
}

function pay(span) {
    var cId   = $(span).attr("id").split("-");
    var resId = cId[0];
    var stlId = cId[1];

    var req = new XMLHttpRequest();

    req.responseType = 'json';
    req.open("GET", "/payments/pay/" + resId + "/" + stlId);
    req.send();

    req.onreadystatechange = (e) => {
        if (req.readyState == 4 && req.status == 200)
        {
            var p    = req.response;
            var trId = "tr-" + p.id.residentId + "-" + p.id.settlementId;
            $("tr#" + trId).remove();
        }
    }
}