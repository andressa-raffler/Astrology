const ul = document.getElementById("people");
const list = document.createDocumentFragment();
const authToken = window.localStorage.getItem("token");
const form = document.querySelector("form");
const inputName = document.querySelector(".name");
const inputBirthDate = document.querySelector(".birthDate");
const inputBirthHour = document.querySelector(".birthHour");
const inputBirthMinute = document.querySelector(".birthMinute");
const inputCity = document.querySelector(".city");
const inputSate = document.getElementById("state");
const stateSelectElement = document.getElementById('state');
const apiUrl = "http://localhost:9090/astrology/v1/user/person"

window.onload = function(){
  createStatesDropdown();
  loadPeopleList();
}

async function loadPeopleList() {
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
    const closeModal = () => {
      cleanNewPersonForm();
      document.getElementById('modal').classList.remove('active');
    };

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
      alert(data.message);
      if (data.message.includes("created")) {
        cleanNewPersonForm();
        document.getElementById('modal').classList.remove('active')
        window.location.pathname = "/people_list.html"}
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
          state: stateSelectElement.options[stateSelectElement.selectedIndex].text,
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

async function openEditModal(id, name, birthdate, birthHour, birthMinute, city, state) {
  createStatesDropdown();
  document.getElementById('modalFilled').classList.add('active');
  document.getElementById("id_name").value = name;
  document.getElementById("id_birth_date").value = birthdate;
  document.getElementById("id_birth_hour").value = birthHour;
  document.getElementById("id_birth_minute").value = birthMinute;
  document.getElementById("city").value = city;
  document.getElementById("state").value = state;
  window.localStorage.setItem('edited_person_id', id);

  const stateId = stateSelectElement.value;

  if (stateId) {
    const cities = await getCities(stateId);
    updateCitiesDropdown(cities);
    const citySelectElement = document.getElementById('city');
    citySelectElement.value = city;
  }
}



async function editPerson() {
  const personId = window.localStorage.getItem('edited_person_id');
  const stateId = stateSelectElement.value;
  const cityName = document.getElementById("city").value;
  const cityId = await getCityIdByName(cityName, stateId);

  if (stateId && cityId) {
    const requestBody = {
      name: document.getElementById("id_name").value,
      birthDate: document.getElementById("id_birth_date").value,
      birthHour: document.getElementById("id_birth_hour").value,
      birthMinute: document.getElementById("id_birth_minute").value,
      city: cityId, 
      state: stateId,
    };

    sendEditRequest(personId, requestBody).then((data) => {
      alert(data.message);
      if (data.message.includes("updated")) {
        document.getElementById('modalFilled').classList.remove('active');
        window.localStorage.removeItem('edited_person_id');
        window.location.pathname = "/people_list.html";
        cleanNewPersonForm();
      }
    });
  }
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
          city:         document.getElementById("city"        ).value ,
          state:        document.getElementById("state"       ).value 
      }),
  });
  const data = await response.json();
  return data;
}

function openChart(name){
  window.localStorage.setItem('chart_person_name', name);
  window.location.pathname = "/chart.html"
}

async function getStates() {
  const response = await fetch('https://servicodados.ibge.gov.br/api/v1/localidades/estados');
  const data = await response.json();
  return data;
}

async function createStatesDropdown() {
  const states = await getStates();
  const selectElement = document.getElementById('state');
  states.forEach(state => {
    const optionElement = document.createElement('option');
    optionElement.value = state.id;
    optionElement.textContent = state.nome;
    selectElement.appendChild(optionElement);
  });
}

async function getCities(stateId) {
  const response = await fetch(`https://servicodados.ibge.gov.br/api/v1/localidades/estados/${stateId}/municipios`);
  const data = await response.json();
  return data;
}

function updateCitiesDropdown(cities) {
  const selectElement = document.getElementById('city');
  selectElement.innerHTML = '';

  cities.forEach(city => {
    const optionElement = document.createElement('option');
    optionElement.value = city.nome; 
    optionElement.textContent = city.nome;
    selectElement.appendChild(optionElement);
  });
}

stateSelectElement.addEventListener('change', async () => {
  const stateId = stateSelectElement.value;
  const cities = await getCities(stateId);
  updateCitiesDropdown(cities);
});
