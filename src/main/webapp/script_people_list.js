const ul = document.getElementById("people");
const list = document.createDocumentFragment();
const authToken = window.localStorage.getItem("token");
const form = document.querySelector("form");
const inputName = document.querySelector(".name");
const inputBirthDate = document.querySelector(".birthDate");
const inputBirthHour = document.querySelector(".birthHour");
const inputBirthMinute = document.querySelector(".birthMinute");
const inputCity = document.querySelector(".city");
const inputSate = document.querySelector(".state");

window.onload = async function loadChart() {
  await fetch("http://localhost:9090/astrology/v1/user/person", {
    method: "GET",
    headers: {
      Authorization: authToken,
      Accept: "application/json",
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      let birthNames = data;
      let birthDates = data;
      birthNames.map(function (birthName) {

        let li = document.createElement("li");
        let name = document.createElement("h2");
        let birthDate = document.createElement("h3");
        let birthHour = document.createElement("h3");
        let birthMinute = document.createElement("h3");
        let city = document.createElement("h3");
        let state = document.createElement("h3");

        name.innerHTML         = `${birthName.name}`        ;
        birthDate.innerHTML    = `${person.birthDate}`   ;
        birthHour.innerHTML    = `${person.birthHour}`   ;
        birthMinute.innerHTML  = `${person.birthMinute}` ;
        city.innerHTML         = `${person.city}`        ;
        state.innerHTML        = `${person.state}`       ;

        li.appendChild(name);
        li.appendChild(birthDate);
        li.appendChild(birthHour);
        li.appendChild(birthMinute);
        li.appendChild(city);
        li.appendChild(state);

        list.appendChild(li);
        ul.appendChild(list);

      });


    })


    .catch(function (error) {
      console.log(error);
    });
};







const openModal = () => document.getElementById('modal')
    .classList.add('active')

const closeModal = () => document.getElementById('modal')
    .classList.remove('active')



document.getElementById('newChart')
    .addEventListener('click', openModal)

document.getElementById('modalClose')
    .addEventListener('click', closeModal)

document.getElementById('save')
  .addEventListener('click', save)    

function save(){
  newChart().then((data) => {
    clean();
    closeModal;
    window.location.pathname = "/people_list.html"
  })
}

function clean(){
  inputName.value = "";
  inputBirthDate.value = "";
  inputBirthHour.value = "";
  inputBirthMinute.value = "";
  inputCity.value = "";
  inputSate.value = "";
};



async function newChart(){
  
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


