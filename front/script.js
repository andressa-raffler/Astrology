const form = document.querySelector("form");


const inputLoginEmail = document.querySelector(".login_email");
const inputLoginPassword = document.querySelector(".login_password");
const inputName = document.querySelector(".name");
const inputEmail = document.querySelector(".email");
const inputPassword = document.querySelector(".password");






//document.getElementById("buttons").onclick = button;
    // var buttons = document.querySelectorAll('.btn');
    // for (let i = 0; i < buttons.length; i++) {
    // buttons[i].onclick = function(event) {
    //     event.preventDefault();
    //     console.log('percorrendo');
    //     button(event);
    // }
    // }



function login (){
    fetch("http://localhost:9090/astrology/v1/user/login",
    {
        headers:{
            'Accept':'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({
            inputEmail: inputLoginEmail.value,
            inputPassword: inputLoginPassword.value

        })
    })
    .then(function(res){console.log(res)})
    .catch(function(res){console.log(res)})
    };

function newUser(){
    fetch("http://localhost:9090/astrology/v1/user/sign_up",
    {
        headers:{
            'Accept':'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({
            inputName: inputName.value,
            email: inputEmail.value,
            password: inputPassword.value

        })
    })
    .then(function(res){console.log(res)})
    .catch(function(res){console.log(res)})
    };

function clean(){
    // inputEmail.value = "",
    // inputPassword.value = "",
    // inputName.value = "",
    inputLoginEmail.value = "",
    inputLoginPassword.value = ""
}

form.addEventListener('submit', function (event){
    event.preventDefault();
    login();
    clean();
});

