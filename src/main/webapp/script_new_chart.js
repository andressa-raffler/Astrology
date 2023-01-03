const form = document.querySelector("form");
const inputName = document.querySelector(".name");
const inputBirthDate = document.getElementById("birthDate");
const inputBirthHour = document.querySelector(".birthHour");
const inputBirthMinute = document.querySelector(".birthMinute");
const inputCity = document.querySelector(".city");
const inputSate = document.querySelector(".state");
const authToken = window.localStorage.getItem('token');

async function newChart (){

    await fetch("http://localhost:9090/astrology/v1/person/",
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
    .then(function(res){console.log(res)})
    .catch(function(res){console.log(res)})
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
    console.log(authToken);
    newChart();
    console.log(authToken);
    clean();

});


