const ul = document.getElementById("people");
const list = document.createDocumentFragment();
const API_URL =
  "http://localhost:9090/astrology/v1/user/person";
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
      let people = data;
      people.map(function (person) {
        let li = document.createElement("li");
        let name = document.createElement("h2");
        let link = document.createElement("a");

        name.innerHTML  = `${person.name}`;
        link.innerHTML  = "/lougout.html";

        li.appendChild(name);
        li.appendChild(link);
        
        list.appendChild(li);
        ul.appendChild(list);
      });
    })
    .catch(function (error) {
      console.log(error);
    });
};
