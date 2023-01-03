const form = document.querySelector("form");
const inputLoginEmail = document.querySelector(".email");
const inputLoginPassword = document.querySelector(".password");

const API_URL = "http://localhost:9090/astrology/v1/user/login";

async function login(){
  const response = await fetch(API_URL,
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
    return response.json();
};


function clean(){
    inputLoginEmail.value = "",
    inputLoginPassword.value = ""
}

form.addEventListener('submit', function (event){
    event.preventDefault();
    login().then((data) => {window.localStorage.setItem('token', JSON.stringify(data.token));});    
    clean();
    window.location.pathname = "/new_chart.html"
});



