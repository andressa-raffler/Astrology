const form = document.querySelector("form");
const inputName = document.querySelector(".name");
const inputEmail = document.querySelector(".email");
const inputPassword = document.querySelector(".password");
const apiUrl = "http://localhost:8090/astrology/v1/user/singn-up"


function newUser (){
    fetch(apiUrl,
    {
        headers:{
            'Accept':'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({
            name: inputName.value,
            email: inputEmail.value,
            password: inputPassword.value,

        })
    })
};

function clean(){
    inputName.value = "";
    inputEmail.value = "";
    inputPassword.value = ""
}

form.addEventListener('submit', function (event){
    event.preventDefault();
    newUser();
    clean();
    window.location.pathname = "/login.html" 
});


