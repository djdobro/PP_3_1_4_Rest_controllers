document.getElementById('addNewUser').addEventListener('click', createUser)

async function createUser() {
    // Retrieving input values
    const inputName = document.getElementById('newName').value;
    const inputLastName = document.getElementById('newLastName').value;
    const inputAge = document.getElementById('newAge').value;
    const inputEmail = document.getElementById('newEmail').value;
    const inputPassword = document.getElementById('newPassword').value;

    // Assigning input values to variables
    const name = inputName;
    const lastname = inputLastName;
    const age = inputAge;
    const email = inputEmail;
    const password = inputPassword;

    // Retrieving selected roles from a dropdown list
    const listRoles = Array.from(document.getElementById('newRoles').options)
        .filter(option => option.selected)
        .map(option => ({
            id: option.value,
            name: `ROLE_${option.text}`,
        }));

    // Checking if all required fields are filled
    if (name && lastname && age && email && password && listRoles.length !== 0) {
        // Sending a POST request to the API endpoint
        const res = await fetch("http://localhost:8080/api", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({name, lastname, age, email, password, roles: listRoles})
        });
        const result = await res.json();
        addUser(result);
    }

    // Clearing input fields
    document.getElementById('newName').value = '';
    document.getElementById('newLastName').value = '';
    document.getElementById('newAge').value = '';
    document.getElementById('newEmail').value = '';
    document.getElementById('newPassword').value = '';
}

let roleArray = (options) => {
    let array = []
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {
                id: options[i].value,
                name: options[i].text,
            }
            array.push(role)
        }
    }
    return array
}