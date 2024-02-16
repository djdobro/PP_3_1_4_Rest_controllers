async function deleteUserData(id) {

    document.getElementById(`delete-user-button${id}`).addEventListener('click', async () => {
        const res = await fetch(`http://localhost:8080/api/${id}`, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
            }
        });
        document.getElementById(`user${id}`).remove();

        $(`#delete${id}`).modal('toggle');
        document.getElementById(`delete${id}`).remove();
        document.getElementById(`edit${id}`).remove();
    })
}