document.querySelectorAll('.user-edit-btn').forEach(btn => {
    btn.addEventListener('click', editUserHandler);
});

function editUserHandler(event) {
    // Get the row containing the clicked button
    const row = event.target.closest('tr');

    // Get the elements within the row
    const usernameElement = row.querySelector('.username');
    const emailElement = row.querySelector('.email');
    const rolesElement = row.querySelector('.roles'); // Assuming you have a cell with the class 'roles'
    const actionsElement = row.querySelector('.actions');

    // Create input elements with the same values as the current text
    const usernameInput = createInput('text', 'username', usernameElement.textContent);
    const emailInput = createInput('text', 'email', emailElement.textContent);

    // Extract the current roles from the rolesElement (assuming roles are represented as a comma-separated string)
    const currentRoles = rolesElement.textContent.trim().split(', ');

    // Create a select element for roles
    const rolesSelect = createRolesSelect(currentRoles);

    // Create Save and Undo buttons
    const saveUserButton = createButton('button', 'save-user-btn', 'Save', saveUserHandler);
    const undoUserButton = createButton('button', 'undo-user-btn', 'Undo', undoHandler);

    // Replace text with inputs
    replaceElement(usernameElement, usernameInput);
    replaceElement(emailElement, emailInput);
    replaceElement(rolesElement, rolesSelect); // Replace roles with the select element

    // Replace Edit and Delete buttons with Save and Undo buttons
    replaceBtn(actionsElement.querySelector('.user-edit-btn'), saveUserButton);
    replaceBtn(actionsElement.querySelector('.delete-btn'), undoUserButton);

    saveUserButton.classList.add('btn', 'btn-success', 'btn-sm');
    undoUserButton.classList.add('btn', 'btn-dark', 'btn-sm');
    usernameInput.classList.add('w-100');
    emailInput.classList.add('w-100');
    actionsElement.classList.add('align-items-center');
}

function createRolesSelect(currentRoles) {
    const select = document.createElement('select');
    select.name = 'roles';
    select.multiple = true;
    select.classList.add('form-select', 'w-100');

    // Assuming you have a list of available roles
    const availableRoles = ['USER', 'ADMIN'];

    availableRoles.forEach(role => {
        const option = document.createElement('option');
        option.value = role;
        option.text = role;
        if (currentRoles.includes(role)) {
            option.selected = true;
        }
        select.appendChild(option);
    });

    return select;
}

function createInput(type, name, value) {
    const input = document.createElement('input');
    input.type = type;
    input.name = name;
    input.value = value;
    return input;
}

function createButton(type, id, content, clickHandler) {
    const button = document.createElement('button');
    button.type = type;
    button.id = id;
    button.textContent = content;
    button.addEventListener('click', clickHandler);
    return button;
}

function replaceElement(oldElement, newElement) {
    oldElement.innerHTML = ''; // Clear the contents of the old element
    oldElement.appendChild(newElement); // Append the new element inside the old element
}

function replaceBtn(oldElement, newElement) {
    oldElement.replaceWith(newElement);
}

function saveUserHandler(event) {
    const row = event.target.closest('tr');
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    const id = row.querySelector('.id').textContent; // Assuming you have a cell with the class 'id'
    const username = row.querySelector('.username input').value;
    const email = row.querySelector('.email input').value;
    const role = row.querySelector('.roles select').value;

    // Make an AJAX request to update the entity
    fetch(`/api/admins/management/user/update/${id}`, {
        method: 'PUT', // or 'PATCH' depending on your backend
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken,
        },
        body: JSON.stringify({
            username: username,
            email: email,
            role: role,
        }),
    })
        .then(response => {
            if (response.ok) {
                // Handle success
                alert(`User with ID ${id} is updated successfully!`);
                location.reload();
            } else {
                // Handle error
                return response.text().then(errorMessage => {
                    console.error('Error updating entity:', errorMessage);
                    alert(`Error updating entity with ID ${id}`);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle errors, e.g., show an alert
            alert(`Error updating entity with ID ${id}`);
        });
}

function undoHandler() {
    location.reload();
}

