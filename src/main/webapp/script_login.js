const form = document.querySelector("form");
const inputLoginEmail    = document.getElementById("login_email");
const inputLoginPassword = document.getElementById("login_password");

const API_URL = "http://localhost:9090/astrology/v1/user/login";

async function loginAPI(){
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
    loginAPI().then((data) => { 
         window.localStorage.setItem('token', data.token);
         clean();
         window.location.pathname = "/menu.html"   
    });    
});



