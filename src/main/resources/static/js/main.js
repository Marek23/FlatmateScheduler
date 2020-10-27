const Http = new XMLHttpRequest();

function getRoles() {
    Http.open("GET", "../roles");
    Http.send();
    
    Http.onreadystatechange = (e) => {
        var resp = Http.responseText;

        $(".roles")[0].innerHTML = "<b>Role: </b>" + resp;
    }
}