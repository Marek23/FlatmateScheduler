function getRoles() {
    var req = new XMLHttpRequest();
    req.open("GET", "../roles");
    req.send();
    
    req.onreadystatechange = (e) => {
        var resp = req.responseText;

        $(".roles")[0].innerHTML = "<b>Role: </b>" + resp;
    }
}

function toggleSettlementPayments(span) {
    var id = $(span).attr("id");
    var p = document.getElementById("p-" + id);
    if (p.style.display === "none") {
        p.style.display = "";
        $("span#" + id).replaceWith(
            '<span class="fas-stack fa-lg" onclick="toggleSettlementPayments(this)" id="' + id + '"><i class="fas fa-arrow-up"></i></span>'
        );
    } else {
        p.style.display = "none";
        $("span#" + id).replaceWith(
            '<span class="fas-stack fa-lg" onclick="toggleSettlementPayments(this)" id="' + id + '"><i class="fas fa-arrow-down"></i></span>'
        );
    }
}
