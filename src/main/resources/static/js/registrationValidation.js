let name = document.getElementById("name");
let surname = document.getElementById("surname");
let username = document.getElementById("username");
let password1 = document.getElementById("password1");
let password2 = document.getElementById("password2");
let button_created = false;

/*
<div class="form-group">
                    <button id="registerbutton" type="submit" class="btn btn-primary btn-block">Register</button>
                </div>
 */

function createButton(){
    let div = document.createElement("DIV");
    div.class = "form-group"

    let button = document.createElement("BUTTON");
    button.id = "registerbutton";
    button.type = "submit";
    button.className = "btn btn-primary btn-block";
    button.innerHTML = "Register";

    div.appendChild(button);;
    document.getElementById("form").appendChild(div);
}

function showRegisterButton(){
    document.getElementById("registerbutton").hidden = false;
}

function hideRegisterButton(){
    document.getElementById("registerbutton").hidden = true;
}

function validPassword(pass, pass2){
    if(/^[a-zA-Z0-9_-_]+$/.test(pass.value) && pass.value == pass2.value && pass.value.length >= 8 && pass.value.length <= 15){
        pass.style.color = "green";
        pass2.style.color = "green";
        return true;
    }
    pass.style.color = "red";
    pass2.style.color = "red";
    return false;
}

function validName(name){
    if(/^[a-zA-Z]+$/.test(name.value)){
        name.style.color = "green";
        return true;
    }
    name.style.color = "red";
    return false;
}

function validSurname(surname){
    if(/^[a-zA-Z]+$/.test(surname.value)){
        surname.style.color = "green";
        return true;
    }
    surname.style.color = "red";
    return false;
}

function validUsername(username){
    if(/^[a-zA-Z0-9_-_]+$/.test(username.value)){
        username.style.color = "green";
        return true;
    }
    username.style.color = "red";
    return false;
}

function checkIfOk(){
    if(validName(name)  && validSurname(surname) && validUsername(username) && validPassword(password1,password2)){
        if(!button_created){
            createButton();
            button_created = true;
        }
        showRegisterButton();
    } else {
        if(button_created)
            hideRegisterButton();
    }
}

name.addEventListener("input", checkIfOk);
surname.addEventListener("input", checkIfOk);
username.addEventListener("input",checkIfOk);
password1.addEventListener("input", checkIfOk);
password2.addEventListener("input", checkIfOk);