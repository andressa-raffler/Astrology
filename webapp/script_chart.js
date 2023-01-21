const ul = document.getElementById("planets");
const list = document.createDocumentFragment();
const person_name = window.localStorage.getItem("chart_person_name");
window.localStorage.removeItem('chart_person_name');
const API_URL =
  "http://localhost:9090/astrology/v1/user/person/zodiac-chart/" + person_name;
const authToken = window.localStorage.getItem("token");

window.onload = async function loadChart() {
  await fetch(API_URL, {
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
      let planets = data;
      console.log(Array.isArray(planets));
      planets.map(function (planet) {
        let li = document.createElement("li");
        let name = document.createElement("h2");
        let position = document.createElement("h3");

        name.innerHTML       = `${planet.name}`;
        position.innerHTML   = `${planet.position}`;

        li.appendChild(name);
        li.appendChild(position);
        
        list.appendChild(li);
        ul.appendChild(list);
        console.log(ul);
      });
    })
    .catch(function (error) {
      console.log(error);
    });
};
