const form = document.querySelector("form");


const inputLoginEmail = document.querySelector(".login_email");
const inputLoginPassword = document.querySelector(".login_password");



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



function clean(){
    inputLoginEmail.value = "",
    inputLoginPassword.value = ""
}

form.addEventListener('submit', function (event){
    event.preventDefault();
    login();
    clean();
});

