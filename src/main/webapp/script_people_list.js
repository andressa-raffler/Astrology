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


window.onload = async function loadPeopleList() {
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
    printPeopleList(data);
  })
}

function printPeopleList(val){
    let output = document.getElementById("stats-output");
    let html = '';
    val.forEach((person, index) =>{
        var id =  person.id;
        html += `<tr>
            <td>${person.id}       </td>
            <td>${person.name}       </td>
            <td>${person.birthDate}  </td>
            <td>${person.birthHour}  </td>
            <td>${person.birthMinute}</td>
            <td>${person.city}       </td>
            <td>${person.state}      </td>
            <td>
              <button type="button" class="button green edit" id="${person.id}">edit</button>
              <button type="button" class="button red" id="delete" onclick="deletePerson(${id})">delete</button>
            </td>
            </tr>
        `;
    })
    output.innerHTML = html;
}

const openModal = () => document.getElementById('modal')
    .classList.add('active')

const closeModal = () => document.getElementById('modal')
    .classList.remove('active')

document.getElementById('newChart')
    .addEventListener('click', openModal)

document.getElementById('cancel')
    .addEventListener('click', closeModal)

document.getElementById('save')
  .addEventListener('click', saveNewPerson) 

function saveNewPerson(){
  calculateNewChart().then((data) => {
    cleanNewPersonForm();
    closeModal;
    window.location.pathname = "/people_list.html"
    //"alert('did stuff inline');"
  })
}

async function calculateNewChart(){
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

function cleanNewPersonForm(){
  inputName.value = "";
  inputBirthDate.value = "";
  inputBirthHour.value = "";
  inputBirthMinute.value = "";
  inputCity.value = "";
  inputSate.value = "";
};

function deletePerson(person_id){
  console.log(person_id)
  var id = person_id.data
  console.log(id)
  sendPersonDeleteToBackend(person_id).then((data) => {
    console.log('dentro do send')
    //window.location.pathname = "/people_list.html"
  })
}

async function sendPersonDeleteToBackend(person_id){
  await fetch("http://localhost:9090/astrology/v1/user/person/"+person_id,
  {
      method: "DELETE",
      headers:{
          'Authorization': authToken,
          'Accept':'application/json',
          'Content-Type': 'application/json'
      },
  })
};

