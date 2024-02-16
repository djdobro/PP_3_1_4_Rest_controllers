async function updateUserData(idx) {
    document.getElementById(`edit-user-button${idx}`).addEventListener('click', async () => {
        const inputId = document.getElementById(`eId${idx}`);
        const inputName = document.getElementById(`eName${idx}`);
        const inputLastName = document.getElementById(`eLastname${idx}`);
        const inputAge = document.getElementById(`eAge${idx}`);
        const inputEmail = document.getElementById(`eEmail${idx}`)
        const inputPassword = document.getElementById(`ePassword${idx}`)

        const id = inputId.value;
        const name = inputName.value;
        const lastname = inputLastName.value;
        const age = inputAge.value;
        const email = inputEmail.value;
        const password = inputPassword.value;
        const listRoleEditUser = roleArray(document.getElementById(`eRoles${id}`)).map(role => ({
            ...role,
            name: `ROLE_${role.name}`,
        }));

        if (id && name && lastname && age && email && password) {
            const res = await fetch("http://localhost:8080/api", {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({id, name, lastname, age, email, password, roles: listRoleEditUser})
            });

            const result = await res.json();
            document.getElementById(`user${id}`).remove();
            $(`#edit${id}`).modal('toggle');

            document.getElementById(`delete${id}`).remove();
            document.getElementById(`edit${id}`).remove();

            await addUser(result);

            console.log(document.getElementById(`navBarId${id}`))
            console.log(`navBarId${id}`)
            if (document.getElementById(`navBarId${id}`) !== null) {
                editBar(result)
            }
        }
    })
}

function editBar({id, name, roles}) {
    console.log(id);
    console.log(roles);
    document.getElementById(`navBar`).remove();
    const tbody = document.getElementById('navBarUser');

    let strRole = ' '
    roles.forEach((role) => {
        console.log(`${role.name}`);
        strRole += role.name;
    })

    let textBar = `
        <ul class="navbar-nav mr-auto" id="navBar">
            <li class="nav-item">
                <span id="navBarId${id}" class="navbar-text text-white font-weight-bold"
                ><strong>${name}</strong>
                </span>
            </li>
            <li class="nav-item">
                <span class="navbar-text text-white"
                      >&nbspwith roles:&nbsp
                </span>
            </li>
            <li class="nav-item">
                <span class="navbar-text text-white">${strRole}
                </span>
            </li>
        </ul>
    `
    tbody.insertAdjacentHTML('beforeend', textBar)
}

$(document).ready(function () {
    $('#usersTable').show();
    $('#newUser').hide();
});

$('a[href="#newUser"]').click(function () {
    $('#usersTable').hide();
    $('#newUser').show();
});

$('a[href="#usersTable"]').click(function () {
    $('#newUser').hide();
    $('#usersTable').show();
});