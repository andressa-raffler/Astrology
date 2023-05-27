const table = document.getElementById("planets");
const descriptionParagraph = document.getElementById("short_description");
let person_name;
person_name = window.localStorage.getItem("chart_person_name");
window.localStorage.removeItem("chart_person_name");
const API_URL = "http://localhost:9090/astrology/v1/user/person/zodiac-chart";
const authToken = window.localStorage.getItem("token");

loadChart(API_URL, authToken, person_name);

async function loadChart(API_URL, authToken, person_name) {
  try {
    const response = await fetch(API_URL + "/" + person_name, {
      method: "GET",
      headers: {
        Authorization: authToken,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    });

    if (response.ok) {
      const data = await response.json();
      const planets = data.htmlRowList;
      const short_description = data.shortDescription

      // Create table header row
      let headerRow = document.createElement("tr");
      let planetHeader = document.createElement("th");
      let zodiacHeader = document.createElement("th");
      let houseHeader = document.createElement("th");
      let degreeHeader = document.createElement("th");

      planetHeader.innerHTML = "Planet";
      zodiacHeader.innerHTML = "Zodiac";
      houseHeader.innerHTML =  "House";
      degreeHeader.innerHTML = "Degree";

      headerRow.appendChild(planetHeader);
      headerRow.appendChild(zodiacHeader);
      headerRow.appendChild(houseHeader);
      headerRow.appendChild(degreeHeader);

      table.appendChild(headerRow);

      planets.forEach(function (planet) {
        let row = document.createElement("tr");
        let planetCell = document.createElement("td");
        let zodiacCell = document.createElement("td");
        let houseCell = document.createElement("td");
        let degreeCell = document.createElement("td");

        planetCell.innerHTML = planet.planet;
        zodiacCell.innerHTML = planet.zodiac;
        houseCell.innerHTML = planet.house;
        degreeCell.innerHTML = planet.degree;

        row.appendChild(planetCell);
        row.appendChild(zodiacCell);
        row.appendChild(houseCell);
        row.appendChild(degreeCell);

        table.appendChild(row);
      });
    } else {
      console.error(response.status);
      alert("Error retrieving astrology chart");
    }
  } catch (error) {
    console.error(error);
    alert("Error retrieving astrology chart");
  }
}
