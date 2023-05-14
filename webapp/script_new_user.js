const form = document.querySelector("form");
const inputName = document.querySelector(".name");
const inputEmail = document.querySelector(".email");
const inputPassword = document.querySelector(".password");
const apiUrl = "http://localhost:9090/astrology/v1/user/singn-up"


function newUser (){
    return fetch(apiUrl,
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
    .then(response => response.json())
    .then(data => {
        responseMessage.textContent = data.message;
        if(data.message.includes("created")){
            const messageElement = document.getElementById('message');
            messageElement.innerHTML = message;
            window.location.href = "/login.html" 
        }else{
            alert(data.message);
        }
    })
    .catch(error => {
        console.error(error);
        alert("Error creating new user");
        const messageElement = document.getElementById('message');
            messageElement.innerHTML = message;
        responseMessage.textContent = 'Ocorreu um erro ao criar o novo usuário.';
    });
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


