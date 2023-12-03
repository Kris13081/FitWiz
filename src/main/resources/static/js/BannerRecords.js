document.querySelectorAll('.edit-btn').forEach(btn => {
    btn.addEventListener('click', editHandler);
});


function editHandler() {
// Get the row containing the clicked button
    const row = event.target.closest('tr');

    // Get the elements within the row
    const nameElement = row.querySelector('.name');
    const titleElement = row.querySelector('.title');
    const descriptionElement = row.querySelector('.text');
    const actionsElement = row.querySelector('.actions');

    // Create input elements with the same values as the current text
    const nameInput = createInput('text', 'name', nameElement.textContent);
    const titleInput = createInput('text', 'title', titleElement.textContent);
    const descriptionInput = createInput('text', 'text', descriptionElement.textContent);

    // Create Save and Undo buttons
    const saveButton = createButton('button', 'save-btn', 'Save', saveHandler);
    const undoButton = createButton('button', 'undo-btn', 'Undo', undoHandler);

    // Replace text with inputs
    replaceElement(nameElement, nameInput);
    replaceElement(titleElement, titleInput);
    replaceElement(descriptionElement, descriptionInput);

    // Replace Edit and Delete buttons with Save and Undo buttons
    replaceBtn(actionsElement.querySelector('.edit-btn'), saveButton);
    replaceBtn(actionsElement.querySelector('.delete-btn'), undoButton);

    saveButton.classList.add('btn', 'btn-success', 'btn-sm');
    undoButton.classList.add('btn', 'btn-dark', 'btn-sm');
}

function createInput(type, name, value) {
    const input = document.createElement('input');
    input.type = type;
    input.name = name;
    input.value = value;
    return input;
}

function createButton(type, id, text, clickHandler) {
    const button = document.createElement('button');
    button.type = type;
    button.id = id;
    button.textContent = text;
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

function saveHandler(event) {
    const row = event.target.closest('tr');
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    const id = row.querySelector('.id').textContent; // Assuming you have a cell with the class 'id'
    const name = row.querySelector('.name input').value;
    const title = row.querySelector('.title input').value;
    const text = row.querySelector('.text input').value;

    // Make an AJAX request to update the entity
    fetch(`/api/admins/management/banner/update/${id}`, {
        method: 'PUT', // or 'PATCH' depending on your backend
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken,
        },
        body: JSON.stringify({
            name: name,
            title: title,
            text: text,
            // Add other fields if needed
        }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error updating entity with ID ${id}`);
            }
            return response.json();
        })
        .then(data => {
            // Handle the response data, if needed
            console.log('Entity updated successfully:', data);

            alert(`Entity with ID ${id} is updated successfully!`);
            // Reload the page or update the table as needed
            location.reload();
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

