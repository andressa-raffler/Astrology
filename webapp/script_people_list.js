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
const apiUrl = "http://localhost:8090/astrology/v1/user/person"

window.onload = async function loadPeopleList() {
  await fetch(apiUrl, {
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
        html += `<tr>
            <td>${person.id}         </td>
            <td>${person.name}       </td>
            <td>${person.birthDate}  </td>
            <td>${person.birthHour}  </td>
            <td>${person.birthMinute}</td>
            <td>${person.city}       </td>
            <td>${person.state}      </td>
            <td>
              <button type="button" class="button orange"   id="open" onclick="openChart('${person.name}')">open</button>
              <button type="button" class="button green"    id="edit"   onclick="openEditModal('${person.id}','${person.name}','${person.birthDate}','${person.birthHour}','${person.birthMinute}','${person.city}','${person.state}' )">edit</button>
              <button type="button" class="button red"      id="delete" onclick="deletePerson(${person.id})">delete</button>
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
    window.location.pathname = "/people_list.html"
  })
}

async function calculateNewChart(){
  const response = await fetch( apiUrl,
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
  await fetch(apiUrl+"/"+person_id,
  {
      method: "DELETE",
      headers:{
          'Authorization': authToken,
          'Accept':'application/json',
          'Content-Type': 'application/json'
      },
  })
};

function openEditModal(id, name, birthdate, birthHour, birthMinute, city, state){
  document.getElementById('modalFilled').classList.add('active');
  document.getElementById("id_name"        ).value = name
  document.getElementById("id_birth_date"  ).value = birthdate
  document.getElementById("id_birth_hour"  ).value = birthHour
  document.getElementById("id_birth_minute").value = birthMinute
  document.getElementById("id_city"        ).value = city
  document.getElementById("id_state"       ).value = state
  window.localStorage.setItem('edited_person_id', id);
}


function editPerson(){
  person_id = window.localStorage.getItem('edited_person_id');
  editChart(person_id).then((data) => {
    window.alert("Person with id: "+person_id+" was edited");
    document.getElementById('modalFilled').classList.remove('active');
    window.localStorage.removeItem('edited_person_id');
    window.location.pathname = "/people_list.html"
    cleanNewPersonForm;
  })

}


async function editChart(person_id){
  const response = await fetch( apiUrl+"/"+person_id,
  {
      method: "PUT",
      headers:{
          'Authorization': authToken,
          'Accept':'application/json',
          'Content-Type': 'application/json'

      },
      body: JSON.stringify({
          name: document.getElementById("id_name").value ,
          birthDate:    document.getElementById("id_birth_date"  ).value ,
          birthHour:    document.getElementById("id_birth_hour"  ).value ,
          birthMinute:  document.getElementById("id_birth_minute").value ,
          city:         document.getElementById("id_city"        ).value ,
          state:        document.getElementById("id_state"       ).value 
      })
  })

return response.json();
};

function openChart(name){
  window.localStorage.setItem('chart_person_name', name);
  window.location.pathname = "/chart.html"
}