const form = document.querySelector("form");


const inputLoginEmail = document.querySelector(".email");
const inputLoginPassword = document.querySelector(".password");



function login (){
    fetch("http://localhost:9090/astrology/v1/user/login",
    {
        headers:{
            'Accept':'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({
            email: inputLoginEmail.value,
            password: inputLoginPassword.value,
            credentials: 'include',
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



    
  //  clean();
});