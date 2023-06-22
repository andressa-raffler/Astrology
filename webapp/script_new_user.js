const form = document.querySelector("form");
const inputName = document.querySelector(".name");
const inputEmail = document.querySelector(".email");
const inputPassword = document.querySelector(".password");
const inputValidationCode = document.querySelector(".validationCode");
const apiUrl = "http://localhost:9090/astrology/v1/user"


function newUser (){
    console.log('new user')
    return fetch(apiUrl + '/sign-up',
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
        alert(data.message);
        if(!data.message.includes("Error")){
            openModal();
        }
    })
};
// function clean(){
//     inputName.value = "";
//     inputEmail.value = "";
//     inputPassword.value = ""
// }

function openModal() {
    const modal = document.getElementById('modal');
    modal.style.display = "block";
}

function closeModal() {
    const modal = document.getElementById('modal');
    modal.style.display = "none";
}

function sendValidationCode() {
    const validationCode = inputValidationCode.value;
    fetch(apiUrl + '/email_validate', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({
            validationCode: validationCode
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.message === "OK") {
            closeModal();
        } else {
            const errorElement = document.createElement('p');
            errorElement.textContent = data.message;
            const modalContent = document.querySelector('.modal-content');
            modalContent.appendChild(errorElement);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });


form.addEventListener('submit', function (event){
    //event.preventDefault();
    newUser();
    console.log('add event listener')
   // clean();
    
});

document.getElementById('signUp').addEventListener('click', function () {
    form.submit();
    console.log('entrou');
});

document.querySelector('.close').addEventListener('click', function () {
    closeModal();
});

document.getElementById('sendValidationCode').addEventListener('click', function () {
    sendValidationCode();
});

}