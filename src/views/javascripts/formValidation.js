function validateForm(){
    var x=document.forms["playerName"]["player1Name"].value;
    if (x==null || x=="" || x.match(/\W/)){
        alert("Invalid name. Please use only numbers and letters, with no spaces.");
        return false;
    }
}