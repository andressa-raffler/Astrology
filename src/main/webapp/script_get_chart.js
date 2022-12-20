const url = "http://localhost:9090/astrology/v1/person/zodiac-chart";

const loadingElement = document.querySelector("#loading");
const chartsContainer = document.querySelector("#charts-container");

async function getAllCharts(){
    const response = await fetch(url);
    const data = await response.json();
    
    loadingElement.classList.add("hide");

    data.map((chart) => {
        const div = document.createElement("div")
        const title = document.createElement("h2")
        const body = document.createElement("p")
        const link = document.createElement("a")

        title.innerText = chart.title;
        body.innerText = chart.body;
        link.innerText = "Read";
        link.setAttribute("href", '/chart.html?id=${chart.id}');

        div.appendChild(title);
        div.appendChild(body);
        div.appendChild(link);

        chartsContainer(div);

    })

    getAllCharts();

}