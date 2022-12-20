const form = document.querySelector("form");
const inputName = document.querySelector(".name");
const inputEmail = document.querySelector(".email");
const inputPassword = document.querySelector(".password");



function newUser (){
    fetch("http://localhost:9090/astrology/v1/user/singn-up",
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
    .then(function(res){console.log(res)})
    .catch(function(res){console.log(res)})
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

});


