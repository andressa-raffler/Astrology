const form = document.querySelector("form");
const inputName = document.querySelector(".name");
const inputBirthDate = document.getElementById("birthDate");
const inputBirthHour = document.querySelector(".birthHour");
const inputBirthMinute = document.querySelector(".birthMinute");
const inputCity = document.querySelector(".city");
const inputSate = document.querySelector(".state");
const authToken = window.localStorage.getItem('token');


const API_URL = "http://localhost:9090/astrology/v1/user/person/";


async function newChart (){

    const response = await fetch( "http://localhost:9090/astrology/v1/user/person/",
    {
        method: "POST",
        headers:{
            'Authorization': authToken,
            'Accept':'application/json',
            'Content-Type': 'application/json'
            
        },

        body: JSON.stringify({
            name: inputName.value,
            birthDate: inputBirthDate.value,
            birthHour: inputBirthHour.value,
            birthMinute: inputBirthMinute.value,
            city: inputCity.value,
            state: inputSate.value
        })
    })
return response.json();
};

function clean(){
    inputName.value = "";
    inputBirthDate.value = "";
    inputBirthHour.value = "";
    inputBirthMinute.value = "";
    inputCity.value = "";
    inputSate.value = ""
};

form.addEventListener('submit', function (event){
    event.preventDefault();
    newChart().then((data) => {
        window.localStorage.setItem('person_name', inputName.value);
        window.location.pathname = "/chart.html"
        clean();
    })
});




