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
      if(data[0] != null){printPeopleList(data);}
      else{window.alert("There isn't charts yet");}
  })
}

function printPeopleList(val){
    let output = document.getElementById("stats-output");
    let html = '';
    val.forEach((person, index) =>{

      var personId          = person.id         ;
      var personName        = person.name      ;
      var personBirthDate   = person.birthDate  ;
      var personBirthHour   = person.birthHour  ;
      var personBirthMinute = person.birthMinute;
      var personCity        = person.city      ;
      var personState       = person.state     ;



      
      
      
      
      
      
      



        html += `<tr>
            <td>${person.id}         </td>
            <td>${person.name}       </td>
            <td>${person.birthDate}  </td>
            <td>${person.birthHour}  </td>
            <td>${person.birthMinute}</td>
            <td>${person.city}       </td>
            <td>${person.state}      </td>
            <td>
              <button type="button" class="button green" id="edit"   onclick="openEditModal(${personId         },
                ${personName       },
                ${personBirthDate  },
                ${personBirthHour  },
                ${personBirthMinute},
                ${personCity       },
                ${personState      }
              )">edit</button>
              <button type="button" class="button red"   id="delete" onclick="deletePerson(${person.id})">delete</button>
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

const openModalFilled = () => document.getElementById('modalFilled')
    .classList.add('active')

const closeModalFilled = () => document.getElementById('modalFilled')
    .classList.remove('active')


document.getElementById('newChart')
    .addEventListener('click', openModal)

document.getElementById('cancel')
    .addEventListener('click', closeModal)

document.getElementById('save')
  .addEventListener('click', saveNewPerson) 

document.getElementById('cancelEdit')
  .addEventListener('click', closeModalFilled)

document.getElementById('saveEdit')
  .addEventListener('click', editPerson) 

function saveNewPerson(){
    calculateNewChart().then((data) => {
    cleanNewPersonForm();
    document.getElementById('modal').classList.remove('active')
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
  const id = person_id.data
  sendPersonDeleteToBackend(person_id).then((data) => {
    var printId = person_id;
    window.alert("Person with id: "+printId+" was deleted");
    window.location.pathname = "/people_list.html"
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

function openEditModal(id, name, birthDate, birthHour, birthMinute, city, state){
  document.getElementById('modalFilled').classList.add('active');
  document.getElementById("id_name"        ).value = person.id
  document.getElementById("id_birth_date"  ).value = person.birthDate
  document.getElementById("id_birth_hour"  ).value = person.birthHour
  document.getElementById("id_birth_minute").value = person.birthMinute
  document.getElementById("id_city"        ).value = person.city
  document.getElementById("id_state"       ).value = person.state
}


function editPerson(){
  editChart().then((data) => {
    //cleanNewPersonForm;
    //window.location.pathname = "/people_list.html"
    window.alert("Person with id: "+printId+" was edited");
    document.getElementById('modalFilled').classList.remove('active');
    
  })

}


async function editChart(person_id){
  const response = await fetch( "http://localhost:9090/astrology/v1/user/person/"+person_id,
  {
      method: "PUT",
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