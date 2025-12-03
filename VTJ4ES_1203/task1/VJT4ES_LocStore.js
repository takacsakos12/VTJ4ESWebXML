let jsonData = [];
let editIndex = null;

// --- JSON IMPORT ---
document.getElementById("file-in").addEventListener("change", function () {
    const file = this.files[0];
    const reader = new FileReader();

    reader.onload = function (e) {
        try {
            jsonData = JSON.parse(e.target.result);

            if (!Array.isArray(jsonData)) {
                alert("A JSON fájl nem tömb!");
                jsonData = [];
                return;
            }

            renderTable();
        } catch {
            alert("Hibás JSON fájl!");
        }
    };

    reader.readAsText(file);
});

// --- TÁBLÁZAT KIRAJZOLÁS ---
function renderTable() {
    const tbody = document.getElementById("table-body");
    tbody.innerHTML = "";

    if (jsonData.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" style="color:#888;">Nincsenek adatok…</td>
                <td>
                    <button class="btn btn-blue">Módosít</button>
                    <button class="btn btn-red">Töröl</button>
                </td>
            </tr>`;
        return;
    }

    jsonData.forEach((item, index) => {
        const row = `
            <tr>
                <td>${item.nev || ""}</td>
                <td>${item.evfolyam || ""}</td>
                <td>${item.szak || ""}</td>
                <td>${item.email || ""}</td>
                <td>${item.neptun || ""}</td>
                <td>
                    <button class="btn btn-blue" onclick="editRow(${index})">Módosít</button>
                    <button class="btn btn-red" onclick="deleteRow(${index})">Töröl</button>
                </td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}

// --- TÖRLÉS ---
function deleteRow(index) {
    jsonData.splice(index, 1);
    renderTable();
}

// --- MÓDOSÍTÁS ---
function editRow(index) {
    editIndex = index;
    const item = jsonData[index];

    document.getElementById("nev").value = item.nev;
    document.getElementById("evfolyam").value = item.evfolyam;
    document.getElementById("szak").value = item.szak;
    document.getElementById("email").value = item.email;
    document.getElementById("neptun").value = item.neptun;
}

// --- FORM MENTÉS ---
document.getElementById("save-btn").addEventListener("click", function () {
    const newItem = {
        nev: document.getElementById("nev").value,
        evfolyam: document.getElementById("evfolyam").value,
        szak: document.getElementById("szak").value,
        email: document.getElementById("email").value,
        neptun: document.getElementById("neptun").value
    };

    if (editIndex !== null) {
        jsonData[editIndex] = newItem;
        editIndex = null;
    } else {
        jsonData.push(newItem);
    }

    clearForm();
    renderTable();
});

// --- FORM ÜRÍTÉS ---
function clearForm() {
    document.getElementById("nev").value = "";
    document.getElementById("evfolyam").value = "";
    document.getElementById("szak").value = "";
    document.getElementById("email").value = "";
    document.getElementById("neptun").value = "";
}

// --- EXPORT: töltsön le a táblázatnak megfelelő JSON-t ---
document.getElementById("file-out").addEventListener("click", function () {
    const jsonStr = JSON.stringify(jsonData, null, 2);

    const blob = new Blob([jsonStr], { type: "application/json" });
    const url = URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;
    a.download = "hallgatok_export.json";
    a.click();

    URL.revokeObjectURL(url);
});



